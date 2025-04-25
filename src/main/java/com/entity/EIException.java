package com.entity;

/**
 * 自定义异常类，继承自RuntimeException
 */
public class EIException extends RuntimeException {
    private static final long serialVersionUID = 1L; // 序列化版本号

    private String msg; // 异常消息
    private int code = 500; // 异常代码，默认为500

    /**
     * 构造函数，仅包含异常消息
     * @param msg 异常消息
     */
    public EIException(String msg) {
        super(msg); // 调用父类构造函数
        this.msg = msg; // 设置异常消息
    }

    /**
     * 构造函数，包含异常消息和异常原因
     * @param msg 异常消息
     * @param e 异常原因
     */
    public EIException(String msg, Throwable e) {
        super(msg, e); // 调用父类构造函数
        this.msg = msg; // 设置异常消息
    }

    /**
     * 构造函数，包含异常消息和异常代码
     * @param msg 异常消息
     * @param code 异常代码
     */
    public EIException(String msg, int code) {
        super(msg); // 调用父类构造函数
        this.msg = msg; // 设置异常消息
        this.code = code; // 设置异常代码
    }

    /**
     * 构造函数，包含异常消息、异常代码和异常原因
     * @param msg 异常消息
     * @param code 异常代码
     * @param e 异常原因
     */
    public EIException(String msg, int code, Throwable e) {
        super(msg, e); // 调用父类构造函数
        this.msg = msg; // 设置异常消息
        this.code = code; // 设置异常代码
    }

    /**
     * 获取异常消息
     * @return 异常消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置异常消息
     * @param msg 异常消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取异常代码
     * @return 异常代码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置异常代码
     * @param code 异常代码
     */
    public void setCode(int code) {
        this.code = code;
    }
}
