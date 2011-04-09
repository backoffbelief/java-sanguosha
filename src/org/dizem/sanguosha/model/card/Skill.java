package org.dizem.sanguosha.model.card;


import org.dizem.sanguosha.model.player.Player;

public class Skill {

	public final static int EFFECT_NONE = 0;
	public final static int EFFECT_INCREASE_LIFE = 1;
	public final static int EFFECT_DECREASE_LIFE = 2;
	public final static int EFFECT_GET_CARD = 3;
	public final static int EFFECT_DROP_HANDCARD = 4;
	public final static int EFFECT_DROP_EQUIPMENTCARD = 5;
	public final static int EFFECT_GIVE_CARD = 6;
	public final static int EFFECT_KILL_OTHER = 7;
	public final static int EFFECT_CANT_SHOW = 8;
	public final static int EFFECT_INCREASE_DISTANCE = 9;
	public final static int EFFECT_DECREASE_DISTANCE = 10;
	public final static int TO_PLAYER = 100;
	public final static int TO_DROP = 101;

	/**
	 * 技能名称
	 */
	private String name;
	/**
	 * 技能描述
	 */
	private String description;
	/**
	 * 是否立即生效
	 */
	private boolean isImmediate;
	/**
	 * 是否被动技能
	 */
	private boolean isPassive;
	/**
	 * 使用该技能需要消耗的手牌
	 */
	private String[] costHandCard;
	/**
	 * 消耗牌的去向
	 */
	private int costToWhere;
	/**
	 * 对谁使用，效果作用在谁身上
	 */
	private Player[] toPlayer;
	/**
	 * 使用距离
	 */
	private int distance;
	/**
	 * 需要回应的牌
	 */
	private String[] responseCard;
	/**
	 * 无回应时toPlayer的效果
	 */
	private int noResponseEffect;
	/**
	 * 无回应时toPlayer的效果的数量
	 */
	private int noResponseEffectCount;
	/**
	 * 有回应时toPlayer的效果
	 */
	private int responseEffect;
	/**
	 * 有回应时toPlayer的效果的数量
	 */
	private int responseEffectCount;
	// /** 有回应时他人的效果 */
	// private int responseEffectOther;
	// /** 有回应时他人的效果的数量 */
	// private int responseEffectOtherCount;


	public Skill(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Skill{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	public String getHtmlDescription() {
		return "<HTML>" + description.replaceAll("\\n", "<p>") + "</HTML>";
	}
}
