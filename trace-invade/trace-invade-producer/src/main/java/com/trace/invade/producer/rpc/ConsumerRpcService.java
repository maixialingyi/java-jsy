package com.trace.invade.producer.rpc;

import com.trace.invade.producer.interceptors.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "trace-invade-consumer", configuration = FeignRequestInterceptor.class)
public interface ConsumerRpcService {

    @GetMapping("/hi")
    String hi();
}