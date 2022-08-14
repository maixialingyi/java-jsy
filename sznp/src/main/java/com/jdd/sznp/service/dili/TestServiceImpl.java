package com.jdd.sznp.service.dili;

import com.jdd.sznp.service.AbstractTestService;
import org.springframework.stereotype.Service;

@Service("403ITestService")
public class TestServiceImpl extends AbstractTestService {

    public TestServiceImpl() {
        super(403);
    }
}
