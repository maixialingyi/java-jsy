package com.jsy.learn.guava;

import com.google.common.util.concurrent.RateLimiter;

public class GuavaRateLimiter {

    public static void main(String[] args) {
        //每秒产生 1 个令牌
        RateLimiter rateLimiter = RateLimiter.create(1);

        while (true) {
            //从令牌桶内获取令牌，获取成功则返回，获取不到则一直阻塞直至获取
            System.out.println("get 1 tokens: " + rateLimiter.acquire() + "s");
        }
    }
}
