package org.dizem.sanguosha.view.gameview;

import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.Deck;
import org.dizem.sanguosha.view.component.HandCardLabel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 弃牌显示
 * <p/>
 * User: dizem@126.com
 * Time: 11-5-1 下午4:04
 */
public class DiscardedPane extends JLayeredPane {

	/**
	 * 显示牌数上限
	 */
	public static final int MAX_CARD_COUNT = 10;
	/**
	 * 显示弃牌集合
	 */
	public List<HandCardLabel> labelList = new ArrayList<HandCardLabel>();

	/**
	 * 控件添加深度
	 */
	int depth = 1;


	/**
	 * 添加一张弃牌
	 *
	 * @param card
	 * @param message
	 */
	public synchronized void addCard(AbstractCard card, String message) {
		Deck.getInstance().addToBack(card);//加入弃牌堆

		if (labelList.size() == MAX_CARD_COUNT) {
			for (HandCardLabel label : labelList) {
				remove(label);
				label.setPosition(0, 0);
				new Thread(label).start();
			}
			labelList.clear();
		}
		repaint();

		HandCardLabel label = new HandCardLabel(card);
		label.setMessage(message);
		labelList.add(label);
		add(label, new Integer(depth++));
		updateCardLocation();
	}

	/**
	 * 调整弃牌位置
	 */
	private void updateCardLocation() {
		int gap;
		if (300 > labelList.size() * 90) {
			gap = 90;

		} else { //计算重叠间距
			gap = (int) (210. / (labelList.size() - 1));
		}
		int i = 0;
		for (HandCardLabel label : labelList) {
			label.setPosition(gap * i++, 0);
			new Thread(label).start();
		}
	}

	/**
	 * 构造函数
	 */
	public DiscardedPane() {
		setSize(300, 135);
		setLayout(null);
		setOpaque(false);
	}

}
