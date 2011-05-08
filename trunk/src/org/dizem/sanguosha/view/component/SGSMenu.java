package org.dizem.sanguosha.view.component;

import craky.componentc.JCMenu;
import craky.componentc.JCMenuItem;
import craky.util.UIUtil;
import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.view.dialog.AboutDialog;
import org.dizem.sanguosha.view.gameview.GameFrame;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: dizem
 * Time: 11-4-23 上午10:34
 */
public class SGSMenu extends JCMenu implements PopupMenuListener {

	private static final Icon ICON = ImageUtil.getIcon("system/menu_button_icon.png");
	private static final Image ROLLOVER_IMAGE = ImageUtil.getImage("system/common_button_rollover_bg.png");
	private static final Image PRESSED_IMAGE = ImageUtil.getImage("system/common_button_pressed_bg.png");
	private GameFrame owner;

	public SGSMenu(GameFrame owner) {
		this.owner = owner;
		setToolTipText("\u4E3B\u83DC\u5355");
		setPreferredSize(new Dimension(5, 20));
		setShowWhenRollover(false);
		init();
		getPopupMenu().addPopupMenuListener(this);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				getModel().setRollover(true);
			}

			public void mouseExited(MouseEvent e) {
				getModel().setRollover(false);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		int iconWidth = ICON.getIconWidth();
		int iconHeight = ICON.getIconHeight();

		if (this.getModel().isSelected()) {
			UIUtil.paintImage(g, PRESSED_IMAGE, new Insets(2, 2, 2, 2), new Rectangle(0, 0, width, height), this);
		} else if (this.getModel().isRollover()) {
			UIUtil.paintImage(g, ROLLOVER_IMAGE, new Insets(2, 2, 2, 2), new Rectangle(0, 0, width, height), this);
		}

		ICON.paintIcon(this, g, (width - iconWidth) / 2, (height - iconHeight) / 2);
	}

	private void init() {

		JMenuItem itemHelp = new JCMenuItem("\u67e5\u770b\u5e2e\u52a9(V)");
		itemHelp.setMnemonic('V');
		this.add(itemHelp);
		JMenuItem itemAbout = new JCMenuItem("\u5173\u4e8e(A)");
		itemAbout.setMnemonic('A');
		itemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutDialog(owner);
			}
		});
		this.add(itemAbout);
	}

	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	}

	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	public void popupMenuCanceled(PopupMenuEvent e) {
	}
}
