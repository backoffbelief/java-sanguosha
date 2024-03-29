package org.dizem.sanguosha.controller;

import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;

import static org.dizem.sanguosha.model.constants.Constants.*;

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

		if (packet.is(OP_INIT_CLIENT)) {//客户端接入
			this.client.showGameFrame(packet);

		} else if (packet.is(OP_UPDATE_PLAYERS)) {//添加玩家
			this.client.updatePlayers(packet);

		} else if (packet.is(OP_SEND_MESSAGE)) {//系统消息
			this.client.showMessage(packet.getMessage());

		} else if (packet.is(OP_SEND_CHAT_MESSAGE)) {//聊天消息
			this.client.showChatMessage(packet.getMessage());

		} else if (packet.is(OP_DISTRIBUTE_ROLE)) {//分配角色
			this.client.setRole(packet.getRole(), packet.getLordId());

		} else if (packet.is(OP_DISTRIBUTE_LORD_CHARACTER)) {//分配主公角色
			this.client.distributeLordCharacter(packet);

		} else if (packet.is(OP_DISTRIBUTE_CHARACTER)) {//分配其他玩家角色
			this.client.distributeCharacter(packet);

		} else if (packet.is(OP_DISTRIBUTE_CARD)) {//发牌
			this.client.distributeCards(packet);

		} else if (packet.is(OP_FINISH_CHOOSING_CHARACTER)) {//角色选择完毕
			this.client.setCharacter(packet);

		} else if (packet.is(OP_UPDATE_PLAYERS_INFO)) {//更新玩家信息
			this.client.updatePlayersInfo(packet);

		} else if (packet.is(OP_PHASE_START)) {//开始阶段
			client.startPhase(packet.getPlayerId());

		} else if (packet.is(OP_PHASE_JUDGE_BEGIN)) {//判定阶段
			client.judgePhase(packet.getPlayerId());

		} else if (packet.is(OP_PHASE_DRAW_BEGIN)) {//摸牌阶段
			client.drawPhase(packet);

		} else if (packet.is(OP_PHASE_PLAY_BEGIN)) {//出牌阶段
			client.playPhase(packet.getPlayerId());

		} else if (packet.is(OP_OFFER_CARD_TO)) {//向某人出牌
			client.beOfferredCardTo(packet);

		} else if (packet.is(OP_FEEDBACK)) {
			client.feedback(packet);

		} else if (packet.is(OP_DECREASE_LIFE)) {//减血
			client.decreaseLife(packet);

		} else if (packet.is(OP_PHASE_DISCARD_BEGIN)) {//弃牌阶段开始
			client.discard(packet);

		} else if (packet.is(OP_DISCARD)) {//弃牌
			client.discardEnd(packet);

		} else if (packet.is(OP_EAT_PEACH)) {
			client.eatPeach(packet);

		} else if (packet.is(OP_ADD_EQUIPMENT)) {
			client.addEquipmentCard(packet);
		}

	}

	public void dispatch(String jsonString) {
		SGSPacket packet = (SGSPacket) JSONUtil.convertToVO(jsonString, SGSPacket.class);
		dispatch(packet);
	}
}
