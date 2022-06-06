package com.jsy.learn.testBean.testAnnotation.dao.impl;

import com.jsy.learn.testBean.testAnnotation.dao.ITestDao;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements ITestDao {
    @Override
    public void test() {
        System.out.println("调用TestDaoImpl");
    }
}
