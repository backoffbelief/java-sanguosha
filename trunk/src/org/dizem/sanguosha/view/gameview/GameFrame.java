package org.dizem.sanguosha.view.gameview;

import craky.component.JImagePane;
import craky.componentc.JCFrame;
import craky.layout.LineLayout;
import org.dizem.sanguosha.controller.GameClient;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.PlayerVO;
import org.dizem.sanguosha.view.component.ComboBoxItem;
import org.dizem.sanguosha.view.component.MessageLabel;
import org.dizem.sanguosha.view.component.SGSMenu;
import org.dizem.sanguosha.view.component.SGSMessageBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * 游戏界面
 *
 * User: DIZEM
 * Time: 11-4-9 下午10:37
 */
public class GameFrame extends JCFrame {

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
	 * @param client 客户端控制类
	 * @param playerName 当前玩家姓名
	 */
	public GameFrame(GameClient client, String playerName) {
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
		setVisible(true);
	}

	/**
	 * 初始化游戏界面
	 * @param playerName 玩家姓名
	 */
	private void initGamePane(String playerName) {
		int cnt = OTHER_PANE_POSITION_OFFSET[playerCount - 1];

		for (int i = 0; i < playerCount; ++i) {
			//	Player player = new Player("Player" + i, ROLE_DISTRIBUTION[i]);
			//	roleList[i] = player.getRoleID();
			//	players.add(player);

			if (i != currentPlayerID) {
				OtherPlayerPane pane = new OtherPlayerPane();
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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * 窗体关闭事件
	 * @param e
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (!SGSMessageBox.query(this, "是否退出？", "中途退出游戏是不对的，尤其是这么好玩的三国杀游戏")) {
				return;
			} else {
				//todo 发送离线请求
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
	 * @param id
	 * @param character
	 */
	public void setCharacter(int id, Character character) {
		client.players[id].setCharacter(character);
		otherPlayerPaneList.get(getIndex(id)).setCharacter();
	}

	private JPanel createMainPane() {
		JPanel pane = new JPanel();
		pane.setLayout(null);
		for (OtherPlayerPane p : otherPlayerPaneList) {
			pane.add(p);
		}
		msgPane.setLocation(500, 45);
		rolePane.setBounds(530, -10, 150, 50);
		rolePane.setOpaque(false);
		msgLabel.setLocation(100, 330);
		discardedPane.setLocation(180, 250);
		pane.add(discardedPane);

		pane.add(msgPane);
		pane.add(rolePane);
		pane.add(msgLabel);
		pane.setOpaque(false);
		return pane;
	}

	public int getCurrentPlayerID() {
		return currentPlayerID;
	}

	public void setCurrentPlayerID(int currentPlayerID) {
		client.setPlayerId(currentPlayerID);
		this.currentPlayerID = currentPlayerID;
	}

	private void initMenu() {
		JMenuBar bar = new JMenuBar();
		bar.setOpaque(false);
		bar.setBorder(new EmptyBorder(0, 0, 0, 0));
		bar.setBorderPainted(false);
		bar.setLayout(new GridLayout(1, 2));
		bar.setPreferredSize(new Dimension(20, 20));
		bar.setFocusable(false);
		bar.add(new SGSMenu());
		JImagePane titleContent = getTitleContentPane();
		titleContent.setLayout(new LineLayout(0, 0, 0, 2, 0,
				LineLayout.TRAILING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		titleContent.add(bar, LineLayout.END);
	}

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

	public int getIndex(int id) {
		if (id > currentPlayerID) {
			return id - currentPlayerID - 1;
		} else if (id < currentPlayerID) {
			return (id + currentPlayerID) % (playerCount - 1);
		} else {
			return 0;
		}
	}

	public void appendLog(String message) {
		msgPane.appendLog(message);
	}


	public void appendChatMessage(String message) {
		msgPane.appendMessage(message);
	}

	public void setRole(Role role, int lordId) {
		dashboard.setRole(role);

		if(lordId == currentPlayerID)
			return;
		if (client.players != null) {
			client.players[getIndex(lordId)].setRole(Role.ZG);
			otherPlayerPaneList.get(getIndex(lordId)).repaint();
		}
		showMessage("您分配了角色: " + role);
	}

	public void showMessage(String text) {
		if(text == null || text.isEmpty())
			return;
		appendLog(text);
		msgLabel.showText(text);
	}

	public void setCharacter(Character character, boolean isLord) {
		showMessage("您选择了武将：" + character.getName());
		dashboard.setCharacter(character);
		client.players[currentPlayerID].setCharacter(character);
		if (isLord)
			client.chooseLordCharacterFinish();
		else
			client.chooseCharacterFinish();
	}

	public Player[] getPlayers() {
		return client.players;
	}

	public Player getCurrentPlayer() {
		return client.players[currentPlayerID];
	}

	public void distributeCards(AbstractCard[] cards) {
		for (AbstractCard card : cards) {
			client.players[currentPlayerID].addHandCard(card);
			dashboard.addHandCard(card);
		}
	}

	public void setOtherPlayerInfo(int playerId, int handCardCount) {
		otherPlayerPaneList.get(getIndex(playerId)).setHandCardCount(handCardCount);
	}

	public void addDiscardedCard(AbstractCard card, String message) {
		discardedPane.addCard(card, message);
	}
}
