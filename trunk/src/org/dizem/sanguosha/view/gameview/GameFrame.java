package org.dizem.sanguosha.view.gameview;

import craky.component.JImagePane;
import craky.componentc.JCFrame;
import craky.layout.LineLayout;
import org.apache.log4j.Logger;
import org.dizem.common.TwoWayMap;
import org.dizem.sanguosha.controller.GameClient;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.vo.PlayerVO;
import org.dizem.sanguosha.view.component.SGSMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: DIZEM
 * Time: 11-4-9 下午10:37
 */
public class GameFrame extends JCFrame {

	private static Logger log = Logger.getLogger(GameFrame.class);
	private Player[] players;
	private GameClient client;
	private int currentPlayerID;
	private int playerCount = 5;
	private TwoWayMap<Player, OtherPlayerPane> playerToPane = new TwoWayMap<Player, OtherPlayerPane>();
	private java.util.List<OtherPlayerPane> otherPlayerPaneList = new ArrayList<OtherPlayerPane>();
	private DashboardPane dashboard;
	private MessagePane msgPane = new MessagePane();
	int[] roleList;

	private JPanel rolePane = new JPanel() {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (players != null) {
				for (int i = 0; i < players.length; ++i) {
				//	g.drawImage(IMG_ROLE[roleList[i]], i * 30, roleList[i] == 3 ? 23 : 20, null);
				}
			}
		}
	};

//
//	private void test() {
//
//		currentPlayerID = 0;
//		int size = 4;
//		roleList = new int[size];
//		int cnt = OTHER_PANE_POSITION_OFFSET[size - 1];
//
//		for (int i = 0; i < size; ++i) {
//			Player player = new Player("Player" + i, ROLE_DISTRIBUTION[i]);
//			roleList[i] = player.getRoleID();
//			players.add(player);
//
//			if (i != currentPlayerID) {
//				OtherPlayerPane pane = new OtherPlayerPane(player);
//				pane.setLocation(OTHER_PANE_POSITION[cnt][0], OTHER_PANE_POSITION[cnt++][1]);
//				playerToPane.put(player, pane);
//			}
//		}
//		Arrays.sort(roleList);
//
//		dashboard = new DashboardPane(players.get(currentPlayerID));
//	}

	public GameFrame(GameClient client, String playerName) {
		if (client != null) {
			this.client = client;
			this.playerCount = client.getPlayerCount();
		}
		initGamePane(playerName);
		initFrame();
		initLayout();
		initMenu();
		setBackgroundImage(IMG_GAME_FRAME_BACK);
		setVisible(true);
	}

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
		dashboard = new DashboardPane(playerName);
	}

	private void initFrame() {
		setSize(740, 620);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		dashboard.setLocation(0, 0);
		add(createMainPane(), BorderLayout.CENTER);
		add(dashboard, BorderLayout.SOUTH);
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
		pane.add(msgPane);
		pane.add(rolePane);
		pane.setOpaque(false);
		return pane;
	}

	public int getCurrentPlayerID() {
		return currentPlayerID;
	}

	public void setCurrentPlayerID(int currentPlayerID) {
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

	public static void main(String[] args) {
		new GameFrame(null, "Test");
	}


	public void updatePlayers(PlayerVO[] players) {
		this.players = new Player[players.length];
		int index = 0;
		for (int i = 0; i < players.length; ++i) {
			System.out.println(i + " " + currentPlayerID);

			if (i != currentPlayerID && players[i] != null) {

				this.players[i] = new Player(players[i]);
				otherPlayerPaneList.get(index++).setPlayer(this.players[i]);
			}
		}
	}
}
