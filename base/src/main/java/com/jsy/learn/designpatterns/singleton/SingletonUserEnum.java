package com.jsy.learn.designpatterns.singleton;

/**
 * 枚举实例化,会为每个属性生成一个对象,且禁止反射
 */
public enum SingletonUserEnum {

    INSTANCE;
    private User user;
    void SingletonEnum(){
        user = new User();
    }

}
