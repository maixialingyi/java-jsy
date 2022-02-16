package com.jsy.learn.io.myRactor;

/**
 *
 */
public class MainThread {
    public static void main(String[] args) {
        /**
         * boss selector线程组监听OP_ACCEPT
         * num : 一个selector可以绑定多个端口,一般 selector : port = 1:1 , 想绑几个端口参数传几
         */
        SelectorThreadGroup bossGroup = new SelectorThreadGroup(3);
        //worker 监听OP_READ
        SelectorThreadGroup workerGroup = new SelectorThreadGroup(3);
        //boss中持有worker引用,注册读/写监听时方便获取worker,进行selector分配
        bossGroup.setWorker(workerGroup);

        bossGroup.bind(9999);
        bossGroup.bind(8888);
        bossGroup.bind(6666);
        //bossGroup.bind(7777);
    }
}
