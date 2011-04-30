package org.dizem.sanguosha.model.vo;

import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.Deck;
import org.dizem.sanguosha.model.card.equipment.ArmorCard;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.card.equipment.WeaponCard;

/**
 * User: dizem
 * Time: 11-4-26 上午8:08
 */
public class CardVO {
	protected int suit;
	protected int color;
	protected String rank;
	protected String name;
	protected String description;
	protected String filename;
	private int cardType;
	private int range;

	public CardVO() {
	}

	public CardVO(AbstractCard card) {
		this.suit = card.getSuit();
		this.color = card.getColor();
		this.rank = card.getRank();
		this.name = card.getName();
		this.description = card.getDescription();
		this.filename = card.getFilename();
		this.cardType = -1;

		if (card instanceof EquipmentCard) {
			this.range = ((EquipmentCard) card).getRange();
			this.cardType = ((EquipmentCard) card).getCardType();
		}
	}



	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public static void main(String[] args) {
		CardVO vo = new CardVO(Deck.getInstance().popCard());
		System.out.println(JSONUtil.convertToString(vo));
	}
}
