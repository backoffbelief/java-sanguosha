package org.dizem.sanguosha.view.dialog;

import craky.componentc.JCDialog;
import org.dizem.common.LogUtil;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.view.component.SGSCharacterCardLabel;

import java.awt.*;

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
			SGSCharacterCardLabel label = new SGSCharacterCardLabel(characters[i], this);
			label.setLocation(10 + 140 * i, 20);
			add(label);
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}



	public static void main(String[] args) {
		LogUtil.init();
		new ChooseCharacterDialog(null, CharacterDeck.getInstance().popCharacters(5));
	}
}
