package com.tool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 日期工具类
 * @Author KerVinLi
 * @since 2021/10/24
 */
public class DateUtils {
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String CHINESE_DATE_TIME_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String currentLongDateTime(){
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(new Date());
    }

    /**
     * 当前时间 yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String currentChineseDateTime(){
        return new SimpleDateFormat(CHINESE_DATE_TIME_FORMAT).format(new Date());
    }
}
