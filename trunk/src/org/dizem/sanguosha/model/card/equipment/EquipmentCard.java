package org.dizem.sanguosha.model.card.equipment;

import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.ICard;

public class EquipmentCard extends AbstractCard {


	public static int TYPE_HORSE_CARD_MINUS = 3;

	public static int TYPE_HORSE_CARD_PLUS = 2;

	public static int TYPE_ARMOR_CARD = 1;

	public static int TYPE_WEAPON_CARD = 0;

	private int cardType;

	private int range;

	public boolean isSelectable() {
		return name.startsWith("丈八蛇矛") || name.startsWith("雌雄双股剑")
				|| name.startsWith("寒冰剑");
	}

	public EquipmentCard(String pattern, String number, String name,
						 String description, String filename) {
		super(pattern, number, name, description, filename);
	}

	public EquipmentCard(String pattern, String number, String name,
						 String description, String filename, int cardType) {
		this(pattern, number, name, description, filename);
		this.cardType = cardType;
	}

	public EquipmentCard(String pattern, String number, String name,
						 String description, String filename, int cardType, int range) {
		this(pattern, number, name, description, filename, cardType);
		this.range = range;
	}

	public static EquipmentCard create(String pattern, String number, String name,
									   String description, String filename, String tag) {


		if (tag.equals("h1")) {
			return new EquipmentCard(pattern, number, name + "(+1)",
					description, filename, TYPE_HORSE_CARD_PLUS);

		} else if (tag.equals("h-1")) {
			return new EquipmentCard(pattern, number, name + "(-1)",
					description, filename, TYPE_HORSE_CARD_MINUS);

		} else if (tag.startsWith("w")) {
			int range = Integer.parseInt(tag.substring(1, tag.length()));
			return new EquipmentCard(pattern, number, name + "(+" + range + ")",
					description, filename, TYPE_WEAPON_CARD,
					range);

		} else {
			return new EquipmentCard(pattern, number, name,
					description, filename, TYPE_ARMOR_CARD);
		}
	}

	public int getCardType() {
		return cardType;
	}

	public int getRange() {
		return range;
	}

	public int getCategory() {
		return ICard.CATEGORY_EQUIPMENT;
	}

}
