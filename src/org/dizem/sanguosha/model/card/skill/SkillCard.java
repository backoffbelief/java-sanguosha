package org.dizem.sanguosha.model.card.skill;

import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.ICard;

/**
 * 锦囊牌
 */
public class SkillCard extends AbstractCard {

	public SkillCard(String pattern, String number, String name,
					 String description, String filename) {
		super(pattern, number, name, description, filename);
	}


	public int getCategory() {
		return ICard.CATEGORY_SKILL;
	}

}
