package org.dizem.common;


import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-4-1 下午3:01
 */
public class PanelViewer extends JFrame {

	private PanelViewer(JComponent panel) {
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(panel.getWidth() + 12, panel.getHeight() + 38);
		panel.setLocation(0, 0);
		add(panel);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public static void display(JComponent panel) {
		new PanelViewer(panel);
	}


}
