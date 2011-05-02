package org.dizem.sanguosha.view.component;

import craky.componentc.JCFrame;
import craky.componentc.JCMessageBox;

import java.awt.*;

/**
 * User: dizem@126.com
 * Time: 11-5-1 下午3:20
 */
public class SGSMessageBox {

	public static boolean query(Window owner, String title, String message) {
		JCMessageBox box = JCMessageBox.createQuestionMessageBox(owner, title, message);
		if (owner instanceof JCFrame) {
			JCFrame frame = (JCFrame) owner;
			box.setBackgroundImage(frame.getBackgroundImage());
		}

		return box.open() == JCMessageBox.YES_OPTION;
	}
}
