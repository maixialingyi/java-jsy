package com.jsy.learn.testBean;

import com.jsy.learn.testBean.testXml.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //自定义FacotryBean
        Person person = (Person)ctx.getBean("myFacotryBean");
        //实现Aware接口 的bean
        My2Aware my2Aware = (My2Aware) ctx.getBean("my2Aware");

        System.out.println(my2Aware);
    }
}
