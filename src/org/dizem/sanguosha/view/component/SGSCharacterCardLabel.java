package org.dizem.sanguosha.view.component;

import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.view.dialog.ChooseCharacterDialog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
	private ChooseCharacterDialog owner;

	public SGSCharacterCardLabel(Character character, ChooseCharacterDialog owner) {
		super(ImageUtil.getIcon("generals/card/" + character.getJPGFilename()));
		this.character = character;
		this.owner = owner;
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
		JLabel label = (JLabel) e.getSource();
		label.setBorder(new LineBorder(Color.GREEN, 4));
	}

	public void mouseReleased(MouseEvent e) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		owner.dispose();
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
		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(label.getX(), 20 - (int) (20 * Math.sin(e.getX() * Math.acos(-1.) / 130.)));
			label.updateUI();
		}
	}
}