package org.dizem.sanguosha.view.gameview;

import org.dizem.common.AudioUtil;
import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.player.Phase;
import org.dizem.sanguosha.model.player.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * 对手面板
 * <p/>
 * User: dizem
 * Time: 11-4-19 上午9:10
 */
public class OtherPlayerPane extends JPanel {

	/**
	 * 默认宽度
	 */
	public static final int DEFAULT_WIDTH = 132;
	/**
	 * 默认高度
	 */
	public static final int DEFAULT_HEIGHT = 187;
	/**
	 * 背景图片
	 */
	private static final Image IMG_BACK = ImageUtil.getImage("system/photo-back.png");
	/**
	 * 牌数背景色
	 */
	public static final Color COLOR_CARD_COUNT_BACK = new Color(244, 229, 181);

	/**
	 * 武将图片
	 */
	private Image imgAvatar;
	/**
	 * 国家图片
	 */
	private Image imgKingdom;
	/**
	 * 武将边框
	 */
	private Image imgKingdomFrame;
	/**
	 * 玩家对象
	 */
	public Player player;
	/**
	 * 玩家姓名
	 */
	private String playerName;
	/**
	 * 手牌数
	 */
	private int handCardCount;
	/**
	 * 武将角色是否已经确定
	 */
	private boolean characterChoosed = false;
	/**
	 * 当前对手是否可选
	 */
	private boolean canSelect = false;
	/**
	 * 当前对手是否已被选
	 */
	private boolean isSelected = false;
	/**
	 * 游戏窗体
	 */
	private GameFrame owner;

	/**
	 * 构造函数
	 *
	 * @param owner
	 */
	public OtherPlayerPane(final GameFrame owner) {
		super();
		this.owner = owner;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!canSelect)
					return;
				if (isSelected) {
					setBorder(null);
					owner.dashboard.setOfferable(isSelected = false);
				} else {
					setBorder(new LineBorder(Color.RED, 5));
					owner.dashboard.setOfferable(isSelected = true);
				}
			}
		});
	}

	/**
	 * 设置玩家对象
	 *
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
		setPlayerName(player.getName());
		repaint();
	}

	/**
	 * 设置玩家姓名
	 *
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * 更新角色
	 */
	public void setCharacter() {
		imgAvatar = ImageUtil.getImage("/generals/small/" + player.getCharacter().getPNGFilename());
		imgKingdom = ImageUtil.getImage("/kingdom/icon/" + player.getCharacter().getKingdomImgName());
		imgKingdomFrame = ImageUtil.getImage("/kingdom/frame/" + player.getCharacter().getKingdomImgName());
		characterChoosed = true;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(IMG_BACK, 0, 0, null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		if (imgAvatar != null) {
			g.drawImage(imgAvatar, 5, 22, null);
		}
		if (imgKingdomFrame != null) {
			g.drawImage(imgKingdomFrame, 3, 20, null);
		}
		if (imgKingdom != null) {
			g.drawImage(imgKingdom, -3, -3, null);
		}
		if (player != null) {
			g.setColor(COLOR_CARD_COUNT_BACK);
			g.fillRect(1, 103, 16, 16);
			g.setColor(Color.BLACK);
			g.drawString("" + handCardCount, 5, 115);
			if (player.getPhase() != Phase.NOT_ACTIVE) {
				g.drawImage(IMG_PHASE[player.getPhaseID()], 113, 120, null);
			}
			if (player.getRole() != null) {
				g.drawImage(IMG_ROLE2[player.getRoleID()], 85, 3, null);
			}
			if (player.getCharacter() != null) {
				drawLife(g);
			}
		}
		if (playerName != null) {
			g.setColor(Color.WHITE);
			g.drawString(playerName, (DEFAULT_WIDTH - g.getFontMetrics().stringWidth(player.getName())) / 2, 15);
		}

	}

	/**
	 * 画生命值
	 *
	 * @param g
	 */
	private void drawLife(Graphics g) {
		org.dizem.sanguosha.model.card.character.Character character = player.getCharacter();
		if (characterChoosed) {
			int rate = (int) ((double) character.getLife() / character.getMaxLife() * 5 + 0.01);
			for (int i = 0; i < character.getLife(); ++i) {
				g.drawImage(IMG_HP_SMALL[rate], 30 + i * 17, 85, null);
			}
			for (int i = character.getLife(); i < character.getMaxLife(); ++i) {
				g.drawImage(IMG_HP_SMALL[0], 30 + i * 17, 85, null);
			}
		}
	}


	public int getHandCardCount() {
		return handCardCount;
	}

	public void setHandCardCount(int handCardCount) {
		this.handCardCount = handCardCount;
		repaint();
	}

	public void showEffect(AbstractCard card) {
		final JLabel effectLabel = new JLabel();
		effectLabel.setSize(256, 256);
		effectLabel.setOpaque(false);
		effectLabel.setLocation(0, 100);
		add(effectLabel, new Integer(100));

		ImageIcon[] imgList = new ImageIcon[0];
		if (card.getName().equals("杀")) {
			AudioUtil.play(player.getCharacter().isMale() ? AUDIO_SHA_MALE : AUDIO_SHA_FEMALE);
			imgList = IMG_SHA;


		} else if (card.getName().equals("闪")) {
			AudioUtil.play(player.getCharacter().isMale() ? AUDIO_SHAN_MALE : AUDIO_SHAN_FEMALE);
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

	public void setCanSelect(boolean flag) {
		canSelect = flag;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
		repaint();
	}

	public boolean isSelected() {
		return isSelected;
	}

	public Player getPlayer() {
		return player;
	}

	public void decreaseLife() {
		final JLabel labelLight = new JLabel();
		labelLight.setSize(172, 170);
		labelLight.setOpaque(false);
		labelLight.setLocation(0, 0);
		add(labelLight, new Integer(1000000));

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
