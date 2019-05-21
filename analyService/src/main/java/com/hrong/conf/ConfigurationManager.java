package com.hrong.conf;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Author hrong
 * @ClassName ConfigurationManager
 * @Description
 * @Date 2019/5/21 09:49
 **/
public class ConfigurationManager {
	private static Properties prop = new Properties();

	static {
		try {
			InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream("application.yml");
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取指定key对应的value
	 * @param key
	 * @return value
	 */
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static int getInteger(String property) {
		String value = prop.getProperty(property, "0");
		return Integer.valueOf(value);
	}
	public static boolean getBoolean(String property) {
		String result = prop.getProperty(property);
		return Boolean.valueOf(result);
	}

	public static void main(String[] args) {
		String a = prop.getProperty("hbase.zookeeper.quorum");
		System.out.println(a);
	}
}
