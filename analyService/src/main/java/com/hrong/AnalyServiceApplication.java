package com.hrong;

import com.hrong.conf.HbaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author hrong
 * @ClassName AnalyServiceApplication
 * @Description
 * @Date 2019/5/20 15:32
 **/
@SpringBootApplication
@EnableConfigurationProperties({HbaseConfig.class})
public class AnalyServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnalyServiceApplication.class, args);
	}
}
