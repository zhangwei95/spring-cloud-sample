package com.zhangwei95.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;
import com.zhangwei95.client.ProviderClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * controller
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/22
 */
@Api(tags = {"test"})
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class TestController {

    @Autowired
    private ProviderClient providerClient;

    @Autowired
    public RestTemplate restTemplate;

    /**
     * restTemplate 熔断
     * @return
     */
    @GetMapping(value = "/test")
    @ApiOperation(value = "testApi", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @HystrixCommand(fallbackMethod = "getMessageFail",
    commandProperties = {
            @HystrixProperty(name = "fallback.enabled",value = "true")
    })
    public String getMessageRestTemplate(){
        String url = "http://service-provider/api/public/test";
        return restTemplate.getForEntity(url,String.class).getBody();
//        return  providerClient.getMessage();
    }

    /**
     * feign 熔断
     * @return
     */
    @GetMapping(value = "/test/feign")
    @ApiOperation(value = "testApi", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getMessageFeign(){
        return providerClient.getMessage();
    }

//    private String getMessageFail() {
//		//备用逻辑
//		return "restTemplate熔断";
//	}

    private String getMessageFail(Throwable throwable) {
        log.error("异常信息",throwable);
        //备用逻辑
        return "restTemplate熔断";
    }
}
