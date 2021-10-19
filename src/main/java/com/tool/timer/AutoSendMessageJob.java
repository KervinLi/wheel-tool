package com.tool.timer;

import com.tool.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 基于fixedRate消息定时发送任务(上一次开始执行时间点之后多长时间再执行)
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
@Component
@EnableScheduling
@ConditionalOnProperty(prefix = "timerTask", name = "sendMessageJob", havingValue = "true",matchIfMissing = false)
public class AutoSendMessageJob {
/**
 * 模拟5S执行一次消息扫描发送任务 启动延迟10秒钟执行
 * 上一次开始执行时间点之后多长时间再执行
 */
    @Scheduled(fixedRate = 5000L,initialDelay = 10000L)
    public void autoSendMessage() throws InterruptedException {
        log.info("->TID:{}发送消息任务开始{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
        Thread.sleep(3000);
        log.info("<-TID:{}发送消息任务结束{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
    }
}
