package com.jsy.learn.io.nioreactor.reactor_v2;

import java.util.concurrent.atomic.AtomicInteger;

public class EventLoopGroup {

    AtomicInteger cid = new AtomicInteger(0);
    EventLoop[] eventLoops = null;

    EventLoopGroup(int nThreads) {
        eventLoops = new EventLoop[nThreads];
        for (int i = 0; i < nThreads; i++) {
            eventLoops[i] = new EventLoop("T" + i);
        }
    }

    public EventLoop chosser() {
        return eventLoops[cid.getAndIncrement() % eventLoops.length];
    }

}
