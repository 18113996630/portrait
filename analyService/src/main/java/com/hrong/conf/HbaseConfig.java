package com.hrong.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @Author hrong
 * @ClassName HbaseConfig
 * @Description
 * @Date 2019/5/20 15:38
 **/
@Configuration(value = "hbaseConfig")
@ConfigurationProperties(prefix = HbaseConfig.CONF_PREFIX)
@PropertySource("/application.yml")
public class HbaseConfig {
	static final String CONF_PREFIX = "hbase.conf";

	private Map<String,String> confMaps;

	public Map<String, String> getConfMaps() {
		return confMaps;
	}

	public void setConfMaps(Map<String, String> confMaps) {
		this.confMaps = confMaps;
	}
}
