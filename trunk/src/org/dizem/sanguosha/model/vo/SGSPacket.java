package org.dizem.sanguosha.model.vo;

import org.dizem.sanguosha.model.player.Player;

import java.util.List;

/**
 * User: DIZEM
 * Time: 11-4-6 下午3:45
 */
public class SGSPacket {

	private String operation;
	private int clientPort;

	private String playerName;
	private int playerCount;

	private int playerId;

	private PlayerVO[] players;

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

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public PlayerVO[] getPlayers() {
		return players;
	}

	public void setPlayers(PlayerVO[] players) {
		this.players = players;
	}

	public void setPlayers(Player[] players) {
		this.players = new PlayerVO[players.length];
		for (int i = 0; i < players.length; ++i) {
			if (players[i] != null) {
				this.players[i] = new PlayerVO(players[i]);
			}
		}
	}
}
