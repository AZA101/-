package com.imooc.sell.Service;

import com.imooc.sell.dto.OrderDTO;

/**
 * 买家接口
 */
public interface BuyerService {
    //查询一个订单
    public OrderDTO findOne(String openid, String orderId);

    //取消订单
    public OrderDTO cancel(String openid, String orderId);
}
