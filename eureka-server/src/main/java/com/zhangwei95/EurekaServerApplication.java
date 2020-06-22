package com.zhangwei95;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @Describe EurekaServerApplication
 * create by zhangwei
 * @date 2020/6/22
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EurekaServerApplication.class, args);
    }
}
