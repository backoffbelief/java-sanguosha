package org.dizem.sanguosha.view.component;

import craky.componentc.GridBorder;
import craky.util.UIUtil;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * User: DIZEM
 * Time: 11-4-10 下午2:24
 */
public class SGSEquipmentLabel extends JLabel
		 {

	private int suit;
	private String rank;
	private String name;
	private Color color;

	private final Font equipmentTextFont = new Font("宋体", Font.PLAIN, 13);

	public SGSEquipmentLabel(EquipmentCard card) {
		super();
		suit = card.getSuit() - 1;
		rank = card.getRank();
		name = card.getName();
		color = card.isRed() ? Color.RED : Color.WHITE;
		setToolTipText(card.getHtmlDescription());
		setSize(130, 20);
		setName(Constants.UNSELECTED_TAG);
		initEvent();
	}

	private void initEvent() {

		
		UIUtil.actionLabel(this, new AbstractAction() {
			Border selectedBorder = new GridBorder(Color.WHITE, 1, 1, 1, 1);

			public void actionPerformed(ActionEvent e) {
				if (getName().equals(Constants.UNSELECTED_TAG)) {
					setName(Constants.SELECTED_TAG);
					setLocation(getX() + 25, getY());
					setBorder(selectedBorder);
				} else {
					setName(Constants.UNSELECTED_TAG);
					setLocation(getX() - 25, getY());
					setBorder(null);
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(SGSHandCardLabel.imgSuit[suit], 8, 6, null);
		g.setColor(color);
		g.setFont(equipmentTextFont);
		g.drawString(rank, 19, 15);
		g.setColor(Color.WHITE);
		g.drawString(name, 30, 15);

	}

	@Override
	public String toString() {
		return "SGSEquipmentLabel{" +
				"rank='" + rank + '\'' +
				", suit=" + suit +
				", name='" + name + '\'' +
				'}';
	}


}
