package com.tool.thread.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * @Description: fork join 简单案例(计算开始值到结束值得和)
 * @Author KerVinLi
 * @since 2021/10/26
 */
@Slf4j
public class MyForkJoinTask extends RecursiveTask<Integer> {
    static final int MAX = 10;
    //子任务开始计算值
    private Integer startValue;
    //子任务结束计算值
    private Integer endValue;

    public MyForkJoinTask(Integer startValue, Integer endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }
    @Override
    protected Integer compute() {
        if(endValue - startValue < MAX){
            log.info("开始计算|开始值：{}，结束值：{}",startValue,endValue);
            Integer total = 0;
            for(int index = startValue;index<=endValue;index++){
                total +=index;
            }
            return total;
        }else{
            MyForkJoinTask subTaskOne = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
            subTaskOne.fork();
            MyForkJoinTask subTaskTwo = new MyForkJoinTask((startValue + endValue) / 2 + 1 , endValue);
            subTaskTwo.fork();
            return subTaskOne.join() + subTaskTwo.join();
        }
    }
}
