package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.BuyerService;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOne(String openid, String orderId) {

        return checkOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancel(String openid, String orderId) {
        OrderDTO orderDTO = checkOwner(openid, orderId);
        if(orderDTO==null){
           log.error("[订单错误] 查不到订单,orderId={}",orderId);
           throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    /**
     * 核查订单和openid
     * @param openid
     * @param orderId
     * @return
     */
    private OrderDTO checkOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        //不考虑大小写，字符长度相等，且对应位置的字符也相等就是相等的
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("[订单错误] 订单的openid不一致,openid={},orderID={}", openid, orderId);
            throw new SellException(ResultEnum.OPENID_NOT_EXIST);
        }
        return orderDTO;
    }
}
