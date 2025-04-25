package com.utils;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param s 要判断的字符串
     * @return 如果字符串为null、空字符串或"null"，返回true；否则返回false
     */
    public static boolean isEmpty(String s) {
        // 如果字符串为null、空字符串或"null"，返回true
        if (s == null || s.equals("") || s.equals("null")) {
            return true;
        }
        // 否则返回false
        return false;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param s 要判断的字符串
     * @return 如果字符串不为null、空字符串且不为"null"，返回true；否则返回false
     */
    public static boolean isNotEmpty(String s) {
        // 调用isEmpty方法，返回其相反值
        return !StringUtil.isEmpty(s);
    }
}
