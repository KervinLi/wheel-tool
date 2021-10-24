package com.tool.module.order.controller;

import com.tool.module.order.entity.OmsOrder;
import com.tool.module.order.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @Description: 订单控制层
 * @Author KerVinLi
 * @since 2021/10/24
 */
@Controller
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService omsOrderService;


    @RequestMapping("/insert")
    @ResponseBody
    public String insertOrder(){
        OmsOrder order = new OmsOrder();
        order.setOrderSerialNumber("T202108010001");
        order.setOrderPrice(new BigDecimal("19.99"));
        order.setOrderAmount(100);
        omsOrderService.save(order);
        return "insert success!";
    }


    @RequestMapping("/update")
    @ResponseBody
    public String updateOrder(@Param("id") String id, @Param("type") String type){
        OmsOrder order = new OmsOrder();
        order.setId(Long.parseLong(id));
        order.setOrderSerialNumber("T202108010099");
        order.setOrderPrice(new BigDecimal("29.99"));
        order.setOrderAmount(300);
        if("".equals(type)){
            omsOrderService.updateById(order);
        }else{
            omsOrderService.updateOrderById(order);
        }
        return "update success!";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteOrder(@Param("id") String id){
        omsOrderService.removeById(id);
        return "delete success!";
    }
}
