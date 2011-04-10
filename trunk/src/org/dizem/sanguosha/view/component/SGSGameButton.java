package org.dizem.sanguosha.view.component;

import craky.componentc.JCButton;

import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-10 下午2:14
 */
public class SGSGameButton extends JCButton {

	private static final Dimension PREFERRED_SIZE = new Dimension(62, 30);

	public SGSGameButton(String text) {
		super(text);
		setFocusable(false);
		setSize(PREFERRED_SIZE);
		setPreferredSize(PREFERRED_SIZE);
		setEnabled(false);
	}

	public SGSGameButton(String text, String toolTipText) {
		this(text);
		setToolTipText(toolTipText);
	}
}
