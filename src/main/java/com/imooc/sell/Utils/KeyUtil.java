package com.imooc.sell.Utils;

import java.util.Random;

/**
 * 定义商品表的主键生成规则
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 时间+随机数
     * 并设置同一时刻只能一个线程执行
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        //随机生成[0,900000)范围的数字，并加上10
        Integer number = random.nextInt(900000) + 10;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
