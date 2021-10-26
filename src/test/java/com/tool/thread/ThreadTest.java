package com.tool.thread;

import com.tool.thread.forkjoin.MyForkJoinTask;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

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
}
