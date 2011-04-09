package org.dizem.sanguosha.model.card.equipment;


import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.Deck;

/**
 * User: DIZEM
 * Time: 11-4-1 下午10:51
 */
public class ArmorCard extends EquipmentCard {

	private Logger logger = Logger.getLogger(ArmorCard.class);

	public ArmorCard(String pattern, String number, String name, String description, String filename) {
		super(pattern, number, name, description, filename);
	}

	/**
	 *
	 * @param attackCard
	 * @return true if cancelled the attack
	 */
	public boolean cancelAttack(AbstractCard attackCard) {

		if(attackCard.getName().equals("杀")
				|| attackCard.getName().equals("万箭齐发")) {

			if(getName().equals("八卦阵")) {
				AbstractCard card = Deck.getInstance().popCard();
				return card.isRed();

			} else if(getName().equals("仁王盾")) {
				return !attackCard.isRed();
			}
		}
		return false;
	}
}
