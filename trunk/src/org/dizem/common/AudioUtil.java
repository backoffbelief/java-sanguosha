package org.dizem.common;


import javazoom.jl.player.Player;
import org.dizem.sanguosha.model.constants.Constants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AudioUtil {

	public static Map<String, FileInputStream> cache
			= new HashMap<String, FileInputStream>();


	public static Thread play(final String filename) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					String path = Constants.AUDIO_DIR + Constants.FILE_SEP + filename;
					FileInputStream stream = null;
					if(cache.containsKey(path)) {
						stream = cache.get(path);
					} else {
						stream = new FileInputStream(new File(path));
					}

					new Player(stream).play();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		t.start();
		return t;
	}
}