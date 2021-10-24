package com.tool.module.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.module.order.entity.OmsOrder;
import com.tool.module.order.mapper.OmsOrderMapper;
import com.tool.module.order.service.OmsOrderService;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author KerVinLi
 * @Version 1.0
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Override
    public void updateOrderById(OmsOrder order) {

    }
}
