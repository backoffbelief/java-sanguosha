package org.dizem.common;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;
import org.apache.log4j.Logger;
import org.dizem.sanguosha.model.vo.SGSPacket;

import java.io.StringReader;

/**
 * User: DIZEM
 * Time: 11-3-25 上午12:49
 */
public class JSONUtil {

	/**
	 * logger instance
	 */
	private static Logger log = Logger.getLogger(JSONUtil.class);

	/**
	 * convert object to JSON string
	 *
	 * @param object to converted
	 * @return JSON string
	 */
	public static String convertToString(Object object) {
		try {
			return JSONMapper.toJSON(object).render(false);

		} catch (MapperException e) {
			log.error(e.getMessage());
			throw new RuntimeException("Error when convert vo to string ");
		}
	}

	/**
	 * Convert JSON string to object
	 * @param message JSON string
	 * @param destClass object class
	 * @return object
	 */
	public static Object convertToVO(String message, Class destClass) {
		try {
			JSONValue value = new JSONParser(new StringReader(message)).nextValue();
			return JSONMapper.toJava(value, destClass);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public static void main(String[] args) {
		String s = convertToString(new SGSPacket("123", "dizem", 123));
		System.out.println(s);
		SGSPacket p = (SGSPacket) convertToVO(s, SGSPacket.class);
		System.out.println(p.getPlayerName());
	}
}
