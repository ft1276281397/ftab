package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;

/**
 * 药品信息
 *
 * @author
 * @email
 */
@TableName("yaopin") // 指定数据库表名为yaopin
public class YaopinEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    public YaopinEntity() {
        // 默认构造方法
    }

    public YaopinEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t); // 使用BeanUtils复制属性
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); // 打印异常堆栈信息
        }
    }

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO) // 主键自增
    @ColumnInfo(comment = "主键", type = "int(11)") // 字段注释和类型
    @TableField(value = "id") // 指定数据库字段名为id

    private Integer id; // 主键ID

    /**
     * 药品名称
     */
    @ColumnInfo(comment = "药品名称", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yaopin_name") // 指定数据库字段名为yaopin_name

    private String yaopinName; // 药品名称

    /**
     * 药品照片
     */
    @ColumnInfo(comment = "药品照片", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yaopin_photo") // 指定数据库字段名为yaopin_photo

    private String yaopinPhoto; // 药品照片路径

    /**
     * 药品类型
     */
    @ColumnInfo(comment = "药品类型", type = "int(11)") // 字段注释和类型
    @TableField(value = "yaopin_types") // 指定数据库字段名为yaopin_types

    private Integer yaopinTypes; // 药品类型

    /**
     * 药品作用
     */
    @ColumnInfo(comment = "药品作用", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yaopin_zuoyng") // 指定数据库字段名为yaopin_zuoyng

    private String yaopinZuoyng; // 药品作用

    /**
     * 药品成分
     */
    @ColumnInfo(comment = "药品成分", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yaopin_chengfen") // 指定数据库字段名为yaopin_chengfen

    private String yaopinChengfen; // 药品成分

    /**
     * 药品详情
     */
    @ColumnInfo(comment = "药品详情", type = "longtext") // 字段注释和类型
    @TableField(value = "yaopin_content") // 指定数据库字段名为yaopin_content

    private String yaopinContent; // 药品详情

    /**
     * 逻辑删除
     */
    @ColumnInfo(comment = "逻辑删除", type = "int(11)") // 字段注释和类型
    @TableField(value = "yaopin_delete") // 指定数据库字段名为yaopin_delete

    private Integer yaopinDelete; // 逻辑删除标志

    /**
     * 录入时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON格式化
    @DateTimeFormat // 日期时间格式化
    @ColumnInfo(comment = "录入时间", type = "timestamp") // 字段注释和类型
    @TableField(value = "insert_time", fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime; // 录入时间

    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON格式化
    @DateTimeFormat // 日期时间格式化
    @ColumnInfo(comment = "创建时间", type = "timestamp") // 字段注释和类型
    @TableField(value = "create_time", fill = FieldFill.INSERT) // 指定数据库字段名为create_time，并在插入时自动填充

    private Date createTime; // 创建时间

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id; // 获取主键ID
    }

    /**
     * 设置：主键
     */

    public void setId(Integer id) {
        this.id = id; // 设置主键ID
    }

    /**
     * 获取：药品名称
     */
    public String getYaopinName() {
        return yaopinName; // 获取药品名称
    }

    /**
     * 设置：药品名称
     */

    public void setYaopinName(String yaopinName) {
        this.yaopinName = yaopinName; // 设置药品名称
    }

    /**
     * 获取：药品照片
     */
    public String getYaopinPhoto() {
        return yaopinPhoto; // 获取药品照片路径
    }

    /**
     * 设置：药品照片
     */

    public void setYaopinPhoto(String yaopinPhoto) {
        this.yaopinPhoto = yaopinPhoto; // 设置药品照片路径
    }

    /**
     * 获取：药品类型
     */
    public Integer getYaopinTypes() {
        return yaopinTypes; // 获取药品类型
    }

    /**
     * 设置：药品类型
     */

    public void setYaopinTypes(Integer yaopinTypes) {
        this.yaopinTypes = yaopinTypes; // 设置药品类型
    }

    /**
     * 获取：药品作用
     */
    public String getYaopinZuoyng() {
        return yaopinZuoyng; // 获取药品作用
    }

    /**
     * 设置：药品作用
     */

    public void setYaopinZuoyng(String yaopinZuoyng) {
        this.yaopinZuoyng = yaopinZuoyng; // 设置药品作用
    }

    /**
     * 获取：药品成分
     */
    public String getYaopinChengfen() {
        return yaopinChengfen; // 获取药品成分
    }

    /**
     * 设置：药品成分
     */

    public void setYaopinChengfen(String yaopinChengfen) {
        this.yaopinChengfen = yaopinChengfen; // 设置药品成分
    }

    /**
     * 获取：药品详情
     */
    public String getYaopinContent() {
        return yaopinContent; // 获取药品详情
    }

    /**
     * 设置：药品详情
     */

    public void setYaopinContent(String yaopinContent) {
        this.yaopinContent = yaopinContent; // 设置药品详情
    }

    /**
     * 获取：逻辑删除
     */
    public Integer getYaopinDelete() {
        return yaopinDelete; // 获取逻辑删除标志
    }

    /**
     * 设置：逻辑删除
     */

    public void setYaopinDelete(Integer yaopinDelete) {
        this.yaopinDelete = yaopinDelete; // 设置逻辑删除标志
    }

    /**
     * 获取：录入时间
     */
    public Date getInsertTime() {
        return insertTime; // 获取录入时间
    }

    /**
     * 设置：录入时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime; // 设置录入时间
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime; // 获取创建时间
    }

    /**
     * 设置：创建时间
     */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime; // 设置创建时间
    }

    @Override
    public String toString() {
        return "Yaopin{" + // 开始构建对象的字符串表示
                ", id=" + id + // 添加主键ID
                ", yaopinName=" + yaopinName + // 添加药品名称
                ", yaopinPhoto=" + yaopinPhoto + // 添加药品照片路径
                ", yaopinTypes=" + yaopinTypes + // 添加药品类型
                ", yaopinZuoyng=" + yaopinZuoyng + // 添加药品作用
                ", yaopinChengfen=" + yaopinChengfen + // 添加药品成分
                ", yaopinContent=" + yaopinContent + // 添加药品详情
                ", yaopinDelete=" + yaopinDelete + // 添加逻辑删除标志
                ", insertTime=" + DateUtil.convertString(insertTime, "yyyy-MM-dd") + // 格式化录入时间为字符串并添加
                ", createTime=" + DateUtil.convertString(createTime, "yyyy-MM-dd") + // 格式化创建时间为字符串并添加
                "}"; // 结束构建对象的字符串表示并返回
    }
}
