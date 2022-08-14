package com.jdd.sznp.controller;

import com.jdd.sznp.config.aspect.AppContext;
import com.jdd.sznp.service.AbstractTestService;
import com.jdd.sznp.service.ITestService;
import com.jdd.sznp.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private Map<String,ITestService> map;

    @GetMapping(value = "/test")
    public void test(){
        String str = map.get(AppContext.getAppContext().getGroupId() + "ITestService").testMethod1();
        System.out.println(str);
    }


}
