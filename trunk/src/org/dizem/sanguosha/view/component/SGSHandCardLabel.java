package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtils;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.card.AbstractCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * User: DIZEM
 * Time: 11-4-10 下午2:19
 */
public class SGSHandCardLabel extends JLabel
		implements MouseMotionListener, MouseListener {

	public static final Image[] imgSuit = {
			ImageUtils.getImage("system/suit/1.png"),
			ImageUtils.getImage("system/suit/2.png"),
			ImageUtils.getImage("system/suit/3.png"),
			ImageUtils.getImage("system/suit/4.png")
	};

	private static final Font font = new Font("Consolas", Font.PLAIN, 12);

	private int suit;
	private String rank;
	private Color color;


	public SGSHandCardLabel(AbstractCard card) {
		super(ImageUtils.getIcon("/card/" + card.getFilename()));
		suit = card.getSuit() - 1;
		rank = card.getRank();
		color = card.isRed() ? Color.RED : Color.BLACK;
		setName(Constants.UNSELECTED_TAG);
		setSize(90, 130);
		setToolTipText(card.getHtmlDescription());
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imgSuit[suit], 8, 9, null);
		g.setColor(color);
		g.setFont(font);
		g.drawString(rank, 8, 28);
	}

	@Override
	public String toString() {
		return "JLabel{" +
				"suit=" + suit +
				", rank='" + rank + '\'' +
				'}';
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		/*
			f(0) = f(90) = 0, f(45) = 30
			f(x) = a(x - 90) * x
			f(x) = a * -45 * 45 = 30 => a = -2/135
		  */
		double scale = -2. / 135.;
		double delta = scale * (e.getX() - 90) * e.getX();
		if (label.getName().equals(Constants.UNSELECTED_TAG)) {
			label.setLocation(label.getX(), (int) (30 + 38 - delta));
			label.updateUI();
		}
	}

	public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if (label.getName().equals("F")) {
			label.setLocation(label.getX(), 30 + 38);
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if (label.getName().equals("F")) {
			label.setLocation(label.getX(), 30 + 38);
		}
	}
}