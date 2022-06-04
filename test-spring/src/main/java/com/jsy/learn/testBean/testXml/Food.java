package com.jsy.learn.testBean.testXml;

public class Food {

	private String name;

	public void init(){
		System.out.println("Food # init()");
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
