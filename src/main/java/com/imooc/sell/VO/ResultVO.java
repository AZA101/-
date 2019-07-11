package com.imooc.sell.VO;

import lombok.Data;

/**
 * http请求返回给最外层对象
 *
 * @author YJB
 * 2019-07-01
 */
@Data
public class ResultVO<T> {
    /*错误码*/
    private Integer code;
   /* 提示信息*/
    private String msg;
    /*返回的数据*/
    private T data;

}
