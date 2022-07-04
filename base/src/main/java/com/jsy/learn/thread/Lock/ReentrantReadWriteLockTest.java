package com.jsy.learn.thread.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/17 15:14
 */
public class ReentrantReadWriteLockTest{
    static Map<String,Object> map = new HashMap<String,Object>();
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock r =readWriteLock.readLock();
    static Lock w =readWriteLock.writeLock();

    public static final Object get(String key){
        r.lock();
        try{
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    public static final Object put(String key,Object value){
        w.lock();
        try {
            return map.put(key,value);
        }finally {
            w.unlock();
        }
    }

    public static final void clear(){
        w.lock();
        try{
            map.clear();
        }finally {
            w.unlock();
        }
    }

    //锁降级
    public static void lockDowngrade(){
        try {
            w.lock();
            try {
                //数据准备流程
                r.lock();
            } finally {
                w.unlock();   //锁降级完成
            }
            //使用数据流程
        }finally {
            r.unlock();
        }


    }

}
