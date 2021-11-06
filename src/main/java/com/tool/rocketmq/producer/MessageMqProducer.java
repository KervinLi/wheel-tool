package com.tool.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 消息生产者
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
@Configuration
public class MessageMqProducer {
    //生产者组
    @Value("${rocketmqinfo.producerGroup.common}")
    private String producerGroup;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Bean
    //@ConditionalOnMissingBean
    public DefaultMQProducer messageProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServer);
        producer.setInstanceName("message_sys_producer");
        //producer.setMaxMessageSize(maxMessageSize);
        //producer.setSendMsgTimeout(sendMsgTimeout);
        //producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(5);
        try {
            producer.start();
            log.info("================>消息生产者创建完成，ProducerGroupName: {}<================", producerGroup);
        } catch (MQClientException e) {
            log.error("failed to start producer.", e);
            throw new RuntimeException(e);
        }

        return producer;
    }
}
