package com.trace.agent.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScans(value = {@ComponentScan("com.trace.agent.myagent.interceptor")})
public class TraceAgentApplicationProducer {

    public static void main(String[] args) {
        SpringApplication.run(TraceAgentApplicationProducer.class, args);
    }
}
