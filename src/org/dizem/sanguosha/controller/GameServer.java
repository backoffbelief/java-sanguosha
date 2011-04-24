package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

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
	private Map<Integer, Player> idToPlayerMap = new HashMap<Integer, Player>();

	public GameServer(MainFrame owner, String serverName, int port, int timeDelay, int playerCount) {
		this.serverName = serverName;
		this.port = port;
		this.timeDelay = timeDelay;
		this.playerCount = playerCount;
		this.owner = owner;
		players = new Player[playerCount];
		owner.appendLog("启动服务器:" + serverName);
		owner.appendLog("本机地址:127.0.0.1");
		try {
			owner.appendLog("局域网地址:" + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
		}
		owner.appendLog("端口:" + port + "  游戏模式:" + playerCount + "人局");
		owner.appendLog("出牌时间:" + (timeDelay == -1 ? "不限时间" : timeDelay + "秒"));

		new GameServerMonitor(this).start();
	}

	public synchronized void playerConnect(SGSPacket packet, String address) {

		Player player = new Player(packet.getPlayerName(), address, packet.getClientPort());

		//给客户端分配ID
		SGSPacket idPacket = new SGSPacket(OP_DISTRIBUTE_ID);
		idPacket.setPlayerId(player.getPlayerId());
		send(idPacket, player);

		idToPlayerMap.put(player.getPlayerId(), player);

		for (Player p : players) {
			if (p == null) {
				p = player;
				playerOnline++;
				break;
			}
		}
		log.info("在" + playerOnline + "位置添加玩家" + packet.getPlayerName());

		if (playerOnline == playerCount) {
			owner.appendLog("人数已满");
		}
	}

	public void send(SGSPacket packet, Player player) {
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

	public void updatePlayer() {

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
