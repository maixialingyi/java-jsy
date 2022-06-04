package com.jsy.learn.testBean.testXml;

public class Person {

	private String name;
	private Food food;

	public Person(String name, Food food) {
		this.name = name;
		this.food = food;
	}

	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
