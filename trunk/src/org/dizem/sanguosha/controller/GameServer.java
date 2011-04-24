package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	private MainFrame owner;

	public GameServer(MainFrame owner, String serverName, int port, int timeDelay, int playerCount) {
		this.serverName = serverName;
		this.port = port;
		this.timeDelay = timeDelay;
		this.playerCount = playerCount;
		this.owner = owner;
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
