package com.dippy.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class URLUtils {


    /**
     * 解析出url参数中的键值对
     * <p>
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String strUrlParam) {
        Map<String, String> mapRequest = new HashMap<>();

        String[] arrSplit = null;


        if (strUrlParam == null) {
            return mapRequest;

        }

        arrSplit = strUrlParam.split("[&]");

        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (!Objects.equals(arrSplitEqual[0], "")) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");

                }
            }
        }
        return mapRequest;

    }
}
