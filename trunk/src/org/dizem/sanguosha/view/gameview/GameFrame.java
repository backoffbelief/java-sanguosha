package org.dizem.sanguosha.view.gameview;

import craky.component.JImagePane;
import craky.componentc.JCFrame;
import craky.layout.LineLayout;
import org.apache.log4j.Logger;
import org.dizem.common.AudioUtil;
import org.dizem.sanguosha.controller.GameClient;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.PlayerVO;
import org.dizem.sanguosha.view.MainFrame;
import org.dizem.sanguosha.view.component.ComboBoxItem;
import org.dizem.sanguosha.view.component.MessageLabel;
import org.dizem.sanguosha.view.component.SGSMenu;
import org.dizem.sanguosha.view.component.SGSMessageBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * 游戏界面
 * <p/>
 * User: DIZEM
 * Time: 11-4-9 下午10:37
 */
public class GameFrame extends JCFrame {

	private final static Logger log = Logger.getLogger(GameFrame.class);

	/**
	 * 客户端控制类
	 */
	private GameClient client;
	/**
	 * 当前玩家ID
	 */
	private int currentPlayerID;
	/**
	 * 玩家数目
	 */
	private int playerCount = 5;
	/**
	 * 对手面板列表
	 */
	public java.util.List<OtherPlayerPane> otherPlayerPaneList = new ArrayList<OtherPlayerPane>();
	/**
	 * 出牌面板
	 */
	public DashboardPane dashboard;
	/**
	 * 消息面板
	 */
	private MessagePane msgPane;
	/**
	 * 消息提示
	 */
	private MessageLabel msgLabel = new MessageLabel();
	/**
	 * 弃牌显示
	 */
	private DiscardedPane discardedPane = new DiscardedPane();

	private JLayeredPane mainPanel;
	/**
	 * 角色列表
	 */
	int[] roleList;

