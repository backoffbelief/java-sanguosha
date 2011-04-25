package org.dizem.sanguosha.model.card;


import org.dizem.sanguosha.model.player.Player;

public class Skill {

	public Skill() {
	}

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

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHtmlDescription() {
		return "<HTML>" + description.replaceAll("\\n", "<p>") + "</HTML>";
	}

	public boolean isImmediate() {
		return isImmediate;
	}

	public void setImmediate(boolean immediate) {
		isImmediate = immediate;
	}

	public boolean isPassive() {
		return isPassive;
	}

	public void setPassive(boolean passive) {
		isPassive = passive;
	}

	public String[] getCostHandCard() {
		return costHandCard;
	}

	public void setCostHandCard(String[] costHandCard) {
		this.costHandCard = costHandCard;
	}

	public int getCostToWhere() {
		return costToWhere;
	}

	public void setCostToWhere(int costToWhere) {
		this.costToWhere = costToWhere;
	}

	public Player[] getToPlayer() {
		return toPlayer;
	}

	public void setToPlayer(Player[] toPlayer) {
		this.toPlayer = toPlayer;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String[] getResponseCard() {
		return responseCard;
	}

	public void setResponseCard(String[] responseCard) {
		this.responseCard = responseCard;
	}

	public int getNoResponseEffect() {
		return noResponseEffect;
	}

	public void setNoResponseEffect(int noResponseEffect) {
		this.noResponseEffect = noResponseEffect;
	}

	public int getNoResponseEffectCount() {
		return noResponseEffectCount;
	}

	public void setNoResponseEffectCount(int noResponseEffectCount) {
		this.noResponseEffectCount = noResponseEffectCount;
	}

	public int getResponseEffect() {
		return responseEffect;
	}

	public void setResponseEffect(int responseEffect) {
		this.responseEffect = responseEffect;
	}

	public int getResponseEffectCount() {
		return responseEffectCount;
	}

	public void setResponseEffectCount(int responseEffectCount) {
		this.responseEffectCount = responseEffectCount;
	}
}
