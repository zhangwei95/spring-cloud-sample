package com.zhangwei95.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/23
 */
@RequestMapping(value = "/api")
@RestController
public class PropertyController {

    @Value(value = "${eureka.instance.hostname}")
    private String hostname;

    /**
     *
     * @return
     */
    @GetMapping(value = "/hostname")
    public String getProperty(){
        return hostname;
    }
}
