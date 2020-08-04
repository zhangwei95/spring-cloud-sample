package com.zhangwei95.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/22
 */
@Api(tags = "test")
@RestController
@RequestMapping(value = "/api")
public class TestController {

    @GetMapping(value = "/test")
    @ApiOperation(value = "testApi", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getMessage() throws InterruptedException {
//        Thread.sleep(1000);
        return "hello";
    }

    @GetMapping(value = "/public/test")
    @ApiOperation(value = "unAuthApi", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getMessage1() throws InterruptedException {
//        Thread.sleep(1000);
        return "hello";
    }
}
