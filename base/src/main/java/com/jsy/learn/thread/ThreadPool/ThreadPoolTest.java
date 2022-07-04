package com.jsy.learn.thread.ThreadPool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/22 16:50
 * 核心线程池估算    待解决
 */
public class ThreadPoolTest {

    //线程池
    static class MyThreadPoolExecutor {
        //最大可用的CPU核数
        public static final int PROCESSORS=Runtime.getRuntime().availableProcessors();
        //线程最大的空闲存活时间，单位为秒
        public static final int KEEPALIVETIME=60;
        //任务缓存队列长度
        public static final int BLOCKINGQUEUE_LENGTH=500;

        /**
         * 理论：核心线程数 = cpu数*2 最优
         * 测试：通过设置线程池核心线程数来查看执行时间
         * @return
         */
        public ThreadPoolExecutor createThreadPool(){
            return new ThreadPoolExecutor(PROCESSORS * 2,PROCESSORS * 4,KEEPALIVETIME, SECONDS,
                    new ArrayBlockingQueue<Runnable>(BLOCKINGQUEUE_LENGTH));
        }
    }

    static class MyTask implements Runnable{
        private int i;

        public MyTask(int i){
            this.i=i;
        }

        @Override
        public void run() {
            for (int i=0;i<32766;i++){
                Random random=new Random();
                int randNum=random.nextInt();
                int[] a={1,2,3,4,5,6,9,18,290,238,991,100,19,1932,randNum};
                Arrays.sort(a);
                Arrays.hashCode(a);
                Arrays.stream(a);
            }
        }
    }

    public synchronized static void main(String[] args) {

        //ThreadPoolExecutor threadPoolExecutor=new MyThreadPoolExecutor().createThreadPool();
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(1,2 , 10000,SECONDS,new ArrayBlockingQueue<Runnable>(100));
        for(int j=0;j<8;j++){
            long start = System.currentTimeMillis();
            for (int i = 0; i <= 100; i++) {
                MyTask myTask = new MyTask(i);
                threadPoolExecutor.execute(myTask);
            }
            long end = System.currentTimeMillis();
            System.out.println(end-start +"-------------");
            threadPoolExecutor.shutdown();
        }

    }
}
