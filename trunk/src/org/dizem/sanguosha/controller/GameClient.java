package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.LogUtils;
import org.dizem.common.StringUtils;
import org.dizem.sanguosha.model.exception.SGSException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * User: DIZEM
 * Time: 11-4-6 下午1:40
 */
public class GameClient {

	private static Logger log = Logger.getLogger(GameClient.class);

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private String serverIp;
	private int serverPort;

	private boolean isConnected = false;

	public static void main(String[] args) {
		LogUtils.init();
		System.out.println(
				new GameClient("localhost", 7000).connect());
	}

	public GameClient(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	public void send(Object e) throws IOException {
		oos.writeObject(e);
	}


	public String connect() {
		try {
			Socket s = new Socket(InetAddress.getByName(serverIp), serverPort);
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
			byte[] b = new byte[10];
			is.read(b);
			new ClientThread(ois).start();
			isConnected = true;
			return new String(b);

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean connected) {
		isConnected = connected;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		if(!StringUtils.isValidIp(serverIp))
			throw new SGSException("Ip is invalid");
		this.serverIp = serverIp;
	}

	class ClientThread extends Thread {
		private Logger log = Logger.getLogger(GameClient.class);

		private ObjectInputStream ois;

		public ClientThread(ObjectInputStream ois) {
			this.ois = ois;
		}

		@Override
		public void run() {
			Object object;

			while (true) {
				try {
					object = ois.readObject();
					log.info("receive: " + object);

				} catch (ClassNotFoundException e) {
					log.error(e.getMessage());

				} catch (IOException e) {
					log.info("exit");
					break;
				}
			}
		}
	}
}
