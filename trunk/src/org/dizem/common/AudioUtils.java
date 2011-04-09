package org.dizem.common;


import org.dizem.sanguosha.model.Constants;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AudioUtils {

	public static Map<String, AudioStream> cache = new HashMap<String, AudioStream>();

	private static AudioStream getAudioStream(String path) throws IOException {

//		if (cache.containsKey(path)) {
//			return cache.get(path);
//
//		} else {
		InputStream in = new FileInputStream(path);
		AudioStream as = new AudioStream(in);
		cache.put(path, as);
		return as;
//		}
	}

	public static void play(final String filename) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String path = Constants.AUDIO_DIR + Constants.FILE_SEP + filename;
					AudioPlayer.player.start(getAudioStream(path));
//					player player = Manager.createPlayer(new File(path).toURI().toURL());
//					player.prefetch();
//					player.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

//
//	public static void main(String[] args) throws IOException, NoPlayerException {
//		String path = Constants.AUDIO_DIR + "\\system\\choose-item.wav";
//		String playPath = "C:/music/1.mp3";
//		Player player = Manager.createPlayer(new File(playPath).toURI().toURL());
//		player.prefetch();
//		player.start();
//	}
}