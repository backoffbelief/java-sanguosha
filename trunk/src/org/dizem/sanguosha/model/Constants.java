package org.dizem.sanguosha.model;

import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.player.Role;

import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * User: DIZEM
 * Time: 11-3-2 下午4:40
 */
public interface Constants {

	Role[] ROLE_DISTRIBUTION = {
			Role.ZG, Role.NJ, Role.FZ, Role.ZC, Role.FZ
	};

	int[] OTHER_PANE_POSITION_OFFSET = {
			0, 0, 1, 3, 6,
	};

	int[][] OTHER_PANE_POSITION = {
			{300, 10},
			{100, 10}, {300, 10},
			{30, 50}, {30 + 160, 0}, {30 + 320, 0},
			{30, 200}, {30, 0}, {30 + 160, 0}, {30 + 320, 0},
	};

	String APP_TITLE = "Java 三国杀";
	String APP_VERSION = "v0.1";

	String FILE_SEP = System.getProperty("file.separator");

	/**
	 * Directory path
	 */
	String USER_DIR = System.getProperty("user.dir");
	String DATA_DIR = USER_DIR + FILE_SEP + "data";
	String CONFIG_DIR = USER_DIR + FILE_SEP + "config";
	String RES_DIR = USER_DIR + FILE_SEP + "res";
	String IMAGE_DIR = RES_DIR + FILE_SEP + "image";
	String AUDIO_DIR = RES_DIR + FILE_SEP + "audio";

	String CARD_DIR = IMAGE_DIR + FILE_SEP + "big-card";
	/**
	 * File path
	 */
	String CONFIG_PATH = CONFIG_DIR + FILE_SEP + "config.properties";
	String LOG4J_CONFIG_PATH = CONFIG_DIR + FILE_SEP + "log4j.properties";
	String CARD_SETTING_PATH = CONFIG_DIR + FILE_SEP + "CardSettings.xml";
	String CHARACTER_SETTING_PATH = CONFIG_DIR + FILE_SEP + "CharacterSettings.xml";
	;

	String ICON_PATH = IMAGE_DIR + FILE_SEP + "sgs.ico";
	String LOGO_FILE_NAME = "system" + FILE_SEP + "logo.png";
	/**
	 * Database Setting
	 */
	String DB_DRIVER = "smallsql.database.SSDriver";
	String DB_URL = "jdbc:smallsql:data";

	String SELECTED_TAG = "T";
	String UNSELECTED_TAG = "F";

	String GC_PERIOD = "GC_Period";
	String EMPTY = "";
	String STR_ABOUT = "关于";
	String STR_START_GAME = "启动游戏";
	String STR_EXIT = "退出";
	String STR_SETTING = "设置";
	String STR_START_SERVER = "启动服务器";

	int DATA_PACKET_SIZE = 10000;
	

	String OP_TEST_SERVER = "OP_TEST_SERVER";
	String OP_CONNECT = "OP_CONNECT";
	String OP_UPDATE_PLAYERS = "OP_UPDATE_PLAYERS";
	String OP_DISTRIBUTE_ID = "OP_DISTRIBUTE_ID";


	
	Image[] IMG_ROLE = {
			ImageUtil.getImage("system/roles/small-lord.png"),
			ImageUtil.getImage("system/roles/small-loyalist.png"),
			ImageUtil.getImage("system/roles/small-rebel.png"),
			ImageUtil.getImage("system/roles/small-renegade.png"),
	};

	Image IMG_DASHBOARD_AVATAR = ImageUtil.getImage("system/dashboard-avatar.png");
	Image IMG_DASHBOARD_EQUIP = ImageUtil.getImage("system/dashboard-equip.png");
	Image IMG_DASHBOARD_BACK = ImageUtil.getImage("system/dashboard-hand.png");
	Image IMG_GAME_FRAME_BACK = ImageUtil.getImage("system/gamebackground.jpg");

	Image[] IMG_HP_SMALL = {
			ImageUtil.getImage("system/magatamas/small-0.png"),
			ImageUtil.getImage("system/magatamas/small-1.png"),
			ImageUtil.getImage("system/magatamas/small-2.png"),
			ImageUtil.getImage("system/magatamas/small-3.png"),
			ImageUtil.getImage("system/magatamas/small-4.png"),
			ImageUtil.getImage("system/magatamas/small-5.png")
	};

	Image[] IMG_ROLE_DEAD = {
			ImageUtil.getImage("system/roles/small-lord.png"),
			ImageUtil.getImage("system/roles/small-loyalist.png"),
			ImageUtil.getImage("system/roles/small-rebel.png"),
			ImageUtil.getImage("system/roles/small-renegade.png"),
	};

	Image[] IMG_PHASE = {
			ImageUtil.getImage("system/phase/start.png"),
			ImageUtil.getImage("system/phase/judge.png"),
			ImageUtil.getImage("system/phase/draw.png"),
			ImageUtil.getImage("system/phase/play.png"),
			ImageUtil.getImage("system/phase/discard.png"),
			ImageUtil.getImage("system/phase/finish.png"),
			ImageUtil.getImage("system/phase/response.png"),
	};

	Image[] IMG_SUIT = {
			ImageUtil.getImage("system/suit/1.png"),
			ImageUtil.getImage("system/suit/2.png"),
			ImageUtil.getImage("system/suit/3.png"),
			ImageUtil.getImage("system/suit/4.png")
	};


	SimpleDateFormat LOG_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss - ");

}
