package org.dizem.sanguosha.view.gameview;

import craky.util.UIUtil;
import org.apache.log4j.Logger;
import org.dizem.common.ImageUtils;
import org.dizem.common.PanelViewer;
import org.dizem.common.TwoWayMap;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.card.*;
import org.dizem.sanguosha.model.card.Skill;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.view.component.SGSEquipmentLabel;
import org.dizem.sanguosha.view.component.SGSGameButton;
import org.dizem.sanguosha.view.component.SGSHandCardLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午7:17
 */
public class DashboardPane extends JLayeredPane
		implements ActionListener {

	private static Logger log = Logger.getLogger(DashboardPane.class);

	private static final int posY = 30;
	private static final int equipX = 0;
	private static final int cardX = 134;
	private static final int avatarX = cardX + 480;
	private static final Image imgDashboardAvatar = ImageUtils.getImage("system/dashboard-avatar.png");
	private static final Image imgDashboardEquip = ImageUtils.getImage("system/dashboard-equip.png");
	private static final Image imgBack = ImageUtils.getImage("system/dashboard-hand.png");

	private ActionListener listener = this;

	private JButton[] btnSkills;
	private JButton btnOfferHandCard;
	private JButton btnThrowHandCard;


	private static final Image[] IMG_HP_BIG = {
			ImageUtils.getImage("system/magatamas/small-0.png"),
			ImageUtils.getImage("system/magatamas/small-1.png"),
			ImageUtils.getImage("system/magatamas/small-2.png"),
			ImageUtils.getImage("system/magatamas/small-3.png"),
			ImageUtils.getImage("system/magatamas/small-4.png"),
			ImageUtils.getImage("system/magatamas/small-5.png")
	};

	private static final Image[] IMG_ROLE = {
			ImageUtils.getImage("system/roles/small-lord.png"),
			ImageUtils.getImage("system/roles/small-loyalist.png"),
			ImageUtils.getImage("system/roles/small-renegade.png"),
			ImageUtils.getImage("system/roles/small-rebel.png")
	};



	private org.dizem.sanguosha.model.card.character.Character character;
	private Player player;
	private Image imgAvatar;
	private Image imgKingdom;


	private TwoWayMap<AbstractCard, JLabel> handCardLabelMap = new TwoWayMap<AbstractCard, JLabel>();
	private TwoWayMap<Integer, JLabel> equipmentLabelMap = new TwoWayMap<Integer, JLabel>();
	private java.util.List<JLabel> handCardLabelList = new ArrayList<JLabel>();
	private Set<JLabel> cardSelectedSet = new HashSet<JLabel>();


	private int labelDisplayLevel = 100;

	public DashboardPane() {
		super();
		character = CharacterDeck.getInstance().popCharacters(1)[0];
		imgAvatar = ImageUtils.getImage("/generals/big/" + character.getFilename());
		imgKingdom = ImageUtils.getImage("/kingdom/icon/" + character.getKingdomImgName());

		player = new Player("dizem", Role.ROLE_NJ);
		//setLayout(null);
		setSize(480 + imgDashboardAvatar.getWidth(null)
				+ imgDashboardEquip.getWidth(null), imgDashboardAvatar.getHeight(null) + 30);
		setPreferredSize(getSize());
		initButtons();

		try {
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());
			player.getHandCards().add(Deck.getInstance().popCard());

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		for (AbstractCard card : player.getHandCards()) {
			addHandCardLabel(createCardLabel(card));
		}
		character.decreaseLife();
		character.decreaseLife();

	}

	private void initButtons() {
		btnOfferHandCard = new SGSGameButton("出牌");
		btnOfferHandCard.setLocation(avatarX, 0);
		btnOfferHandCard.addActionListener(listener);
		add(btnOfferHandCard);

		btnThrowHandCard = new SGSGameButton("弃牌");
		btnThrowHandCard.setLocation(avatarX + 62, 0);
		btnThrowHandCard.setEnabled(true);
		btnThrowHandCard.addActionListener(listener);
		add(btnThrowHandCard);

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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imgBack, cardX, 30, null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawDashboard(g);
		drawLife(g);
		drawName(g);
	}

	private void drawDashboard(Graphics g) {
		g.drawImage(imgDashboardEquip, equipX, posY, null);
		g.drawImage(imgDashboardAvatar, avatarX, 0, null);
		if (imgAvatar != null) {
			g.drawImage(imgAvatar, avatarX + 23, 64, null);
		}
		if (imgKingdom != null) {
			g.drawImage(imgKingdom, avatarX + 21, 62, null);
		}
		int x = avatarX - 3, y = 37;
		switch (player.getRole()) {
			case ROLE_ZG:
				g.drawImage(IMG_ROLE[0], x, y, null);
				break;
			case ROLE_ZC:
				g.drawImage(IMG_ROLE[1], x, y, null);
				break;
			case ROLE_NJ:
				g.drawImage(IMG_ROLE[2], x, y, null);
				break;
			case ROLE_FZ:
				g.drawImage(IMG_ROLE[3], x, y, null);
				break;
		}

	}

	private JLabel createCardLabel(final AbstractCard card) {

		final JLabel label = new SGSHandCardLabel(card);

		handCardLabelMap.put(card, label);

		UIUtil.actionLabel(label, new AbstractAction() {

			public void actionPerformed(ActionEvent e) {

				if (label.getName().equals(Constants.SELECTED_TAG)) {
					label.setName(Constants.UNSELECTED_TAG);
					label.setLocation(label.getX(), posY + 38 + 30);
					DashboardPane.this.repaint(label.getX(), label.getY() - 30, 90, 160);
					cardSelectedSet.remove(label);

					if (cardSelectedSet.size() == 0) {
						btnOfferHandCard.setEnabled(false);
					}

				} else {
					label.setName(Constants.SELECTED_TAG);
					label.setLocation(label.getX(), posY + 38 - 30);
					DashboardPane.this.repaint(label.getX(), label.getY(), 90, 160);
					cardSelectedSet.add(label);

					if (cardSelectedSet.size() > 0) {
						btnOfferHandCard.setEnabled(true);
					}
				}
			}
		});
		return label;
	}

	private JLabel createEquipmentLabel(final EquipmentCard card) {
		final JLabel label = new SGSEquipmentLabel(card);
		equipmentLabelMap.put(card.getCardType(), label);
		label.setLocation(10, 42 + posY + card.getCardType() * 32);
		return label;
	}

	private void updateHandCardGap() {
		int gap;
		if (480 > handCardLabelList.size() * 90) {
			gap = 90;
		} else {
			gap = 390 / (handCardLabelList.size() - 1);
		}
		for (int i = 0; i < handCardLabelList.size(); ++i) {
			handCardLabelList.get(i).setLocation(cardX + i * gap, posY + 38);
		}
	}

	private void drawLife(Graphics g) {
		int rate = (int) ((double) character.getLife() / character.getMaxLife() * 5 + 0.01);
		for (int i = 0; i < character.getLife(); ++i) {
			g.drawImage(IMG_HP_BIG[rate], avatarX + 2, 70 + i * 23, null);
		}
		for (int i = character.getLife(); i < character.getMaxLife(); ++i) {
			g.drawImage(IMG_HP_BIG[0], avatarX + 2, 70 + i * 23, null);
		}
	}

	private void drawName(Graphics g) {
		g.setColor(Color.WHITE);
		String name = player.getName();
		int nameWidth = g.getFontMetrics().stringWidth(name);
		g.drawString(name, avatarX + (122 - nameWidth) / 2 + 8, 53);
	}

	private void addHandCardLabel(JLabel handCardLabel) {
		add(handCardLabel, new Integer(labelDisplayLevel++));
		handCardLabelList.add(handCardLabel);
		updateHandCardGap();
	}

	private void removeHandCardLabel(JLabel handCardLabel) {
		remove(handCardLabel);
		handCardLabelList.remove(handCardLabel);
		//cardSelectedSet.remove(handCardLabel);
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


	public static void main(String[] args) {
		PanelViewer.display(new DashboardPane(), "Test");
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnOfferHandCard) {
			Iterator itCardLabel = cardSelectedSet.iterator();

			while (itCardLabel.hasNext()) {
				JLabel label = (JLabel) itCardLabel.next();
				AbstractCard card = handCardLabelMap.getKey(label);

				if (card instanceof EquipmentCard) {
					EquipmentCard equipmentCard = (EquipmentCard) card;
					if (!player.canAddEquipmentCard(equipmentCard)) {
						JLabel cardLabelToRemove = equipmentLabelMap.getValue(((EquipmentCard) card).getCardType());
						removeEquipmentCardLabel(cardLabelToRemove);
						player.removeEquipmentCard(equipmentCard.getCardType());
					}
					player.addEquipmentCard(equipmentCard);
					addEquipmentCardLabel(createEquipmentLabel(equipmentCard));
				}
				removeHandCardLabel(label);
				itCardLabel.remove();
			}
		} else if (e.getSource() == btnThrowHandCard) {
			addHandCardLabel(createCardLabel(Deck.getInstance().popCard()));
		}
	}


}
