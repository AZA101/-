package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private  OrderDetailRepository repository;
    @Test
    public void findByOrderId(){
      List<OrderDetail> list=repository.findByOrderId("123");
      Assert.assertNotEquals(0,list.size());
    }
    @Test
    public void save(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("ddmx123");
        orderDetail.setOrderId("123");
        orderDetail.setProductId("789");
        orderDetail.setProductName("阿迪");
        orderDetail.setProductPrice(new BigDecimal(202.6));
        orderDetail.setProductQuantity(100);
        orderDetail.setProductIcon("http://xxx.jpg");
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);

    }
}