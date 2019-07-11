package com.imooc.sell.Utils;

import com.imooc.sell.VO.ResultVO;

/**
 * @author YJB
 * 2019-07-05
 * resultVo的成功和失败的方法
 */
public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO result=new ResultVO();
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(object);
        return result;
    }
    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error( Integer code,Object object){
        ResultVO result=new ResultVO();
        result.setCode(code);
        result.setMsg("操作失败");
        result.setData(object);
        return result;
    }
}
