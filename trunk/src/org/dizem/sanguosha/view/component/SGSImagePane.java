package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午2:56
 */
public abstract class SGSImagePane extends JPanel {

	protected JLabel lblImage;

	protected SGSImagePane(JFrame owner, String imgPath) {
		super();
		lblImage = new JLabel();
		ImageIcon img = ImageUtils.getIcon(imgPath);
		lblImage.setIcon(img);
		System.out.println(img.getIconHeight());
		lblImage.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		setSize(lblImage.getSize());
		owner.getLayeredPane().add(lblImage, new Integer(Integer.MIN_VALUE));
		((JPanel) owner.getContentPane()).setOpaque(false);
	}
}
