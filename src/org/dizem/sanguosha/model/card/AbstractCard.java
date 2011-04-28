package org.dizem.sanguosha.model.card;


import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.vo.CardVO;

public abstract class AbstractCard implements ICard {

	protected int suit;
	protected int color;
	protected String rank;
	protected String name;
	protected String description;
	protected String filename;

	public static int getSuitCode(String pattern) {
		int suit = 0;
		if (pattern.equals("黑桃"))
			suit = ICard.PATTERN_SPADE;
		else if (pattern.equals("梅花"))
			suit = ICard.PATTERN_CLUB;
		else if (pattern.equals("红桃"))
			suit = ICard.PATTERN_HEART;
		else if (pattern.equals("方块"))
			suit = ICard.PATTERN_DIAMOND;
		return suit;
	}

	public static String getSuitString(int suit) {
		switch (suit) {
			case ICard.PATTERN_CLUB:
				return "梅花";
			case ICard.PATTERN_DIAMOND:
				return "方块";
			case ICard.PATTERN_SPADE:
				return "黑桃";
			default:
				return "红桃";
		}
	}

	public AbstractCard(String pattern, String number, String name,
						String description, String strImgFile) {
		super();
		this.suit = getSuitCode(pattern);

		this.color = (this.suit == ICard.PATTERN_SPADE
				|| this.suit == ICard.PATTERN_CLUB ? ICard.COLOR_BLACK
				: ICard.COLOR_RED);

		this.rank = number;
		this.name = name;
		this.description = description;
		this.filename = strImgFile;
	}


	public static AbstractCard createCard(CardVO vo) {
		AbstractCard card;
		switch (vo.getCardType()) {
			case -1:
				card = new BasicCard(getSuitString(vo.getSuit()), vo.getRank(),
						vo.getName(), vo.getDescription(), vo.getFilename());
				break;
			default:
				card = new EquipmentCard(getSuitString(vo.getSuit()), vo.getRank(), vo.getName(),
						vo.getDescription(), vo.getFilename(), vo.getCardType(), vo.getRange());
				break;

		}
		return card;
	}

	public AbstractCard() {
	}


	public int getColor() {
		return color;
	}

	public int getSuit() {
		return suit;
	}

	public String getName() {
		return name;
	}

	public String getRank() {
		return rank;
	}

	public String getFilename() {
		return filename;
	}

	public String getDescription() {
		return description;
	}

	public String getHtmlDescription() {
		return "<html>" + description.replaceAll("\\n", "<p>") + "</HTML>";
	}

	public boolean isRed() {
		return color == ICard.COLOR_RED;
	}

	@Override
	public String toString() {
		return "AbstractCard{" +
				"suit=" + suit +
				", color=" + color +
				", rank='" + rank + '\'' +
				", name='" + name + '\'' +
				//", description='" + description + '\'' +
				", filename='" + filename + '\'' +
				'}';
	}
}
