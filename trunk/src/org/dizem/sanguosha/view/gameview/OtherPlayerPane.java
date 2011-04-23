package org.dizem.sanguosha.view.gameview;

import org.apache.log4j.Logger;
import org.dizem.common.ImageUtils;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.Deck;
import org.dizem.sanguosha.model.player.Player;

import javax.swing.*;
import java.awt.*;

import static org.dizem.sanguosha.model.Constants.*;
/**
 * User: dizem
 * Time: 11-4-19 上午9:10
 */
public class OtherPlayerPane extends JPanel {

	public static final int DEFAULT_WIDTH = 132;
	public static final int DEFAULT_HEIGHT = 187;

	private static Logger log = Logger.getLogger(OtherPlayerPane.class);
	private org.dizem.sanguosha.model.card.character.Character character = CharacterDeck.getInstance().popCharacters(1)[0];

	private static final Image IMG_BACK = ImageUtils.getImage("system/photo-back.png");
	public static final Color COLOR_CARD_COUNT_BACK = new Color(244, 229, 181);


	private Image imgAvatar;
	private Image imgKingdom;
	private Image imgKingdomFrame;

	private Player player;

	public OtherPlayerPane(Player player) {
		super();
		imgAvatar = ImageUtils.getImage("/generals/small/" + character.getFilename());
		imgKingdom = ImageUtils.getImage("/kingdom/icon/" + character.getKingdomImgName());
		imgKingdomFrame = ImageUtils.getImage("/kingdom/frame/" + character.getKingdomImgName());
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.player = player;
		try {
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		character.decreaseLife();
		character.decreaseLife();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(IMG_BACK, 0, 0, null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		if (imgAvatar != null) {
			g.drawImage(imgAvatar, 5, 22, null);
		}
		if (imgKingdomFrame != null) {
			g.drawImage(imgKingdomFrame, 3, 20, null);
		}
		if (imgKingdom != null) {
			g.drawImage(imgKingdom, -3, -3, null);
		}
		g.setColor(COLOR_CARD_COUNT_BACK);
		g.fillRect(1, 103, 16, 16);
		g.setColor(Color.BLACK);
		g.drawString("" + player.getHandCards().size(), 5, 115);

		g.setColor(Color.WHITE);
		g.drawString(player.getName(), (DEFAULT_WIDTH - g.getFontMetrics().stringWidth(player.getName())) / 2 - 9, 15);

		g.drawImage(IMG_PHASE[player.getPhaseID()], 115, 120, null);
		drawLife(g);
	}

	private void drawLife(Graphics g) {
		int rate = (int) ((double) character.getLife() / character.getMaxLife() * 5 + 0.01);
		for (int i = 0; i < character.getLife(); ++i) {
			g.drawImage(IMG_HP_SMALL[rate], 30 + i * 17, 85, null);
		}
		for (int i = character.getLife(); i < character.getMaxLife(); ++i) {
			g.drawImage(IMG_HP_SMALL[0], 30 + i * 17, 85, null);
		}
	}

}
