package com.tool.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 自定义线程池
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
public class MyThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    //记录任务数
    private final AtomicLong tasksNum = new AtomicLong();
    //记录任务总耗时
    private final AtomicLong totalTime = new AtomicLong();

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info(String.format("BeforeExecute: ThreadID : %s",Thread.currentThread().getId()));
        startTime.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long taskTime = System.currentTimeMillis() - startTime.get();
            //任务计数
            tasksNum.incrementAndGet();
            //任务总计耗时
            totalTime.addAndGet(taskTime);
            //[Running,             --线程运行状态
            // pool size = 3,       --线程池中工作线程数
            // active threads = 3,  --线程池中活跃线程数(线程运行中非阻塞状态)
            // queued tasks = 4,    --线程池待处理的任务数
            // completed tasks = 23]--已完成的任务数量
            log.info(String.format("线程池相关信息：%s",this.toString()));
            log.info(String.format("AfterExecute: ThreadID : %s, taskRunTime=%d(ms)",Thread.currentThread().getId(),taskTime));
        } finally {
            super.afterExecute(r,t);
        }
    }

    @Override
    protected void terminated() {
        try {
            log.info(String.format("Terminated: avgTaskRuntime=%d(ms)",totalTime.get() / tasksNum.get()));
        } finally {
            super.terminated();
        }
    }

}
