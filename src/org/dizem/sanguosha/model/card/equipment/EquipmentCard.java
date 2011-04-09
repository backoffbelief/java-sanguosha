package org.dizem.sanguosha.model.card.equipment;

import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.ICard;

public class EquipmentCard extends AbstractCard {

	public EquipmentCard(String pattern, String number, String name,
			String description, String filename) {
		super(pattern, number, name, description, filename);
		// TODO Auto-generated constructor stub
	}


	public static EquipmentCard create(String pattern, String number, String name,
			String description, String filename, String tag) {
		if(tag.equals("h1")) {
			return new HouseCardPlus(pattern, number, name,  description, filename);

		} else if(tag.equals("h-1")) {
			return new HouseCardMinus(pattern, number, name,  description, filename);

		} else if(tag.startsWith("w")) {
			return new WeaponCard(pattern, number, name,  description, filename, tag.substring(1, tag.length()));

		} else {
			return new ArmorCard(pattern, number, name,  description, filename);
		}
	}

	public int getCategory() {
		return ICard.CATEGORY_EQUIPMENT;
	}

}