	/**
	 * 角色显示面板
	 */
	private JPanel rolePane = new JPanel() {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			//todo: complete it
//			if (players != null) {
//				for (int i = 0; i < players.length; ++i) {
//					//	g.drawImage(IMG_ROLE[roleList[i]], i * 30, roleList[i] == 3 ? 23 : 20, null);
//				}
//			}
		}
	};


	/**
	 * 构造函数
	 *
	 * @param client	 客户端控制类
	 * @param playerName 当前玩家姓名
	 */
	public GameFrame(MainFrame main, GameClient client, String playerName) {
		if (client != null) {
			this.client = client;
			this.playerCount = client.getPlayerCount();
			msgPane = new MessagePane(client);
		}
		initGamePane(playerName);
		initFrame();
		initLayout();
		initMenu();
		setBackgroundImage(IMG_GAME_FRAME_BACK);

		setLocationRelativeTo(main);
		setVisible(true);
	}

	/**
	 * 初始化游戏界面
	 *
	 * @param playerName 玩家姓名
	 */
	private void initGamePane(String playerName) {
		int cnt = OTHER_PANE_POSITION_OFFSET[playerCount - 1];

		for (int i = 0; i < playerCount; ++i) {
			//	Player player = new Player("Player" + i, ROLE_DISTRIBUTION[i]);
			//	roleList[i] = player.getRoleID();
			//	players.add(player);

			if (i != currentPlayerID) {
				OtherPlayerPane pane = new OtherPlayerPane(this);
				pane.setLocation(OTHER_PANE_POSITION[cnt][0], OTHER_PANE_POSITION[cnt++][1]);
				otherPlayerPaneList.add(pane);
			}
		}
		//Arrays.sort(roleList);
		dashboard = new DashboardPane(playerName, this);
	}

	/**
	 * 初始化窗体
	 */
	private void initFrame() {
		setSize(740, 620);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * 窗体关闭事件
	 *
	 * @param e
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (!SGSMessageBox.query(this, "是否退出？", "中途退出游戏是不对的，尤其是这么好玩的三国杀游戏")) {
				return;
			} else {
				client.offline();
			}
		}
		super.processWindowEvent(e);
	}

	/**
	 * 初始化布局
	 */
	private void initLayout() {
		setLayout(new BorderLayout());
		dashboard.setLocation(0, 0);
		add(createMainPane(), BorderLayout.CENTER);
		add(dashboard, BorderLayout.SOUTH);
	}

	/**
	 * 设置玩家
	 *
	 * @param id
	 * @param character
	 */
	public void setCharacter(int id, Character character) {
		client.players[id].setCharacter(character);
		otherPlayerPaneList.get(getIndex(id)).setCharacter();
	}

	/**
	 * 构造游戏主界面
	 *
	 * @return 游戏主界面
	 */
	private JLayeredPane createMainPane() {
		mainPanel = new JLayeredPane();
		mainPanel.setLayout(null);
		for (OtherPlayerPane p : otherPlayerPaneList) {
			mainPanel.add(p);
		}
		msgPane.setLocation(500, 45);
		rolePane.setBounds(530, -10, 150, 50);
		rolePane.setOpaque(false);
		msgLabel.setLocation(100, 190);
		discardedPane.setLocation(180, 250);
		JLabel iconLabel = new JLabel(IMG_ICON_LABEL);
		iconLabel.setLocation(0, 0);
		mainPanel.add(iconLabel);
		mainPanel.add(msgPane);
		mainPanel.add(rolePane);
		mainPanel.add(msgLabel);
		mainPanel.add(discardedPane);
		mainPanel.setOpaque(false);
		return mainPanel;
	}

	/**
	 * 取得当前玩家的id
	 *
	 * @return 当前玩家的id
	 */
	public int getCurrentPlayerID() {
		return currentPlayerID;
	}

	/**
	 * 设置当前玩家的id
	 *
	 * @param currentPlayerID 当前玩家的id
	 */
	public void setCurrentPlayerID(int currentPlayerID) {
		client.setPlayerId(currentPlayerID);
		this.currentPlayerID = currentPlayerID;
	}

	/**
	 * 初始化菜单
	 */
	private void initMenu() {
		JMenuBar bar = new JMenuBar();
		bar.setOpaque(false);
		bar.setBorder(new EmptyBorder(0, 0, 0, 0));
		bar.setBorderPainted(false);
		bar.setLayout(new GridLayout(1, 2));
		bar.setPreferredSize(new Dimension(20, 20));
		bar.setFocusable(false);
		bar.add(new SGSMenu(this));
		JImagePane titleContent = getTitleContentPane();
		titleContent.setLayout(new LineLayout(0, 0, 0, 2, 0,
				LineLayout.TRAILING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		titleContent.add(bar, LineLayout.END);
	}

	/**
	 * 更新玩家信息
	 *
	 * @param players 所有玩家
	 */
	public void updatePlayers(PlayerVO[] players) {
		client.players = new Player[players.length];
		msgPane.clearUsers();
		for (int i = 0; i < players.length; ++i) {
			if (players[i] != null)
				client.players[i] = new Player(players[i]);
			if (i != currentPlayerID && players[i] != null) {
				msgPane.addUser(new ComboBoxItem(players[i].getName(), players[i].getPlayerId()));
				otherPlayerPaneList.get(getIndex(i)).setPlayer(client.players[i]);
			}
		}
	}

	/**
	 * 计算玩家在客户端显示的位置下标
	 *
	 * @param id 玩家id
	 * @return 当前玩家角度的位置
	 */
	public int getIndex(int id) {
		if (id > currentPlayerID) {
			return id - currentPlayerID - 1;

		} else if (id < currentPlayerID) {
			return (id + currentPlayerID) % (playerCount - 1);

		} else {
			return 0;
		}
	}


	/**
	 * 添加游戏日志
	 *
	 * @param message 日志消息
	 */
	public void appendLog(String message) {
		msgPane.appendLog(message);
	}


	/**
	 * 添加聊天消息
	 *
	 * @param message 消息
	 */
	public void appendChatMessage(String message) {
		msgPane.appendMessage(message);
	}

	/**
	 * 设置玩家角色
	 *
	 * @param role   角色
	 * @param lordId 主公id
	 */
	public void setRole(Role role, int lordId) {
		dashboard.setRole(role);
		if (lordId == currentPlayerID)
			return;

		if (client.players != null) {
			client.players[lordId].setRole(Role.ZG);
			otherPlayerPaneList.get(getIndex(lordId)).repaint();
		}
		showMessage("您分配了角色: " + role);
	}


	/**
	 * 显示消息
	 *
	 * @param text 消息内容
	 */
	public void showMessage(String text) {
		if (text == null || text.isEmpty())
			return;
		appendLog(text);
		msgLabel.showText(text);
	}

	/**
	 * 设置玩家武将角色
	 *
	 * @param character 武将角色
	 * @param isLord	是否是主公
	 */
	public void setCharacter(Character character, boolean isLord) {
		showMessage("您选择了武将：" + character.getName());
		dashboard.setCharacter(character);
		client.players[currentPlayerID].setCharacter(character);
		if (isLord)
			client.chooseLordCharacterFinish();
		else
			client.chooseCharacterFinish();
	}

	/**
	 * 取得玩家列表
	 *
	 * @return 玩家列表
	 */
	public Player[] getPlayers() {
		return client.players;
	}

	/**
	 * 取得当前玩家
	 *
	 * @return 当前玩家对象
	 */
	public Player getCurrentPlayer() {
		return client.players[currentPlayerID];
	}

	/**
	 * 摸牌
	 *
	 * @param cards 牌列表
	 */
	public void distributeCards(AbstractCard[] cards) {
		for (AbstractCard card : cards) {
			dashboard.addHandCard(card);
		}
	}

	/**
	 * 更新对手玩家手牌数
	 *
	 * @param playerId	  玩家id
	 * @param handCardCount 手牌数
	 */
	public void setOtherPlayerInfo(int playerId, int handCardCount) {
		otherPlayerPaneList.get(getIndex(playerId)).setHandCardCount(handCardCount);
	}

	/**
	 * 添加弃牌
	 *
	 * @param card	弃牌
	 * @param message 附加信息
	 */
	public void addDiscardedCard(AbstractCard card, String message) {
		discardedPane.addCard(card, message);
	}

	/**
	 * 想指定对手出牌
	 *
	 * @param card 出牌
	 * @param toId 指定对手id
	 */
	public void offerCardTo(AbstractCard card, int toId) {
		client.sendOfferCardToInfo(card, toId);
	}

	public void showMessageKeep(String message) {
		showMessage(message);//todo
	}


	private AbstractCard feedbackCard;
	private int feedbackToId;

	public void setFeedbackCard(AbstractCard card) {
		this.feedbackCard = card;
	}

	public AbstractCard getFeedbackCard() {
		return feedbackCard;
	}

	public GameClient getClient() {
		return client;
	}

	public void setFeedBackToId(int playerId) {
		this.feedbackToId = playerId;
	}

	public int getFeedbackToId() {
		return feedbackToId;
	}

	public void decreaseLife(int id) {
		if (id == currentPlayerID) {
			dashboard.decreaseLife();

		} else {
			client.players[id].getCharacter().decreaseLife();
			otherPlayerPaneList.get(getIndex(id)).decreaseLife();
		}
	}

	public void discard() {
		int n = getCurrentPlayer().cardToBeDiscard();
		dashboard.discard(n);
	}

	/**
	 * 显示动画效果
	 *
	 * @param card
	 */
	public void showEffect(AbstractCard card, OtherPlayerPane pane) {
		final JLabel effectLabel = new JLabel();
		effectLabel.setSize(256, 256);
		effectLabel.setOpaque(false);
		effectLabel.setLocation(pane.getX() - 80, pane.getY() + 30);
		mainPanel.add(effectLabel, 10000);

		ImageIcon[] imgList = new ImageIcon[0];
		Player player = pane.getPlayer();
		if (card.getName().equals("杀")) {
			AudioUtil.play(player.getCharacter().isMale() ? AUDIO_SHA_MALE : AUDIO_SHA_FEMALE);
			imgList = IMG_SHA;


		} else if (card.getName().equals("闪")) {
			AudioUtil.play(player.getCharacter().isMale() ? AUDIO_SHAN_MALE : AUDIO_SHAN_FEMALE);
			imgList = IMG_SHAN;

		} else if (card.getName().equals("桃")) {
			AudioUtil.play(AUDIO_PEACH);
			imgList = IMG_PEACH;
		} else {
			return;
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

				mainPanel.remove(effectLabel);
				repaint();
			}
		}).start();
	}
}
