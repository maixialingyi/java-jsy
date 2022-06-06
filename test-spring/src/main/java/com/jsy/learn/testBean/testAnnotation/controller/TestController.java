package com.jsy.learn.testBean.testAnnotation.controller;

import com.jsy.learn.testBean.testAnnotation.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    private ITestService testService;

    public void test(){
        testService.test();
    }
}
