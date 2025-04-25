package com.utils;

// 导入Java集合框架中的Map接口，用于存储键值对
import java.util.*;

/**
 * 公共方法
 */
public class CommonUtil {

    /**
     * 获取随机字符串
     *
     * @param num 随机字符串的长度
     * @return 生成的随机字符串
     */
    public static String getRandomString(Integer num) {
        // 定义基础字符集
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        // 创建Random对象用于生成随机数
        Random random = new Random();
        // 创建StringBuffer对象用于构建随机字符串
        StringBuffer sb = new StringBuffer();
        // 循环生成指定长度的随机字符串
        for (int i = 0; i < num; i++) {
            // 生成随机索引
            int number = random.nextInt(base.length());
            // 根据随机索引获取字符并添加到StringBuffer中
            sb.append(base.charAt(number));
        }
        // 返回生成的随机字符串
        return sb.toString();
    }

    /**
     * 检查map参数并添加缺失参数
     *
     * @param params 参数Map对象
     */
    public static void checkMap(Map<String, Object> params) {
        // 初始化标志变量，用于检查参数是否存在
        boolean page = true, limit = true, sort = true, order = true;
        // 获取Map的迭代器
        Iterator<Map.Entry<String, Object>> iter = params.entrySet().iterator();
        // 遍历Map中的每个条目
        while (iter.hasNext()) {
            // 获取当前条目
            Map.Entry<String, Object> info = iter.next();
            // 获取键和值
            Object key = info.getKey();
            Object value = info.getValue();
            // 如果值为空、空字符串或"null"，则移除该条目
            if (value == null || "".equals(value) || "null".equals(value)) {
                iter.remove();
            } else if ("page".equals(key)) {
                // 如果键为"page"，设置page标志为false
                page = false;
            } else if ("limit".equals(key)) {
                // 如果键为"limit"，设置limit标志为false
                limit = false;
            } else if ("sort".equals(key)) {
                // 如果键为"sort"，设置sort标志为false
                sort = false;
            } else if ("order".equals(key)) {
                // 如果键为"order"，设置order标志为false
                order = false;
            }
        }
        // 如果page标志为true，添加默认的"page"参数
        if (page) {
            params.put("page", "1");
        }
        // 如果limit标志为true，添加默认的"limit"参数
        if (limit) {
            params.put("limit", "10");
        }
        // 如果sort标志为true，添加默认的"sort"参数
        if (sort) {
            params.put("sort", "id");
        }
        // 如果order标志为true，添加默认的"order"参数
        if (order) {
            params.put("order", "desc");
        }
    }
}
