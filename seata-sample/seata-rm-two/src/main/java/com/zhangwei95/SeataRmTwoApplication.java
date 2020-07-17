package com.zhangwei95;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yueyi2019
 */
@EnableDiscoveryClient
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class SeataRmTwoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SeataRmTwoApplication.class, args);
	}

}
