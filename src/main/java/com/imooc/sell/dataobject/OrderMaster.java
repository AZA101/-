package com.imooc.sell.dataobject;

import com.imooc.sell.enums.OrderMasterEnum;
import com.imooc.sell.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表对应数据库表
 *
 * @author YJB
 * 2019-07-02
 */
@Entity
@Data
@DynamicUpdate   /*动态更新注解主要针对更新时间字段*/
public class OrderMaster {
    /*订单id*/
    @Id
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
    private Integer orderStatus = OrderMasterEnum.NEW.getCode();
    /*订单支付状态 默认0未支付*/
    private Integer payStatus = PayStatusEnums.WAIT.getCode();
    /* 订单创建时间*/
    private Date creatTime;
    /*订单更新时间*/
    private Date updateTime;

   /* public OrderMaster() {
    }*/
    /* public OrderMaster(String orderId, String buyerName, String buyerPhone,
                       String buyerAddress, String buyerOpenid, BigDecimal orderAmout,
                       Integer orderStatus, Integer payStatus, Date creatTime, Date updateTime) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmout = orderAmout;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
    }*/
}
