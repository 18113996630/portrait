package com.hrong.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author hrong
 * @ClassName ServerApplication
 * @Description
 * @Date 2019/5/25 12:54
 **/
@SpringBootApplication
@EnableEurekaServer
public class ServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
