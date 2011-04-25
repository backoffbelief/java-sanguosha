package org.dizem.sanguosha.view;

import craky.componentc.JCFrame;
import craky.util.UIUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dizem.sanguosha.controller.GameServer;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.exception.SGSException;
import org.dizem.sanguosha.view.component.SGSButton;
import org.dizem.sanguosha.view.component.SGSTextArea;
import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.view.dialog.AboutDialog;
import org.dizem.sanguosha.view.dialog.StartGameDialog;
import org.dizem.sanguosha.view.dialog.StartServerDialog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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

	private GameServer server;

	public static final Font LABEL_FONT = new Font("微软雅黑", Font.PLAIN, 15);

	public MainFrame() {
		super(Constants.APP_TITLE);
		initFrame();
		initLayout();
		setVisible(true);
		startGC();
	}

	private void initFrame() {
		setIconImage(ImageUtil.getImage("sgs.png"));
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
		scrollPane.setForeground(Color.WHITE);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(
				new TitledBorder(new LineBorder(Color.WHITE, 3), "服务器日志", 0, 0, LABEL_FONT, Color.WHITE));


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
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateBgImage();
			}
		});
	}

	private void updateBgImage() {

		int oldBgImageId = bgImageId;
		bgImageId = rand.nextInt(4) + 1;
		if (oldBgImageId == bgImageId)
			bgImageId = bgImageId % 4 + 1;
		setBackgroundImage(ImageUtil.getImage("system/background/bg" + bgImageId + ".jpg"));
		log.info("Change background image");
		repaint();

	}


	public static void main(String[] args) {

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
			log.info("Open about dialog");
			new AboutDialog(this);

		} else if (e.getSource() == btnStartServer) {
			log.info("Open dialog to start server");
			new StartServerDialog(this);

		} else if (e.getSource() == btnSetting) {
			log.info("Open setting dialog");

		} else if (e.getSource() == btnStartGame) {
			log.info("Open dialog to start game");
			new StartGameDialog(this);
		}
	}

	public void startOrStopServer() {
		if (showLogger) {
			btnStartServer.setText("启动服务器");
			loggerArea.setVisible(false);
			scrollPane.setVisible(false);

		} else {

			btnStartServer.setText("关闭服务器");
			loggerArea.setVisible(true);
			scrollPane.setVisible(true);
		}

		showLogger = !showLogger;
	}

	public void setServer(GameServer server) {
		this.server = server;
	}

	public void appendLog(String s) {
		if (!showLogger) {
			throw new SGSException("Server is not start");
		}
		loggerArea.append(Constants.LOG_TIME_FORMAT.format(new Date()) + s + "\n");
		loggerArea.selectAll();
		loggerArea.setCaretPosition(loggerArea.getSelectedText()
				.length());
	}
}
