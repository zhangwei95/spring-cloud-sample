package com.zhangwei95.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/22
 */
@RestController
@RequestMapping(value = "/api")
public class TestController {

    @GetMapping(value = "/test")
    public String getMessage(){
        return "hello";
    }
}
