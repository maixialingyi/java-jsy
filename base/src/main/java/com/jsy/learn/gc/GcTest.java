package com.jsy.learn.gc;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *  -Xms4M -Xmx8M                           堆大小
 *  -Xmn1M                                  年轻代大小
 *  -XX:-UseAdaptiveSizePolicy	            关闭survivor区大小动态调整
 *  -XX:+UseParNewGC                        年轻代并行收集器ParNew
 *  -XX:+UseConcMarkSweepGC                 CMS收集器
 *  -XX:CMSWaitDuration=1   				轮询间隔：默认2s
 *  -XX:CMSInitiatingOccupancyFraction  	触发cms background阈值，默认92，建议70-75
 *  -XX:+UseCMSInitiatingOccupancyOnly 	    只是用配置回收阈值，不使用默认
 *  -XX:+ParallelRefProcEnabled 			并行处理Reference，加快处理速度，缩短耗时
 *  -XX:+ExplicitGCInvokesConcurrent
 *  -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses
 *  -XX:+TraceClassLoading
 *  -XX:+TraceClassUnLoading
 *  -XX:+PrintGC                            输出GC日志
 *  -XX:+PrintGCDetails                     输出GC的详细日志
 *  -XX:+PrintGCTimeStamps                  输出GC的时间戳（以基准时间的形式）
 *  -XX:+PrintGCDateStamps                  输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800）
 *  -XX:+PrintTenuringDistribution          输出显示在survivor空间里面有效对象不同年龄的分布情况
 *  -XX:+PrintGCApplicationStoppedTime      打印垃圾回收期间程序暂停的时间.可与上面混合使用
 *  -XX:+PrintGCApplicationConcurrentTime   打印每次垃圾回收前,程序未中断的执行时间.可与上面混合使用
 *  -XX:+PrintHeapAtGC                      在进行GC的前后打印出堆的信息
 *  -Xloggc:/Users/jiangshaoyue/Desktop/gclog.log 日志文件的输出路径
 */
public class GcTest {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 年轻代回收，不会晋升
         * -verbose:gc -Xms8M -Xmx8M -Xmn2M -XX:-UseAdaptiveSizePolicy -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
         *
         * 2021-06-18T16:25:53.731-0800: [GC (Allocation Failure) 2021-06-18T16:25:53.731-0800: GC原因，分配对象失败
         * [ParNew: 1664K->4K(1856K), 0.0003072 secs]  回收前年轻代大小  回收后年轻代大小 年轻代总大小  耗时
         * 2565K->905K(8000K), 0.0003251 secs]         回收前堆大小     回收后堆大小     堆总大小     耗时
         * [Times: user=0.00 sys=0.01, real=0.00 secs] 用户态耗时，内核态耗时，总耗时
         */
        /*for( ;; ){
            byte[] b = new byte[517];
        }*/

        /**
         * 年轻代晋升失败
         * -verbose:gc -Xms8M -Xmx8M -Xmn2M -XX:-UseAdaptiveSizePolicy -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
         *
         */
        Queue objCache =  new ConcurrentLinkedDeque<>();
        for(;;){
            objCache.add(new byte[1]);
        }


    }
}
