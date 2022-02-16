package com.jsy.learn.io.myRactor;
// 用nc ip port测试   window需下载安装 https://blog.csdn.net/nicolewjt/article/details/88898735
/**
 *  参照netty-reactor模型,写的简单demo
 *  SelectorThread       创建selector,注册事件,select()有效事件并业务处理
 *  SelectorThreadGroup  管理SelectorThreads,进行selector监听类型划分OP_ACCEPT OP_READ OP_WRITE
 *      划分类型一: 一个Group,N个selector,每个selector上都可注册OP_ACCEPT OP_READ
 *      划分类型二: 一个Group,N个selector,监听OP_ACCEPT         称为 bossGroup
 *                一个Group,N个selector,监听OP_READ OP_WRITE  称为 workGroup
 */
public class MainThread {
    public static void main(String[] args) {
        //划分类型一: 创建一个Group,N个selector,每个selector上都可注册OP_ACCEPT OP_READ
        /*SelectorThreadGroup bossGroup = new SelectorThreadGroup(3);
        bossGroup.bind(9999);
        bossGroup.bind(8888);
        bossGroup.bind(6666);*/

        /**
         * 划分类型二: 一个Group,N个selector,监听OP_ACCEPT         称为 bossGroup
         *           一个Group,N个selector,监听OP_READ OP_WRITE  称为 workGroup
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
