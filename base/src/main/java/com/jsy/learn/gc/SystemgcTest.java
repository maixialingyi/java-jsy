package com.jsy.learn.gc;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

//-verbose:gc -Xms2048M -Xmx2048M -Xmn512M -XX:-UseAdaptiveSizePolicy -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+ExplicitGCInvokesConcurrent -XX:+PrintGCDetails -XX:+PrintGCDateStamps
public class SystemgcTest {

    //-XX:+ExplicitGCInvokesConcurrent System.gc()会选择cms background模式
    public static void main(String[] args) throws InterruptedException {
        Queue objCache =  new ConcurrentLinkedDeque<>();
        for(;;){
            objCache.add(new byte[1]);
            System.gc();
        }
    }
}
