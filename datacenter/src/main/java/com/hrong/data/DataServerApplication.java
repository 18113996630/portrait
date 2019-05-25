package com.hrong.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author hrong
 * @ClassName DataServerApplication
 * @Description
 * @Date 2019/5/25 14:28
 **/
@SpringBootApplication
@EnableEurekaClient
public class DataServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataServerApplication.class, args);
	}
}
