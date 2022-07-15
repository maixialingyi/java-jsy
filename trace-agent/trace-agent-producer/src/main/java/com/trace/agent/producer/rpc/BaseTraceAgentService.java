package com.trace.agent.producer.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "trace-agent-consumer")
public interface BaseTraceAgentService {

    @GetMapping("/hi")
    String hi();
}
