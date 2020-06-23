package com.zhangwei95;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

/**
 * 健康检查
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/23
 */
@SpringBootApplication
@EnableAdminServer
public class CloudAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudAdminApplication.class, args);
    }
}
