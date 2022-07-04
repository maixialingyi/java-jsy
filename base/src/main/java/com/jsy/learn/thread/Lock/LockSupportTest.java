package com.jsy.learn.thread.Lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author jiangshaoyue
 * @Date 2019/5/27 11:29
 */
public class LockSupportTest {

    public static void main(String args[]) {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println( Thread.currentThread().isInterrupted() );
                    LockSupport.park();
                    System.out.println( Thread.currentThread().isInterrupted() );
                }catch (Exception e){
                    System.out.println("线程被中断-->"+e.getClass());
                }
            }
        });
        threadOne.start();        //启动子线程

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //threadOne.interrupt();             //被中断  输出 false  true
        LockSupport.unpark(threadOne);       //输出         false  false

        /**
         * 结论: LockSupport.park();  中断interrupt() 或 unpark() 效果一样,不同是unpark()不修改中断状态
         */
    }
}

