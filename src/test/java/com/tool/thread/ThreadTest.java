package com.tool.thread;

import com.tool.thread.forkjoin.MyForkJoinTask;
import com.tool.thread.pool.MyThreadPool;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.*;

/**
 * @Description: 线程相关测试
 * @Author KerVinLi
 * @since 2021/10/26
 */
public class ThreadTest {
    @Test
    void testForkJoin(){
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = pool.submit(new MyForkJoinTask(1, 100));
        try {
            Integer res = submit.get();
            System.out.println("计算结果："+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMyThreadPool(){
        MyThreadPool myThreadPool = new MyThreadPool(3, 3, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        List<MyTask> taskList = new ArrayList<>();
        for(int i=0;i<30;i++){
            taskList.add(new MyTask());
        }

        try {
            List<Future<Long>> futures = myThreadPool.invokeAll(taskList);
            StringJoiner joiner = new StringJoiner("-","[","]");
            for (Future<Long> f:futures){
                joiner.add(String.valueOf(f.get()));
            }
            System.out.println("任务执行时间："+ joiner.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            myThreadPool.shutdown();
        }
    }
}
//模拟任务 实现Callable接口
class MyTask implements Callable<Long> {

    @Override
    public Long call() throws Exception {
        long time= Math.round(Math.random() * 1000);
        Thread.sleep(time);
        return time;
    }
}
