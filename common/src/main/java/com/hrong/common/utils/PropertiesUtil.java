package com.hrong.common.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @Author hrong
 * @ClassName PropertiesUtil
 * @Description
 * @Date 2019/5/27 11:12
 **/
public class PropertiesUtil {
	private static Config CONFIG = null;

	private PropertiesUtil(String resourceName) {
		CONFIG = ConfigFactory.load(resourceName);
	}

	public static PropertiesUtil getInstance(String resourceName) {
		return new PropertiesUtil(resourceName);
	}

	public String get(String key) {
		return CONFIG.getString(key).trim();
	}
}
