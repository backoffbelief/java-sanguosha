package org.dizem.sanguosha.model.vo;

import java.util.List;

/**
 * User: DIZEM
 * Time: 11-4-6 下午3:45
 */
public class SGSPacket {

	private String operation;
	private int clientPort;

	private String playerName;
	private int playerId;
	private CardVO cardVO;
	private PlayerVO playerVO;
	private List<CardVO> cardVOList;

	public SGSPacket() {
	}

	public SGSPacket(String operation) {
		this.operation = operation;
	}

	public SGSPacket(String operation, int clientPort) {
		this(operation);
		this.clientPort = clientPort;
	}

	public SGSPacket(String operation, String playerName, int clientPort) {
		this(operation, clientPort);
		this.playerName = playerName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
}
