package org.dizem.sanguosha.view.gameview;

import craky.componentc.JCFrame;
import org.apache.log4j.Logger;
import org.dizem.common.ImageUtils;
import org.dizem.common.LogUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-9 下午10:37
 */
public class GameFrame extends JCFrame {
	private static Logger log = Logger.getLogger(GameFrame.class);
	private static final Image IMG_BACK = ImageUtils.getImage("system/gamebackground.jpg");

	private OtherPlayerPane otherPane = new OtherPlayerPane();
	private DashboardPane dashboard = new DashboardPane();
	private MessagePane msgPane = new MessagePane();

	public GameFrame() {
		initFrame();
		initLayout();
		setBackgroundImage(IMG_BACK);
		
		setVisible(true);
	}

	private void initFrame() {
		setSize(740, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		dashboard.setLocation(0, 0);
		otherPane.setLocation(50, 50);
		add(createMainPane(), BorderLayout.CENTER);
		add(dashboard, BorderLayout.SOUTH);
	}
	//public static final Image IMG_ICON = ImageUtils.getImage("system/sgs_icon.gif");

	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
	}

	private JPanel createMainPane() {
		JPanel pane = new JPanel();
		pane.setLayout(null);
		otherPane.setLocation(300, 5);
		pane.add(otherPane);
		msgPane.setLocation(500, 20);
		pane.add(msgPane);
		pane.setOpaque(false);
		return pane;
	}

	public static void main(String[] args) {
		LogUtils.init();
		new GameFrame();
	}
}
