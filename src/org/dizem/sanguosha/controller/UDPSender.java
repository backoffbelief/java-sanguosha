package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * User: dizem
 * Time: 11-4-24 下午12:03
 */
public class UDPSender {

	private static Logger log = Logger.getLogger(UDPSender.class);

	public static void send(SGSPacket packet, String _ip, int _port) {
		try {
			DatagramSocket ds = new DatagramSocket();
			String message = JSONUtil.convertToString(packet);
			log.info("Send:" + message);
			byte[] data = message.getBytes("UTF-8");
			DatagramPacket dp = new DatagramPacket(
					data, data.length, InetAddress.getByName(_ip), _port
			);
			ds.send(dp);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}
