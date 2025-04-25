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
 * 健康贴士实体类
 *
 * @author
 * @email
 */
@TableName("jiankangtieshi")
// 指定数据库表名为jiankangtieshi
public class JiankangtieshiEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L;
    // 序列化版本号

    // 无参构造函数
    public JiankangtieshiEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public JiankangtieshiEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
            // 使用BeanUtils复制属性
        } catch (IllegalAccessException | InvocationTargetException e) {
            // 捕获并打印异常
            e.printStackTrace();
        }
    }

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    // 指定主键自增
    @ColumnInfo(comment="主键",type="int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "id")
    // 指定数据库字段名为id

    private Integer id;

    /**
     * 贴士标题
     */
    @ColumnInfo(comment="贴士标题",type="varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "jiankangtieshi_name")
    // 指定数据库字段名为jiankangtieshi_name

    private String jiankangtieshiName;

    /**
     * 贴士类型
     */
    @ColumnInfo(comment="贴士类型",type="int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "jiankangtieshi_types")
    // 指定数据库字段名为jiankangtieshi_types

    private Integer jiankangtieshiTypes;

    /**
     * 贴士图片
     */
    @ColumnInfo(comment="贴士图片",type="varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "jiankangtieshi_photo")
    // 指定数据库字段名为jiankangtieshi_photo

    private String jiankangtieshiPhoto;

    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="添加时间",type="timestamp")
    // 自定义注解，描述字段信息
    @TableField(value = "insert_time",fill = FieldFill.INSERT)
    // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime;

    /**
     * 贴士详情
     */
    @ColumnInfo(comment="贴士详情",type="longtext")
    // 自定义注解，描述字段信息
    @TableField(value = "jiankangtieshi_content")
    // 指定数据库字段名为jiankangtieshi_content

    private String jiankangtieshiContent;

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat
    // 日期格式化注解
    @ColumnInfo(comment="创建时间",type="timestamp")
    // 自定义注解，描述字段信息
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    // 指定数据库字段名为create_time，并在插入时自动填充

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
     * 获取：贴士标题
     */
    public String getJiankangtieshiName() {
        return jiankangtieshiName;
    }
    /**
     * 设置：贴士标题
     */

    public void setJiankangtieshiName(String jiankangtieshiName) {
        this.jiankangtieshiName = jiankangtieshiName;
    }
    /**
     * 获取：贴士类型
     */
    public Integer getJiankangtieshiTypes() {
        return jiankangtieshiTypes;
    }
    /**
     * 设置：贴士类型
     */

    public void setJiankangtieshiTypes(Integer jiankangtieshiTypes) {
        this.jiankangtieshiTypes = jiankangtieshiTypes;
    }
    /**
     * 获取：贴士图片
     */
    public String getJiankangtieshiPhoto() {
        return jiankangtieshiPhoto;
    }
    /**
     * 设置：贴士图片
     */

    public void setJiankangtieshiPhoto(String jiankangtieshiPhoto) {
        this.jiankangtieshiPhoto = jiankangtieshiPhoto;
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
     * 获取：贴士详情
     */
    public String getJiankangtieshiContent() {
        return jiankangtieshiContent;
    }
    /**
     * 设置：贴士详情
     */

    public void setJiankangtieshiContent(String jiankangtieshiContent) {
        this.jiankangtieshiContent = jiankangtieshiContent;
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
        return "Jiankangtieshi{" +
            // 添加主键id
            ", id=" + id +
            // 添加贴士标题jiankangtieshiName
            ", jiankangtieshiName=" + jiankangtieshiName +
            // 添加贴士类型jiankangtieshiTypes
            ", jiankangtieshiTypes=" + jiankangtieshiTypes +
            // 添加贴士图片jiankangtieshiPhoto
            ", jiankangtieshiPhoto=" + jiankangtieshiPhoto +
            // 添加添加时间insertTime，并使用DateUtil格式化日期
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            // 添加贴士详情jiankangtieshiContent
            ", jiankangtieshiContent=" + jiankangtieshiContent +
            // 添加创建时间createTime，并使用DateUtil格式化日期
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
