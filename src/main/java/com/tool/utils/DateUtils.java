package com.tool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: TODO
 * @Author KerVinLi
 * @Version 1.0
 */
public class DateUtils {
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String currentLongDateTime(){
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(new Date());
    }
}
