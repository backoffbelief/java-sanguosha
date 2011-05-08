package org.dizem.sanguosha.controller;

import org.dizem.sanguosha.model.vo.SGSPacket;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * 调度服务器收到的请求
 * <p/>
 * User: dizem
 * Time: 11-4-28 下午3:45
 */
public class ServerDispatcher {
	/**
	 * 服务器实例
	 */
	private GameServer server;

	/**
	 * 构造函数
	 *
	 * @param server 服务器
	 */
	public ServerDispatcher(GameServer server) {
		this.server = server;
	}

	/**
	 * 处理请求
	 *
	 * @param packet 请求数据包
	 */
	public void dispatch(SGSPacket packet) {
		if (packet.is(OP_SEND_CHAT_MESSAGE)) {//客户端聊天
			server.playerTalk(packet);

		} else if (packet.is(OP_FINISH_CHOOSING_LORD_CHARACTER)) {//主公选择武将角色结束
			server.distributeCharacters(packet);

		} else if (packet.is(OP_FINISH_CHOOSING_CHARACTER)) {//其他玩家选择武将角色结束
			server.setCharacter(packet.getPlayerId(),
					new org.dizem.sanguosha.model.card.character.Character(packet.getCharacterVO()));

		} else if (packet.is(OP_PHASE_JUDGE_END)) {//当前玩家判定阶段结束
			notifyServer();

		} else if (packet.is(OP_PHASE_DRAW_END)) {//当前玩家摸牌阶段结束
			notifyServer();

		} else if (packet.is(OP_OFFER_CARD_TO)) {
			server.offerCard(packet);

		} else if (packet.is(OP_FEEDBACK)) {
			server.feedback(packet);

		} else if (packet.is(OP_DECREASE_LIFE)) {
			server.decreaseLife(packet);

		} else if (packet.is(OP_PHASE_PLAY_END)) {
			notifyServer();

		} else if (packet.is(OP_DISCARD)) {
			server.discard(packet);
			notifyServer();

		} else if (packet.is(OP_PHASE_DISCARD_END)) {
			notifyServer();

		} else if (packet.is(OP_EAT_PEACH)) {
			server.eatPeach(packet);

		} else if (packet.is(OP_ADD_EQUIPMENT)) {
			server.addEquipmentCard(packet);
		}
	}


	/**
	 * 通知服务端游戏线程继续执行
	 */
	public void notifyServer() {
		synchronized (server.gameThread) {
			server.gameThread.notify();
		}
	}
}
