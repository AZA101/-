package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详情表
 *
 * @author YJB
 * 2019-07-02
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    /*订单详情表主键*/
    @Id
    private String detailId;
    /*订单表的主键*/
    private String orderId;
    /*商品表的主键*/
    private String productId;
    /*商品名称*/
    private String productName;
    /*商品价格*/
    private BigDecimal productPrice;
    /*商品数量*/
    private Integer productQuantity;
    /*商品图片*/
    private String productIcon;

}
