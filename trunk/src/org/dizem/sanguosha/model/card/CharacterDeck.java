package org.dizem.sanguosha.model.card;

import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.card.character.Character;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Random;

public class CharacterDeck {


	private static Logger log = Logger.getLogger(Deck.class);

	private static CharacterDeck instance;
	/**
	 * 当前牌堆
	 */
	private ArrayList<Character> deck;
	private Random ran;

	private CharacterDeck() {
		this.deck = new ArrayList<Character>();
		this.ran = new Random();
	}

	public synchronized static CharacterDeck getInstance() {
		if (instance == null) {
			instance = new CharacterDeck();
			instance.init();
		}
		return instance;
	}

	/**
	 * 从角色牌中取n张牌
	 *
	 * @param n
	 * @return
	 */
	public Character[] popCharacters(int n) {
		Character[] ch = new Character[n];
		int p = 0;
		for (int i = 0; i < n; i++) {
			ch[p++] = deck.remove(ran.nextInt(deck.size()));
		}
		return ch;
	}

	/**
	 * 获取3张主公牌和随机2张普通牌
	 *
	 * @return
	 */
	public Character[] popLoadCharacter() {
		Character[] ch = new Character[5];
		int p = 0;
		for (int i = 0; i < deck.size(); i++) {
			if (deck.get(i).getName().equals("刘备")
					|| deck.get(i).getName().equals("曹操")
					|| deck.get(i).getName().equals("孙权")) {
				ch[p++] = deck.remove(i);
			}
		}
		Character[] c = popCharacters(2);
		ch[3] = c[0];
		ch[4] = c[1];
		return ch;
	}

	/**
	 * 向牌堆添加角色
	 *
	 * @param c
	 */
	public void addCharacter(Character c) {
		deck.add(c);
	}

	/**
	 * 从配置文件CharacterSettings.xml读取数据，初始化牌堆
	 */
	private void init() {
		String CardXML = Constants.CHARACTER_SETTING_PATH;
		int cnt = 0;

		try {
			DocumentBuilder bf = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Node charSettings = bf.parse(CardXML).getChildNodes().item(0);
			NodeList charList = charSettings.getChildNodes();

			for (int i = 1; i < charList.getLength(); i++) {
				Node node = charList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {


					NamedNodeMap map = node.getAttributes();

					Character ch = new Character(map.getNamedItem("name")
							.getNodeValue(), map.getNamedItem("gender")
							.getNodeValue(), map.getNamedItem("life")
							.getNodeValue(), map.getNamedItem("kingdom")
							.getNodeValue(), map.getNamedItem("filename").getNodeValue());

					NodeList skills = node.getChildNodes();
					for (int j = 0; j < skills.getLength(); ++j) {
						Node skillNode = skills.item(j);
						if (skillNode.getNodeType() != Node.ELEMENT_NODE)
							continue;
						String skillName = null, skillDesciption = null;
						for (Node n = skillNode.getFirstChild(); n != null; n = n.getNextSibling()) {
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								if (n.getNodeName().equals("Name")) {
									skillName = n.getFirstChild().getNodeValue();
								} else if (n.getNodeName().equals("Description")) {
									skillDesciption = n.getFirstChild().getNodeValue();
								}
							}
						}

						ch.addSkill(new Skill(skillName, skillDesciption));
						++cnt;
					}

					addCharacter(ch);
				}

			}

			log.info("Read " + cnt + " characters from CharacterSetting.xml");

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 重置牌堆
	 */
	public void reset() {
		instance = null;
	}

	public static void main(String[] args) {
		CharacterDeck deck = CharacterDeck.getInstance();
		Character[] cs = deck.popLoadCharacter();
		for (org.dizem.sanguosha.model.card.character.Character c : cs) {
			System.out.println(c);
		}
	}
}
