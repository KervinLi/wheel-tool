package com.tool.timer;

import com.alibaba.fastjson.JSON;
import com.tool.rocketmq.config.MessageVo;
import com.tool.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmqinfo.consumerTopic}")
    private String consumerTopic;
    //触发tag
    @Value("${rocketmqinfo.consumerTag}")
    private String consumerTag;

    @Autowired
    @Qualifier("messageProducer")
    private DefaultMQProducer messageProducer;

    //计数器
    private AtomicInteger count = new AtomicInteger(1);
/**
 * 模拟5S执行一次消息扫描发送任务 启动延迟10秒钟执行
 * 上一次开始执行时间点之后多长时间再执行
 */
    @Scheduled(fixedRate = 5000L,initialDelay = 10000L)
    public void autoSendMessage() throws Exception {
        log.info("->TID:{}发送消息任务开始{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
        for(int i=0;i<20;i++){
            String msgKey = UUID.randomUUID().toString();
            int andIncrement = count.getAndIncrement();
            MessageVo vo = new MessageVo();
            vo.setMsgKey(msgKey);
            vo.setSerialNumber(andIncrement);
            vo.setMsgContent("第"+andIncrement+"个消息");
            String msgVo = JSON.toJSONString(vo);
            Message message = new Message(consumerTopic,
                    consumerTag, msgKey,
                    msgVo.getBytes(RemotingHelper.DEFAULT_CHARSET));

            try {
                SendResult sendResult = messageProducer.send(message);
                log.info("生成者推送成功: {}", sendResult);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            }
        }
        log.info("<-TID:{}发送消息任务结束{}",Thread.currentThread().getId(), DateUtils.currentLongDateTime());
    }
}
