package com.hrong.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author hrong
 * @ClassName PropertiesUtil
 * @Description
 * @Date 2019/5/27 11:12
 **/
@Slf4j
public class PropertiesUtil {

	public static Properties getProperties(String resourceName) {
		Properties properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemResourceAsStream(resourceName));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("获取失败:{}", e.getMessage());
		}
		return properties;
	}
}
