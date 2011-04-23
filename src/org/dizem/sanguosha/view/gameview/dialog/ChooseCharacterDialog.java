package org.dizem.sanguosha.view.gameview.dialog;

import craky.componentc.JCDialog;
import org.dizem.common.ImageUtils;
import org.dizem.common.LogUtils;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.view.component.SGSCharacterCardLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * User: dizem
 * Time: 11-4-23 下午1:09
 */
public class ChooseCharacterDialog extends JCDialog {


	public ChooseCharacterDialog(Frame owner, Character[] characters) {

		super(owner, "选择武将牌");
		setLayout(null);
		setSize(140 * 5 + 10, 250);

		for (int i = 0; i < characters.length; ++i) {
			SGSCharacterCardLabel label = new SGSCharacterCardLabel(characters[i]);
			label.setLocation(10 + 140 * i, 30);
			add(label);
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}



	public static void main(String[] args) {
		LogUtils.init();
		new ChooseCharacterDialog(null, CharacterDeck.getInstance().popCharacters(5));
	}
}
