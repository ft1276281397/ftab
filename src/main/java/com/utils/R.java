package com.utils;

// 导入HashMap类，用于存储键值对
import java.util.HashMap;
// 导入Map接口，用于存储键值对
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    // 序列化版本号
    private static final long serialVersionUID = 1L;

    /**
     * 默认构造函数，初始化返回码为0
     */
    public R() {
        put("code", 0);
    }

    /**
     * 返回一个错误信息，默认错误码为500，消息为“未知异常，请联系管理员”
     *
     * @return 包含错误信息的R对象
     */
    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    /**
     * 返回一个错误信息，默认错误码为500
     *
     * @param msg 错误消息
     * @return 包含错误信息的R对象
     */
    public static R error(String msg) {
        return error(500, msg);
    }

    /**
     * 返回一个错误信息
     *
     * @param code 错误码
     * @param msg  错误消息
     * @return 包含错误信息的R对象
     */
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 返回一个成功信息，消息为指定的字符串
     *
     * @param msg 成功消息
     * @return 包含成功信息的R对象
     */
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    /**
     * 返回一个成功信息，包含指定的键值对
     *
     * @param map 包含键值对的Map对象
     * @return 包含成功信息的R对象
     */
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     * 返回一个默认的成功信息，返回码为0
     *
     * @return 包含成功信息的R对象
     */
    public static R ok() {
        return new R();
    }

    /**
     * 重写put方法，支持链式调用
     *
     * @param key   键
     * @param value 值
     * @return 当前R对象
     */
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
