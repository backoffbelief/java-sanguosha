package org.dizem.sanguosha.controller;

import org.dizem.common.AudioUtil;
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


		if (packet.getOperation().equals(OP_INIT_CLIENT)) {//客户端接入
			this.client.showGameFrame(packet);

		} else if (packet.getOperation().equals(OP_UPDATE_PLAYERS)) {//添加玩家
			AudioUtil.play("system/add-player.mp3");
			this.client.updatePlayers(packet);

		} else if (packet.getOperation().equals(OP_SEND_MESSAGE)) {//系统消息
			this.client.showMessage(packet.getMessage());

		} else if (packet.getOperation().equals(OP_SEND_CHAT_MESSAGE)) {//聊天消息
			this.client.showChatMessage(packet.getMessage());

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_ROLE)) {//分配角色
			this.client.setRole(packet.getRole(), packet.getLordId());

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_LORD_CHARACTER)) {//分配主公角色
			this.client.distributeLordCharacter(packet);

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_CHARACTER)) {//分配其他玩家角色
			this.client.distributeCharacter(packet);

		} else if (packet.getOperation().equals(OP_DISTRIBUTE_CARD)) {//发牌
			this.client.distributeCards(packet);

		} else if (packet.getOperation().equals(OP_FINISH_CHOOSING_CHARACTER)) {//角色选择完毕
			this.client.setCharacter(packet);

		} else if (packet.getOperation().equals(OP_UPDATE_PLAYERS_INFO)) {
			this.client.updatePlayersInfo(packet);

		} else if(packet.getOperation().equals(OP_PHASE_START)) {
			client.startPhase(packet.getPlayerId());

		} else if(packet.getOperation().equals(OP_PHASE_JUDGE_BEGIN)) {
			client.judgePhase(packet.getPlayerId());

		} else if(packet.getOperation().equals(OP_PHASE_DRAW_BEGIN)) {
			client.drawPhase(packet);
		}

	}

	public void dispatch(String jsonString) {
		SGSPacket packet = (SGSPacket) JSONUtil.convertToVO(jsonString, SGSPacket.class);
		dispatch(packet);
	}
}
