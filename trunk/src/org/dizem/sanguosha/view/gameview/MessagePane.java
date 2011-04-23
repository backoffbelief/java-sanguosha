package org.dizem.sanguosha.view.gameview;

import craky.componentc.JCButton;
import craky.componentc.JCComboBox;
import craky.componentc.JCTextArea;
import craky.componentc.JCTextField;
import craky.layout.LineLayout;
import org.dizem.common.PanelViewer;
import org.dizem.sanguosha.view.component.EmptyComponent;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: dizem
 * Time: 11-4-22 下午12:58
 */
public class MessagePane extends JPanel {
	private JTextArea txtLog = new JTextArea();
	private JTextArea txtMessage = new JTextArea();
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
	public MessagePane() {
		super();
		cbUsers.addItem("所有人");
		setSize(220, 350);
		setOpaque(false);
		setLayout(null);
		txtLog.setEditable(false);
		txtLog.setFocusable(false);
		txtLog.setLineWrap(true);
		txtLog.setOpaque(false);

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
		txtMessage.setFocusable(false);
		txtMessage.setLineWrap(true);
		txtMessage.setOpaque(false);
		txtMessage.setFont(MSG_FONT);
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
					txtMessage.append(txtInput.getText() + "\n");
					txtInput.setText("");
				} else {
					txtMessage.append("系统提示：不能发送空消息\n");
				}
			}
		});
		JComponent inputPanel = createInputPanel();
		inputPanel.setBounds(0, 300, 218, 50);
		add(scLog);
		add(scMessage);
		add(inputPanel);
	}

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

	public static void main(String[] args) {
		PanelViewer.display(new MessagePane());
	}
}
