package com.tool.timer;

import com.tool.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 基于fixedDelay的数据对接定时任务(上一次任务调度完毕时间点之后多长时间再执行)
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
@Component
@EnableScheduling
@ConditionalOnProperty(prefix = "timerTask", name = "dataDockingJob", havingValue = "true",matchIfMissing = false)
public class AutoDockingDataJob {
    /**
     * 模拟数据对接3S执行一次 模拟作业执行时长5S
     * 上一次数据对接执行完毕时间点之后多长时间再执行
     */
    @Scheduled(fixedDelay = 3000L)
    public void autoDataDocking() throws InterruptedException {
        log.info("->TID:{}数据对接任务开始{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
        Thread.sleep(5000);
        log.info("<-TID:{}数据对接任务结束{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
    }
}
