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
 * 运动教程收藏
 *
 * @author
 * @email
 */
@TableName("yundong_collection") // 指定数据库表名为yundong_collection
public class YundongCollectionEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    public YundongCollectionEntity() {
        // 默认构造方法
    }

    public YundongCollectionEntity(T t) {
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
     * 运动教程
     */
    @ColumnInfo(comment = "运动教程", type = "int(11)") // 字段注释和类型
    @TableField(value = "yundong_id") // 指定数据库字段名为yundong_id

    private Integer yundongId; // 运动教程ID

    /**
     * 用户
     */
    @ColumnInfo(comment = "用户", type = "int(11)") // 字段注释和类型
    @TableField(value = "yonghu_id") // 指定数据库字段名为yonghu_id

    private Integer yonghuId; // 用户ID

    /**
     * 类型
     */
    @ColumnInfo(comment = "类型", type = "int(11)") // 字段注释和类型
    @TableField(value = "yundong_collection_types") // 指定数据库字段名为yundong_collection_types

    private Integer yundongCollectionTypes; // 收藏类型

    /**
     * 收藏时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON格式化
    @DateTimeFormat // 日期时间格式化
    @ColumnInfo(comment = "收藏时间", type = "timestamp") // 字段注释和类型
    @TableField(value = "insert_time", fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime; // 收藏时间

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
     * 获取：运动教程
     */
    public Integer getYundongId() {
        return yundongId; // 获取运动教程ID
    }

    /**
     * 设置：运动教程
     */

    public void setYundongId(Integer yundongId) {
        this.yundongId = yundongId; // 设置运动教程ID
    }

    /**
     * 获取：用户
     */
    public Integer getYonghuId() {
        return yonghuId; // 获取用户ID
    }

    /**
     * 设置：用户
     */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId; // 设置用户ID
    }

    /**
     * 获取：类型
     */
    public Integer getYundongCollectionTypes() {
        return yundongCollectionTypes; // 获取收藏类型
    }

    /**
     * 设置：类型
     */

    public void setYundongCollectionTypes(Integer yundongCollectionTypes) {
        this.yundongCollectionTypes = yundongCollectionTypes; // 设置收藏类型
    }

    /**
     * 获取：收藏时间
     */
    public Date getInsertTime() {
        return insertTime; // 获取收藏时间
    }

    /**
     * 设置：收藏时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime; // 设置收藏时间
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
        return "YundongCollection{" +
                // 开始构建对象的字符串表示
                ", id=" + id +
                // 添加主键ID
                ", yundongId=" + yundongId +
                // 添加运动教程ID
                ", yonghuId=" + yonghuId +
                // 添加用户ID
                ", yundongCollectionTypes=" + yundongCollectionTypes +
                // 添加收藏类型
                ", insertTime=" + DateUtil.convertString(insertTime, "yyyy-MM-dd") +
                // 格式化收藏时间为字符串并添加
                ", createTime=" + DateUtil.convertString(createTime, "yyyy-MM-dd") +
                // 格式化创建时间为字符串并添加
                "}";
        // 结束构建对象的字符串表示并返回
    }
}

