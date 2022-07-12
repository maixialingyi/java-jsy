package com.trace.invade.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiController.class);

    @GetMapping("/hi")
    public String hi() {

        LOGGER.info("hi");
        return "hi";
    }
}