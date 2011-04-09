package org.dizem.sanguosha.model;

import javax.swing.*;
import java.awt.*;

/**
 * User: DIZEM
 * Time: 11-3-2 下午4:40
 */
public interface Constants {

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


	String GC_PERIOD = "GC_Period";
	String EMPTY = "";
	String STR_ABOUT = "关于";
	String STR_START_GAME = "启动游戏";
	String STR_EXIT = "退出";
	String STR_SETTING = "设置";
	String STR_START_SERVER = "启动服务器";

	Dimension DEFAULT_WINDOW_SIZE = new Dimension(500, 500);

}
