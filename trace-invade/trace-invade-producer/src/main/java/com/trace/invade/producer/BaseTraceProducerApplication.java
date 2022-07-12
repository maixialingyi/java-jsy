package com.trace.invade.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.trace.invade.producer.rpc"})
@SpringBootApplication
public class BaseTraceProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseTraceProducerApplication.class, args);
    }

}
