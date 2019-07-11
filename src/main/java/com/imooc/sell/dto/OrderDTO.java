package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.Utils.serializer.Date2LongSerializer;
import com.imooc.sell.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * dto中数据用java逻辑层之间数据传输
 *订单数据
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)  这个是旧版本的JsonSerialize注解，但是在我的当前版本中也没过时
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /*订单id*/
    private String orderId;
    /*买家名称*/
    private String buyerName;
    /*买家电话*/
    private String buyerPhone;
    /*买家地址*/
    private String buyerAddress;
    /*买家的微信id*/
    private String buyerOpenid;
    /*订单金额*/
    private BigDecimal orderAmout;
    /*订单状态,默认0新下单*/
    private Integer orderStatus ;
    /*订单支付状态 默认0未支付*/
    private Integer payStatus ;
    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date creatTime;
    /*更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /*订单详情表中的数据*/
    private List<OrderDetail> orderDetails;
}
