package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.CharacterVO;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.dialog.ChooseCharacterDialog;
import org.dizem.sanguosha.view.gameview.GameFrame;

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
	private String serverAddress;
	private int serverPort;
	private String name;
	private GameClientMonitor clientMonitor;
	private GameFrame gameFrame;
	private int playerCount;

	public GameClient(int serverPort, String serverAddress, String name) {
		this.serverPort = serverPort;
		this.serverAddress = serverAddress;
		this.name = name;
		clientMonitor = new GameClientMonitor(this);
		clientMonitor.start();
		connect();
	}

	public void connect() {
		while (!clientMonitor.isReady()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}
		send(new SGSPacket(OP_CONNECT, name, clientMonitor.getClientPort()));
	}

	public void showGameFrame(SGSPacket packet) {
		playerCount = packet.getPlayerCount();
		gameFrame = new GameFrame(this, name);
		gameFrame.setCurrentPlayerID(packet.getPlayerId());
	}

	public void updatePlayers(SGSPacket packet) {
		gameFrame.updatePlayers(packet.getPlayers());
	}

	public void sendMessage(String message, int toId) {
		SGSPacket packet = new SGSPacket(OP_SEND_CHAT_MESSAGE);
		packet.setMessage(message);
		packet.setMessageToID(toId);
		packet.setPlayerId(gameFrame.getCurrentPlayerID());
		send(packet);
	}

	public void send(SGSPacket packet) {
		try {
			DatagramSocket ds = new DatagramSocket();
			String message = JSONUtil.convertToString(packet);
			log.info("send : " + message);
			byte[] data = message.getBytes("UTF-8");
			DatagramPacket dp = new DatagramPacket(
					data, data.length, InetAddress.getByName(serverAddress), serverPort
			);
			ds.send(dp);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
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


	public void showMessage(String message) {
		gameFrame.appendLog(message);
	}

	public void showChatMessage(String message) {
		gameFrame.appendChatMessage(message);
	}

	public void setRole(Role role, int lordId) {
		gameFrame.setRole(role, lordId);
	}

	public void distributeLordCharacter(SGSPacket dp) {
		if (dp.getLordId() == gameFrame.getCurrentPlayerID()) {
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

	public void chooseLordCharacterFinish() {
		SGSPacket packet = new SGSPacket(OP_FINISH_CHOOSING_LORD_CHARACTER);
		packet.setCharacterVO(new CharacterVO(gameFrame.getPlayers()[gameFrame.getCurrentPlayerID()].getCharacter()));
		send(packet);
	}

	public void distributeCharacter(SGSPacket dp) {
		gameFrame.showMessage("主公选择了武将:" + dp.getCharacterVO().getName());
		org.dizem.sanguosha.model.card.character.Character[] characters
				= new org.dizem.sanguosha.model.card.character.Character[3];
		for (int i = 0; i < 3; ++i) {
			characters[i] = new org.dizem.sanguosha.model.card.character.Character(dp.getCharacterVOs()[i]);
		}

		new ChooseCharacterDialog(gameFrame, characters);
	}

	public void chooseCharacterFinish() {
		SGSPacket packet = new SGSPacket(OP_FINISH_CHOOSING_CHARACTER);
		packet.setCharacterVO(new CharacterVO(gameFrame.getPlayers()[gameFrame.getCurrentPlayerID()].getCharacter()));
		send(packet);
	}
}
