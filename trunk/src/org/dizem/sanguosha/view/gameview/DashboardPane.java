package org.dizem.sanguosha.view.gameview;

import craky.util.UIUtil;
import org.apache.log4j.Logger;
import org.dizem.common.ImageUtil;
import org.dizem.common.TwoWayMap;
import org.dizem.sanguosha.model.card.*;
import org.dizem.sanguosha.model.card.Skill;
import org.dizem.sanguosha.model.card.character.*;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.view.component.HandCardLabel;
import org.dizem.sanguosha.view.component.EquipmentLabel;
import org.dizem.sanguosha.view.component.SGSGameButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static org.dizem.sanguosha.model.Constants.*;

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

	private ActionListener listener = this;

	private JButton[] btnSkills;
	private JButton btnOK;
	private JButton btnCancel;

	private org.dizem.sanguosha.model.card.character.Character character;
	private Player player;
	private Image imgAvatar;
	private Image imgKingdom;
	private String playerName;

	private TwoWayMap<AbstractCard, JLabel> handCardLabelMap = new TwoWayMap<AbstractCard, JLabel>();
	private TwoWayMap<Integer, JLabel> equipmentLabelMap = new TwoWayMap<Integer, JLabel>();
	private java.util.List<JLabel> handCardLabelList = new ArrayList<JLabel>();
	private Set<JLabel> cardSelectedSet = new HashSet<JLabel>();


	private int labelDisplayLevel = 100;
	private boolean characterChoosed = false;

	public DashboardPane(String playerName) {
		super();
		this.player = new Player(playerName);
		this.playerName = playerName;
		setSize(480 + IMG_DASHBOARD_AVATAR.getWidth(null)
				+ IMG_DASHBOARD_EQUIP.getWidth(null), IMG_DASHBOARD_AVATAR.getHeight(null) + 30);
		setPreferredSize(getSize());
		setOpaque(false);
		initButtons();
	}

	public DashboardPane(Player player) {
		this(player.getName());

		this.player = player;

		for (AbstractCard card : player.getHandCards()) {
			addHandCardLabel(createCardLabel(card));
		}
	}

	public void setCharacter(org.dizem.sanguosha.model.card.character.Character character) {
		player.setCharacter(character);
		this.character = player.getCharacter();
		imgAvatar = ImageUtil.getImage("/generals/big/" + character.getPNGFilename());
		imgKingdom = ImageUtil.getImage("/kingdom/icon/" + character.getKingdomImgName());
		characterChoosed = true;
		initButtons();
		repaint();
	}

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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(IMG_DASHBOARD_BACK, cardX, 30, null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawDashboard(g);
		drawLife(g);
		drawName(g);
	}

	private void drawDashboard(Graphics g) {
		g.drawImage(IMG_DASHBOARD_EQUIP, equipX, posY, null);
		g.drawImage(IMG_DASHBOARD_AVATAR, avatarX, 0, null);
		if (imgAvatar != null) {
			g.drawImage(imgAvatar, avatarX + 23, 64, null);
		}
		if (imgKingdom != null) {
			g.drawImage(imgKingdom, avatarX + 21, 62, null);
		}

	}

	private JLabel createCardLabel(final AbstractCard card) {

		final JLabel label = new HandCardLabel(card);

		handCardLabelMap.put(card, label);

		UIUtil.actionLabel(label, new AbstractAction() {

			public void actionPerformed(ActionEvent e) {

				if (label.getName().equals(SELECTED_TAG)) {
					label.setName(UNSELECTED_TAG);
					label.setLocation(label.getX(), posY + 38 + 30);
					DashboardPane.this.repaint(label.getX(), label.getY() - 30, 90, 160);
					cardSelectedSet.remove(label);

					if (cardSelectedSet.size() == 0) {
						btnOK.setEnabled(false);
					}

				} else {
					label.setName(SELECTED_TAG);
					label.setLocation(label.getX(), posY + 38 - 30);
					DashboardPane.this.repaint(label.getX(), label.getY(), 90, 160);
					cardSelectedSet.add(label);

					if (cardSelectedSet.size() > 0) {
						btnOK.setEnabled(true);
					}
				}
			}
		});
		return label;
	}

	private JLabel createEquipmentLabel(final EquipmentCard card) {
		final JLabel label = new EquipmentLabel(card);
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
		if (characterChoosed) {
			int rate = (int) ((double) character.getLife() / character.getMaxLife() * 5 + 0.01);
			for (int i = 0; i < character.getLife(); ++i) {
				g.drawImage(IMG_HP_SMALL[rate], avatarX + 2, 70 + i * 23, null);
			}
			for (int i = character.getLife(); i < character.getMaxLife(); ++i) {
				g.drawImage(IMG_HP_SMALL[0], avatarX + 2, 70 + i * 23, null);
			}
		}
	}

	private void drawName(Graphics g) {
		g.setColor(Color.WHITE);
		int nameWidth = g.getFontMetrics().stringWidth(playerName);
		g.drawString(playerName, avatarX + (122 - nameWidth) / 2 + 8, 53);
	}

	private void addHandCardLabel(JLabel handCardLabel) {
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

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnOK) {
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
		} else if (e.getSource() == btnCancel) {
			addHandCardLabel(createCardLabel(Deck.getInstance().popCard()));
		}
	}


	public void setRole(final Role role) {
		player.setRole(role);
		final JLabel roleLabel = new JLabel(IMG_ROLE[player.getRoleID()]);
		roleLabel.setSize(IMG_ROLE[player.getRoleID()].getIconWidth(), IMG_ROLE[player.getRoleID()].getIconHeight());
		final int x = avatarX - 3, y = 140;
		roleLabel.setLocation(x, y);
		add(roleLabel, new Integer(labelDisplayLevel++));

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
}
