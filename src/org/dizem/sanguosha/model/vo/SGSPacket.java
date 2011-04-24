package org.dizem.sanguosha.model.vo;

import java.util.List;

/**
 * User: DIZEM
 * Time: 11-4-6 下午3:45
 */
public class SGSPacket {

	private String operation;
	private int clientPort;
	private String playerId;
	private CardVO cardVO;
	private PlayerVO playerVO;
	private List<CardVO> cardVOList;

	public SGSPacket() {
	}

	public SGSPacket(String operation) {
		this.operation = operation;
	}

	public SGSPacket(String operation, int clientPort) {
		this(operation);
		this.clientPort = clientPort;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}
}
