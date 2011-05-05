package org.dizem.sanguosha.view.component;

import javax.swing.*;
import java.awt.*;

/**
 * 消息显示区
 * <p/>
 * User: dizem
 * Time: 11-4-25 上午8:20
 */
public class MessageLabel extends JLabel implements Runnable {


	/**
	 * 渐变字体集合
	 */
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

	/**
	 * 当前显示县城
	 */
	public Thread currentThread;
	/**
	 * 是否正在显示
	 */
	public boolean isShowing = false;

	private boolean keep = false;

	public MessageLabel() {
		setSize(500, 80);
		setHorizontalAlignment(CENTER);
		setOpaque(false);
	}

	/**
	 * 动态显示消息
	 *
	 * @param text 消息
	 */
	public void showText(String text) {
		showText(text, false);
	}

	public void showText(String text, boolean keep) {
		this.keep = keep;
		setText(text);
		if (currentThread == null && isShowing) {
			currentThread.stop();
			currentThread = null;
			System.gc();
			clear();
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
		if (!keep) clear();
	}

	public void clear() {
		setText("");
	}

}
