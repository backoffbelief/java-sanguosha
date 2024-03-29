package org.dizem.sanguosha.model.constants;

import org.dizem.common.ImageUtil;
import org.dizem.sanguosha.model.player.Role;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * 全局常量
 * <p/>
 * User: DIZEM
 * Time: 11-3-2 下午4:40
 */
public interface Constants {

	Role[] ROLE_DISTRIBUTION = {
			Role.ZG, Role.NJ, Role.FZ, Role.ZC, Role.FZ
	};
	String[] ROLE_NAME = {
			"主公", "忠臣", "反贼", "内奸"
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

	boolean FLAG = true;

	/**
	 * Directory path
	 */
	String USER_DIR = System.getProperty("user.dir");
	String DATA_DIR = FLAG ? USER_DIR + FILE_SEP + "data" : "/data";
	String CONFIG_DIR = FLAG ? USER_DIR + FILE_SEP + "config" : "/config";
	String RES_DIR = FLAG ? USER_DIR + FILE_SEP + "res" : "/res";
	String IMAGE_DIR = RES_DIR + FILE_SEP + "image";
	String AUDIO_DIR = RES_DIR + FILE_SEP + "audio";

	String CARD_DIR = IMAGE_DIR + FILE_SEP + "big-card";
	/**
	 * File path
	 */
	String CONFIG_PATH = CONFIG_DIR + FILE_SEP + "config.properties";
	String LOG4J_CONFIG_PATH = CONFIG_DIR + FILE_SEP + "log4j.properties";
	String CARD_SETTING_PATH = CONFIG_DIR + FILE_SEP + "CardSettings2.xml";
	String CHARACTER_SETTING_PATH = CONFIG_DIR + FILE_SEP + "CharacterSettings.xml";


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
	String LOCAL_ADDRESS = "127.0.0.1";
	int DEFAULT_SERVER_PORT = 7777;

	String OP_TEST_SERVER = "OP_TEST_SERVER";
	String OP_CONNECT = "OP_CONNECT";
	String OP_UPDATE_PLAYERS = "OP_UPDATE_PLAYERS";
	String OP_INIT_CLIENT = "OP_INIT_CLIENT";
	String OP_SEND_MESSAGE = "OP_SEND_MESSAGE";
	String OP_SEND_CHAT_MESSAGE = "OP_SEND_CHAT_MESSAGE";
	String OP_DISTRIBUTE_ROLE = "OP_DISTRIBUTE_ROLE";
	String OP_DISTRIBUTE_LORD_CHARACTER = "OP_DISTRIBUTE_LORD_CHARACTER";
	String OP_DISTRIBUTE_CHARACTER = "OP_DISTRIBUTE_CHARACTER";
	String OP_FINISH_CHOOSING_LORD_CHARACTER = "OP_FINISH_CHOOSING_LORD_CHARACTER";
	String OP_FINISH_CHOOSING_CHARACTER = "OP_FINISH_CHOOSING_CHARACTER";
	String OP_DISTRIBUTE_CARD = "OP_DISTRIBUTE_CARD";
	String OP_UPDATE_PLAYERS_INFO = "OP_UPDATE_PLAYERS_INFO";
	String OP_PHASE_START = "OP_PHASE_START"; //回合开始
	String OP_PHASE_JUDGE_BEGIN = "OP_PHASE_JUDGE_BEGIN"; //判定阶段开始
	String OP_PHASE_JUDGE_END = "OP_PHASE_JUDGE_END"; //判定阶段结束
	String OP_PHASE_DRAW_BEGIN = "OP_PHASE_DRAW_BEGIN"; //摸牌阶段开始
	String OP_PHASE_DRAW_END = "OP_PHASE_DRAW_END"; //摸牌阶段结束
	String OP_PHASE_PLAY_BEGIN = "OP_PHASE_PLAY_BEGIN"; //出牌阶段开始
	String OP_PHASE_PLAY_END = "OP_PHASE_PLAY_END"; //出牌阶段结束
	String OP_PHASE_DISCARD_BEGIN = "OP_PHASE_DISCARD_BEGIN"; //弃牌阶段开始
	String OP_PHASE_DISCARD_END = "OP_PHASE_DISCARD_END"; //弃牌阶段结束
	String OP_ADD_EQUIPMENT = "OP_ADD_EQUIPMENT";
	String OP_DISCARD = "OP_DISCARD";//弃牌
	String OP_OFFER_CARD_TO = "OP_OFFER_CARD_TO";
	String OP_FEEDBACK = "OP_FEEDBACK";
	String OP_DECREASE_LIFE = "OP_DECREASE_LIFE";//减血
	String OP_OFFLINE = "OP_OFFLINE"; //客户端离线
	String OP_SERVER_STOP = "OP_SERVER_STOP";//服务器关闭
	String OP_EAT_PEACH = "OP_EAT_PEACH";//吃桃

	String PATTERN_IP = "((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)";
	String PATTERN_PORT = "\\d{1,5}";

	ImageIcon[] IMG_ROLE = {
			ImageUtil.getIcon("system/roles/small-lord.png"),
			ImageUtil.getIcon("system/roles/small-loyalist.png"),
			ImageUtil.getIcon("system/roles/small-rebel.png"),
			ImageUtil.getIcon("system/roles/small-renegade.png"),
	};

	Image[] IMG_ROLE2 = {
			ImageUtil.getImage("system/roles/lord-1.png"),
			ImageUtil.getImage("system/roles/loyalist-1.png"),
			ImageUtil.getImage("system/roles/rebel-1.png"),
			ImageUtil.getImage("system/roles/renegade-1.png"),
	};

	Image IMG_DASHBOARD_AVATAR = ImageUtil.getImage("system/dashboard-avatar.png");
	Image IMG_DASHBOARD_EQUIP = ImageUtil.getImage("system/dashboard-equip.png");
	Image IMG_DASHBOARD_BACK = ImageUtil.getImage("system/dashboard-hand.png");
	Image IMG_GAME_FRAME_BACK = ImageUtil.getImage("system/gamebackground.jpg");
	Image IMG_CARD_COVER = ImageUtil.getImage("system/card_cover.png");
	Image IMG_FRAME = ImageUtil.getImage("system/frame/bad.png");
	Image IMG_MARK_OTHER_PLAYER = ImageUtil.getImage("system/markCurrent.png");
	ImageIcon IMG_ICON_LABEL = ImageUtil.getIcon("system/sgs_icon.gif");

	Image[] IMG_HP_SMALL = {
			ImageUtil.getImage("system/magatamas/small-0.png"),
			ImageUtil.getImage("system/magatamas/small-1.png"),
			ImageUtil.getImage("system/magatamas/small-2.png"),
			ImageUtil.getImage("system/magatamas/small-3.png"),
			ImageUtil.getImage("system/magatamas/small-4.png"),
			ImageUtil.getImage("system/magatamas/small-5.png")
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


	String AUDIO_ADD_EQUIPMENT = "system/add-equip.mp3";

	ImageIcon[] IMG_SHA = AnimationConstants.IMG_SHA;
	ImageIcon[] IMG_SHAN = AnimationConstants.IMG_SHAN;
	ImageIcon[] IMG_DAO_GUANG = AnimationConstants.IMG_DAO_GUANG;
	ImageIcon[] IMG_PEACH = AnimationConstants.IMG_PEACH;

	String AUDIO_HIT = "system/hit.mp3";
	String AUDIO_SHA_MALE = "card/male/slash.mp3";
	String AUDIO_SHA_FEMALE = "card/female/slash.mp3";
	String AUDIO_SHAN_MALE = "card/male/jink.mp3";
	String AUDIO_SHAN_FEMALE = "card/female/jink.mp3";
	String AUDIO_GAME_BACK = "system/game-back-music.mp3";
	String AUDIO_PEACH = "card/common/peach.mp3";
}
