package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.AudioUtil;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.CardVO;
import org.dizem.sanguosha.model.vo.CharacterVO;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;
import org.dizem.sanguosha.view.dialog.ChooseCharacterDialog;
import org.dizem.sanguosha.view.gameview.GameFrame;
import org.dizem.sanguosha.view.gameview.OtherPlayerPane;

import java.util.ArrayList;
import java.util.List;

import static org.dizem.sanguosha.model.constants.Constants.*;

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
		gameFrame = new GameFrame(MainFrame.instance, this, name);
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
		AudioUtil.play(AUDIO_GAME_BACK);
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
	 *
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
			players[playerId].addHandCard(cards[i]);
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

		System.out.println("drawPhase++++" + id + " " + playerId);
		if (id != playerId) {
			OtherPlayerPane pane = gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(id));
			pane.setHandCardCount(packet.getHandCardCount());
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(id)).repaint();

		} else {
			for (CardVO vo : packet.getCardVOs()) {
				AbstractCard card = AbstractCard.createCard(vo);
				players[playerId].addHandCard(card);
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
		players[id].setPhase(Phase.PLAY);

		if (id != playerId) {
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(id)).repaint();

		} else {
			gameFrame.dashboard.setHasOffedSha(false);
			gameFrame.dashboard.setCancelable(true);
		}
	}

	/**
	 * 发送离线请求
	 */
	public void offline() {
		SGSPacket packet = new SGSPacket(OP_OFFLINE);
		packet.setPlayerId(playerId);
		send(packet);
	}


	/**
	 * 向对手出牌
	 *
	 * @param card 出牌
	 * @param toId 对手id
	 */
	public void sendOfferCardToInfo(AbstractCard card, int toId) {
		SGSPacket packet = new SGSPacket(OP_OFFER_CARD_TO);
		packet.setCardVO(new CardVO(card));
		packet.setPlayerId(playerId);
		packet.setToPlayerId(toId);
		players[playerId].removeHandCard(card);
		players[playerId].setPhase(Phase.WAIT_OTHER);
		gameFrame.dashboard.repaint();
		send(packet);
	}

	public void beOfferredCardTo(SGSPacket packet) {
		AbstractCard card = AbstractCard.createCard(packet.getCardVO());
		String message = getPlayerName(packet.getPlayerId()) + "向" +
				getPlayerName(packet.getToPlayerId()) + "出牌《" + card.getName() + "》";
		gameFrame.showMessage(message);


		if (packet.getToPlayerId() == playerId) {
			gameFrame.setFeedbackCard(card);
			gameFrame.setFeedBackToId(packet.getPlayerId());
			gameFrame.getCurrentPlayer().setPhase(Phase.FEEDBACK);
			gameFrame.showMessageKeep(message);
			gameFrame.dashboard.setCancelable(true);
			gameFrame.dashboard.repaint();
		}

		gameFrame.addDiscardedCard(card, getPlayerName(packet.getPlayerId()) + "出牌");
		players[packet.getPlayerId()].removeHandCard(card);

		for (OtherPlayerPane pane : gameFrame.otherPlayerPaneList) {

			if (pane.getPlayer().getPlayerId() == packet.getToPlayerId()) {
				pane.setSelected(true);

			} else if (pane.getPlayer().getPlayerId() == packet.getPlayerId()) {
				pane.showEffect(card);
			}
		}
	}

	public void sendFeedbackInfo(AbstractCard cardToFeedback) {
		SGSPacket packet = new SGSPacket(OP_FEEDBACK);
		if (cardToFeedback != null) {
			CardVO cardVO = new CardVO(cardToFeedback);
			packet.setCardVO(cardVO);
		}
		packet.setPlayerId(playerId);
		packet.setToPlayerId(gameFrame.getFeedbackToId());
		send(packet);
	}

	public void feedback(SGSPacket packet) {
		if (packet.getCardVO() != null) {
			AbstractCard card = AbstractCard.createCard(packet.getCardVO());

			if (card.getName().equals("闪")) {
				gameFrame.addDiscardedCard(card, players[packet.getPlayerId()].getName() + "出《闪》");
				players[packet.getPlayerId()].removeHandCard(card);

				if (packet.getPlayerId() == playerId) {
					gameFrame.dashboard.showEffect(card);

				} else {
					gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(packet.getPlayerId())).showEffect(card);
				}
			}
		}
		if (packet.getPlayerId() == playerId) {
			players[playerId].setPhase(Phase.NOT_ACTIVE);
			gameFrame.dashboard.repaint();

		} else if (packet.getToPlayerId() == playerId) {
			players[playerId].setPhase(Phase.PLAY);
			gameFrame.dashboard.repaint();
		}
	}

	public void sendDecreaseLifeInfo() {
		SGSPacket packet = new SGSPacket(OP_DECREASE_LIFE);
		packet.setPlayerId(playerId);
		send(packet);
	}


	public void decreaseLife(SGSPacket packet) {
//		players[packet.getPlayerId()].getCharacter().decreaseLife();
		gameFrame.decreaseLife(packet.getPlayerId());
	}

	public void sendAddEquipmentInfo(EquipmentCard equipmentCard, EquipmentCard cardToRemove) {
		SGSPacket packet = new SGSPacket(OP_ADD_EQUIPMENT);
		packet.setPlayerId(playerId);
		packet.setCardVO(new CardVO(equipmentCard));
		if (cardToRemove != null)
			packet.setAnotherCardVO(new CardVO(cardToRemove));
		send(packet);
	}

	/**
	 * 出牌结束
	 */
	public void sendEndPlayInfo() {
		SGSPacket packet = new SGSPacket(OP_PHASE_PLAY_END);
		packet.setPlayerId(playerId);
		send(packet);
	}

	public void discard(SGSPacket packet) {

		String message = getPlayerName(packet.getPlayerId()) + "进入弃牌阶段";
		gameFrame.showMessage(message);
		players[packet.getPlayerId()].setPhase(Phase.DISCARD);
		if (packet.getPlayerId() == playerId) {

			if (!players[playerId].needToDiscard()) {
				endDiscard();
			} else {
				gameFrame.discard();
			}
		}
	}

	/**
	 * 弃牌结束
	 */
	public void endDiscard() {
		discardCards(new ArrayList<AbstractCard>());
	}

	/**
	 * 弃一组牌
	 *
	 * @param cardList
	 */
	public void discardCards(List<AbstractCard> cardList) {
		CardVO[] cardVOs = new CardVO[cardList.size()];
		int index = 0;
		for (AbstractCard card : cardList) {
			cardVOs[index++] = new CardVO(card);
		}
		SGSPacket packet = new SGSPacket(OP_DISCARD);
		packet.setCardVOs(cardVOs);
		packet.setPlayerId(playerId);
		send(packet);
	}


	/**
	 * 弃牌结束
	 *
	 * @param packet
	 */
	public void discardEnd(SGSPacket packet) {
		for (CardVO cardVO : packet.getCardVOs()) {
			AbstractCard card = AbstractCard.createCard(cardVO);
			gameFrame.addDiscardedCard(card, getPlayerName(packet.getPlayerId()) + "弃牌");
		}
		players[packet.getPlayerId()].setPhase(Phase.NOT_ACTIVE);

		if (packet.getPlayerId() == playerId) {
			gameFrame.dashboard.repaint();
		} else {
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(packet.getPlayerId())).repaint();
		}
	}

	public void useHandCard(AbstractCard card) {
		if (card.getName().equals("桃")) {
			SGSPacket packet = new SGSPacket(OP_EAT_PEACH);
			packet.setPlayerId(playerId);
			packet.setCardVO(new CardVO(card));
			send(packet);
		}
	}

	public void eatPeach(SGSPacket packet) {
		players[packet.getPlayerId()].getCharacter().increaseLife();
		AbstractCard card = AbstractCard.createCard(packet.getCardVO());
		players[packet.getPlayerId()].removeHandCard(card);

		if (packet.getPlayerId() == playerId) {
			gameFrame.dashboard.repaint();
			gameFrame.dashboard.showEffect(card);

		} else {
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(packet.getPlayerId())).repaint();
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(packet.getPlayerId())).showEffect(card);
		}
	}

	public void addEquipmentCard(SGSPacket packet) {
		EquipmentCard card = (EquipmentCard) AbstractCard.createCard(packet.getCardVO());
		gameFrame.showMessage(getPlayerName(packet.getPlayerId()) + "装备了" + card.getName());
		players[packet.getPlayerId()].removeHandCard(card);

		if (packet.getAnotherCardVO() != null) {
			gameFrame.addDiscardedCard(AbstractCard.createCard(packet.getAnotherCardVO()),
					getPlayerName(packet.getPlayerId()) + "卸掉装备牌");
		}

		if (packet.getPlayerId() == playerId) {
			gameFrame.dashboard.addEquipmentCard(card);

		} else {
			gameFrame.otherPlayerPaneList.get(gameFrame.getIndex(packet.getPlayerId())).addEquipmentCard(card);
		}
	}
}
