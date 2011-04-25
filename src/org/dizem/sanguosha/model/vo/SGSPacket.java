package org.dizem.sanguosha.model.vo;

import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;

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
	private int lordId;
	private PlayerVO[] players;
	private Role role;
	private String message;
	private int messageToID;
	private CharacterVO[] characterVOs;
	private CharacterVO characterVO;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessageToID() {
		return messageToID;
	}

	public void setMessageToID(int messageToID) {
		this.messageToID = messageToID;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getLordId() {
		return lordId;
	}

	public void setLordId(int lordId) {
		this.lordId = lordId;
	}

	public CharacterVO[] getCharacterVOs() {
		return characterVOs;
	}

	public void setCharacterVOs(CharacterVO[] characterVOs) {
		this.characterVOs = characterVOs;
	}

	public CharacterVO getCharacterVO() {
		return characterVO;
	}

	public void setCharacterVO(CharacterVO characterVO) {
		this.characterVO = characterVO;
	}
}
