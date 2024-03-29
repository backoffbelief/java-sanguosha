package org.dizem.sanguosha.view.dialog;

import craky.component.JImagePane;
import craky.componentc.*;
import craky.layout.LineLayout;
import org.apache.log4j.Logger;
import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.controller.GameClient;
import org.dizem.sanguosha.controller.UDPSender;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;
import org.dizem.sanguosha.view.component.EmptyComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * 客户端启动对话框
 * <p/>
 * User: DIZEM
 * Time: 11-3-31 上午1:16
 */
public class StartGameDialog extends JDialog implements ActionListener {

	private static Logger log = Logger.getLogger(StartGameDialog.class);

	private JLabel lblName;
	private JLabel lblServerIp;
	private JTextField txtServerIp;
	private JLabel lblServerPort;
	private JTextField txtServerPort;
	private JTextField txtName;
	private JButton btnSearchPort;
	private JButton btnStart;
	private JButton btnCancel;
	private MainFrame owner;

	/**
	 * 构造函数
	 *
	 * @param owner 父窗体
	 */
	public StartGameDialog(MainFrame owner) {
		super(owner, "\u542f\u52a8\u6e38\u620f", ModalityType.DOCUMENT_MODAL);
		this.owner = owner;
		getContentPane().setPreferredSize(new Dimension(300, 150));

		setIconImage(ImageUtil.getImage("sgs.png"));
		setSize(new Dimension(300, 150));
		add(createMainPane(), BorderLayout.CENTER);
		add(createBottomPane(), BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * 创建主界面
	 *
	 * @return
	 */
	private JImagePane createMainPane() {
		JImagePane mainPane = new JImagePane();
		mainPane.setBackground(new Color(228, 244, 255));

		mainPane.setBorder(new GridBorder(Color.WHITE, 0, 1, 0, 1));
		mainPane.setLayout(new LineLayout(0, 0, 0, 0, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.VERTICAL));

		lblName = new JCLabel("\u540d\u79f0");
		lblName.setBorder(new EmptyBorder(1, 0, 0, 10));
		lblName.setPreferredSize(new Dimension(74, 24));
		lblName.setHorizontalAlignment(JCLabel.RIGHT);

		txtName = new JCTextField();
		txtName.setPreferredSize(new Dimension(-1, 26));
		txtName.setEditable(true);
		txtName.setText(getUserName());

		EmptyComponent ecName = new EmptyComponent();
		ecName.setBorder(new EmptyBorder(0, 0, 0, 40));
		ecName.setLayout(new LineLayout(0, 0, 0, 14, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecName.setPreferredSize(new Dimension(-1, 40));
		ecName.add(lblName, LineLayout.START);
		ecName.add(txtName, LineLayout.MIDDLE);

		lblServerIp = new JCLabel("\u670d\u52a1\u5668\u5730\u5740");
		lblServerIp.setBorder(new EmptyBorder(1, 0, 0, 10));
		lblServerIp.setPreferredSize(new Dimension(74, 24));
		lblServerIp.setHorizontalAlignment(JCLabel.RIGHT);

		txtServerIp = new JCTextField();
		txtServerIp.setPreferredSize(new Dimension(-1, 26));
		txtServerIp.setEditable(true);
		txtServerIp.setText(LOCAL_ADDRESS);

		EmptyComponent ecIp = new EmptyComponent();
		ecIp.setBorder(new EmptyBorder(0, 0, 0, 40));
		ecIp.setLayout(new LineLayout(0, 0, 0, 14, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecIp.setPreferredSize(new Dimension(-1, 40));
		ecIp.add(lblServerIp, LineLayout.START);
		ecIp.add(txtServerIp, LineLayout.MIDDLE);

		lblServerPort = new JCLabel("\u7aef\u53e3");
		lblServerPort.setBorder(new EmptyBorder(1, 0, 0, 10));
		lblServerPort.setPreferredSize(new Dimension(74, 24));
		lblServerPort.setHorizontalAlignment(JCLabel.RIGHT);

		txtServerPort = new JCTextField();
		txtServerPort.setPreferredSize(new Dimension(50, 26));
		txtServerPort.setEditable(true);
		txtServerPort.setText("7777");

		btnSearchPort = new JCButton("探测端口");
		btnSearchPort.setPreferredSize(new Dimension(80, 24));

		EmptyComponent ecPort = new EmptyComponent();
		ecPort.setBorder(new EmptyBorder(0, 0, 0, 90));
		ecPort.setLayout(new LineLayout(0, 0, 0, 14, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecPort.setPreferredSize(new Dimension(-1, 40));
		ecPort.add(lblServerPort, LineLayout.START);
		ecPort.add(txtServerPort, LineLayout.START);
		ecPort.add(btnSearchPort, LineLayout.END);

		mainPane.add(ecName, LineLayout.START_FILL);
		mainPane.add(ecIp, LineLayout.START_FILL);
		mainPane.add(ecPort, LineLayout.START_FILL);
		btnSearchPort.setHorizontalAlignment(JCButton.CENTER);
		return mainPane;
	}

	/**
	 * 创建底栏
	 *
	 * @return
	 */
	private JImagePane createBottomPane() {
		JImagePane bottomPane = new JImagePane();
		bottomPane.setPreferredSize(new Dimension(334, 30));
		bottomPane.setImage(ImageUtil.getImage("dialog_bottom.png"));
		bottomPane.setPreferredSize(new Dimension(334, 30));
		bottomPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		bottomPane.setLayout(new LineLayout(5, 5, 7, 5, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		btnStart = new JCButton("\u542f\u52a8(S)");
		btnStart.setPreferredSize(new Dimension(73, 21));
		btnStart.setMnemonic('R');
		btnStart.addActionListener(this);
		btnCancel = new JCButton("\u53d6\u6d88(C)");
		btnCancel.setPreferredSize(new Dimension(73, 21));
		btnCancel.setMnemonic('C');
		btnCancel.addActionListener(this);
		bottomPane.add(btnStart, LineLayout.END);
		bottomPane.add(btnCancel, LineLayout.END);
		return bottomPane;
	}


	/**
	 * 获取主机名称
	 *
	 * @return
	 */
	public String getUserName() {
		Map<String, String> map = System.getenv();
		if (map != null)
			return map.get("USERNAME");
		else
			return "NO NAME";
	}

	/**
	 * 按键事件
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCancel) {
			owner.btnStartGame.setEnabled(true);
			dispose();

		} else if (e.getSource() == btnStart) {
			if (!txtServerIp.getText().matches(PATTERN_IP)) {
				JCMessageBox.createErrorMessageBox(this, "Error", "IP地址格式错误").open();

			} else if (!txtServerPort.getText().matches(PATTERN_PORT)) {
				JCMessageBox.createErrorMessageBox(this, "Error", "端口格式错误").open();

			} else {
				new GameClient(Integer.parseInt(txtServerPort.getText()), txtServerIp.getText(), txtName.getText());
				owner.startOrStopBackMusic();
				dispose();
			}
		}
	}

	/**
	 * 连接服务器
	 */
	private void connect() {
		SGSPacket packet = new SGSPacket(OP_TEST_SERVER, 5068);
		UDPSender.send(packet, txtServerIp.getText(), Integer.parseInt(txtServerPort.getText()));

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
				}
			}
		});
	}
}
