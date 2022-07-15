package com.trace.agent.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class HiController {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @GetMapping("/hi")
    public String hi() {
        log.info("hi");
        hi1();
        return "hi";
    }

    public void hi1() {
        log.info("hi1");
        new Thread(() -> hi2()).start();
    }

    public void hi2() {
        log.info("hi2");
        threadPoolExecutor.execute(() -> hi3());
    }

    public void hi3() {
        log.info("hi3");
        threadPoolExecutor.execute(() -> hi4());
    }

    public void hi4() {
        log.info("hi4");
    }
}
