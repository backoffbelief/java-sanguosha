package org.dizem.sanguosha.view.gameview;

import org.dizem.common.ImageUtils;
import org.dizem.common.PanelViewer;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.Deck;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午6:35
 */
public class CardImgPane extends JPanel {
	private AbstractCard card;
	private Image imgBack;
	public CardImgPane(AbstractCard card) {
		this.card = card;
		imgBack = ImageUtils.getImage("card//" + card.getFilename());
		setSize(imgBack.getWidth(null), imgBack.getHeight(null));
		setToolTipText("<html>" + card.getDescription().replaceAll("\\n", "<p>") + "</html>");
	}


	@Override
	public void paint(Graphics g) {
		g.drawImage(imgBack, 0, 0, null);
	}

	public static void main(String[] args) throws Exception {
		PanelViewer.display(new CardImgPane(Deck.getInstance().popCard()));
	}
}
