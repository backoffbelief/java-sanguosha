package org.dizem.common;

import org.apache.log4j.PropertyConfigurator;
import org.dizem.sanguosha.model.constants.Constants;

/**
 * User: DIZEM
 * Time: 11-4-6 下午2:29
 */
public class LogUtil {

	public static void init() {
		PropertyConfigurator.configure(LogUtil.class.getResource(Constants.LOG4J_CONFIG_PATH));
	}
}
