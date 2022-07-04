package com.jsy.learn.thread.ThreadPool;

import java.util.concurrent.Executors;

public class FixedThreadPoolTest {

    public static void main(String[] args) {
        Executors.newFixedThreadPool(4);
    }
}
