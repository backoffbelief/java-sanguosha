package org.dizem.sanguosha.view.dialog;

import craky.component.JImagePane;
import craky.componentc.*;
import craky.layout.LineLayout;
import craky.util.UIUtil;
import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.view.component.EmptyComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * 关于对话框
 *
 * User: DIZEM
 * Time: 11-3-26 下午9:24
 */
public class AboutDialog extends JCDialog implements ActionListener {

	private JComponent content;
	private JCButton btnOk;

	private static final int LABEL_HEIGHT = 21;

	public AboutDialog(Window owner) {
		super(owner, "关于", ModalityType.DOCUMENT_MODAL);
		getContentPane().setPreferredSize(new Dimension(300, 241));
		initUI();
		setLocationRelativeTo(this.getOwner());
		setVisible(true);
	}


	private void initUI() {
		content = (JComponent) getContentPane();
		JImagePane bannerPane = new JImagePane();
		content.setPreferredSize(new Dimension(338, 284));
		content.setLayout(new BorderLayout());
		content.setBorder(new EmptyBorder(0, 2, 2, 2));
		bannerPane.setPreferredSize(new Dimension(-1, 110));
		bannerPane.setMode(JImagePane.TILED);
		Image image = ImageUtil.getImage("system/banner.png");
		bannerPane.setImage(image);

		content.add(bannerPane, BorderLayout.NORTH);
		content.add(createMainPane(), BorderLayout.CENTER);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(this.getOwner());
		UIUtil.escAndEnterAction(this, btnOk, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	private JImagePane createMainPane() {
		JImagePane mainPane = new JImagePane();
		JCLabel lbVersion = new JCLabel(Constants.APP_TITLE + ' ' + Constants.APP_VERSION);
		JCLabel lbCopyright = new JCLabel("Copyright\u00A9 2010-2011 dizem.org All Rights Reserved");
		JCTextArea txtInfo = new JCTextArea();
		mainPane.setBackground(new Color(255, 255, 255, 150));
		mainPane.setLayout(new LineLayout(0, 6, 0, 4, 6, LineLayout.TRAILING, LineLayout.TRAILING, LineLayout.VERTICAL));
		lbVersion.setHorizontalAlignment(JCLabel.LEFT);
		lbVersion.setVerticalAlignment(JCLabel.TOP);
		lbVersion.setPreferredSize(new Dimension(-1, LABEL_HEIGHT));
		lbVersion.setBorder(null);
		lbCopyright.setHorizontalAlignment(JCLabel.LEFT);
		lbCopyright.setVerticalAlignment(JCLabel.TOP);
		lbCopyright.setPreferredSize(lbVersion.getPreferredSize());
		lbCopyright.setBorder(null);
		lbCopyright.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtInfo.setBorder(null);
		txtInfo.clearBorderListener();
		txtInfo.setImageOnly(true);
		txtInfo.setEditable(false);
		txtInfo.setPopupMenuEnabled(false);
		txtInfo.setFocusable(false);
		txtInfo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txtInfo.setText("\u672C\u8F6F\u4EF6\u5B8C\u5168\u514D\u8D39\uFF0C\u7981\u6B62\u7528\u4E8E\u4EFB\u4F55\u5546\u4E1A" +
				"\u7528\u9014\uFF0C\u4E2A\u4EBA\u7528\u6237\u57FA\u4E8E\u5B66\u4E60\n\u548C\u7814\u7A76\u76EE\u7684\u7684" +
				"\u4F20\u64AD\u3001\u4FEE\u6539\u5C06\u4E0D\u53D7\u9650\u5236\u3002");
		mainPane.add(lbVersion, LineLayout.START_FILL);
		mainPane.add(createAuthorComponent(), LineLayout.START_FILL);
		mainPane.add(lbCopyright, LineLayout.START_FILL);
		mainPane.add(txtInfo, LineLayout.MIDDLE_FILL);
		mainPane.add(createButton(), LineLayout.END);
		return mainPane;
	}

	private JComponent createAuthorComponent() {
		EmptyComponent ecAuthor = new EmptyComponent();
		JCLabel lbAuthor = new JCLabel("作者：");
		JCLabel lbMail = new JCLabel("dizem.org");
		ecAuthor.setPreferredSize(new Dimension(-1, LABEL_HEIGHT));
		ecAuthor.setLayout(new LineLayout(0, LineLayout.LEADING, LineLayout.LEADING, LineLayout.HORIZONTAL));
		lbAuthor.setHorizontalAlignment(JCLabel.LEFT);
		lbAuthor.setVerticalAlignment(JCLabel.TOP);
		lbAuthor.setBorder(null);
		lbMail.setHorizontalAlignment(JCLabel.LEFT);
		lbMail.setVerticalAlignment(JCLabel.TOP);
		lbMail.setBorder(null);
		lbMail.setForeground(new Color(22, 112, 235));
		lbMail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lbMail.setToolTipText("dizem@126.com");
		ecAuthor.add(lbAuthor, LineLayout.START_FILL);
		ecAuthor.add(lbMail, LineLayout.START_FILL);
		UIUtil.actionLabel(lbMail, new AbstractAction() {
			private static final long serialVersionUID = -8727069883429572261L;

			public void actionPerformed(ActionEvent e) {
				mailToAuthor(e);
			}
		});

		return ecAuthor;
	}

	private JComponent createButton() {
		EmptyComponent ecButton = new EmptyComponent();
		btnOk = new JCButton("确定(O)");
		ecButton.setPreferredSize(new Dimension(79, 21));
		ecButton.setLayout(new BorderLayout());
		btnOk.setPreferredSize(new Dimension(73, 21));
		btnOk.setMnemonic('O');
		btnOk.addActionListener(this);
		ecButton.add(btnOk, BorderLayout.WEST);
		return ecButton;
	}

	private void mailToAuthor(ActionEvent e) {
		String mailTo = ((JCLabel) e.getSource()).getToolTipText();

		try {
			Desktop.getDesktop().mail(new URI("mailto:" + mailTo));
		} catch (Exception ex) {
			String message = "打开邮件客户端错误，请登陆web" +
					"邮箱联系作者：" + mailTo + "!";
			JCMessageBox.createErrorMessageBox(AboutDialog.this, AboutDialog.this.getTitle(), message).open();
		}
	}

	public static void main(String[] args) {
		new AboutDialog(null);
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			this.dispose();
		}
	}
}
