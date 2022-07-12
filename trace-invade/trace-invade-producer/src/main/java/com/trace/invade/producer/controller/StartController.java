package com.trace.invade.producer.controller;

import com.trace.invade.producer.rpc.ConsumerRpcService;
import com.trace.invade.producer.thread.MdcThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;


@RestController
public class StartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartController.class);

    private static MdcThreadPoolExecutor mdcThreadPoolExecutor = MdcThreadPoolExecutor.newWithInheritedMdc(10, 200, 20, TimeUnit.SECONDS, new LinkedTransferQueue());

    @Autowired
    private ConsumerRpcService consumerRpcService;
//    @Autowired
//    private CustomThreadPoolTaskExecutor customThreadPoolTaskExecutor;

    @GetMapping("/start")
    public String start() {
        LOGGER.info("开始");

//        customThreadPoolTaskExecutor.execute(() -> test1());

        mdcThreadPoolExecutor.execute(() -> test1());

        return consumerRpcService.hi();
    }

    public void test1() {
        LOGGER.info("测试结果：hi1");
        test2();
    }

    public void test2() {
        LOGGER.info("测试结果：hi2");
        mdcThreadPoolExecutor.execute(() -> test3());
    }

    public void test3() {
        LOGGER.info("测试结果：hi3");
    }
}