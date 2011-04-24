package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static org.dizem.sanguosha.model.Constants.*;
/**
 * User: dizem
 * Time: 11-4-23 下午9:57
 */
public class GameServerMonitor extends Thread {

	private static Logger log = Logger.getLogger(GameServerMonitor.class);
	private int port;

	private GameServer server;
	private MainFrame owner;

	public GameServerMonitor(GameServer server) {
		this.server = server;
		this.owner = server.getOwner();
		this.port = server.getPort();
	}

	@Override
	public void run() {

		try {
			DatagramSocket ds = new DatagramSocket(port);

			while (true) {
				log.info("Listening at port:" + port);

				byte[] data = new byte[10000];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				ds.receive(packet);

				String jsonString = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
				SGSPacket dp = (SGSPacket) JSONUtil.convertToVO(jsonString, SGSPacket.class);

				if(dp.getOperation().equals(OP_TEST_SERVER)) {
					
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
