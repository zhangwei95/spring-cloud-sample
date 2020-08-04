package com.zhangwei95.hystrixturbosample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * http://localhost:8901/turbine.stream
 */
@SpringBootApplication
@EnableTurbine
public class HystrixTurboSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixTurboSampleApplication.class, args);
    }

}
