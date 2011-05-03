package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.view.gameview.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * 手牌标签
 * <p/>
 * User: dizem.org
 * Time: 11-4-10 下午2:19
 */
public class HandCardLabel extends JLabel
		implements MouseMotionListener, MouseListener, Runnable {


	/**
	 * 牌点数字体
	 */
	private static final Font RANK_FONT = new Font("Consolas", Font.PLAIN, 12);
	/**
	 * 消息字体
	 */
	private static final Font MSG_FONT = new Font("微软雅黑", Font.PLAIN, 12);
	/**
	 * 花色
	 */
	private int suit;
	/**
	 * 点数
	 */
	private String rank;
	/**
	 * 颜色
	 */
	private Color color;
	/**
	 * 游戏窗体
	 */
	private GameFrame owner;
	/**
	 * 附加信息
	 */
	private String message;

	private Integer posX;

	private int posY;

	public void run() {
		synchronized (posX) {
			int stepX = Math.abs(posX - getX());

			int dir = posX - getX() > 0 ? 1 : -1;

			while (posX != getX()) {
				setLocation(getX() + dir, getY());
				try {
					Thread.sleep(1000 / stepX);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			setLocation(posX, posY);
		}
	}

	public HandCardLabel(AbstractCard card, GameFrame owner) {
		super(ImageUtil.getIcon("/card/" + card.getFilename()));
		this.owner = owner;
		suit = card.getSuit() - 1;
		rank = card.getRank();
		color = card.isRed() ? Color.RED : Color.BLACK;
		setName(UNSELECTED_TAG);
		setSize(90, 130);
		setToolTipText(card.getHtmlDescription());
		addMouseMotionListener(this);
		addMouseListener(this);
		setLocation(0, 0);
	}

	public HandCardLabel(AbstractCard card) {
		this(card, null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(IMG_SUIT[suit], 8, 9, null);
		g.setColor(color);
		g.setFont(RANK_FONT);
		g.drawString(rank, 8, 28);
		if (message != null) {
			g.setColor(Color.RED);
			g.setFont(MSG_FONT);
			int len = g.getFontMetrics().stringWidth(message);
			g.drawString(message, (90 - len) / 2, 110);
		}

		if (owner != null && owner.getCurrentPlayer().getPhase().equals(Phase.NOT_ACTIVE)) {
			g.drawImage(IMG_CARD_COVER, 0, 0, null);
		}
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

	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
}
