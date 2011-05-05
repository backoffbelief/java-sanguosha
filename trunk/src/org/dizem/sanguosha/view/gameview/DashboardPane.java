package org.dizem.sanguosha.view.gameview;

import craky.util.UIUtil;
import org.dizem.common.AudioUtil;
import org.dizem.common.ImageUtil;
import org.dizem.common.collection.TwoWayMap;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.BasicCard;
import org.dizem.sanguosha.model.card.Skill;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.view.component.EquipmentLabel;
import org.dizem.sanguosha.view.component.HandCardLabel;
import org.dizem.sanguosha.view.component.SGSGameButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午7:17
 */
public class DashboardPane extends JLayeredPane
		implements ActionListener {

	private static final int posY = 30;
	private static final int equipX = 0;
	private static final int cardX = 134;
	private static final int avatarX = cardX + 480;

	private ActionListener listener = this;

	private JButton[] btnSkills;
	private JButton btnOK;
	private JButton btnCancel;

	private org.dizem.sanguosha.model.card.character.Character character;
	private Image imgAvatar;
	private Image imgKingdom;
	private String playerName;

	private TwoWayMap<AbstractCard, JLabel> handCardLabelMap = new TwoWayMap<AbstractCard, JLabel>();
	private TwoWayMap<Integer, JLabel> equipmentLabelMap = new TwoWayMap<Integer, JLabel>();
	private java.util.List<JLabel> handCardLabelList = new ArrayList<JLabel>();
	private Set<JLabel> cardSelectedSet = new HashSet<JLabel>();


	private int labelDisplayLevel = 100;
	private boolean characterChoosed = false;
	private GameFrame owner;

	public DashboardPane(String playerName, GameFrame owner) {
		super();
		this.owner = owner;
		this.playerName = playerName;
		setSize(480 + IMG_DASHBOARD_AVATAR.getWidth(null)
				+ IMG_DASHBOARD_EQUIP.getWidth(null), IMG_DASHBOARD_AVATAR.getHeight(null) + 30);
		setPreferredSize(getSize());
		setOpaque(false);
		initButtons();
	}


	public void setCharacter(org.dizem.sanguosha.model.card.character.Character character) {
		this.character = character;
		imgAvatar = ImageUtil.getImage("/generals/big/" + character.getPNGFilename());
		imgKingdom = ImageUtil.getImage("/kingdom/icon/" + character.getKingdomImgName());
		characterChoosed = true;
		initButtons();
		repaint();
	}

	/**
	 * 初始化按钮
	 * 如果角色为选定，则设定确定、取消按钮
	 * 否则根据角色的技能设定技能按钮
	 */
	private void initButtons() {
		if (!characterChoosed) {
			btnOK = new SGSGameButton("确定");
			btnOK.setLocation(avatarX, 0);
			btnOK.addActionListener(listener);
			add(btnOK);

			btnCancel = new SGSGameButton("取消");
			btnCancel.setLocation(avatarX + 62, 0);
			btnCancel.setEnabled(true);
			btnCancel.addActionListener(listener);
			add(btnCancel);

		} else {

			btnSkills = new JButton[character.getSkills().length];
			for (int i = 0; i < btnSkills.length; ++i) {
				Skill skill = (Skill) character.getSkills()[i];
				btnSkills[i] = new SGSGameButton(skill.getName(), skill.getHtmlDescription());
				btnSkills[i].setLocation(avatarX + i * 62 + i, 140 + posY);
				btnSkills[i].addActionListener(listener);
				add(btnSkills[i]);
			}
			if (btnSkills.length == 1) {
				btnSkills[0].setSize(124, 30);
			}
		}
	}

	/**
	 * 重绘组建
	 *
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(IMG_DASHBOARD_BACK, cardX, 30, null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//dashboard
		g.drawImage(IMG_DASHBOARD_EQUIP, equipX, posY, null);
		g.drawImage(IMG_DASHBOARD_AVATAR, avatarX, 0, null);
		if (imgAvatar != null) {
			g.drawImage(imgAvatar, avatarX + 23, 64, null);
		}
		if (imgKingdom != null) {
			g.drawImage(imgKingdom, avatarX + 21, 62, null);
		}

		//血条
		if (characterChoosed) {
			int rate = (int) ((double) character.getLife() / character.getMaxLife() * 5 + 0.01);
			for (int i = 0; i < character.getLife(); ++i) {
				g.drawImage(IMG_HP_SMALL[rate], avatarX + 2, 70 + i * 23, null);
			}
			for (int i = character.getLife(); i < character.getMaxLife(); ++i) {
				g.drawImage(IMG_HP_SMALL[0], avatarX + 2, 70 + i * 23, null);
			}
		}
		//名字
		g.setColor(Color.WHITE);
		int nameWidth = g.getFontMetrics().stringWidth(playerName);
		g.drawString(playerName, avatarX + (122 - nameWidth) / 2 + 8, 53);
	}


	private int canSelectCardCount = 1;

	private static final String[] CANNOT_USE_LIST = {
			"无懈可击", "闪"
	};

	private boolean canUseCard(AbstractCard card) {
		for (String name : CANNOT_USE_LIST) {
			if (card.getName().equals(name))
				return false;
		}
		if (card.equals("桃")) {
			return character.getLife() < character.getMaxLife();
		}
		return true;
	}

	/**
	 * 用手牌创建label
	 *
	 * @param card 手牌
	 * @return label
	 */
	private JLabel createCardLabel(final AbstractCard card) {

		final JLabel label = new HandCardLabel(card, owner);

		handCardLabelMap.put(card, label);

		//注册单击事件
		UIUtil.actionLabel(label, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				//如果玩家在NOT_ACTIVE阶段 不作处理
				if (owner.getCurrentPlayer().getPhase().equals(Phase.NOT_ACTIVE))
					return;

				//如果当前手牌已经选择，则撤销选中
				if (label.getName().equals(SELECTED_TAG)) {
					label.setName(UNSELECTED_TAG);
					label.setLocation(label.getX(), 38 + 30);
					DashboardPane.this.repaint(label.getX(), label.getY() - 30, 90, 160);
					cardSelectedSet.remove(label);

					if (cardSelectedSet.size() == 0) {
						btnOK.setEnabled(false);
					}


				} else if (canUseCard(handCardLabelMap.getKey(label))) {


					label.setName(SELECTED_TAG);
					if (canSelectCardCount == cardSelectedSet.size()) {
						unselectDefalutSelectedCard();
					}
					selectBasicCard(handCardLabelMap.getKey(label));
					label.setLocation(label.getX(), posY + 38 - 30);
					DashboardPane.this.repaint(label.getX(), label.getY(), 90, 160);
					cardSelectedSet.add(label);
				}
			}


		});
		return label;
	}

	/**
	 * 撤销选中第一张已经选中的手牌
	 */
	private void unselectDefalutSelectedCard() {
		Iterator itCardLabel = cardSelectedSet.iterator();

		if (itCardLabel.hasNext()) {
			JLabel label = (JLabel) itCardLabel.next();
			label.setLocation(label.getX(), 38 + 30);
			cardSelectedSet.remove(label);
		}
	}

	/**
	 * 用装备牌创建label
	 *
	 * @param card 装备牌
	 * @return label
	 */
	private JLabel createEquipmentLabel(final EquipmentCard card) {
		final JLabel label = new EquipmentLabel(card);
		equipmentLabelMap.put(card.getCardType(), label);
		label.setLocation(10, 42 + posY + card.getCardType() * 32);
		return label;
	}

	/**
	 * 调整手牌放置间距
	 */
	private void updateHandCardGap() {
		int gap;
		//如果手牌总宽度小于面板 则不需要重叠
		if (480 > handCardLabelList.size() * 90) {
			gap = 90;

		} else { //计算重叠间距
			gap = 390 / (handCardLabelList.size() - 1);
		}
		for (int i = 0; i < handCardLabelList.size(); ++i) {
			handCardLabelList.get(i).setLocation(cardX + i * gap, posY + 38);
		}
	}

	public void addHandCard(AbstractCard card) {
		addHandCardLabel(createCardLabel(card));
		repaint();
	}

	public void addHandCardLabel(JLabel handCardLabel) {
		add(handCardLabel, new Integer(labelDisplayLevel++));
		handCardLabelList.add(handCardLabel);
		updateHandCardGap();
	}

	private void removeHandCardLabel(JLabel handCardLabel) {
		remove(handCardLabel);
		handCardLabelList.remove(handCardLabel);
		handCardLabelMap.removeByValue(handCardLabel);
		updateHandCardGap();
	}

	private void addEquipmentCardLabel(JLabel equipmentCardLabel) {
		add(equipmentCardLabel, new Integer(labelDisplayLevel++));
	}

	private void removeEquipmentCardLabel(JLabel equipmentCardLabel) {
		remove(equipmentCardLabel);
		equipmentLabelMap.removeByValue(equipmentCardLabel);
	}


	/**
	 * 处理按钮事件
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnOK) {

			if (owner.getCurrentPlayer().getPhase() == Phase.PLAY) {
				useHandCard();

			} else if (owner.getCurrentPlayer().getPhase() == Phase.DISCARD) {

			}


		} else if (e.getSource() == btnCancel) {
//			addHandCardLabel(createCardLabel(Deck.getInstance().popCard()));
			if (owner.getCurrentPlayer().getPhase() == Phase.FEEDBACK) {
				feedback(null);
			}
			btnCancel.setEnabled(false);
		}
	}

	private void feedback(AbstractCard cardToFeedback) {

		if (owner.getFeedbackCard().getName().equals("杀")) {
			owner.getClient().sendFeedbackInfo(cardToFeedback);
			if (cardToFeedback == null) {
				owner.getClient().sendDecreaseLifeInfo();
			}
		}
	}


	/**
	 * 设置当前玩家的角色势力
	 *
	 * @param role 角色势力
	 */
	public void setRole(final Role role) {
		owner.getCurrentPlayer().setRole(role);
		final JLabel roleLabel = new JLabel(IMG_ROLE[owner.getCurrentPlayer().getRoleID()]);
		roleLabel.setSize(IMG_ROLE[owner.getCurrentPlayer().getRoleID()].getIconWidth(),
				IMG_ROLE[owner.getCurrentPlayer().getRoleID()].getIconHeight());
		final int x = avatarX - 3, y = 140;
		roleLabel.setLocation(x, y);

		//将势力label天添加至面板最上层
		add(roleLabel, new Integer(labelDisplayLevel++));

		//将势力label平移至相应坐标
		new Thread(new Runnable() {
			public void run() {
				while (roleLabel.getY() > 38) {
					roleLabel.setLocation(x, roleLabel.getY() - 2);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 显示出牌动画效果
	 *
	 * @param card 出牌
	 */
	public void showEffect(AbstractCard card) {
		final JLabel effectLabel = new JLabel();
		effectLabel.setSize(256, 256);
		effectLabel.setOpaque(false);
		effectLabel.setLocation(400, -50);
		add(effectLabel, new Integer(labelDisplayLevel++));

		ImageIcon[] imgList = new ImageIcon[0];
		if (card.getName().equals("杀")) {
			AudioUtil.play(character.isMale() ? AUDIO_SHA_MALE : AUDIO_SHA_FEMALE);
			imgList = IMG_SHA;


		} else if (card.getName().equals("闪")) {
			AudioUtil.play(character.isMale() ? AUDIO_SHAN_MALE : AUDIO_SHAN_FEMALE);
			imgList = IMG_SHAN;
		}

		final ImageIcon[] finalImgList = imgList;
		new Thread(new Runnable() {
			public void run() {
				for (ImageIcon img : finalImgList) {
					effectLabel.setIcon(img);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				remove(effectLabel);
				repaint();
			}
		}).start();
	}

	/**
	 * 使用选中的手牌
	 */
	public void useHandCard() {
		Iterator itCardLabel = cardSelectedSet.iterator();

		while (itCardLabel.hasNext()) {
			JLabel label = (JLabel) itCardLabel.next();
			AbstractCard card = handCardLabelMap.getKey(label);

			owner.addDiscardedCard(card, playerName + "出牌");

			if (card instanceof EquipmentCard) {
				AudioUtil.play(AUDIO_ADD_EQUIPMENT);
				EquipmentCard equipmentCard = (EquipmentCard) card;

				if (!owner.getCurrentPlayer().canAddEquipmentCard(equipmentCard)) {
					JLabel cardLabelToRemove = equipmentLabelMap.getValue(((EquipmentCard) card).getCardType());
					removeEquipmentCardLabel(cardLabelToRemove);
					owner.getCurrentPlayer().removeEquipmentCard(equipmentCard.getCardType());
				}
				owner.getCurrentPlayer().addEquipmentCard(equipmentCard);
				addEquipmentCardLabel(createEquipmentLabel(equipmentCard));


			} else if (card instanceof BasicCard) {
				useBasicCard(card);
			}
			removeHandCardLabel(label);
			itCardLabel.remove();
		}

		repaint();
	}

	public void useBasicCard(AbstractCard card) {
		if (card.getName().equals("杀")) {
			showEffect(card);
			for (OtherPlayerPane pane : owner.otherPlayerPaneList) {
				if (pane.isSelected()) {
					owner.offerCardTo(card, pane.getPlayer().getPlayerId());
					return;
				}
			}
		}

//		for (OtherPlayerPane pane : owner.otherPlayerPaneList) {
//			if (pane.isSelected())
//				pane.setSelected(false);
//		}
	}

	public void selectBasicCard(AbstractCard card) {

		if (card.getName().equals("杀") || card.getName().equals("决斗")) {
			for (OtherPlayerPane pane : owner.otherPlayerPaneList) {
				if (Math.abs(pane.getPlayer().getPlayerId() - owner.getCurrentPlayerID()) <= 1) { //todo fixme
					pane.setCanSelect(true);
				} else {
					pane.setCanSelect(false);
				}
			}
		}
	}

	/**
	 * 是否可以出牌
	 *
	 * @param flag
	 */
	public void setOfferable(boolean flag) {
		btnOK.setEnabled(flag);
	}


	public void setCancelable(boolean flag) {
		btnCancel.setEnabled(flag);
	}

	public void decreaseLife() {
		character.decreaseLife();
		final JLabel labelLight = new JLabel();
		labelLight.setSize(172, 170);
		labelLight.setOpaque(false);
		labelLight.setLocation(avatarX - 20, 20);
		add(labelLight, new Integer(labelDisplayLevel++));

		new Thread(new Runnable() {
			public void run() {
				for (ImageIcon img : IMG_DAO_GUANG) {
					labelLight.setIcon(img);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				remove(labelLight);
				repaint();
			}
		}).start();
		AudioUtil.play(AUDIO_HIT);
		repaint();
	}
}
