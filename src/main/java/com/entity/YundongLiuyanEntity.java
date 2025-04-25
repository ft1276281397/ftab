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
 * 运动教程留言
 *
 * @author
 * @email
 */
@TableName("yundong_liuyan") // 指定数据库表名为yundong_liuyan
public class YundongLiuyanEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    public YundongLiuyanEntity() {
        // 默认构造方法
    }

    public YundongLiuyanEntity(T t) {
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
     * 留言内容
     */
    @ColumnInfo(comment = "留言内容", type = "longtext") // 字段注释和类型
    @TableField(value = "yundong_liuyan_text") // 指定数据库字段名为yundong_liuyan_text

    private String yundongLiuyanText; // 留言内容

    /**
     * 留言时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON格式化
    @DateTimeFormat // 日期时间格式化
    @ColumnInfo(comment = "留言时间", type = "timestamp") // 字段注释和类型
    @TableField(value = "insert_time", fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime; // 留言时间

    /**
     * 回复内容
     */
    @ColumnInfo(comment = "回复内容", type = "longtext") // 字段注释和类型
    @TableField(value = "reply_text") // 指定数据库字段名为reply_text

    private String replyText; // 回复内容

    /**
     * 回复时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON格式化
    @DateTimeFormat // 日期时间格式化
    @ColumnInfo(comment = "回复时间", type = "timestamp") // 字段注释和类型
    @TableField(value = "update_time", fill = FieldFill.UPDATE) // 指定数据库字段名为update_time，并在更新时自动填充

    private Date updateTime; // 回复时间

    /**
     * 创建时间  listShow
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
     * 获取：留言内容
     */
    public String getYundongLiuyanText() {
        return yundongLiuyanText; // 获取留言内容
    }

    /**
     * 设置：留言内容
     */

    public void setYundongLiuyanText(String yundongLiuyanText) {
        this.yundongLiuyanText = yundongLiuyanText; // 设置留言内容
    }

    /**
     * 获取：留言时间
     */
    public Date getInsertTime() {
        return insertTime; // 获取留言时间
    }

    /**
     * 设置：留言时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime; // 设置留言时间
    }

    /**
     * 获取：回复内容
     */
    public String getReplyText() {
        return replyText; // 获取回复内容
    }

    /**
     * 设置：回复内容
     */

    public void setReplyText(String replyText) {
        this.replyText = replyText; // 设置回复内容
    }

    /**
     * 获取：回复时间
     */
    public Date getUpdateTime() {
        return updateTime; // 获取回复时间
    }

    /**
     * 设置：回复时间
     */

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime; // 设置回复时间
    }

    /**
     * 获取：创建时间  listShow
     */
    public Date getCreateTime() {
        return createTime; // 获取创建时间
    }

    /**
     * 设置：创建时间  listShow
     */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime; // 设置创建时间
    }

    @Override
    public String toString() {
        return "YundongLiuyan{" +
                // 开始构建对象的字符串表示
                ", id=" + id +
                // 添加主键ID
                ", yundongId=" + yundongId +
                // 添加运动教程ID
                ", yonghuId=" + yonghuId +
                // 添加用户ID
                ", yundongLiuyanText=" + yundongLiuyanText +
                // 添加留言内容
                ", insertTime=" + DateUtil.convertString(insertTime, "yyyy-MM-dd") +
                // 格式化留言时间为字符串并添加
                ", replyText=" + replyText +
                // 添加回复内容
                ", updateTime=" + DateUtil.convertString(updateTime, "yyyy-MM-dd") +
                // 格式化回复时间为字符串并添加
                ", createTime=" + DateUtil.convertString(createTime, "yyyy-MM-dd") +
                // 格式化创建时间为字符串并添加
                "}"; // 结束构建对象的字符串表示并返回
    }
}

