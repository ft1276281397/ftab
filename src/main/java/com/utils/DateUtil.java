package com.utils;

// 导入Format类，用于格式化日期
import java.text.Format;
// 导入SimpleDateFormat类，用于日期格式化
import java.text.SimpleDateFormat;
// 导入Date类，用于表示日期和时间
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 将日期转换为指定格式的字符串
     *
     * @param date   要转换的日期对象
     * @param format 日期格式字符串，例如 "yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的日期字符串，如果日期对象为null，则返回null
     */
    public static String convertString(Date date, String format) {
        // 如果日期对象为null，返回null
        if (date == null) {
            return null;
        }
        // 创建SimpleDateFormat对象，用于格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 使用SimpleDateFormat格式化日期并返回结果
        return sdf.format(date);
    }
}
