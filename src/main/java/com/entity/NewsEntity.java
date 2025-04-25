package com.entity;

// 自定义注解，用于字段信息描述
import com.annotation.ColumnInfo;
// 用于字段验证的注解
import javax.validation.constraints.*;
// 忽略JSON序列化时的某些属性
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// 反射工具类
import java.lang.reflect.InvocationTargetException;
// 序列化接口
import java.io.Serializable;
// 集合工具类
import java.util.*;
// 日期工具类
import org.apache.tools.ant.util.DateUtils;
// 日期格式化注解
import org.springframework.format.annotation.DateTimeFormat;
// JSON日期格式化注解
import com.fasterxml.jackson.annotation.JsonFormat;
// BeanUtils工具类，用于对象属性复制
import org.apache.commons.beanutils.BeanUtils;
// MyBatis-Plus注解，用于标识实体类对应的数据库表
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
// 日期工具类
import com.utils.DateUtil;

/**
 * 公告信息实体类
 *
 * @author
 * @email
 */
@TableName("news") // 指定数据库表名为news
public class NewsEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    // 无参构造函数
    public NewsEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public NewsEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t); // 使用BeanUtils复制属性
        } catch (IllegalAccessException | InvocationTargetException e) {
            // 捕获并打印异常
            e.printStackTrace();
        }
    }

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO) // 指定主键自增
    @ColumnInfo(comment="主键",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "id") // 指定数据库字段名为id

    private Integer id;

    /**
     * 公告标题
     */
    @ColumnInfo(comment="公告标题",type="varchar(200)") // 自定义注解，描述字段信息
    @TableField(value = "news_name") // 指定数据库字段名为news_name

    private String newsName;

    /**
     * 公告类型
     */
    @ColumnInfo(comment="公告类型",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "news_types") // 指定数据库字段名为news_types

    private Integer newsTypes;

    /**
     * 公告图片
     */
    @ColumnInfo(comment="公告图片",type="varchar(200)") // 自定义注解，描述字段信息
    @TableField(value = "news_photo") // 指定数据库字段名为news_photo

    private String newsPhoto;

    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="添加时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "insert_time",fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime;

    /**
     * 公告详情
     */
    @ColumnInfo(comment="公告详情",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "news_content") // 指定数据库字段名为news_content

    private String newsContent;

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="创建时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "create_time",fill = FieldFill.INSERT) // 指定数据库字段名为create_time，并在插入时自动填充

    private Date createTime;

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：主键
     */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：公告标题
     */
    public String getNewsName() {
        return newsName;
    }
    /**
     * 设置：公告标题
     */

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }
    /**
     * 获取：公告类型
     */
    public Integer getNewsTypes() {
        return newsTypes;
    }
    /**
     * 设置：公告类型
     */

    public void setNewsTypes(Integer newsTypes) {
        this.newsTypes = newsTypes;
    }
    /**
     * 获取：公告图片
     */
    public String getNewsPhoto() {
        return newsPhoto;
    }
    /**
     * 设置：公告图片
     */

    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }
    /**
     * 获取：添加时间
     */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
     * 设置：添加时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
     * 获取：公告详情
     */
    public String getNewsContent() {
        return newsContent;
    }
    /**
     * 设置：公告详情
     */

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 设置：创建时间
     */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    // 重写toString方法，便于调试和日志输出
    public String toString() {
        return "News{" +
            // 添加主键id
            ", id=" + id +
            // 添加公告标题newsName
            ", newsName=" + newsName +
            // 添加公告类型newsTypes
            ", newsTypes=" + newsTypes +
            // 添加公告图片newsPhoto
            ", newsPhoto=" + newsPhoto +
            // 添加添加时间insertTime，并使用DateUtil格式化日期
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            // 添加公告详情newsContent
            ", newsContent=" + newsContent +
            // 添加创建时间createTime，并使用DateUtil格式化日期
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
