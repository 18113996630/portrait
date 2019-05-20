package com.hrong.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Author hrong
 * @ClassName HbaseConfig
 * @Description
 * @Date 2019/5/20 15:38
 **/
@Configuration
@ConfigurationProperties(prefix = HbaseConfig.CONF_PREFIX)
public class HbaseConfig {
	static final String CONF_PREFIX = "hbase.conf";

	private Map<String,String> confMaps;

	public Map<String, String> getHbaseConfig() {
		return confMaps;
	}
	public void setHbaseConfig(Map<String, String> confMaps) {
		this.confMaps = confMaps;
	}
}
