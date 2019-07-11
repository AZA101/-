package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.OrderService;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderMasterEnum;
import com.imooc.sell.enums.PayStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    private final String BUYER_OPENID = "wx004";
    private final String ORDERID = "1562219248291625106";

    @Test
    public void create() {
        /*订单表的开头内容*/
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("cs新数据");
        orderDTO.setBuyerAddress("武汉");
        orderDTO.setBuyerPhone("15025616485");
        orderDTO.setBuyerOpenid("wx004");

        //订单内容的详细
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("456789");
        orderDetail.setProductQuantity(1);
        //再下一个订单
        /*OrderDetail ol = new OrderDetail();
        ol.setProductId("123456");
        ol.setProductQuantity(2);*/

        orderDetailList.add(orderDetail);
      //  orderDetailList.add(ol);
        orderDTO.setOrderDetails(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("创建新的订单 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {

        OrderDTO orderDTO = orderService.findOne(ORDERID);
        log.info("查询一条订单记录 orderDTO={}", orderDTO);
        Assert.assertEquals(ORDERID, orderDTO.getOrderId());

    }

    @Test
    public void findlist() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findlist(BUYER_OPENID, request);
        System.out.println("显示方法输出内容:" + orderDTOPage.getTotalElements());
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderMasterEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        String pid="456";
        OrderDTO orderDTO=orderService.findOne(pid);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderMasterEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        String pid="123";
        OrderDTO orderDTO=orderService.findOne(pid);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnums.SUCCESS.getCode(),result.getPayStatus());
    }
}