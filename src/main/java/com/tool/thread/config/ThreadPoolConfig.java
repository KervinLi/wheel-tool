package com.tool.thread.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.*;

/**
 * @Description:线程池统一配置
 * @since 2021/10/21
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 处理器核心数
     */
    private static int processorsCore = Runtime.getRuntime().availableProcessors();
    /**
     * 此线程池用于CPU密集型任务多线程调度
     * CPU密集型：核心线程数=CPU核心数(或 核心线程数=CPU核心数+1)
     * 请直接使用，使用完毕后请勿关闭线程池
     * @return
     */
    @Primary
    @Scope("singleton")
    @Bean("mainThreadPool")
    public ExecutorService mainThreadPool() {
        BlockingDeque<Runnable> working = new LinkedBlockingDeque<>(Integer.MAX_VALUE);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadFactory threadFactory= new CustomizeThreadFactory("main");
        ExecutorService threadPool = new ThreadPoolExecutor(processorsCore + 1,
                processorsCore + 1,
                0L,
                TimeUnit.MICROSECONDS,
                working,
                threadFactory,
                handler);
        return threadPool;
    }

    /**
     * 此线程池用于IO密集型任务多线程调度
     * IO密集型：核心线程数=2*CPU核心数(或 核心线程数=CPU核心数/(1-阻塞系数))
     * 请直接使用，使用完毕后请勿关闭线程池
     * @return
     */
    @Scope("singleton")
    @Bean("deputyThreadPool")
    public ExecutorService deputyThreadPool() {
        BlockingDeque<Runnable> working = new LinkedBlockingDeque<>(Integer.MAX_VALUE);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadFactory threadFactory= new CustomizeThreadFactory("deputy");
        ExecutorService threadPool = new ThreadPoolExecutor(processorsCore << 1,
                processorsCore << 1,
                0L,
                TimeUnit.MICROSECONDS,
                working,
                threadFactory,
                handler);
        return threadPool;
    }

    /**
     * 此线程池用于混合型型任务多线程调度
     * 混合型：核心线程数=(线程等待时间/线程CPU时间+1)*CPU核心数
     * 请直接使用，使用完毕后请勿关闭线程池
     * @return
     */
    @Scope("singleton")
    @Bean("otherThreadPool")
    public ExecutorService otherThreadPool() {
        BlockingDeque<Runnable> working = new LinkedBlockingDeque<>(Integer.MAX_VALUE);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadFactory threadFactory= new CustomizeThreadFactory("other");
        ExecutorService threadPool = new ThreadPoolExecutor(5,
                20,
                0L,
                TimeUnit.MICROSECONDS,
                working,
                threadFactory,
                handler);
        return threadPool;
    }
}
