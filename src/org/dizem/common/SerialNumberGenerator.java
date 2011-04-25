package org.dizem.common;

/**
 * User: DIZEM
 * Time: 11-3-25 下午7:27
 */
public class SerialNumberGenerator {

	private static int id = 1;

	public synchronized static int nextInt() {
		return id++;
	}
}
