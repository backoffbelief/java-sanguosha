package org.dizem.sanguosha.controller;

import org.apache.log4j.Logger;
import org.dizem.common.JSONUtil;
import org.dizem.sanguosha.model.vo.SGSPacket;
import org.dizem.sanguosha.view.MainFrame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static org.dizem.sanguosha.model.constants.Constants.*;

/**
 * 服务端监控
 * 用于监控客户端发送的请求
 * <p/>
 * User: dizem
 * Time: 11-4-23 下午9:57
 */
public class GameServerMonitor extends Thread {

	private static Logger log = Logger.getLogger(GameServerMonitor.class);
	/**
	 * 监听端口
	 */
	private int port;
	/**
	 * 服务端实例
	 */
	private GameServer server;
	/**
	 * 服务端主窗体
	 */
	private MainFrame owner;
	/**
	 * 请求调度
	 */
	private ServerDispatcher dispatcher;

	/**
	 * 构造函数
	 *
	 * @param server 服务端实例
	 */
	public GameServerMonitor(GameServer server) {
		this.server = server;
		this.dispatcher = new ServerDispatcher(server);
		this.owner = server.getOwner();
		this.port = server.getPort();
	}

	/**
	 * 线程运行过程
	 */
	@Override
	public void run() {

		try {
			DatagramSocket ds = new DatagramSocket(port);

			while (true) {
				//log.info("Listening at port:" + port);

				//接收数据包
				byte[] data = new byte[DATA_PACKET_SIZE];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				ds.receive(packet);

				//生成JSON字符串
				String jsonString = new String(packet.getData(), 0, packet.getLength(), "UTF-8");


				//将字符串转换成类实例
				SGSPacket dp = (SGSPacket) JSONUtil.convertToVO(jsonString, SGSPacket.class);
				log.info("receive:" + dp.getOperation());
				//处理请求
				if (dp.is(OP_CONNECT)) {
					owner.appendLog("玩家:" + dp.getPlayerName() + "加入服务器");
					server.playerConnect(dp, packet.getAddress().getHostAddress());

				} else {
					dispatcher.dispatch(dp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

}
