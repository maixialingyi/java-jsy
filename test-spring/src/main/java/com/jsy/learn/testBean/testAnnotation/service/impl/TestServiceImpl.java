package com.jsy.learn.testBean.testAnnotation.service.impl;

import com.jsy.learn.testBean.testAnnotation.dao.ITestDao;
import com.jsy.learn.testBean.testAnnotation.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private ITestDao testDao;

    @Override
    public void test() {
        testDao.test();
    }
}
