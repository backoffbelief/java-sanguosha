package org.dizem.sanguosha.model.card;

public class BasicCard extends AbstractCard {

	public BasicCard(String pattern,String number, String name,
			String description, String filename) {
		super(pattern, number, name, description, filename);
		// TODO Auto-generated constructor stub
	}


	
	public int getCategory() {
		return ICard.CATEGORY_BASIC;
	}

}
