package org.dizem.sanguosha.controller;

import org.dizem.sanguosha.model.vo.SGSPacket;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: dizem
 * Time: 11-4-28 下午3:45
 */
public class ServerDispatcher {
	private GameServer server;

	public ServerDispatcher(GameServer server) {
		this.server = server;
	}

	public void dispatch(SGSPacket packet) {
		if (packet.getOperation().equals(OP_SEND_CHAT_MESSAGE)) {
			server.playerTalk(packet);

		} else if (packet.getOperation().equals(OP_FINISH_CHOOSING_LORD_CHARACTER)) {
			server.distributeCharacters(packet);

		} else if (packet.getOperation().equals(OP_FINISH_CHOOSING_CHARACTER)) {
			server.setCharacter(packet.getPlayerId(),
					new org.dizem.sanguosha.model.card.character.Character(packet.getCharacterVO()));

		} else if (packet.getOperation().equals(OP_PHASE_JUDGE_END)) {
			synchronized (server.gameThread) {
				server.gameThread.notify();
			}
		}
	}
}
