package com.tool.module.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.module.order.entity.OmsOrder;
import org.apache.ibatis.annotations.Param;

public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    /**
     * 更新订单表
     * @param order
     */
    void updateOrderById(@Param("order") OmsOrder order);
}
