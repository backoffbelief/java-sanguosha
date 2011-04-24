package org.dizem.sanguosha.model;

/**
 * User: dizem
 * Time: 11-4-24 下午1:00
 */
public class IDGenerator {

	private static int id = 1;

	public synchronized static int nextId() {
		return id++;
	}
}
