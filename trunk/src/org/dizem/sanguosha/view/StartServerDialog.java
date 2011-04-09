package org.dizem.sanguosha.view;

import craky.component.JImagePane;
import craky.componentc.*;
import craky.layout.LineLayout;
import org.apache.log4j.Logger;
import org.dizem.sanguosha.view.component.EmptyComponent;
import org.dizem.common.ImageUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * User: DIZEM
 * Time: 11-3-30 下午11:07
 */
public class StartServerDialog extends JDialog implements ActionListener {

	private static Logger log = Logger.getLogger(StartServerDialog.class);

	private MainFrame mainFrame;

	private JLabel lblServerName;
	private JTextField txtServerName;
	private JLabel lblTimeDelay;
	private JSpinner txtTimeDelay;
	private JLabel lblIp;
	private JTextField txtIp;
	private JLabel lblPort;
	private JTextField txtPort;
	private JButton btnStart;
	private JButton btnCancel;
	private JButton btnGetWLanIp;
	private JCheckBox checkNoTimeDelay;
	private JLabel lblGameType;
	private JComboBox cbGameType;

	public StartServerDialog(Window owner) {
		super(owner, "启动服务器", ModalityType.DOCUMENT_MODAL);
		getContentPane().setPreferredSize(new Dimension(300, 200));

		mainFrame = (MainFrame) owner;

		setIconImage(ImageUtils.getImage("sgs.png"));
		setSize(new Dimension(300, 200));
		setLayout(new BorderLayout());
		add(createMainPane(), BorderLayout.CENTER);
		add(createBottomPane(), BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(owner);

		setVisible(true);
	}


	public static void main(String[] args) {
		new StartServerDialog(null);
	}


	private JImagePane createMainPane() {
		JImagePane mainPane = new JImagePane();
		mainPane.setBackground(new Color(228, 244, 255));
		mainPane.setBorder(new GridBorder(Color.WHITE, 0, 1, 0, 1));
		mainPane.setLayout(new LineLayout(0, 0, 0, 0, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.VERTICAL));

		lblServerName = new JCLabel("\u670d\u52a1\u5668\u540d");
		lblServerName.setBorder(new EmptyBorder(1, 0, 0, 10));
		lblServerName.setPreferredSize(new Dimension(74, 24));
		lblServerName.setHorizontalAlignment(JCLabel.RIGHT);

		txtServerName = new JCTextField();
		txtServerName.setPreferredSize(new Dimension(-1, 26));
		txtServerName.setEditable(true);
		txtServerName.setText(getUserName() + "\u7684\u670d\u52a1\u5668");

		EmptyComponent ecServerName = new EmptyComponent();
		ecServerName.setBorder(new EmptyBorder(0, 0, 0, 40));
		ecServerName.setLayout(new LineLayout(0, 0, 0, 14, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecServerName.setPreferredSize(new Dimension(-1, 50));
		ecServerName.add(lblServerName, LineLayout.START);
		ecServerName.add(txtServerName, LineLayout.MIDDLE);

		Double current = new Double(15);
		Double min = new Double(5);
		Double max = new Double(30);
		Double step = new Double(1);
		SpinnerNumberModel m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
		txtTimeDelay = new JCSpinner(m_numberSpinnerModel);
		txtTimeDelay.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblTimeDelay = new JCLabel("\u51fa\u724c\u65f6\u95f4");
		lblTimeDelay.setBorder(new EmptyBorder(1, 0, 0, 10));
		lblTimeDelay.setPreferredSize(new Dimension(74, 24));
		lblTimeDelay.setHorizontalAlignment(JCLabel.RIGHT);

		checkNoTimeDelay = new JCCheckBox("\u4e0d\u9650\u65f6\u95f4");
		checkNoTimeDelay.setBorder(new EmptyBorder(1, 20, 10, 0));
		checkNoTimeDelay.addActionListener(this);

		JLabel lblSecond = new JCLabel("\u79d2");
		lblSecond.setBorder(new EmptyBorder(4, 3, 5, 10));

		EmptyComponent ecTimeDelay = new EmptyComponent();
		ecTimeDelay.setBorder(new EmptyBorder(0, 0, 0, 100));

		ecTimeDelay.setLayout(new LineLayout(0, 0, 0, 0, 0, LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecTimeDelay.setPreferredSize(new Dimension(-1, 35));
		ecTimeDelay.add(lblTimeDelay, LineLayout.START);
		ecTimeDelay.add(txtTimeDelay, LineLayout.START);
		ecTimeDelay.add(lblSecond, LineLayout.START);
		ecTimeDelay.add(checkNoTimeDelay, LineLayout.END_FILL);

		lblIp = new JCLabel("\u670d\u52a1\u5668\u5730\u5740");
		lblIp.setBorder(new EmptyBorder(1, 0, 0, 10));
		lblIp.setPreferredSize(new Dimension(74, 24));
		lblIp.setHorizontalAlignment(JCLabel.RIGHT);

		txtIp = new JCTextField("127.0.0.1");
		txtIp.setPreferredSize(new Dimension(100, 26));


		lblPort = new JCLabel("\u7aef\u53e3");
		lblPort.setBorder(new EmptyBorder(1, 0, 0, 5));
		lblPort.setPreferredSize(new Dimension(50, 24));
		lblPort.setHorizontalAlignment(JCLabel.RIGHT);

		txtPort = new JCTextField("7777");
		txtPort.setPreferredSize(new Dimension(40, 26));


		EmptyComponent ecIp = new EmptyComponent();
		ecIp.setBorder(new EmptyBorder(0, 0, 0, 90));
		ecIp.setLayout(new LineLayout(0, 0, 0, 0, 0, LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecIp.setPreferredSize(new Dimension(-1, 35));
		ecIp.add(lblIp, LineLayout.START);
		ecIp.add(txtIp, LineLayout.START);
		ecIp.add(lblPort, LineLayout.START);
		ecIp.add(txtPort, LineLayout.START);


		EmptyComponent ecBtnIp = new EmptyComponent();
		ecBtnIp.setBorder(new EmptyBorder(0, 75, 0, 90));
		ecBtnIp.setLayout(new LineLayout(0, 0, 0, 0, 0, LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecBtnIp.setPreferredSize(new Dimension(-1, 35));

		btnGetWLanIp = new JCButton("\u63a2\u6d4b\u5e7f\u57df\u7f51IP");
		btnGetWLanIp.setPreferredSize(new Dimension(120, 21));
		btnGetWLanIp.setHorizontalAlignment(JCButton.CENTER);
		btnGetWLanIp.addActionListener(this);
		ecBtnIp.add(btnGetWLanIp, LineLayout.START);

		lblGameType = new JCLabel("\u6e38\u620f\u6a21\u5f0f");
		lblGameType.setBorder(new EmptyBorder(1, 0, 5, 10));
		lblGameType.setPreferredSize(new Dimension(74, 24));
		lblGameType.setHorizontalAlignment(JCLabel.RIGHT);
		cbGameType = new JCComboBox(
				new String[]{"\u4e24\u4eba\u5c40", "\u4e09\u4eba\u5c40", "\u56db\u4eba\u5c40", "\u4e94\u4eba\u5c40"});
		cbGameType.setPreferredSize(new Dimension(100, 21));
		EmptyComponent ecGameType = new EmptyComponent();
		ecGameType.setBorder(new EmptyBorder(0, 0, 0, 60));
		ecGameType.setLayout(new LineLayout(0, 0, 0, 0, 0, LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ecGameType.setPreferredSize(new Dimension(-1, 35));
		ecGameType.add(lblGameType, LineLayout.START);
		ecGameType.add(cbGameType, LineLayout.START);

		mainPane.add(ecServerName, LineLayout.START_FILL);
		mainPane.add(ecTimeDelay, LineLayout.START_FILL);
		mainPane.add(ecIp, LineLayout.START_FILL);
		mainPane.add(ecBtnIp, LineLayout.START_FILL);
		mainPane.add(ecGameType, LineLayout.START_FILL);
		return mainPane;
	}

	private JImagePane createBottomPane() {
		JImagePane bottomPane = new JImagePane();
		bottomPane.setPreferredSize(new Dimension(334, 30));
		bottomPane.setImage(ImageUtils.getImage("dialog_bottom.png"));
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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == checkNoTimeDelay) {
			txtTimeDelay.setEnabled(!txtTimeDelay.isEnabled());

		} else if (e.getSource() == btnGetWLanIp) {

			try {
				txtIp.setText(InetAddress.getLocalHost().getHostAddress());

			} catch (UnknownHostException e1) {
				log.error("Host unknown");
				JCMessageBox.createErrorMessageBox(this, "Error",
						"\u65e0\u6cd5\u627e\u5230\u4e3b\u673aIP, \u8bf7\u624b\u52a8\u8bbe\u7f6e").open();
			}

		} else if (e.getSource() == btnCancel) {
			this.dispose();

		} else if(e.getSource() == btnStart) {
			mainFrame.changeLogVisible();
			this.dispose();
		}
	}

	public String getUserName() {
		Map<String, String> map = System.getenv();
		return map.get("USERNAME");
	}
}
