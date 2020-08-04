package com.zhangwei95;

import com.zhangwei95.annotation.ExcludeFeignConfig;
import com.zhangwei95.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

/**
 *  eureka  service consumer
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/22
 */
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value= ExcludeFeignConfig.class)
})
@RibbonClient(name = "service-provider",configuration = RibbonConfiguration.class)
public class ConsumerApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate1() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
