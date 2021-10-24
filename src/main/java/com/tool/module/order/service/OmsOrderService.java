package com.tool.module.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.module.order.entity.OmsOrder;

public interface OmsOrderService extends IService<OmsOrder> {
    /**
     * 更新订单表
     * @param order
     */
    void updateOrderById(OmsOrder order);
}
