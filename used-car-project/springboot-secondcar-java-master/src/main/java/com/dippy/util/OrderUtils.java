package com.dippy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成订单号
 */
public class OrderUtils {

    // 时间拼接id 生成的订单号
    public static String createOrderNumber(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = sdf.format(new Date()) + id;

        return result;
    }
}
