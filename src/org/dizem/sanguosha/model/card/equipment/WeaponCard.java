package org.dizem.sanguosha.model.card.equipment;

/**
 * User: DIZEM
 * Time: 11-4-1 下午10:51
 */
public class WeaponCard extends EquipmentCard {
	/**
	 * Attack distance
	 */
	private int range;

	public WeaponCard(String pattern, String number, String name, String description, String filename, String range) {
		super(pattern, number, name, description, filename);
		this.range = Integer.parseInt(range);
	}

	public int getRange() {
		return range;
	}

}
