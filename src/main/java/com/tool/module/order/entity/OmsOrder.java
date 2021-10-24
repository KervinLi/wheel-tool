package com.tool.module.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tool.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description: 订单类
 * @Author KerVinLi
 * @since 2021/10/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_test_order")
public class OmsOrder extends BaseEntity {
    /**
     * 订单编号
     */
    @TableField("order_serial_number")
    private String orderSerialNumber;

    /**
     * 订单数量
     */
    @TableField("order_amount")
    private Integer orderAmount;

    /**
     * 订单价格
     */
    @TableField("order_price")
    private BigDecimal orderPrice;
}
