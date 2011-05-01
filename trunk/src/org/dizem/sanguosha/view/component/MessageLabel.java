package org.dizem.sanguosha.view.component;

import org.dizem.common.PanelViewer;

import javax.swing.*;
import java.awt.*;

/**
 * User: dizem
 * Time: 11-4-25 上午8:20
 */
public class MessageLabel extends JLabel implements Runnable {

	public static final Font[] LABEL_FONT_LIST = {
			new Font("微软雅黑", Font.PLAIN, 15),
			new Font("微软雅黑", Font.PLAIN, 16),
			new Font("微软雅黑", Font.PLAIN, 17),
			new Font("微软雅黑", Font.PLAIN, 18),
			new Font("微软雅黑", Font.PLAIN, 19),
			new Font("微软雅黑", Font.PLAIN, 20),
			new Font("微软雅黑", Font.PLAIN, 21),
			new Font("微软雅黑", Font.PLAIN, 22),
			new Font("微软雅黑", Font.PLAIN, 23),
	};
	public Thread currentThread;
	public boolean isShowing = false;
	public MessageLabel() {
		setSize(500, 80);
		setHorizontalAlignment(CENTER);
		setOpaque(false);
	}

	public void showText(String text) {
		setText(text);
		if (currentThread == null && isShowing) {
			currentThread.stop();
			currentThread = null;
			System.gc();
			setText("");
			isShowing = false;
		}
		currentThread = new Thread(this);
		currentThread.start();
		isShowing = true;
	}


	public void run() {

		try {
			for (int i = 0; i < LABEL_FONT_LIST.length; ++i) {
				setFont(LABEL_FONT_LIST[i]);
				Thread.sleep(100);
			}
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isShowing = false;
		setText("");
	}
}
