package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.AudioUtil;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.character.*;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.CardVO;
import org.dizem.sanguosha.model.vo.CharacterVO;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.dialog.ChooseCharacterDialog;
import org.dizem.sanguosha.view.gameview.GameFrame;
import org.dizem.sanguosha.view.gameview.OtherPlayerPane;

import javax.xml.bind.SchemaOutputResolver;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: DIZEM
 * Time: 11-4-6 下午1:40
 */
public class GameClient {

	private static Logger log = Logger.getLogger(GameClient.class);

	/**
	 * 服务器地址
	 */
	private String serverAddress;
	/**
	 * 服务器端口
	 */
	private int serverPort;
	/**
	 * 玩家姓名
	 */
	private String name;
	/**
	 * 消息接收监控
	 */
	private GameClientMonitor clientMonitor;
	/**
	 * 游戏界面
	 */
	private GameFrame gameFrame;
	/**
	 * 玩家数
	 */
	private int playerCount;
	/**
	 * 主公id
	 */
	private int lordId;
	/**
	 * 当前玩家id
	 */
	private int playerId;
	/**
	 * 玩家列表
	 */
	public Player[] players;

	/**
	 * 构造函数
	 *
	 * @param serverPort	服务器端口
	 * @param serverAddress 服务器地址
	 * @param name		  玩家姓名
	 */
	public GameClient(int serverPort, String serverAddress, String name) {
		this.serverPort = serverPort;
		this.serverAddress = serverAddress;
		this.name = name;
		clientMonitor = new GameClientMonitor(this);
		clientMonitor.start();
		connect();
	}

	/**
	 * 连接服务器
	 */
	public void connect() {
		//等待创建监控连接
		while (!clientMonitor.isReady()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}
		send(new SGSPacket(OP_CONNECT, name, clientMonitor.getClientPort()));
	}

	/**
	 * 显示游戏窗体
	 *
	 * @param packet
	 */
	public void showGameFrame(SGSPacket packet) {
		playerCount = packet.getPlayerCount();
		gameFrame = new GameFrame(this, name);
		gameFrame.setCurrentPlayerID(packet.getPlayerId());
	}

	/**
	 * 添加玩家
	 *
	 * @param packet
	 */
	public void updatePlayers(SGSPacket packet) {

		AudioUtil.play("system/add-player.mp3");
		gameFrame.updatePlayers(packet.getPlayers());
	}

	/**
	 * 发送消息给指定玩家
	 * id = -1表示发送给所有人
	 *
	 * @param message 消息
	 * @param toId	目标玩家id
	 */
	public void sendMessage(String message, int toId) {
		SGSPacket packet = new SGSPacket(OP_SEND_CHAT_MESSAGE);
		packet.setMessage(message);
		packet.setMessageToID(toId);
		packet.setPlayerId(gameFrame.getCurrentPlayerID());
		send(packet);
	}

	/**
	 * 发送数据包给服务器
	 *
	 * @param packet
	 */
	public void send(SGSPacket packet) {
		UDPSender.send(packet, serverAddress, serverPort);
	}


