package org.dizem.sanguosha.model.vo;

import java.util.List;

/**
 * User: DIZEM
 * Time: 11-4-6 下午3:45
 */
public class SGSPacket {
	private String operation;
	private String playerId;
	private CardVO cardVO;
	private PlayerVO playerVO;
	private List<CardVO> cardVOList;

	public SGSPacket() {
	}

	public SGSPacket(String operation) {
		this.operation = operation;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
