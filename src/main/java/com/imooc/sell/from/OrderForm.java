package com.imooc.sell.from;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 表单验证的参数,前端会传递过来这些参数
 */
@Data
public class OrderForm {
    /*姓名*/
    @NotEmpty(message = "姓名必须填写")
    private String name;
    /*电话号码*/
    @NotEmpty(message = "电话号码")
    private String phone;
    /*地址*/
    @NotEmpty(message = "地址必填")
    private String address;
    /*微信id*/
    @NotEmpty(message = "openid必填")
    private String openid;
    /*购物车信息*/
    @NotEmpty(message = "购物车信息")
    private String items;
}
