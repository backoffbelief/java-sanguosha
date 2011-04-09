package org.dizem.sanguosha.view.component;

import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午10:02
 */
public class Rect extends Rectangle {
	boolean flag;

	public Rect(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.flag = false;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
