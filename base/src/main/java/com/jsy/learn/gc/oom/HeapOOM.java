package com.jsy.learn.gc.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 * -Xms4M -Xmx4M -Xmn1M -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    public static class OOMObject {
    }

    public static void main(String[] args) throws InterruptedException {
        //重写方法
        Thread thread = new Thread(() -> {
            List<OOMObject> list = new ArrayList<>();
            while (true) {
                list.add(new OOMObject());
            }
        });
        thread.setName("myThreadName-");
        thread.start();   //启动线程，且调用run方法
        thread.join(11111111);
    }

}
