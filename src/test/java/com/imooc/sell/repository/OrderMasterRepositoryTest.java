package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderMaster> result=repository.findByBuyerOpenid("wx001",pageRequest);
        System.out.println(result.getTotalElements());
    }

    @Test
    public void save() {
        //时间字段取消，由数据库写。
        OrderMaster orderMaster = new OrderMaster();
        /*定义时间的文本格式到秒*/
       // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        /*orderMaster.setOrderId("123");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("13505264668");
        orderMaster.setBuyerAddress("云南昆明");
        orderMaster.setBuyerOpenid("wx001");
        orderMaster.setOrderAmout(new BigDecimal(25.00));*/
       // System.out.println("显示date的值:"+date+"在显示date.getTime的值:"+date.getTime());
        orderMaster.setOrderId("cs222");
        orderMaster.setBuyerName("测试");
        orderMaster.setBuyerPhone("18105627606");
        orderMaster.setBuyerAddress("云南大学");
        orderMaster.setBuyerOpenid("wx002");
        orderMaster.setOrderAmout(new BigDecimal(15.00));
        orderMaster.setCreatTime(date);
        orderMaster.setUpdateTime(date);
        //时间字段没有设定内容，保存不到数据库
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);
    }
}