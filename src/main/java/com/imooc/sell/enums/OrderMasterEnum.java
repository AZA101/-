package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 订单表的枚举类
 */
@Getter
public enum OrderMasterEnum {
    NEW(0,"新下单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消下单");

    private  Integer code;
    private  String message;

    OrderMasterEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
