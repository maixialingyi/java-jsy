package com.jsy.learn.io.nettyRpc1.client;

import com.jsy.learn.io.nettyRpc1.proxymode.rpcprotocol.RpcProxyUtil;
import com.jsy.learn.io.nettyRpc1.rpcenv.serverenv.ServerRPCEnv;
import com.jsy.learn.io.nettyRpc1.service.IUserApi;
import org.junit.Test;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/*
    1，先假设一个需求，写一个RPC
    2，来回通信，连接数量，拆包？
    3，动态代理呀，序列化，协议封装
    4，连接池
    5，就像调用本地方法一样去调用远程的方法，面向java中就是所谓的 面向interface开发
 */

public class MyRPCTest {

    @Test
    public void get() {
        final AtomicInteger num = new AtomicInteger(0);

        //启动服务器
        new Thread(() -> {
            ServerRPCEnv.startServer();
        }).start();

        //模拟并发请求
        Thread[] threads = new Thread[50];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                //面向接口编程,动态代理生成rpc调用代理类,一般集成spring
                IUserApi iUserApi = RpcProxyUtil.proxyGet(IUserApi.class);

                String userId = "userId" + num.incrementAndGet();
                String result = iUserApi.queryUserInfo(userId);

                System.out.println(result + " from ..." + userId);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


