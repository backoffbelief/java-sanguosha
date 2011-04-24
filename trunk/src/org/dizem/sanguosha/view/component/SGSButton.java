package org.dizem.sanguosha.view.component;

import craky.componentc.JCButton;
import org.dizem.common.AudioUtil;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: DIZEM
 * Time: 11-3-30 下午7:13
 */
public class SGSButton extends JCButton {

	public final static Dimension size = new Dimension(100, 40);

	public final static Font font = new Font("微软雅黑", Font.BOLD, 15);

	public SGSButton(String text) {
		super(text);
		setPreferredSize(size);
		setFont(font);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				AudioUtil.play("system/button-hover.wav");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				AudioUtil.play("system/button-down.wav");
			}
		});
	}
}
