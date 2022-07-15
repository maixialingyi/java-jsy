package com.trace.agent.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication
@ComponentScans(value = {@ComponentScan("com.trace.agent.myagent.interceptor")})
public class TraceAgentApplicationConsumer {

    public static void main(String[] args) {
        SpringApplication.run(TraceAgentApplicationConsumer.class, args);
    }
}
