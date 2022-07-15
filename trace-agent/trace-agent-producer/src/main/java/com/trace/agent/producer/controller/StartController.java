package com.trace.agent.producer.controller;

import com.sxf.base.trace.agent.producer.rpc.BaseTraceAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StartController {

    @Autowired
    private BaseTraceAgentService baseTraceAgentService;

    @GetMapping("/start")
    public String start() {
        log.info("开始");
        return baseTraceAgentService.hi();
    }
}