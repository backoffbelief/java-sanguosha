package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static org.dizem.sanguosha.model.Constants.*;

/**
 * User: dizem
 * Time: 11-4-23 下午9:57
 */
public class GameClientMonitor extends Thread {

	private static Logger log = Logger.getLogger(GameClientMonitor.class);

	public int clientPort = 3000;


	private GameClient client;
	private boolean isReady = false;
	private ClientDispatcher dispatcher;

	public GameClientMonitor(GameClient client) {
		this.client = client;
		this.dispatcher = new ClientDispatcher(client);
	}

	@Override
	public void run() {
		try {
			DatagramSocket ds = createSocket();
			isReady = true;

			while (true) {
				byte[] data = new byte[DATA_PACKET_SIZE];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				ds.receive(packet);
				String jsonString = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
				log.info("Received:" + jsonString);
				dispatcher.dispatch(jsonString);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	private DatagramSocket createSocket() {
		try {
			DatagramSocket ds = new DatagramSocket(clientPort);
			log.info("客户端在" + clientPort + "启动");
			return ds;

		} catch (SocketException e) {
			log.info("客户端端口" + clientPort + "被占用");
			clientPort++;
			return createSocket();
		}
	}

	public int getClientPort() {
		return clientPort;
	}


	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean ready) {
		isReady = ready;
	}
}
