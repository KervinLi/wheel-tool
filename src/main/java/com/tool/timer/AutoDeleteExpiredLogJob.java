package com.tool.timer;

import com.tool.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 基于cron表达式的周期性任务调度
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
@Component
@EnableScheduling
@ConditionalOnProperty(prefix = "timerTask", name = "deleteExpiredLogJob", havingValue = "true",matchIfMissing = false)
public class AutoDeleteExpiredLogJob {
    /**
     *
     *  任务周期3S 模拟过期日志删除作业执行时长 1S
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void autoDeleteExpiredLog() throws InterruptedException {
        log.info("->TID:{}过期日志删除任务开始{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
        Thread.sleep(1000);
        log.info("<-TID:{}过期日志删除任务结束{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
    }
}
