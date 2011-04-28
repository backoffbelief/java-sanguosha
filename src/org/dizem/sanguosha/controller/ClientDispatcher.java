package org.dizem.sanguosha.controller;

import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: dizem
 * Time: 11-4-28 下午3:40
 */
public class ClientDispatcher {

	private GameClient client;

	public ClientDispatcher(GameClient client) {
		this.client = client;
	}

	public void dispatch(SGSPacket packet) {
		if (packet.getOperation().equals(OP_INIT_CLIENT)) {
			this.client.showGameFrame(packet);

		} else if (packet.getOperation().equals(OP_UPDATE_PLAYERS)) {
			this.client.updatePlayers(packet);

		} else if (packet.getOperation().equals(OP_SEND_MESSAGE)) {
			this.client.showMessage(packet.getMessage());

		} else if (packet.getOperation().equals(OP_SEND_CHAT_MESSAGE)) {
			this.client.showChatMessage(packet.getMessage());

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_ROLE)) {
			this.client.setRole(packet.getRole(), packet.getLordId());

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_LORD_CHARACTER)) {
			this.client.distributeLordCharacter(packet);

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_CHARACTER)) {
			this.client.distributeCharacter(packet);

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_CARD)) {
			this.client.distributeCards(packet);

		} else if (packet.getOperation().equals(OP_FINISH_CHOOSING_CHARACTER)) {
			this.client.setCharacter(packet);

		} else if (packet.getOperation().equals(OP_UPDATE_PLAYERS_INFO)) {
			this.client.updatePlayersInfo(packet);
		}

	}

	public void dispatch(String jsonString) {
		SGSPacket packet = (SGSPacket) JSONUtil.convertToVO(jsonString, SGSPacket.class);
		dispatch(packet);
	}
}
