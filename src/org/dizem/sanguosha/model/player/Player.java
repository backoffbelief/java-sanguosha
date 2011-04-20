package org.dizem.sanguosha.model.player;

import org.dizem.sanguosha.model.card.AbstractCard;
import org.dizem.sanguosha.model.card.equipment.*;
import org.dizem.sanguosha.model.exception.SGSException;

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
	private Phase phase;

	private org.dizem.sanguosha.model.card.character.Character character;
	private List<AbstractCard> handCards;
	private List<EquipmentCard> equipmentCards;
	private List<AbstractCard> effectCards;
	private static List<Player> instance;

	public Player(String name, Role role) {
		this.role = role;
		this.name = name;
		effectCards = new ArrayList<AbstractCard>();
		handCards = new ArrayList<AbstractCard>();
		equipmentCards = new ArrayList<EquipmentCard>();
		this.phase = Phase.START;
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


	/**
	 * 是否有空位装备当前装备牌
	 *
	 * @param card 装备牌
	 * @return 能否装备
	 */
	public boolean canAddEquipmentCard(EquipmentCard card) {
		System.out.println(card.getCardType());
		for (EquipmentCard equipmentCard : equipmentCards) {
			if (equipmentCard.getCardType() == card.getCardType()) {
				return false;
			}
		}
		return true;
	}

	public void addEquipmentCard(EquipmentCard card) {

		if (!canAddEquipmentCard(card)) {
			throw new SGSException("Player has already had this type of equipmentCard");

		} else {
			equipmentCards.add(card);
		}
	}


	public String getName() {
		return name;
	}

	public List<AbstractCard> getHandCards() {
		return handCards;
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

	public void removeEffectCard(AbstractCard card) {
		effectCards.remove(card);
	}

	public Phase getPhase() {
		return phase;
	}

	public int getPhaseID() {
		switch (phase) {
			case START:
				return 0;
			case JUDGE:
				return 1;
			case DRAW:
				return 2;
			case PLAY:
				return 3;
			case DISCARD:
				return 4;
			case FINISH:
				return 5;
			default:
				return 6;
		}
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public void removeEquipmentCard(int type) {
		for (EquipmentCard card : equipmentCards) {
			if (card.getCardType() == type) {
				equipmentCards.remove(type);
				break;
			}
		}
	}


}
