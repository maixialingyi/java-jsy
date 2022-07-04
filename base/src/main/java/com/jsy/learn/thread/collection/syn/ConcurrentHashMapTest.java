package com.jsy.learn.thread.collection.syn;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/18 9:15
 */
public class ConcurrentHashMapTest {

    /**
     * 并发时 hashMap put操作会引起死循环,因为多线程会导致HashMap的Entry链表形成环形数据结构
     * Entry的next节点永远不为空,就会产生死循环获取Entry
     */
    public static void testHashMapDeadLoop() throws InterruptedException {
        final HashMap<String,String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <1000 ; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           map.put(UUID.randomUUID().toString(),"");
                        }
                    }).start();
                }
            }
        });
        t.start();
        t.join();
        ConcurrentHashMap map1=new ConcurrentHashMap();
    }
    public static void main(String args[]) throws InterruptedException {
        ConcurrentHashMapTest.testHashMapDeadLoop();
    }
}
