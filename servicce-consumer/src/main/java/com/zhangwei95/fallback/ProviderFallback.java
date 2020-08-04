package com.zhangwei95.fallback;

import com.zhangwei95.client.ProviderClient;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei
 * @Describe 类描述
 * @date 2020/6/22
 */
@Component
public class ProviderFallback implements ProviderClient {

    @Override
    public String getMessage() {
        return "feign hystrix failBack";
    }
}
