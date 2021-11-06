package com.tool.rocketmq.config;

import lombok.Data;

/**
 * @Description: TODO
 * @Author KerVinLi
 * @Version 1.0
 */
@Data
public class MessageVo {
    /**
     * 序列号
     */
    private Integer serialNumber;
    /**
     * 消息Key
     */
    private String msgKey;
    /**
     * 消息内容
     */
    private String msgContent;
}
