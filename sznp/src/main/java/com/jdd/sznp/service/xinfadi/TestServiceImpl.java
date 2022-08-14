package com.jdd.sznp.service.xinfadi;

import com.jdd.sznp.service.AbstractTestService;
import org.springframework.stereotype.Service;

@Service("409ITestService")
public class TestServiceImpl extends AbstractTestService {

    public TestServiceImpl() {
        super(409);
    }

    public String testMethod1(){
        return "xinfadi";
    }
}
