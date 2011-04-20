package org.dizem.sanguosha.model.player;

/**
 * User: DIZEM
 * Time: 11-3-29 下午10:37
 */
public enum Phase {
	/**
	 * 回合开始
	 */
	START,

	/**
	 * 判断阶段
	 */
	JUDGE,

	/**
	 * 摸牌阶段
	 */
	DRAW,

	/**
	 * 出牌阶段
	 */
	PLAY,

	/**
	 * 弃牌阶段
	 */
	DISCARD,

	/**
	 * 回合结束
	 */
	FINISH,

	/**
	 * 离线
	 */
	NOT_ACTIVE
}
