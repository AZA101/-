package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 新建的商品信息对象
 * @author YJB
 * 2019-07-01
 */
@Data
public class ProductInfoVO {

    @JsonProperty("ID")  /*商品ID*/
    private String productId;
    @JsonProperty("name")  /*商品名称*/
    private String productName;
    @JsonProperty("price") /*商品价格*/
    private BigDecimal productPrice;
    @JsonProperty("description")   /*商品描述*/
    private String productDescription;
    @JsonProperty("icon")   /*商品图片*/
    private String productIcon;



}
