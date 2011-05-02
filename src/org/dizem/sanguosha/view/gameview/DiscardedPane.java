package org.dizem.sanguosha.view.gameview;

import org.dizem.common.PanelViewer;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.Deck;
import org.dizem.sanguosha.view.component.HandCardLabel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 弃牌显示
 *
 * User: dizem@126.com
 * Time: 11-5-1 下午4:04
 */
public class DiscardedPane extends JLayeredPane {

	/**
	 * 显示牌数上限
	 */
	public static final int MAX_CARD_COUNT = 3;
	/**
	 * 显示弃牌集合
	 */
	public List<HandCardLabel> labelSet = new ArrayList<HandCardLabel>();

	/**
	 * 控件添加深度
	 */
	int depth = 1;

	/**
	 * 添加一张弃牌
	 * @param card
	 * @param message
	 */
	public void addCard(AbstractCard card, String message) {
		Deck.getInstance().addToBack(card);//加入弃牌堆

		if(labelSet.size() == MAX_CARD_COUNT) {
			for(HandCardLabel label : labelSet) {
				remove(label);
			}
			labelSet.clear();
		}

		HandCardLabel label = new HandCardLabel(card);
		label.setMessage(message);
		add(label, new Integer(depth++));
		updateCardLocation(90);
	}

	/**
	 * 调整弃牌位置
	 * @param width	总宽度
	 */
	private void updateCardLocation(int width) {
		
	}

	/**
	 * 清空显示
	 */
	public void clearCards() {

	}

	public DiscardedPane() {
		setSize(300, 200);
	}


	public static void main(String[] args) {
		PanelViewer.display(new DiscardedPane());
	}

}
