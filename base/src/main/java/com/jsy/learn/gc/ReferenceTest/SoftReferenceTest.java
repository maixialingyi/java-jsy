package com.jsy.learn.gc.ReferenceTest;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

//-verbose:gc -Xms8M -Xmx8M -Xmn2M -XX:-UseAdaptiveSizePolicy -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
public class SoftReferenceTest {

    public static void main(String[] args) throws InterruptedException {

        Map<String, SoftReference> map=new HashMap();

        SoftReference reference1=new SoftReference(new byte[5*1024*1024]);
        map.put("1",reference1);
        Thread.sleep(5000);
        System.out.println("--------第一次GC后---------"+map.get("1").get());

        SoftReference reference2=new SoftReference(new byte[5*1024*1024]);
        map.put("2",reference2);
        Thread.sleep(5000);
        System.out.println("--------第二次GC后---------"+map.get("1").get()+"--"+map.get("2").get());

        SoftReference reference3=new SoftReference(new byte[5*1024*1024]);
        map.put("3",reference3);
        Thread.sleep(5000);
        System.out.println("--------第三次GC后---------"+map.get("1").get()+"--"+map.get("2").get()+"--"+map.get("3").get());

    }
}

