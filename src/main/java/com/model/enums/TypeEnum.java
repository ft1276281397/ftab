package com.model.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 必须现在 IEnum 配置 该包扫描自动注入，查看文件 spring-mybatis.xml 参数 typeEnumsPackage
 */
public enum TypeEnum implements IEnum {
    // 定义TypeEnum枚举类并实现IEnum接口
    DISABLED(0, "禁用"),
    // 定义枚举常量DISABLED，值为0，描述为“禁用”
    NORMAL(1, "正常");
    // 定义枚举常量NORMAL，值为1，描述为“正常”

    private final int value;
    // 定义枚举常量的值
    private final String desc;
    // 定义枚举常量的描述

    TypeEnum(final int value, final String desc) {
        // 构造方法，初始化枚举常量的值和描述
        this.value = value;
        // 设置枚举常量的值
        this.desc = desc;
        // 设置枚举常量的描述
    }

    @Override
    public Serializable getValue() {
        return this.value;
        // 返回枚举常量的值
    }

    // Jackson 注解为 JsonValue 返回中文 json 描述
    public String getDesc() {
        return this.desc;
        // 返回枚举常量的描述
    }
}
