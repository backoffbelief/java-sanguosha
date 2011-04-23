package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtils;
import org.dizem.sanguosha.model.card.character.*;
import org.dizem.sanguosha.model.card.character.Character;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static org.dizem.sanguosha.model.Constants.UNSELECTED_TAG;

/**
 * User: dizem
 * Time: 11-4-23 下午4:45
 */
public class SGSCharacterCardLabel extends JLabel
		implements MouseMotionListener, MouseListener {

	private org.dizem.sanguosha.model.card.character.Character character;

	public SGSCharacterCardLabel(Character character) {
		super(ImageUtils.getIcon("generals/card/" + character.getJPGFilename()));
		this.character = character;
		setName(UNSELECTED_TAG);
		setOpaque(false);
		setSize(130, 188);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public Character getCharacter() {
		return character;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(label.getX(), 30);
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();

		double scale = -2. / 135.;
		double delta = scale * (e.getX() - 130) * e.getX();
		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(label.getX(), (int) (30 - delta));
			label.updateUI();
		}
	}
}