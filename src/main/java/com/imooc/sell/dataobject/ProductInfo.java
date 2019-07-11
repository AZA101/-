package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品表
 *
 * @Auth yjb
 * 2019-06-29
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    @Id
    /*主键*/
    private String productId;
    /*商品名称*/
    private String productName;
    /*商品价格*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*商品描述*/
    private String productDescription;
    /*商品图片*/
    private String productIcon;
    /*商品状态,0为正常1为下架*/
    private Integer productStatus;
    /*类目编号*/
    private Integer categoryType;

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice,
                       Integer productStock, String productDescription, String productIcon,
                       Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }
}