	public String getServerAddress() {
		return serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getName() {
		return name;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public int getLordId() {
		return lordId;
	}

	public void setLordId(int lordId) {
		this.lordId = lordId;
	}


	public void showMessage(String message) {
		gameFrame.appendLog(message);
	}

	public void showChatMessage(String message) {
		gameFrame.appendChatMessage(message);
	}

	public void setRole(Role role, int lordId) {
		gameFrame.setRole(role, lordId);
	}

	/**
	 * 选择主公武将橘色
	 *
	 * @param dp
	 */
	public void distributeLordCharacter(SGSPacket dp) {
		lordId = dp.getLordId();

		if (lordId == gameFrame.getCurrentPlayerID()) {

			org.dizem.sanguosha.model.card.character.Character[] characters
					= new org.dizem.sanguosha.model.card.character.Character[5];
			for (int i = 0; i < 5; ++i) {
				characters[i] = new org.dizem.sanguosha.model.card.character.Character(dp.getCharacterVOs()[i]);
			}
			new ChooseCharacterDialog(gameFrame, characters);


		} else {
			gameFrame.showMessage("等待主公选择武将");
		}
	}

	/**
	 * 主公选择武将结束
	 */
	public void chooseLordCharacterFinish() {
		SGSPacket packet = new SGSPacket(OP_FINISH_CHOOSING_LORD_CHARACTER);
		packet.setCharacterVO(new CharacterVO(gameFrame.getPlayers()[gameFrame.getCurrentPlayerID()].getCharacter()));
		send(packet);
	}

	/**
	 * 分配其他玩家武将角色
	 * @param dp
	 */
	public void distributeCharacter(SGSPacket dp) {
		gameFrame.showMessage("主公选择了武将:" + dp.getCharacterVO().getName());
		org.dizem.sanguosha.model.card.character.Character ch
				= new org.dizem.sanguosha.model.card.character.Character(dp.getCharacterVO());
		gameFrame.setCharacter(lordId, ch);

		org.dizem.sanguosha.model.card.character.Character[] characters
				= new org.dizem.sanguosha.model.card.character.Character[3];
		for (int i = 0; i < 3; ++i) {
			characters[i] = new org.dizem.sanguosha.model.card.character.Character(dp.getCharacterVOs()[i]);
		}

		new ChooseCharacterDialog(gameFrame, characters);
	}

	/**
	 * 分配其他玩家武将角色结束
	 */
	public void chooseCharacterFinish() {
		SGSPacket packet = new SGSPacket(OP_FINISH_CHOOSING_CHARACTER);
		packet.setPlayerId(playerId);
		packet.setCharacterVO(new CharacterVO(gameFrame.getPlayers()[gameFrame.getCurrentPlayerID()].getCharacter()));
		send(packet);
	}

	public void distributeCards(SGSPacket dp) {
		AbstractCard[] cards = new AbstractCard[dp.getCardVOs().length];
		for (int i = 0; i < dp.getCardVOs().length; ++i) {
			cards[i] = AbstractCard.createCard(dp.getCardVOs()[i]);
		}
		gameFrame.distributeCards(cards);
	}

	public void setPlayerId(int currentPlayerID) {
		this.playerId = currentPlayerID;
	}

	public void setCharacter(SGSPacket dp) {
		gameFrame.setCharacter(dp.getPlayerId(), new Character(dp.getCharacterVO()));
	}

	public void updatePlayersInfo(SGSPacket dp) {
		gameFrame.showMessage(dp.getMessage());
		gameFrame.setOtherPlayerInfo(dp.getPlayerId(), dp.getHandCardCount());
	}


	/**
	 * 摸牌阶段
	 */
	public void startPhase(int id) {
		gameFrame.showMessage(getPlayerName(id) + "进入开始阶段");
		if (id != playerId) {
			players[id].setPhase(Phase.START);
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(playerId)).repaint();
		} else {
			players[playerId].setPhase(Phase.START);
		}
	}

	/*
	 * 判定阶段
	 *
	 */
	public void judgePhase(int id) {
		gameFrame.showMessage(getPlayerName(id) + "进入判定阶段");

		players[id].setPhase(Phase.JUDGE);
		if (id != playerId) {
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(playerId)).repaint();
		} else {
			SGSPacket packet = new SGSPacket(OP_PHASE_JUDGE_END);
			if (players[id].getEffectCards().size() == 0) {
				log.info("没有判定牌，跳过判定阶段");
				send(packet);
			}
		}


	}

	/**
	 * 摸牌阶段
	 *
	 * @param packet 数据包
	 */
	public void drawPhase(SGSPacket packet) {
		int id = packet.getPlayerId();
		players[id].setPhase(Phase.DRAW);
		gameFrame.showMessage(getPlayerName(id) + "进入摸牌阶段");
		gameFrame.showMessage(getPlayerName(id) + "从牌堆里摸了" + packet.getCardVOs().length + "张牌");
		if (id != playerId) {
			OtherPlayerPane pane = gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(id));
			pane.setHandCardCount(packet.getHandCardCount());
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(id)).repaint();

		} else {
			for (CardVO vo : packet.getCardVOs()) {
				AbstractCard card = AbstractCard.createCard(vo);
				gameFrame.dashboard.addHandCard(card);
			}
			send(new SGSPacket(OP_PHASE_DRAW_END));
		}
	}

	public String getPlayerName(int id) {
		if (id != playerId) {
			return players[id].getCharacterName();
		} else {
			return players[id].getCharacterName() + "(你)";
		}
	}


	public void playPhase(int id) {
		gameFrame.showMessage(getPlayerName(id) + "进入出牌阶段");
		if (id != playerId) {
			players[id].setPhase(Phase.PLAY);
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(id)).repaint();
		}
	}
}
