package org.dizem.sanguosha.view.gameview;

import craky.componentc.JCButton;
import craky.util.UIUtil;
import org.dizem.common.ImageUtils;
import org.dizem.common.PanelViewer;
import org.dizem.sanguosha.model.card.*;
import org.dizem.sanguosha.model.card.Skill;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * User: DIZEM
 * Time: 11-4-1 下午7:17
 */
public class DashboardPane extends JLayeredPane
		implements ActionListener, MouseMotionListener, MouseListener {

	private static Font CARD_RANK_FONT = new Font("宋体", Font.BOLD, 18);


	private static final int posY = 30;
	private static final int equipX = 0;
	private static final int cardX = 134;
	private static final int avatarX = cardX + 480;
	private static final Image imgDashboardAvatar = ImageUtils.getImage("system/dashboard-avatar.png");
	private static final Image imgDashboardEquip = ImageUtils.getImage("system/dashboard-equip.png");
	private static final Image imgBack = ImageUtils.getImage("system/dashboard-hand.png");
	private ActionListener listener = this;
	//private JLabel lblDashboardCard = new JLabel(ImageUtils.getIcon("system/dashboard-hand.png"));

	private JButton[] btnSkills;
	private JButton btnOfferCard;
	private JButton btnThrowCard;


	private static final Image[] imgHpBig = {
			ImageUtils.getImage("system/magatamas/small-0.png"),
			ImageUtils.getImage("system/magatamas/small-1.png"),
			ImageUtils.getImage("system/magatamas/small-2.png"),
			ImageUtils.getImage("system/magatamas/small-3.png"),
			ImageUtils.getImage("system/magatamas/small-4.png"),
			ImageUtils.getImage("system/magatamas/small-5.png")
	};
	private static final Image[] imgSuit = {
			ImageUtils.getImage("system/suit/1.png"),
			ImageUtils.getImage("system/suit/2.png"),
			ImageUtils.getImage("system/suit/3.png"),
			ImageUtils.getImage("system/suit/4.png")
	};
	private static final Image[] imgRole = {
			ImageUtils.getImage("system/roles/small-lord.png"),
			ImageUtils.getImage("system/roles/small-loyalist.png"),
			ImageUtils.getImage("system/roles/small-renegade.png"),
			ImageUtils.getImage("system/roles/small-rebel.png")
	};
	private static final Image[] imgRoleDead = {
			ImageUtils.getImage("system/roles/small-lord.png"),
			ImageUtils.getImage("system/roles/small-loyalist.png"),
			ImageUtils.getImage("system/roles/small-renegade.png"),
			ImageUtils.getImage("system/roles/small-rebel.png")
	};


	private org.dizem.sanguosha.model.card.Character character;
	private Player player;
	private Image imgAvatar;
	private Image imgKingdom;
	private java.util.List<JLabel> lblCardList = new ArrayList<JLabel>();
	private Set<JLabel> cardSelectedSet = new HashSet<JLabel>();

	public DashboardPane() {
		super();

		character = CharacterDeck.getInstance().popCharacters(1)[0];
		imgAvatar = ImageUtils.getImage("/generals/big/" + character.getFilename());
		imgKingdom = ImageUtils.getImage("/kingdom/icon/" + character.getKingdomImgName());
		player = new Player("dizem", Role.ROLE_NJ);
		setLayout(null);
		setSize(480 + imgDashboardAvatar.getWidth(null)
				+ imgDashboardEquip.getWidth(null), imgDashboardAvatar.getHeight(null) + 30);
		initButtons();

		//add(lblDashboardCard);
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
			e.printStackTrace();
		}
		for (AbstractCard card : player.getHandCards()) {
			addCardLabel(createCardLabel(card));
		}
		//	add(lblDashboardCard);
		character.decreaseLife();
		character.decreaseLife();
//		lblCard.setSize(90, 130);
//		lblCard.setIcon(ImageUtils.getIcon("/card/" + player.getHandCards().get(0).getFilename()));

//		this.add(lblCard);

	}


	private void initButtons() {
		btnOfferCard = new JCButton("出牌");
		btnOfferCard.setSize(60, 30);
		btnOfferCard.setLocation(avatarX, 0);
		btnOfferCard.setEnabled(false);
		btnOfferCard.setFocusable(false);
		btnOfferCard.addActionListener(listener);
		add(btnOfferCard);

		btnThrowCard = new JCButton("弃牌");
		btnThrowCard.setSize(60, 30);
		btnThrowCard.setLocation(avatarX + 60, 0);
//		btnThrowCard.setEnabled(false);
		btnThrowCard.setFocusable(false);
		btnThrowCard.addActionListener(listener);
		add(btnThrowCard);

		btnSkills = new JButton[character.getSkills().length];
		for (int i = 0; i < btnSkills.length; ++i) {
			Skill skill = (Skill) character.getSkills()[i];
			btnSkills[i] = new JCButton(skill.getName());
			btnSkills[i].setToolTipText(skill.getHtmlDescription());
			btnSkills[i].setSize(60, 30);
			btnSkills[i].setLocation(avatarX + i * 60, 140 + posY);
			btnSkills[i].setFocusable(false);
			btnSkills[i].addActionListener(listener);
			add(btnSkills[i]);
		}
		if (btnSkills.length == 1) {
			btnSkills[0].setSize(120, 30);
		}
		//lblDashboardCard.setSize(480, 170);
		//lblDashboardCard.setLocation(cardX, posY);
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
				g.drawImage(imgRole[0], x, y, null);
				break;
			case ROLE_ZC:
				g.drawImage(imgRole[1], x, y, null);
				break;
			case ROLE_NJ:
				g.drawImage(imgRole[2], x, y, null);
				break;
			case ROLE_FZ:
				g.drawImage(imgRole[3], x, y, null);
				break;
		}

	}

	private JLabel createCardLabel(final AbstractCard card) {
		final JLabel label = new JLabel(ImageUtils.getIcon("/card/" + card.getFilename())) {
			int suit = card.getSuit() - 1;
			String rank = card.getRank();
			Color color = card.isRed() ? Color.RED : Color.BLACK;
			final Font font = new Font("Consolas", Font.PLAIN, 12);

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imgSuit[suit], 8, 9, null);
				g.setColor(color);
				g.setFont(font);
				g.drawString(rank, 8, 28);
			}
		};

		label.setName("F");
		label.setSize(90, 130);
		label.setToolTipText(card.getHtmlDescription());
		label.addMouseMotionListener(this);
		label.addMouseListener(this);
		UIUtil.actionLabel(label, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (label.getName().equals("T")) {
					label.setName("F");
					label.setLocation(label.getX(), posY + 38 + 30);
					DashboardPane.this.repaint(label.getX(), label.getY() - 30, 90, 160);
					cardSelectedSet.remove(label);
					if (cardSelectedSet.size() == 0) {
						btnOfferCard.setEnabled(false);
					}

				} else {
					label.setName("T");
					label.setLocation(label.getX(), posY + 38 - 30);
					DashboardPane.this.repaint(label.getX(), label.getY(), 90, 160);
					cardSelectedSet.add(label);
					if (cardSelectedSet.size() > 0) {
						btnOfferCard.setEnabled(true);
					}
				}
			}
		});
		return label;
	}

	private void updateCardGap() {
		int gap;
		if (480 > lblCardList.size() * 90) {
			gap = 90;
		} else {
			gap = 390 / (lblCardList.size() - 1);
		}
		for (int i = 0; i < lblCardList.size(); ++i) {
			lblCardList.get(i).setLocation(cardX + i * gap, posY + 38);
		}
	}

	private void drawLife(Graphics g) {
		int rate = (int) ((double) character.getLife() / character.getMaxLife() * 5 + 0.01);
		for (int i = 0; i < character.getLife(); ++i) {
			g.drawImage(imgHpBig[rate], avatarX + 2, 70 + i * 23, null);
		}
		for (int i = character.getLife(); i < character.getMaxLife(); ++i) {
			g.drawImage(imgHpBig[0], avatarX + 2, 70 + i * 23, null);
		}
	}

	private void drawName(Graphics g) {
		g.setColor(Color.WHITE);
		String name = player.getName();
		int nameWidth = g.getFontMetrics().stringWidth(name);
		g.drawString(name, avatarX + (122 - nameWidth) / 2 + 8, 53);
	}

	private void removeCardLabel(JLabel card) {
		remove(card);
		lblCardList.remove(card);
		updateCardGap();
	}

	int level = 100;

	private void addCardLabel(JLabel card) {
		add(card, new Integer(level++));
		lblCardList.add(card);
		updateCardGap();
	}

	public static void main(String[] args) {
		PanelViewer.display(new DashboardPane(), "Test");
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnOfferCard) {
			for (JLabel card : cardSelectedSet) {
				removeCardLabel(card);
			}
		} else if (e.getSource() == btnThrowCard) {
			addCardLabel(createCardLabel(Deck.getInstance().popCard()));
		}
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
		if (label.getName().equals("F")) {
			label.setLocation(label.getX(), (int) (posY + 38 - delta));
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
		JLabel label = (JLabel) e.getSource();
		if (label.getName().equals("F")) {
			label.setLocation(label.getX(), posY + 38);
		}
	}
}
