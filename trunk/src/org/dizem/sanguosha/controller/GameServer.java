package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.IDGenerator;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.character.*;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.CharacterVO;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: DIZEM
 * Time: 11-4-6 下午1:40
 */
public class GameServer {

	private static Logger log = Logger.getLogger(GameServer.class);

	private String serverName;
	private int port;
	private int timeDelay;
	private int playerCount;
	private int playerOnline = 0;
	private MainFrame owner;
	private Player[] players;
	private int lordId;
	private Map<Integer, Player> idToPlayerMap = new HashMap<Integer, Player>();

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
			packet.setRole(roleList.get(i));
			send(packet, players[i]);
		}

		distributeLordCharacter(lordId);
	}

	private void distributeLordCharacter(int lordId) {
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

	public void distributeCharacters(SGSPacket dp) {
		Character character = new Character(dp.getCharacterVO());
		players[lordId].setCharacter(character);

		SGSPacket packet = new SGSPacket(OP_DISTRIBUTE_CHARACTER);
		packet.setCharacterVO(dp.getCharacterVO());

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

	public void send(SGSPacket packet, Player[] players) {
		for (Player p : players) {
			send(packet, p);
		}
	}

	public void playerTalk(SGSPacket packet) {
		packet.setMessage(players[packet.getPlayerId()].getName() + "说: " + packet.getMessage());
		if (packet.getMessageToID() == -1) {
			send(packet, players);

		} else {
			send(packet, players[packet.getMessageToID()]);
			send(packet, players[packet.getPlayerId()]);
		}
	}

	public void send(SGSPacket packet, Player player) {
		if (player == null || packet == null) {
			return;
		}
		try {
			DatagramSocket ds = new DatagramSocket();
			String message = JSONUtil.convertToString(packet);
			log.info("send : " + message);
			byte[] data = message.getBytes("UTF-8");
			DatagramPacket dp = new DatagramPacket(
					data, data.length, InetAddress.getByName(player.getIp()), player.getPort()
			);
			ds.send(dp);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
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


}
