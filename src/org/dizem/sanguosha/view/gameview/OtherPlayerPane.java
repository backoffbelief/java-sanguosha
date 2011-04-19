package org.dizem.sanguosha.view.gameview;

import org.apache.log4j.Logger;
import org.dizem.common.ImageUtils;
import org.dizem.common.PanelViewer;
import org.dizem.sanguosha.model.card.CharacterDeck;
import org.dizem.sanguosha.model.card.character.*;
import org.dizem.sanguosha.model.player.Player;
import org.dizem.sanguosha.model.player.Role;

import javax.swing.*;
import java.awt.*;

/**
 * User: dizem
 * Time: 11-4-19 上午9:10
 */
public class OtherPlayerPane extends JPanel {
	public static final int DEFAULT_WIDTH = 143;
	public static final int DEFAULT_HEIGHT = 195;

	private static Logger log = Logger.getLogger(OtherPlayerPane.class);
	private static final Image imgBack = ImageUtils.getImage("system/photo-back.png");
	private Player player = new Player("Test", Role.ROLE_ZG);
	private org.dizem.sanguosha.model.card.character.Character character = CharacterDeck.getInstance().popCharacters(1)[0];

	private Image imgAvatar;
	private Image imgKingdom;

	public OtherPlayerPane() {
		super();
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imgBack, 0, 0, null);
	}

	public static void main(String[] args) {
		PanelViewer.display(new OtherPlayerPane(), "Test");
	}
}
