package org.dizem.sanguosha.view.component;

import craky.componentc.GridBorder;
import craky.util.UIUtil;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * 装备卡label
 *
 * User: DIZEM
 * Time: 11-4-10 下午2:24
 */
public class EquipmentLabel extends JLabel {

	/**
	 * 花色
	 */
	private int suit;
	/**
	 * 数字
	 */
	private String rank;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 颜色
	 */
	private Color color;
	/**
	 * 是否可选
	 */
	private boolean isSelectable;
	/**
	 * 显示字体
	 */
	private final Font equipmentTextFont = new Font("宋体", Font.PLAIN, 13);

	public EquipmentLabel(EquipmentCard card) {
		super();
		suit = card.getSuit() - 1;
		rank = card.getRank();
		name = card.getName();
		color = card.isRed() ? Color.RED : Color.WHITE;
		isSelectable = card.isSelectable();
		System.out.println(card.getName() + " " + card.isSelectable());
		setToolTipText(card.getHtmlDescription());
		setSize(115, 22);
		setName(UNSELECTED_TAG);
		initEvent();
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {

		UIUtil.actionLabel(this, new AbstractAction() {
			Border selectedBorder = new GridBorder(Color.YELLOW, 2, 2, 2, 2);

			public void actionPerformed(ActionEvent e) {
				if (isSelectable) {
					if (getName().equals(UNSELECTED_TAG)) {
						setName(SELECTED_TAG);
						setBorder(selectedBorder);
						
					} else {
						setName(UNSELECTED_TAG);
						setBorder(null);
					}
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(IMG_SUIT[suit], 8, 6, null);
		g.setColor(color);
		g.setFont(equipmentTextFont);
		g.drawString(rank, 19, 15);
		g.setColor(Color.WHITE);
		g.drawString(name, 30, 15);

	}

	@Override
	public String toString() {
		return "EquipmentLabel{" +
				"rank='" + rank + '\'' +
				", suit=" + suit +
				", name='" + name + '\'' +
				'}';
	}


}
