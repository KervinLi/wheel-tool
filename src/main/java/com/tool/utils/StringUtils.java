package com.tool.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 字符串操作工具类
 * @Author KerVinLi
 * @Version 1.0
 */
public class StringUtils {
    /**
     * 使用StringBuffer进行代替直接string + string
     * @param params
     * @return
     */
    public static String joinStrings(String... params) {
        if (params == null || params.length == 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
        }
        return sb.toString();
    }

    /**
     * 类似于log.info的传值一样 使用{} 传递参数
     *
     * @param str  字符串
     * @param objs 参数
     * @return
     */
    public static String formatString(String str, Object... objs) {
        if (objs.length > 0) {
            for (int i = 0; i < objs.length; i++) {
                str = str.replaceFirst("\\{\\}", JSON.toJSONString(objs[i]));
            }
        }
        str = str.replaceAll("\\{\\}", "").replace("\"", "");
        return str;
    }

}
