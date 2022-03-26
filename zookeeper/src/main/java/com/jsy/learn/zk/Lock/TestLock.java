package com.jsy.learn.zk.Lock;

import org.junit.Test;

public class TestLock {
    @Test
    public void lock() throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                ZkLock zkLock = new ZkLock();
                // 加锁
                zkLock.lock("/appIdpayLock");
                // 业务逻辑
                System.out.println(Thread.currentThread().getName() + "执行业务逻辑");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 释放锁
                zkLock.unLock();
            }).start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

}
