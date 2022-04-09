package com.jsy.learn.designpatterns.singleton;

/**
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 */
public class InnerSingleton {

    private InnerSingleton(){}

    private static class InnerSingletonHolder{
        private final static InnerSingleton INSTANCE = new InnerSingleton();
    }

    public static InnerSingleton getInstance(){
        return InnerSingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i=0; i<100; i++){
            new Thread(()->{
                System.out.println(InnerSingleton.getInstance().hashCode());
            }).start();
        }
    }
}
