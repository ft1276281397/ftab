package com.entity;

import java.io.Serializable;

// MyBatis-Plus注解，用于标识实体类对应的数据库表
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author yangliyuan
 * @version 创建时间：2020年2月7日 下午8:36:05
 * 类说明 : 配置实体类，对应数据库中的config表
 */
@TableName("config") // 指定数据库表名为config
public class ConfigEntity implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    // 主键，自增类型
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * key
     */
    private String name;

    /**
     * value
     */
    private String value;

    // 获取主键id
    public Long getId() {
        return id;
    }

    // 设置主键id
    public void setId(Long id) {
        this.id = id;
    }

    // 获取key
    public String getName() {
        return name;
    }

    // 设置key
    public void setName(String name) {
        this.name = name;
    }

    // 获取value
    public String getValue() {
        return value;
    }

    // 设置value
    public void setValue(String value) {
        this.value = value;
    }
}
