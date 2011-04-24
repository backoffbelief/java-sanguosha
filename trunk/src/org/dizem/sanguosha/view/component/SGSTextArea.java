package org.dizem.sanguosha.view.component;

import craky.componentc.JCTextArea;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-3-30 下午9:42
 */
public class SGSTextArea extends JCTextArea {
	public final static Dimension size = new Dimension(300, 220);

	public final static Font font = new Font("微软雅黑", Font.PLAIN, 13);

	public SGSTextArea() {
		setEditable(false);
		setVisible(false);
		setPreferredSize(size);
		setFocusable(false);
		setSize(size);
		setLineWrap(true);
		setAlpha(0.7f);
		setFont(font);
	}
}
