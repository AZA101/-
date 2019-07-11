package com.imooc.sell.dto;

import lombok.Data;

/**
 * 用于订单库存数据在java层流转,购物车
 * @author YJB
 * 2019-07-04
 */
@Data
public class CartDTO {
    /*商品id*/
    private String productId;
    /*商品库存*/
    private Integer productStock;

    public CartDTO(String productId, Integer productStock) {
        this.productId = productId;
        this.productStock = productStock;
    }
}


