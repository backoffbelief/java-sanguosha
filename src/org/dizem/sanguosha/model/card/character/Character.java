package org.dizem.sanguosha.model.card.character;

import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.card.Skill;
import org.dizem.sanguosha.model.vo.CharacterVO;

import java.util.ArrayList;

/**
 * User: DIZEM
 * Time: 11-3-30 下午2:58
 */
public class Character {

	private static Logger log = Logger.getLogger(Character.class);

	/**
	 * 角色名字
	 */
	private String name;
	/**
	 * 角色性别
	 */
	private String gender;
	/**
	 * 角色生命
	 */
	private int maxLife;
	private int life;

	private String kingdom;

	private ArrayList<Skill> skills;

	private boolean killed;

	private String filename;

	public Character(CharacterVO vo) {
		this.name = vo.getName();
		this.gender = vo.getGender();
		this.filename = vo.getFilename();
		this.maxLife = vo.getMaxLife();
		this.life = vo.getLife();
		this.kingdom = vo.getKingdom();
		this.skills = new ArrayList<Skill>();
		for (Skill skill : vo.getSkills()) {
			skills.add(skill);
		}
	}

	public Character(String name, String gender, String life, String kingdom, String filename) {
		this.name = name;
		this.gender = gender;
		this.filename = filename;
		this.maxLife = Integer.parseInt(life);
		this.life = maxLife;
		this.kingdom = kingdom;
		this.skills = new ArrayList<Skill>();
	}

	public void addSkill(Skill skill) {
		skills.add(skill);
	}

	public Skill[] getSkills() {
		Skill[] skills = new Skill[this.skills.size()];
		for (int i = 0; i < skills.length; ++i)
			skills[i] = this.skills.get(i);
		return skills;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getLife() {
		return life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public String getKingdom() {
		return kingdom;
	}


	public boolean isKilled() {
		return killed;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}

	public String getPNGFilename() {
		return filename;
	}

	public String getJPGFilename() {
		int index = filename.lastIndexOf(".");
		String name = filename.substring(0, index);
		return name + ".jpg";
	}

	public void decreaseLife() {
		if (life > 0) {
			life--;
			log.info("Decrease life");

		} else {
			log.error("Can not decrease life");
		}

	}

	public String getKingdomImgName() {
		if (kingdom.equals("蜀"))
			return "shu.png";
		if (kingdom.equals("魏"))
			return "wei.png";
		if (kingdom.equals("吴"))
			return "wu.png";
		return "qun.png";
	}

	@Override
	public String toString() {
		return "Character{" +
				"name='" + name + '\'' +
				", gender='" + gender + '\'' +
				", life=" + life +
				", kingdom='" + kingdom + '\'' +
				", skills=" + skills +
				", killed=" + killed +
				'}';
	}
}
