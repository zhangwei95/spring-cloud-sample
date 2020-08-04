package com.zhangwei95.client;


import com.zhangwei95.config.FeignDisableHystrixConfiguration;
import com.zhangwei95.fallback.ProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * service-provider feign client
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/22
 */
@FeignClient(name = "service-provider",fallback = ProviderFallback.class)
//@FeignClient(name = "service-provider",configuration = FeignDisableHystrixConfiguration.class)
public interface ProviderClient {

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/public/test", method = RequestMethod.GET)
    public String getMessage();
}
