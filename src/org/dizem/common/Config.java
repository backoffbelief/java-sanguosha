package org.dizem.common;


import org.dizem.sanguosha.model.Constants;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * User: DIZEM
 * Time: 11-3-2 下午7:13
 */
public class Config {


	private String path;
	private Properties config;


	public Config(String path) {
		this.path = path;
		config = new Properties();
		loadConfig();
	}

	private void loadConfig() {
		try {
			config.load(new BufferedInputStream(new FileInputStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveConfig(String comments) {
		try {
			OutputStream out = new FileOutputStream(path);
			config.store(out, comments);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveConfig() {
		saveConfig(null);
	}

	public void saveProperty(String key, String value, String comments) {
		config.setProperty(key, value);
		saveConfig(comments);
	}

	public void saveProperty(String key, String value) {
		saveProperty(key, value, null);
	}

	public void setProperty(String key, String value) {
		config.setProperty(key, value);
	}

	public String getProperty(String key, String defaultValue) {
		return config.getProperty(key, defaultValue);
	}

	public String getProperty(String key) {
		return config.getProperty(key, "");
	}

	public void remove(String key) {
		if(config.containsKey(key))
			config.remove(key);
	}

	public void removeAndSave(String key) {
		remove(key);
		saveConfig();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static void main(String[] args) {
		Config c = new Config(Constants.CONFIG_PATH);
		Iterator itr = c.config.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry e = (Map.Entry) itr.next();
			System.out.println(e.getKey() + " " + e.getValue());
		}
		c.saveConfig("saved");
	}
}
