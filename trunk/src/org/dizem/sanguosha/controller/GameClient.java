package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.vo.SGSPacket;
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
	private Player[] players;
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


}
