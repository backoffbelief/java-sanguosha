package org.dizem.sanguosha.view.gameview;

import craky.componentc.JCButton;
import craky.componentc.JCComboBox;
import craky.componentc.JCTextArea;
import craky.componentc.JCTextField;
import craky.layout.LineLayout;
import org.dizem.common.PanelViewer;
import org.dizem.sanguosha.controller.GameClient;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.vo.PlayerVO;
import org.dizem.sanguosha.view.component.ComboBoxItem;
import org.dizem.sanguosha.view.component.EmptyComponent;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志框和聊天记录面板
 *
 * User: dizem
 * Time: 11-4-22 下午12:58
 */
public class MessagePane extends JPanel {

	private JCTextArea txtLog = new JCTextArea();
	private JCTextArea txtMessage = new JCTextArea();
	private JScrollPane scMessage = new JScrollPane(txtMessage);
	private JScrollPane scLog = new JScrollPane(txtLog);
	private JTextField txtInput = new JTextField() {
		final Color HINT_COLOR = Color.LIGHT_GRAY;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (this.getText().trim().equals("") && !isFocusOwner()) {
				g.setFont(MSG_FONT);
				g.setColor(HINT_COLOR);
				g.drawString("  在此输入聊天消息", 5, 20);
			}
		}
	};
	private JCButton btnSend = new JCButton("发送");
	private JCComboBox cbUsers = new JCComboBox() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(this.getBackground());
			g.drawLine(2, 2, 80, 2);
			g.drawLine(2, 3, 80, 3);
			g.drawLine(2, 2, 2, 40);
			g.drawLine(3, 2, 3, 40);
		}
	};
	public static final Font LABEL_FONT = new Font("微软雅黑", Font.PLAIN, 15);

	public static final Font MSG_FONT = new Font("微软雅黑", Font.PLAIN, 12);

	/**
	 * 客户端控制实例
	 */
	private GameClient client;

	/**
	 * 构造函数
	 * @param client	客户端
	 */
	public MessagePane(final GameClient client) {
		super();
		this.client = client;

		cbUsers.addItem("所有人");
		setSize(220, 350);
		setOpaque(false);
		setLayout(null);
		txtLog.setEditable(false);
		txtLog.setFocusable(false);
		txtLog.setLineWrap(true);
		txtLog.setAlpha(0.3f);
		txtLog.setForeground(Color.BLACK);
		txtLog.setFont(MSG_FONT);

		scLog.setFocusable(false);
		scLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scLog.setBounds(0, 0, 218, 180);
		scLog.setOpaque(false);
		scLog.getViewport().setOpaque(false);
		scLog.getVerticalScrollBar().setOpaque(false);
		scLog.setFont(LABEL_FONT);
		scLog.setBorder(
				new TitledBorder(new LineBorder(new Color(37, 35, 22), 3), "出牌记录", 0, 0, LABEL_FONT));

		txtMessage.setEditable(false);
		txtMessage.setAlpha(0.3f);
		txtMessage.setFocusable(false);
		txtMessage.setLineWrap(true);
		txtMessage.setFont(MSG_FONT);
		txtMessage.setForeground(Color.BLACK);
		scMessage.setFocusable(false);
		scMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		scMessage.setBorder(new EmptyBorder(0, 0, 0, 0));
		scMessage.setBounds(0, 180, 218, 110);
		scMessage.setOpaque(false);
		scMessage.getViewport().setOpaque(false);
		scMessage.getVerticalScrollBar().setOpaque(false);
		scMessage.setBorder(new TitledBorder(new LineBorder(new Color(37, 35, 22), 3), "聊天记录", 0, 0, LABEL_FONT));

		txtInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtInput.getText().trim().length() != 0) {
					ComboBoxItem item = (ComboBoxItem) cbUsers.getSelectedItem();
					client.sendMessage(txtInput.getText(), item.getId());
					txtInput.setText("");


				} else {
					appendMessage("不能发送空消息");
				}
			}
		});
		JComponent inputPanel = createInputPanel();
		inputPanel.setBounds(0, 300, 218, 50);
		add(scLog);
		add(scMessage);
		add(inputPanel);
	}

	/**
	 * 创建输入栏
	 * @return
	 */
	public JComponent createInputPanel() {
		cbUsers.setPreferredSize(new Dimension(75, 30));
		cbUsers.setFocusable(false);
		cbUsers.setBorder(new LineBorder(new Color(37, 35, 22), 2));
		cbUsers.setBackground(new Color(140, 126, 81));
		cbUsers.getEditor().getEditorComponent().setBackground(cbUsers.getBackground());
		txtInput.setPreferredSize(new Dimension(135, 30));
		txtInput.setToolTipText("单击回车发送消息");
		txtInput.setOpaque(false);
		txtInput.setBorder(new LineBorder(new Color(37, 35, 22), 2));
		txtInput.setFont(txtMessage.getFont());
		btnSend.setPreferredSize(new Dimension(40, 28));
		btnSend.setBorder(new EmptyBorder(1, 1, 1, 1));

		EmptyComponent ec = new EmptyComponent();
		ec.setOpaque(false);
		ec.setLayout(new LineLayout(5, 0, 0, 0, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ec.setPreferredSize(new Dimension(-1, 40));
		ec.add(cbUsers, LineLayout.START);
		ec.add(txtInput, LineLayout.START);
		return ec;
	}


	/**
	 * 清空在线用户下拉列表
	 */
	public void clearUsers() {
		cbUsers.removeAllItems();
		cbUsers.addItem(ComboBoxItem.ANY_ONE_ITEM);
	}

	/**
	 * 添加玩家
	 * @param item
	 */
	public void addUser(ComboBoxItem item) {
		cbUsers.addItem(item);
	}

	/**
	 * 添加日志
	 * @param info 日志消息
	 */
	public void appendLog(String info) {
		txtLog.append(Constants.LOG_TIME_FORMAT.format(new Date()) + info + "\n");
		txtLog.selectAll();
		txtLog.setCaretPosition(txtLog.getSelectedText().length());
	}

	/**
	 * 添加聊天记录
	 * @param info 聊天记录
	 */
	public void appendMessage(String info) {
		txtMessage.append(info + "\n");
		txtMessage.selectAll();
		txtMessage.setCaretPosition(txtMessage.getSelectedText().length());
	}

}
