package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.LogUtils;
import org.dizem.sanguosha.model.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * User: DIZEM
 * Time: 11-4-6 下午1:40
 */
public class GameServer {

	private static Logger log = Logger.getLogger(GameServer.class);

	private int port;
	private ServerSocket serverSocket;
	private Vector<ObjectInputStream> vi;
	private Vector<ObjectOutputStream> vo;
	private boolean isStarted = false;
	private Thread listenThread;

	private Player[] players;


	public GameServer(int port, int nPlayer) {
		this.port = port;
		vi = new Vector<ObjectInputStream>();
		vo = new Vector<ObjectOutputStream>();
		players = new Player[nPlayer];
	}

	public static void main(String[] args) {
		LogUtils.init();
		new GameServer(7000, 1).start();
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			log.error(e.getMessage());
			return;
		}

		isStarted = true;

		listenThread = new Thread(new Runnable() {

			public void run() {
				while (isStarted) {
					Socket socket;
					try {
						socket = serverSocket.accept();
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
						socket.getOutputStream().write("connected".getBytes());

						new ServerThread(ois, oos).start();

						log.info(socket.getInetAddress().getHostAddress() + " joined");

					} catch (IOException e) {
						log.error(e.getMessage());

					}

				}
			}
		});

		listenThread.start();

		log.info("Server started.");

	}

	public void stop() throws IOException {
		for (int i = 0; i < vi.size(); ++i) {
			vi.get(i).close();
			vo.get(i).close();
		}
		serverSocket.close();
		listenThread.interrupt();//todo fix
		log.info("Server stopped.");
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		if (!isStarted)
			this.port = port;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean started) {
		isStarted = started;
	}


	/**
	 * Server Thread
	 */
	class ServerThread extends Thread {

		private Logger log = Logger.getLogger(ServerThread.class);

		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public ServerThread(ObjectInputStream ois, ObjectOutputStream oos) {
			vi.add(ois);
			vo.add(oos);
			this.ois = ois;
			this.oos = oos;
		}

		@Override
		public void run() {
			Object object = null;

			while (true) {
				try {
					object = ois.readObject();
					log.info("Received: " + object);
					
					for (int i = 0; i < vo.size(); i++) {
						if (!vo.get(i).equals(oos)) {
							vo.get(i).writeObject(object);
						}
					}

				} catch (IOException e) {
					vi.remove(ois);
					vo.remove(oos);
					log.info("A client is existed");
					break;

				} catch (ClassNotFoundException e) {
					log.error(e.getMessage());
					return;
				}
			}
		}
	}
}
