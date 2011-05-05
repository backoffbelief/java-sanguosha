package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.IDGenerator;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.Deck;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.CardVO;
import org.dizem.sanguosha.model.vo.CharacterVO;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * 服务端
 * <p/>
 * User: DIZEM
 * Time: 11-4-6 下午1:40
 */
public class GameServer {

	private static Logger log = Logger.getLogger(GameServer.class);

	/**
	 * 服务器名称
	 */
	private String serverName;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 出牌时间
	 */
	private int timeDelay;
	/**
	 * 玩家数
	 */
	private int playerCount;
	/**
	 * 已经在线的玩家数
	 */
	private int playerOnline = 0;
	/**
	 * 游戏窗体实例
	 */
	private MainFrame owner;
	/**
	 * 玩家列表
	 */
	private Player[] players;
	/**
	 * 主公id
	 */
	private int lordId;
	/**
	 * 当前轮到玩家的id
	 */
	private int currentId;
	/**
	 * id到玩家的映射
	 */
	private Map<Integer, Player> idToPlayerMap = new HashMap<Integer, Player>();

	/**
	 * 构造函数
	 *
	 * @param owner	   父窗体
	 * @param serverName  服务器名称
	 * @param port		端口
	 * @param timeDelay   出牌时间
	 * @param playerCount 玩家数
	 */
	public GameServer(MainFrame owner, String serverName, int port, int timeDelay, int playerCount) {
		this.serverName = serverName;
		this.port = port;
		this.timeDelay = timeDelay;
		this.playerCount = playerCount;
		this.owner = owner;
		players = new Player[playerCount];
		owner.appendLog("\u542f\u52a8\u670d\u52a1\u5668:" + serverName);
		owner.appendLog("\u672c\u673a\u5730\u5740:127.0.0.1");
		try {
			owner.appendLog("\u5c40\u57df\u7f51\u5730\u5740:" + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		owner.appendLog("\u7aef\u53e3:" + port + "    \u6e38\u620f\u6a21\u5f0f:" + playerCount + "\u4eba\u5c40");
		owner.appendLog("\u51fa\u724c\u65f6\u95f4:" + (timeDelay == -1 ? "\u4e0d\u9650\u65f6\u95f4" : timeDelay + "\u79d2"));

		new GameServerMonitor(this).start();
	}

	/**
	 * 处理客户端的接入
	 *
	 * @param packet  数据包
	 * @param address 客户端地址
	 */
	public synchronized void playerConnect(SGSPacket packet, String address) {

		Player player = new Player(packet.getPlayerName(), address, packet.getClientPort());
		player.setPlayerId(IDGenerator.nextId());
		//给客户端分配ID
		SGSPacket idPacket = new SGSPacket(OP_INIT_CLIENT);
		idPacket.setPlayerId(player.getPlayerId());
		idPacket.setPlayerCount(playerCount);
		send(idPacket, player);

		idToPlayerMap.put(player.getPlayerId(), player);


		for (int i = 0; i < playerCount; ++i) {
			if (players[i] == null) {
				players[i] = player;
				playerOnline++;
				break;
			}
		}

		log.debug((players[0] == null) + "  " + (players[1] == null));
		log.info("在" + playerOnline + "位置添加玩家" + packet.getPlayerName());


		//广播:新玩家进入
		SGSPacket msgPacket = new SGSPacket(OP_SEND_MESSAGE);
		msgPacket.setMessage("玩家:" + packet.getPlayerName() + "进入了房间");
		send(msgPacket, players);

		//更新显示玩家
		SGSPacket updPacket = new SGSPacket(OP_UPDATE_PLAYERS);
		updPacket.setPlayers(players);
		send(updPacket, players);

		if (playerOnline == playerCount) {
			owner.appendLog("人数已满");
			distributeRole();
		}
	}

	/**
	 * 分配角色
	 */
	private void distributeRole() {
		SGSPacket packet = new SGSPacket(OP_DISTRIBUTE_ROLE);
		List<Role> roleList = new ArrayList<Role>();
		for (int i = 0; i < playerCount; ++i) {
			roleList.add(ROLE_DISTRIBUTION[i]);
		}
		Collections.shuffle(roleList);
		lordId = roleList.indexOf(Role.ZG);
		owner.appendLog("分配角色:");
		for (int i = 0; i < playerCount; ++i) {
			players[i].setRole(roleList.get(i));
			owner.appendLog(players[i].getName() + "的角色是" + roleList.get(i));
			log.info(players[i].getName() + "的角色是" + roleList.get(i));
			packet.setRole(roleList.get(i));
			packet.setLordId(lordId);
			send(packet, players[i]);
		}

		distributeLordCharacter();
	}

	/**
	 * 分配主公武将
	 */
	private void distributeLordCharacter() {
		SGSPacket packet = new SGSPacket(OP_DISTRIBUTE_LORD_CHARACTER);
		packet.setLordId(lordId);
		CharacterVO[] characterVOs = new CharacterVO[5];
		Character[] characters = CharacterDeck.getInstance().popLoadCharacter();
		for (int i = 0; i < 5; ++i) {
			characterVOs[i] = new CharacterVO(characters[i]);
		}
		packet.setCharacterVOs(characterVOs);
		send(packet, players);
	}

	/**
	 * 分配其他武将颜色
	 *
	 * @param dp
	 */
	public void distributeCharacters(SGSPacket dp) {
		Character character = new Character(dp.getCharacterVO());
		players[lordId].setCharacter(character);
		characterCount = 1;

		SGSPacket packet = new SGSPacket(OP_DISTRIBUTE_CHARACTER);
		packet.setCharacterVO(dp.getCharacterVO());
		packet.setLordId(lordId);
		CharacterVO[] characterVOs = new CharacterVO[3];

		for (int i = 0; i < playerCount; ++i) {
			if (i != lordId) {
				Character[] characters = CharacterDeck.getInstance().popCharacters(3);
				for (int j = 0; j < 3; ++j) {
					System.out.println(characters[j].getName());
					characterVOs[j] = new CharacterVO(characters[j]);
				}
				packet.setCharacterVOs(characterVOs);
				send(packet, players[i]);
			}
		}
	}

	/**
	 * 发送数据包至所有玩家
	 *
	 * @param packet  数据包
	 * @param players 玩家列表
	 */
	public void send(SGSPacket packet, Player[] players) {
		for (Player p : players) {
			send(packet, p);
		}
	}

	/**
	 * 玩家聊天
	 *
	 * @param packet 数据包
	 */
	public void playerTalk(SGSPacket packet) {
		packet.setMessage(players[packet.getPlayerId()].getName() + "说: " + packet.getMessage());
		if (packet.getMessageToID() == -1) {
			send(packet, players);

		} else {
			send(packet, players[packet.getMessageToID()]);
			send(packet, players[packet.getPlayerId()]);
		}
	}

	/**
	 * 发送数据包至一名玩家
	 *
	 * @param packet 数据包
	 * @param player 玩家
	 */
	public void send(SGSPacket packet, Player player) {
		if (player == null || packet == null) {
			return;
		}
		UDPSender.send(packet, player.getIp(), player.getPort());
	}

	public String getServerName() {
		return serverName;
	}

	public int getPort() {
		return port;
	}

	public int getTimeDelay() {
		return timeDelay;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public MainFrame getOwner() {
		return owner;
	}

	/**
	 * 游戏开始时，为每位玩家分配四张手牌
	 */
	public void distributeCards() {
		currentId = lordId;
		CardVO[] cards = new CardVO[4];
		Deck deck = Deck.getInstance();
		SGSPacket packet = new SGSPacket(OP_DISTRIBUTE_CARD);
		SGSPacket infoPacket = new SGSPacket(OP_UPDATE_PLAYERS_INFO);
		for (int i = 0; i < playerCount; ++i) {
			int playerId = (i + currentId) % playerCount;

			for (int j = 0; j < 4; ++j) {
				AbstractCard card = deck.popCard();
				players[playerId].addHandCard(card);
				cards[j] = new CardVO(card);
			}
			packet.setCardVOs(cards);
			send(packet, players[(i + currentId) % playerCount]);

			infoPacket.setPlayerId(playerId);
			infoPacket.setHandCardCount(players[playerId].getHandCards().size());
			infoPacket.setMessage(players[playerId].getCharacterName() + "从牌堆摸了4张牌");
			send(infoPacket, players);
		}
	}

	public void tellCharacters(SGSPacket dp) {
		for (Player p : players) {
			if (p.getPlayerId() != dp.getPlayerId()) {
				send(dp, p);
			}
		}
	}

	private int characterCount;

	/**
	 * 设置玩家的武将
	 *
	 * @param playerId  玩家id
	 * @param character 武将
	 */
	public void setCharacter(int playerId, Character character) {
		players[playerId].setCharacter(character);
		characterCount++;

		if (characterCount == playerCount) { //全部玩家准备完毕
			SGSPacket packet = new SGSPacket(OP_FINISH_CHOOSING_CHARACTER);
			for (Player p : players) { //广播所有玩家的角色
				if (p.getPlayerId() != lordId) {
					packet.setPlayerId(p.getPlayerId());
					packet.setCharacterVO(new CharacterVO(p.getCharacter()));
					send(packet, players);
				}
			}
			distributeCards(); //初始所有玩家发四张牌
			gameStart(); //从主公开始游戏
		}
	}


	/**
	 * 游戏开始阶段
	 */
	public void startPhase() {
		showMessage(players[currentId].getCharacterName() + "进入开始阶段");
		log.info(players[currentId].getCharacterName() + "进入开始阶段");
		SGSPacket packet = new SGSPacket(OP_PHASE_START);
		packet.setPlayerId(currentId);
		packet.setPhase(Phase.START);
		send(packet, players);
	}

	/**
	 * 判定阶段
	 */
	public void judgePhase() {
		showMessage(players[currentId].getCharacterName() + "进入判定阶段");
		log.info(players[currentId].getCharacterName() + "进入判定阶段");
		SGSPacket packet = new SGSPacket(OP_PHASE_JUDGE_BEGIN);
		packet.setPlayerId(currentId);
		packet.setPhase(Phase.JUDGE);
		send(packet, players);
	}

	/**
	 * 摸牌阶段
	 */
	public void drawPhase() {
		showMessage(players[currentId].getCharacterName() + "进入摸牌阶段");
		log.info(players[currentId].getCharacterName() + "进入摸牌阶段");
		SGSPacket packet = new SGSPacket(OP_PHASE_DRAW_BEGIN);
		packet.setPlayerId(currentId);
		packet.setPhase(Phase.DRAW);
		AbstractCard[] cards = new AbstractCard[2];
		CardVO[] cardVOs = new CardVO[cards.length];
		for (int i = 0; i < cards.length; ++i) {
			cards[i] = Deck.getInstance().popCard();
			cardVOs[i] = new CardVO(cards[i]);
			players[currentId].addHandCard(cards[i]);
		}
		packet.setCardVOs(cardVOs);
		packet.setHandCardCount(players[currentId].getHandCards().size());
		send(packet, players);
	}

	/**
	 * 出牌阶段
	 */
	public void playPhase() {
		showMessage(players[currentId].getCharacterName() + "进入出牌阶段");
		log.info(players[currentId].getCharacterName() + "进入出牌阶段");
		SGSPacket packet = new SGSPacket(OP_PHASE_PLAY_BEGIN);
		packet.setPlayerId(currentId);
		packet.setPhase(Phase.PLAY);
		send(packet, players);
	}

	/**
	 * 游戏线程
	 */
	public Thread gameThread = new Thread(new Runnable() {
		public void run() {
			while (true) {
				try {
					startPhase();
					Thread.sleep(500);

					judgePhase();
					synchronized (gameThread) {
						gameThread.wait();
					}

					drawPhase();
					synchronized (gameThread) {
						gameThread.wait();
					}

					playPhase();
					synchronized (gameThread) {
						gameThread.wait();
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}
	});

	/**
	 * 游戏开始
	 */
	public void gameStart() {
		currentId = lordId;
		gameThread.start();
	}

	/**
	 * 显示日志
	 *
	 * @param log
	 */
	public void showMessage(String log) {
		owner.appendLog(log);
	}

	/**
	 * 关闭服务器
	 */
	public void stop() {
		SGSPacket packet = new SGSPacket(OP_SERVER_STOP);
		send(packet, players);
	}

	/**
	 * 出牌
	 *
	 * @param packet
	 */
	public void offerCard(SGSPacket packet) {
		AbstractCard card = AbstractCard.createCard(packet.getCardVO());
		players[packet.getPlayerId()].removeHandCard(card);
		Deck.getInstance().addToBack(card);
		send(packet, players);
	}


	public void feedback(SGSPacket packet) {
		if (packet.getCardVO() != null) {
			AbstractCard card = AbstractCard.createCard(packet.getCardVO());
			Deck.getInstance().addToBack(card);
		}
		send(packet, players);
	}


	public void decreaseLife(SGSPacket packet) {
		players[packet.getPlayerId()].getCharacter().decreaseLife();
		send(packet, players);
	}
}
