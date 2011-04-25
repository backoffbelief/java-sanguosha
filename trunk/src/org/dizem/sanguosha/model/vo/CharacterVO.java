package org.dizem.sanguosha.model.vo;

import org.dizem.sanguosha.model.card.Skill;
import org.dizem.sanguosha.model.card.character.*;

import java.util.ArrayList;

/**
 * User: dizem
 * Time: 11-4-25 上午9:29
 */
public class CharacterVO {
	private String name;
	private String gender;
	private int maxLife;
	private int life;
	private String kingdom;
	private Skill[] skills;
	private String filename;

	public CharacterVO() {
	}

	public CharacterVO(org.dizem.sanguosha.model.card.character.Character character) {
		this.name = character.getName();
		this.gender = character.getGender();
		this.maxLife = character.getMaxLife();
		this.life = character.getMaxLife();
		this.kingdom = character.getKingdom();
		this.skills = character.getSkills();
		this.filename = character.getPNGFilename();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	public Skill[] getSkills() {
		return skills;
	}

	public void setSkills(Skill[] skills) {
		this.skills = skills;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
