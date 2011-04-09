package org.dizem.sanguosha.model.card;


public abstract class AbstractCard implements ICard {

	protected int suit;
	protected int color;
	protected String rank;
	protected String name;
	protected String description;
	protected String filename;

	public AbstractCard(String pattern, String number, String name,
						String description, String strImgFile) {
		super();
		if (pattern.equals("黑桃"))
			this.suit = ICard.PATTERN_SPADE;
		else if (pattern.equals("梅花"))
			this.suit = ICard.PATTERN_CLUB;
		else if (pattern.equals("红桃"))
			this.suit = ICard.PATTERN_HEART;
		else if (pattern.equals("方块"))
			this.suit = ICard.PATTERN_DIAMOND;

		this.color = (this.suit == ICard.PATTERN_SPADE
				|| this.suit == ICard.PATTERN_CLUB ? ICard.COLOR_BLACK
				: ICard.COLOR_RED);

		this.rank = number;
		this.name = name;
		this.description = description;
		this.filename = strImgFile;
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
