package com.jsy.learn.testBean.testAnnotation;

import com.jsy.learn.testBean.testAnnotation.controller.TestController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		//获取容器对象
		TestController testController = (TestController)ctx.getBean("testController");

		testController.test();
	}
}
