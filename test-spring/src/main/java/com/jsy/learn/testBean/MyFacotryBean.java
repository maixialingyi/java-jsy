package com.jsy.learn.testBean;

import com.jsy.learn.testBean.testXml.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * 自定义创建,不需要走spring生命周期
 */
public class MyFacotryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person("FactoryBean",null);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
