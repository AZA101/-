package com.imooc.sell.enums;

import lombok.Getter;

/**
 * @author YJB
 * 2019-06-30
 * 建立枚举类,商品状态
 */
@Getter
public enum  ProductStatusEnum {
    UP(0,"上架"),
    DOWN(1,"下架")
    ;

    private  Integer code;
    private  String message;

    ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message=message;
    }
}
