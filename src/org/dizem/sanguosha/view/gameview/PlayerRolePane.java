package org.dizem.sanguosha.view.gameview;

import org.dizem.common.ImageUtil;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午2:53
 */
public class PlayerRolePane extends JPanel {


	private static Image imgBack = ImageUtil.getImage("/system/photo-back.png");
	private Image imgRole = ImageUtil.getImage("/generals/big/caocao.png");
	private int imgRoleX = 15;
	private int imgRoleY = 5;

	public PlayerRolePane() {
		setSize(imgBack.getWidth(null), imgBack.getHeight(null));
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(imgBack, 0, 0, null);
		if (imgRole != null) {
			g.drawImage(imgRole, imgRoleX, imgRoleY, null);
		}
	}
}
