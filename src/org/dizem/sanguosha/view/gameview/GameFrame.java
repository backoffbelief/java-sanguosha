package org.dizem.sanguosha.view.gameview;

import craky.componentc.JCFrame;
import org.apache.log4j.Logger;
import org.dizem.common.LogUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-9 下午10:37
 */
public class GameFrame extends JCFrame {
	private static Logger log = Logger.getLogger(GameFrame.class);
	private DashboardPane dashboard = new DashboardPane();
	public GameFrame() {
		initFrame();
		initLayout();
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
		add(dashboard, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		LogUtils.init();
		new GameFrame();
	}
}
