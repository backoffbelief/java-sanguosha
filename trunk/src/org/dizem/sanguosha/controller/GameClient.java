package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: DIZEM
 * Time: 11-4-6 下午1:40
 */
public class GameClient {

	private static Logger log = Logger.getLogger(GameClient.class);
	private String serverAddress;
	private int serverPort;
	private String userName;

	private GameClientMonitor clientMonitor;


	public GameClient(int serverPort, String serverAddress, String userName) {
		this.serverPort = serverPort;
		this.serverAddress = serverAddress;
		this.userName = userName;
		clientMonitor = new GameClientMonitor(this);
		clientMonitor.start();
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getUserName() {
		return userName;
	}
}
