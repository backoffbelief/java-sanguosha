package org.dizem.sanguosha.model.player;

import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * User: DIZEM
 * Time: 11-3-30 下午3:10
 */
public class Player {

	private String name;
	private int score;
	private Role role;
	private Character character;
	private List<AbstractCard> handCards;
	private List<AbstractCard> equipmentCards;
	private List<AbstractCard> effectCards;
	private static List<Player> instance;

	public Player(String name, Role role) {
		this.role = role;
		this.name = name;
		effectCards = new ArrayList<AbstractCard>();
		handCards = new ArrayList<AbstractCard>();
		effectCards = new ArrayList<AbstractCard>();
	}

	public synchronized static List<Player> getPlayerList() {
		if (instance == null)
			instance = new ArrayList<Player>();
		return instance;
	}

	public synchronized static Player getSelf() {
		if (Player.getPlayerList().size() == 0) {
			instance.add(new Player("", null));
		}
		return instance.get(0);
	}

	public String getName() {
		return name;
	}

	public List<AbstractCard> getHandCards() {
		return handCards;
	}

	public List<AbstractCard> getEquipmentCards() {
		return equipmentCards;
	}

	public List<AbstractCard> getEffectCards() {
		return effectCards;
	}

	public Role getRole() {
		return role;
	}

	public int getLife() {
		return character.getLife();
	}


}
