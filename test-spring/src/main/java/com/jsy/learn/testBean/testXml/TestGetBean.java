package com.jsy.learn.testBean.testXml;

import com.jsy.learn.testBean.My2Aware;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestGetBean {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		//获取容器对象
		Person person = (Person)ctx.getBean("person");

		//自定义FacotryBean
		Person personFacotryBean = (Person)ctx.getBean("myFacotryBean");

		//实现Aware接口 的bean
		My2Aware my2Aware = (My2Aware) ctx.getBean("my2Aware");

		System.out.println(my2Aware);
		System.out.println(ToStringBuilder.reflectionToString(person,ToStringStyle.MULTI_LINE_STYLE));;
		System.out.println(ToStringBuilder.reflectionToString(ctx,ToStringStyle.MULTI_LINE_STYLE));;
	}
}
