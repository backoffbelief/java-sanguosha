package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.view.gameview.DashboardPane;
import org.dizem.sanguosha.view.gameview.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: dizem.org
 * Time: 11-4-10 下午2:19
 */
public class HandCardLabel extends JLabel
		implements MouseMotionListener, MouseListener {

	private static final Font font = new Font("Consolas", Font.PLAIN, 12);

	private int suit;
	private String rank;
	private Color color;
	private GameFrame owner;
	private DashboardPane dashboard;
	private String message;

	public HandCardLabel(AbstractCard card, GameFrame owner) {
		super(ImageUtil.getIcon("/card/" + card.getFilename()));
		this.owner = owner;
		this.dashboard = owner.dashboard;
		suit = card.getSuit() - 1;
		rank = card.getRank();
		color = card.isRed() ? Color.RED : Color.BLACK;
		setName(UNSELECTED_TAG);
		setSize(90, 130);
		setToolTipText(card.getHtmlDescription());
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public HandCardLabel(AbstractCard card) {
		this(card, null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(IMG_SUIT[suit], 8, 9, null);
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
		if (owner == null || owner.getCurrentPlayer().getPhase().equals(Phase.NOT_ACTIVE))
			return;
		JLabel label = (JLabel) e.getSource();
		double scale = -2. / 135.;
		double delta = scale * (e.getX() - 90) * e.getX();
		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(label.getX(), (int) (30 + 38 - delta));
			label.updateUI();
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		if (owner == null || owner.getCurrentPlayer().getPhase().equals(Phase.NOT_ACTIVE))
			return;
		JLabel label = (JLabel) e.getSource();
		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(label.getX(), 30 + 38);
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
