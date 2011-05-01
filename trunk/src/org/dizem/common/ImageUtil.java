package org.dizem.common;

import craky.util.SwingResourceManager;
import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: DIZEM
 * Time: 11-2-27 下午12:32
 */
public class ImageUtil {

	private static Logger log = Logger.getLogger(ImageUtil.class);

	/**
	 * cache to store image
	 */
	private static Map<String, Image> imageCache = new HashMap<String, Image>();
	private static Map<String, ImageIcon> iconCache = new HashMap<String, ImageIcon>();

	/**
	 * Get image icon with specific name
	 *
	 * @param name of image icon
	 * @return image icon
	 */
	public static ImageIcon getIcon(String name) {
		if (iconCache.containsKey(name)) {
			return (ImageIcon) iconCache.get(name);

		} else {
			ImageIcon icon = SwingResourceManager.getIcon(Constants.IMAGE_DIR + Constants.FILE_SEP + name);

			if (icon == null) {
				throw new RuntimeException("Icon {name:" + name + "} does not exist");
			} else {
				iconCache.put(name, icon);
			}
			return icon;
		}
	}


	/**
	 * Get image with specific name
	 *
	 * @param name of image
	 * @return image
	 */
	public static Image getImage(String name) {
		if (imageCache.containsKey(name)) {
			return imageCache.get(name);

		} else {
			Image image = new ImageIcon(Constants.IMAGE_DIR + Constants.FILE_SEP + name).getImage();

			if (image == null) {
				throw new RuntimeException("Image {name:" + name + "} does not exist");
			} else {
				imageCache.put(name, image);
			}
			return image;
		}
	}
}
