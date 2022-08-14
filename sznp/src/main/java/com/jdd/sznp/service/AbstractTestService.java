package com.jdd.sznp.service;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractTestService implements ITestService {

    private Integer groupId;

    public AbstractTestService(Integer groupId) {
        this.groupId = groupId;
    }

    public String testMethod1(){
        return "share";
    }

    public Integer getGroupId() {
        return groupId;
    }

}
