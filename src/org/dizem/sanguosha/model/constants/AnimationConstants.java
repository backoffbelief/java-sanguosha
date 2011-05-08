package org.dizem.sanguosha.model.constants;

import org.dizem.common.ImageUtil;

import javax.swing.*;

/**
 * User: dizem@126.com
 * Time: 11-5-4 下午11:27
 */
public class AnimationConstants {
	public static ImageIcon[] IMG_SHA;
	public static ImageIcon[] IMG_SHAN;
	public static ImageIcon[] IMG_DAO_GUANG;
	public static ImageIcon[] IMG_PEACH;

	static {
		IMG_SHA = new ImageIcon[23];
		for (int i = 2; i <= 24; ++i) {
			String path = "system/animation/sha_000";
			if (i < 10) path += "0";
			IMG_SHA[i - 2] = ImageUtil.getIcon(path + i + ".png");
		}

		IMG_SHAN = new ImageIcon[24];
		for (int i = 1; i <= 24; ++i) {
			String path = "system/animation/shan_000";
			if (i < 10) path += "0";
			IMG_SHAN[i - 1] = ImageUtil.getIcon(path + i + ".png");
		}

		IMG_DAO_GUANG = new ImageIcon[39];
		for (int i = 1; i <= 30; ++i) {
			String path = "system/animation/DaoGuang_00";
			if (i < 10) path += "0";
			IMG_DAO_GUANG[i - 1] = ImageUtil.getIcon(path + i + ".png");
		}

		IMG_PEACH = new ImageIcon[20];
		for (int i = 0; i < IMG_PEACH.length; ++i) {
			IMG_PEACH[i] = ImageUtil.getIcon("system/animation/peach.png");
		}
	}

}
