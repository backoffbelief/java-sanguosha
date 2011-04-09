package org.dizem.sanguosha.view.component;

import craky.componentc.JCTextArea;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-3-30 下午9:42
 */
public class SGSTextArea extends JTextArea {
	public final static Dimension size = new Dimension(300, 220);

	public final static Font font = new Font("微软雅黑", Font.PLAIN, 13);

	public SGSTextArea() {
		setEditable(false);
		setVisible(false);
		setForeground(Color.WHITE);
		setPreferredSize(size);
		setSize(size);
		setLineWrap(true);
		setOpaque(false);
		setFont(font);
	}
}
