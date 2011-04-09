package org.dizem.sanguosha.view.gameview;

import org.dizem.sanguosha.model2.card.Card;
import org.dizem.common.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * User: DIZEM
 * Time: 11-3-25 下午10:15
 */
public class PlayerCardContainerPane extends JPanel {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 200;

	public PlayerCardContainerPane() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void paint(Graphics g) {
		System.out.println("paint");
		Image image = ImageUtils.getImage("system\\logo.png");
		g.drawImage(image, 100, 100, null);
		g.drawString("123", 100, 100);
	}

	private void draw(Graphics g, List<Card> cardList) {

	}
}
