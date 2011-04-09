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
public class ImageUtils {

	private static Logger log = Logger.getLogger(ImageUtils.class);

	/**
	 * cache to store image
	 */
	private static Map cache = new HashMap();


	/**
	 * Get image icon with specific name
	 *
	 * @param name of image icon
	 * @return image icon
	 */
	public static ImageIcon getIcon(String name) {
		if (cache.containsKey(name)) {
			return (ImageIcon) cache.get(name);

		} else {
			ImageIcon icon = SwingResourceManager.getIcon(Constants.IMAGE_DIR + Constants.FILE_SEP + name);

			if (icon == null) {
				throw new RuntimeException("Icon {name:" + name + "} does not exist");
			} else {
				cache.put(name, icon);
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
		if (cache.containsKey(name)) {
			return (Image) cache.get(name);

		} else {
			Image image = new ImageIcon(Constants.IMAGE_DIR + Constants.FILE_SEP + name).getImage();

			if (image == null) {
				throw new RuntimeException("Image {name:" + name + "} does not exist");
			} else {
				cache.put(name, image);
			}
			return image;
		}
	}
}
