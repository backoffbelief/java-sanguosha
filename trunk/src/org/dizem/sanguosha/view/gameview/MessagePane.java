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
	private JTextField txtInput = new JTextField();
	private JCButton btnSend = new JCButton("发送");
	private JCComboBox cbUsers = new JCComboBox();
	public static final Font LABEL_FONT = new Font("微软雅黑", Font.PLAIN, 15);

	public MessagePane() {
		super();
		cbUsers.addItem("所有人");
		setSize(220, 320);
		setOpaque(false);
		setLayout(null);
		txtLog.setEditable(false);
		txtLog.setFocusable(false);
		txtLog.setLineWrap(true);
		txtLog.setOpaque(false);

		scLog.setFocusable(false);
		scLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scLog.setBounds(0, 0, 218, 150);
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
		scMessage.setFocusable(false);
		scMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		scMessage.setBorder(new EmptyBorder(0, 0, 0, 0));
		scMessage.setBounds(0, 150, 218, 110);
		scMessage.setOpaque(false);
		scMessage.getViewport().setOpaque(false);
		scMessage.getVerticalScrollBar().setOpaque(false);
		scMessage.setBorder(new TitledBorder(new LineBorder(new Color(37, 35, 22), 3), "聊天记录", 0, 0, LABEL_FONT));
		txtInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtMessage.append(txtInput.getText() + "\n");
				txtInput.setText("");
			}
		});
		JComponent inputPanel = createInputPanel();
		inputPanel.setBounds(0, 270, 218, 50);
		add(scLog);
		add(scMessage);
		add(inputPanel);
	}

	public JComponent createInputPanel() {
		cbUsers.setPreferredSize(new Dimension(75, 30));
		cbUsers.setFocusable(false);
		cbUsers.setBorder(new LineBorder(new Color(37, 35, 22), 2));
		txtInput.setPreferredSize(new Dimension(140, 30));
		txtInput.setToolTipText("单击回车发送消息");
	
		txtInput.setOpaque(false);
		txtInput.setBorder(new LineBorder(new Color(37, 35, 22), 2));
		btnSend.setPreferredSize(new Dimension(40, 28));
		btnSend.setBorder(new EmptyBorder(1, 1, 1, 1));

		EmptyComponent ec = new EmptyComponent();
		ec.setOpaque(false);
		ec.setLayout(new LineLayout(0, 0, 0, 0, 0,
				LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		ec.setPreferredSize(new Dimension(-1, 40));
		ec.add(cbUsers, LineLayout.START);
		ec.add(txtInput, LineLayout.START);
		//ec.add(btnSend, LineLayout.START);
		return ec;
	}

	public static void main(String[] args) {
		PanelViewer.display(new MessagePane());
	}
}
