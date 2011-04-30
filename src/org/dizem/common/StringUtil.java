package org.dizem.common;

/**
 * User: DIZEM
 * Time: 11-4-6 下午3:30
 */
public class StringUtil {

	public static final String IP_PATTERN
			= "(localhost)|(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])" +
			"\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";

	public static boolean isValidIp(String ip) {
		return ip.matches(IP_PATTERN);
	}

	public static void main(String[] args) {
		System.out.println("127.0.0.1111".matches(IP_PATTERN));
		System.out.println("localhost".matches(IP_PATTERN));
	}
}
