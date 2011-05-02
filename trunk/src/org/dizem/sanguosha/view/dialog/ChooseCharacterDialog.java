package org.dizem.sanguosha.view.dialog;

import craky.componentc.JCDialog;
import org.dizem.common.LogUtil;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.character.Character;
import org.dizem.sanguosha.view.component.CharacterCardLabel;
import org.dizem.sanguosha.view.gameview.GameFrame;

import java.awt.*;

/**
 * 选择武将角色对话框
 * 
 * User: dizem
 * Time: 11-4-23 下午1:09
 */
public class ChooseCharacterDialog extends JCDialog {

	/**
	 * 父窗体
	 */
	private GameFrame owner;
	/**
	 * 是否主公选武将角色
	 */
	private boolean isLord;

	/**
	 * 构造函数
	 * @param owner	父窗体
	 * @param characters	可选武将角色
	 */
	public ChooseCharacterDialog(GameFrame owner, Character[] characters) {

		super(owner, "选择武将牌");
		setLayout(null);
		this.owner = owner;
		isLord = characters.length == 5;

		setSize(140 * characters.length + 10, 250);

		for (int i = 0; i < characters.length; ++i) {
			CharacterCardLabel label = new CharacterCardLabel(characters[i], this);
			label.setPosition(10 + 140 * i, 20);
			add(label);
		}
		int offsetX = (owner.getWidth() - getWidth()) / 2;
		int offsetY = (owner.getHeight() - getHeight()) / 2;
		setLocation(owner.getX() + offsetX, owner.getY() + offsetY);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setModal(true);
		setVisible(true);
	}


	/**
	 * 选定武将角色
	 * @param character 武将角色
	 */
	public void selectCharacter(Character character) {
		owner.setCharacter(character, isLord);
		dispose();
	}
}
