package org.dizem.sanguosha.view;

import craky.componentc.JCFrame;
import craky.util.UIUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.view.component.SGSButton;
import org.dizem.sanguosha.view.component.SGSTextArea;
import org.dizem.common.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * User: DIZEM
 * Time: 11-3-25 下午10:12
 */
public class MainFrame extends JCFrame implements ActionListener {

	private static Logger log = Logger.getLogger(MainFrame.class);

	private JButton btnStartGame;
	private JButton btnStartServer;
	private JButton btnSetting;
	private JButton btnAbout;
	private JTextArea loggerArea;
	private JLabel bgLabel;
	private Timer gcTimer;
	JScrollPane scrollPane;

	private static Random rand = new Random();
	private static int bgImageId = 1;
	private boolean showLogger = false;


	public MainFrame() {
		super(Constants.APP_TITLE);
		initFrame();
		initLayout();
		setVisible(true);
		startGC();
	}

	private void initFrame() {
		setIconImage(ImageUtils.getImage("sgs.png"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(700, 525));
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initLayout() {
		setLayout(null);

		loggerArea = new SGSTextArea();
		loggerArea.setPreferredSize(loggerArea.getPreferredSize());
		loggerArea.setLocation(20, 20);
		scrollPane = new JScrollPane(loggerArea);
		scrollPane.setBounds(0, 0, loggerArea.getWidth(), 250);

		scrollPane.setLocation(20, 20);
		scrollPane.setOpaque(false);
		scrollPane.setVisible(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setOpaque(false);
		add(scrollPane);


		int offsetX = 50;
		int offsetY = 280;
		btnStartServer = new SGSButton(Constants.STR_START_SERVER);
		btnStartServer.setBounds(offsetX, offsetY, 100, 40);
		btnStartServer.addActionListener(this);
		add(btnStartServer);
		offsetY += 50;
		btnStartGame = new SGSButton(Constants.STR_START_GAME);
		btnStartGame.setBounds(offsetX, offsetY, 100, 40);
		btnStartGame.addActionListener(this);
		add(btnStartGame);
		offsetY += 50;
		btnSetting = new SGSButton(Constants.STR_SETTING);
		btnSetting.setBounds(offsetX, offsetY, 100, 40);
		btnSetting.addActionListener(this);
		add(btnSetting);
		offsetY += 50;
		btnAbout = new SGSButton(Constants.STR_ABOUT);
		btnAbout.setBounds(offsetX, offsetY, 100, 40);
		btnAbout.addActionListener(this);
		add(btnAbout);
		updateBgImage();

	}

	private void updateBgImage() {

		int oldBgImageId = bgImageId;
		bgImageId = rand.nextInt(4) + 1;
		if (oldBgImageId == bgImageId)
			bgImageId = bgImageId % 4 + 1;
		setBackgroundImage(ImageUtils.getImage("system/background/bg" + bgImageId + ".jpg"));
		repaint();
	}


	public static void main(String[] args) throws InvocationTargetException, InterruptedException {

		PropertyConfigurator.configure(Constants.LOG4J_CONFIG_PATH);
		log.info("start");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}

	public void startGC() {
		int delay = 10000;

		gcTimer = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.gc();
				log.info("gc");
			}
		});

		gcTimer.start();
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAbout) {
			new AboutDialog(this);

		} else if (e.getSource() == btnStartServer) {
			new StartServerDialog(this);

		} else if (e.getSource() == btnSetting) {
			appendLog("123123\n");

		} else if (e.getSource() == btnStartGame) {
			new StartGameDialog(this);
		}
	}

	public void changeLogVisible() {
		if (showLogger) {
			loggerArea.setVisible(false);
			scrollPane.setVisible(false);

		} else {
			loggerArea.setVisible(true);
			scrollPane.setVisible(true);
		}

		showLogger = !showLogger;
	}

	public void appendLog(String s) {
		loggerArea.append(s);
		loggerArea.setCaretPosition(loggerArea.getText().length());
		;
	}
}
