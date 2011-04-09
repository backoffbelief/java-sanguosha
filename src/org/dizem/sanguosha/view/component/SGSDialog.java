package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-3-31 上午1:20
 */
public class SGSDialog extends JDialog {

	protected JButton btnStart;
	protected JButton btnCancel;

	public SGSDialog(Window owner, String title) {
		super(owner, title, ModalityType.DOCUMENT_MODAL);
		setIconImage(ImageUtils.getImage("sgs.png"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		if (owner != null) {
			setLocation(owner.getX() + (owner.getWidth() - getWidth()) / 2,
					owner.getY() + (owner.getHeight() - getHeight()) / 2);
		} else {
			setLocationRelativeTo(null);
		}
		setResizable(false);
	}
}
