package com.utils;

// 导入StringUtils类，用于字符串操作
import org.apache.commons.lang3.StringUtils;

// 导入EIException类，用于自定义异常
import com.entity.EIException;

/**
 * SQL过滤
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     * @return 过滤后的字符串
     * @throws EIException 如果字符串包含非法字符
     */
    public static String sqlInject(String str) {
        // 如果字符串为空或空白，返回null
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // 去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        // 转换成小写
        str = str.toLowerCase();

        // 定义非法字符数组
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        // 判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.indexOf(keyword) != -1) {
                // 如果包含非法字符，抛出自定义异常
                throw new EIException("包含非法字符");
            }
        }

        // 返回过滤后的字符串
        return str;
    }
}
