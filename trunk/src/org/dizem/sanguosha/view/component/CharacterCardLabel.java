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
public class CharacterCardLabel extends JLabel
		implements MouseMotionListener, MouseListener {

	private org.dizem.sanguosha.model.card.character.Character character;
	private ChooseCharacterDialog owner;
	private int posX, posY;

	public CharacterCardLabel(Character character, ChooseCharacterDialog owner) {
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


	public void setPosition(int x, int y) {
		setLocation(x, y);
		posX = x;
		posY = y;
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
		owner.selectCharacter(character);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(posX, posY);
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		int offsetX = (int) ((double) (e.getX() - 65) / 65. * 40);
		int offsetY = (int) ((double) (e.getY() - 98) / 98. * 40);

		if (label.getName().equals(UNSELECTED_TAG)) {
			label.setLocation(posX + offsetX, posY + offsetY);
			//label.updateUI();
		}
	}
}