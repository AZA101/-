package com.imooc.sell.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.from.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将前端传输过来的参数进行转换为创建订单所需要的参数类型
 *
 * @author YJB
 * 2019-07-05
 */
@Slf4j
public class OrderFormToOrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        Gson gson = new Gson();
        //买家姓名
        orderDTO.setBuyerName(orderForm.getName().trim());
        //买家电话
        orderDTO.setBuyerPhone(orderForm.getPhone().trim());
        //买家地址
        orderDTO.setBuyerAddress(orderForm.getAddress().trim());
        //买家微信id
        orderDTO.setBuyerOpenid(orderForm.getOpenid().trim());
        //买家的购物车是个list,且前端传输过来的时候是json格式，
        // 而定义的前端参数列表中购物车是个String,因此需要涉及到json转换为字符串，
        //引入依赖进行转换
        //将字符串转换为列表，要使用new TypeToken
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
       orderDetailList=gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());

        } catch (Exception e) {
            log.error("[转换错误]字符串转换json出错 orderForm={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetails(orderDetailList);
        return orderDTO;
        /**
         * 这里不使用copy的方式是因为对象不同，对象包括对象名称、对象属性
         * 一个是ByerName,一个是Name,不能转换
         */
        //BeanUtils.copyProperties();
    }
}
