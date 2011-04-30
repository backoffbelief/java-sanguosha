package org.dizem.sanguosha.view.gameview;

import craky.component.JImagePane;
import craky.componentc.JCFrame;
import craky.componentc.JCMessageBox;
import craky.layout.LineLayout;
import org.apache.log4j.Logger;
import org.dizem.common.collection.TwoWayMap;
import org.dizem.sanguosha.controller.GameClient;
import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;
import org.dizem.sanguosha.model.vo.PlayerVO;
import org.dizem.sanguosha.view.component.ComboBoxItem;
import org.dizem.sanguosha.view.component.MessageLabel;
import org.dizem.sanguosha.view.component.SGSMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: DIZEM
 * Time: 11-4-9 下午10:37
 */
public class GameFrame extends JCFrame {

	private static Logger log = Logger.getLogger(GameFrame.class);

	private GameClient client;
	private int currentPlayerID;
	private int playerCount = 5;
	public java.util.List<OtherPlayerPane> otherPlayerPaneList = new ArrayList<OtherPlayerPane>();
	public DashboardPane dashboard;
	private MessagePane msgPane;
	private MessageLabel msgLabel = new MessageLabel();
	int[] roleList;

	private JPanel rolePane = new JPanel() {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
//			if (players != null) {
//				for (int i = 0; i < players.length; ++i) {
//					//	g.drawImage(IMG_ROLE[roleList[i]], i * 30, roleList[i] == 3 ? 23 : 20, null);
//				}
//			}
		}
	};

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

	private void initFrame() {
		setSize(740, 620);
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				//todo: complete code
				if(JCMessageBox.createQuestionMessageBox(GameFrame.this, "退出游戏", "中途离开游戏是不道德的，是否继续").open()
					 == JCMessageBox.YES_OPTION) {
					GameFrame.this.dispose();
				}
			}
		});
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		dashboard.setLocation(0, 0);
		add(createMainPane(), BorderLayout.CENTER);
		add(dashboard, BorderLayout.SOUTH);
	}


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
}
