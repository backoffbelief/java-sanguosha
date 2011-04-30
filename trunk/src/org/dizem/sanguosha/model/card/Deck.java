package org.dizem.sanguosha.model.card;

import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.Constants;
import org.dizem.sanguosha.model.card.equipment.EquipmentCard;
import org.dizem.sanguosha.model.card.skill.SkillCard;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Random;

/**
 * User: DIZEM
 * Time: 11-3-30 下午3:01
 */
public class Deck {

	private static Logger log = Logger.getLogger(Deck.class);

	private static Deck instance;

	//当前牌堆
	private ArrayList<AbstractCard> deck;
	//弃牌牌堆
	private ArrayList<AbstractCard> backDeck;
	//顶部缓冲牌堆 用于诸葛亮占星
	private ArrayList<AbstractCard> topBufferDeck;
	//底部缓冲牌堆 用于诸葛亮占星
	private ArrayList<AbstractCard> bottomBufferDeck;

	private Random random;

	private Deck() {
		deck = new ArrayList<AbstractCard>();
		backDeck = new ArrayList<AbstractCard>();
		topBufferDeck = new ArrayList<AbstractCard>();
		bottomBufferDeck = new ArrayList<AbstractCard>();
		random = new Random();
	}

	public synchronized static Deck getInstance() {
		if (instance == null) {
			instance = new Deck();
			instance.init();
		}
		return instance;
	}

	public int getRemainSize() {
		return deck.size();
	}

	/**
	 * 从牌堆摸一张牌
	 *
	 * @return
	 * @throws Exception
	 */
	public AbstractCard popCard() {
		if (!topBufferDeck.isEmpty())
			return topBufferDeck.remove(0);
		if (deck.size() == 0) {
			if (!bottomBufferDeck.isEmpty())
				return bottomBufferDeck.remove(0);
			refreshDeck();
		}
		System.out.println(deck.size());
		AbstractCard card = deck.remove(random.nextInt(deck.size()));
		return card;
	}

	/**
	 * 刷新牌堆，用于牌堆被摸完后
	 *
	 * @return
	 */
	public boolean refreshDeck() {
		if (deck.size() == 0) {
			deck = backDeck;
			backDeck = new ArrayList<AbstractCard>();
			if (deck.size() != 0)
				return true;
			else
				return false;
		}
		return false;
	}

	public void addToBack(AbstractCard card) {
		backDeck.add(card);
	}

	public void addToTop(AbstractCard card) {
		topBufferDeck.add(card);
	}

	public void addToBottom(AbstractCard card) {
		bottomBufferDeck.add(card);
	}

	private void init() {
		String CardXML = Constants.CARD_SETTING_PATH;
		String description = null;
		int cnt = 0;
		try {
			DocumentBuilder bf = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = bf.parse(CardXML);
			Node cardSettings = document.getChildNodes().item(0);
			NodeList nl = cardSettings.getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;

				String cardKind = nl.item(i).getNodeName();
				NodeList typeList = nl.item(i).getChildNodes();
				for (int j = 0; j < typeList.getLength(); j++) {

					if (typeList.item(j).getNodeType() != Node.ELEMENT_NODE)
						continue;

					NodeList cardList = typeList.item(j).getChildNodes();
					NamedNodeMap typeMap = typeList.item(j).getAttributes();

					for (int k = 0; k < cardList.getLength(); k++) {
						if (cardList.item(k).getNodeType() != Node.ELEMENT_NODE)
							continue;

						Node card = cardList.item(k);
						if (card.getNodeName().equals("description")) {
							description = card.getFirstChild().getNodeValue();

						} else if (card.getNodeName().equals("Card")) {

							NamedNodeMap map = card.getAttributes();
							if (cardKind.startsWith("Basic")) {
								addToBack(new BasicCard(
										map.getNamedItem("suit").getNodeValue(),
										map.getNamedItem("rank").getNodeValue(),
										typeMap.getNamedItem("type").getNodeValue(),
										description,
										typeMap.getNamedItem("filename").getNodeValue()));

							} else if (cardKind.startsWith("Skill")) {
								addToBack(new SkillCard(
										map.getNamedItem("suit").getNodeValue(),
										map.getNamedItem("rank").getNodeValue(),
										typeMap.getNamedItem("type").getNodeValue(),
										description,
										typeMap.getNamedItem("filename").getNodeValue()));

							} else if (cardKind.startsWith("Equipment")) {

								addToBack(EquipmentCard.create(
										map.getNamedItem("suit").getNodeValue(),
										map.getNamedItem("rank").getNodeValue(),
										typeMap.getNamedItem("type").getNodeValue(),
										description,
										typeMap.getNamedItem("filename").getNodeValue(),
										typeMap.getNamedItem("tag").getNodeValue()
								));
							}

							++cnt;
						}
					}
				}
			}
			log.info("Read " + cnt + " cards infomation from CardSetting.xml");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public static void main(String[] args) {
		Deck.getInstance();
	}
}
