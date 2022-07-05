package com.jsy.learn.exception.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * gaohaiyang
 */
@Slf4j
public class ThreadPoolUtil {

    private static ThreadPoolExecutor creditpayCustomerAccountWriteCacheExecutor = new ThreadPoolExecutor(100, 200,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 支付失败可用判断
     */
    private static ThreadPoolExecutor lecheckPayFailAvailableExecutor = new ThreadPoolExecutor(30, 100,
            60L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    /**
     * 支付可用性发领域消息
     */
    private static ThreadPoolExecutor lecheckAvailableNotifyExecutor = new ThreadPoolExecutor(30, 100,
            60L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static class ValidateThreadFactory implements ThreadFactory {

        private static AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("AvailableValidateThtread-" + counter.getAndIncrement());
            return thread;
        }
    }

    /**
     * 账户可用查询线程池
     */
    private static ThreadPoolExecutor lecheckAccountAvailableExecutor = new ThreadPoolExecutor(20, 100,
            60L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 标准收银台用户策略专用
     */
    private static ThreadPoolExecutor lecheckUserStrategyExecutor = new ThreadPoolExecutor(100, 150,
            60, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 查询用户账户状态专用
     */
    private static ThreadPoolExecutor LecheckCheckAccountExecutor = new ThreadPoolExecutor(20, 100,
            60, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    //public static final ListeningExecutorService LECHECK_AVAILABLE_EXECUTOR_SERVICE;

    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                200,// 接口核心线程数为200,当前需要5个校验并发
                300,// 接口最大300个线程,当前最大限制200个线程,拒绝策略使用CallerRunsPolicy,再大就串行
                1,// 空闲1分钟回收多余线程
                TimeUnit.MINUTES,
                new SynchronousQueue<>(),// 内部逻辑不需要缓冲队列
                new ValidateThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        log.info("{}创建执行器服务", ThreadPoolUtil.class.getSimpleName());
        // 预启动
        executor.prestartAllCoreThreads();
        log.info("{}核心线程启动完毕", ThreadPoolUtil.class.getSimpleName());
        //ListeningExecutorService executorService = MoreExecutors.listeningDecorator(new ExecutorServiceTraceWrapper(executor));

        /*Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("{}关闭执行器服务", ThreadPoolUtil.class.getSimpleName());
                executorService.shutdown();
            }
        });
        // guava接口适配
        LECHECK_AVAILABLE_EXECUTOR_SERVICE = executorService;*/

    }

    public static ListeningExecutorService getCreditpayCustomerAccountWriteCacheExecutor() {
        return MoreExecutors.listeningDecorator(creditpayCustomerAccountWriteCacheExecutor);
    }

    public static ListeningExecutorService getLecheckAvailableNotifyExecutor() {
        return MoreExecutors.listeningDecorator(lecheckAvailableNotifyExecutor);
    }

    public static ListeningExecutorService getLecheckPayFailAvailableExecutor() {
        return MoreExecutors.listeningDecorator(lecheckPayFailAvailableExecutor);
    }

    public static ListeningExecutorService getLecheckAccountAvailableExecutor() {
        return MoreExecutors.listeningDecorator(lecheckAccountAvailableExecutor);
    }

    public static ListeningExecutorService getLecheckUserStrategyExecutor() {
        return MoreExecutors.listeningDecorator(lecheckUserStrategyExecutor);
    }


    public static ListeningExecutorService getLecheckCheckAccountExecutor() {
        return MoreExecutors.listeningDecorator(LecheckCheckAccountExecutor);
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "biz-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
