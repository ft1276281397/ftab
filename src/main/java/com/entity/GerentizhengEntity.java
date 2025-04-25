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
 * 体检记录实体类
 *
 * @author
 * @email
 */
@TableName("gerentizheng") // 指定数据库表名为gerentizheng
public class GerentizhengEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    // 无参构造函数
    public GerentizhengEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public GerentizhengEntity(T t) {
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
     * 用户
     */
    @ColumnInfo(comment="用户",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "yonghu_id") // 指定数据库字段名为yonghu_id

    private Integer yonghuId;

    /**
     * 当前身高
     */
    @ColumnInfo(comment="当前身高",type="decimal(10,2)") // 自定义注解，描述字段信息
    @TableField(value = "gerentizheng_num") // 指定数据库字段名为gerentizheng_num

    private Double gerentizhengNum;

    /**
     * 当前体重
     */
    @ColumnInfo(comment="当前体重",type="decimal(10,2)") // 自定义注解，描述字段信息
    @TableField(value = "tizhong_num") // 指定数据库字段名为tizhong_num

    private Double tizhongNum;

    /**
     * 身体状况
     */
    @ColumnInfo(comment="身体状况",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "shenti_types") // 指定数据库字段名为shenti_types

    private Integer shentiTypes;

    /**
     * 饮食照片
     */
    @ColumnInfo(comment="饮食照片",type="varchar(200)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_photo") // 指定数据库字段名为meishi_photo

    private String meishiPhoto;

    /**
     * 备注
     */
    @ColumnInfo(comment="备注",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "gerentizheng_content") // 指定数据库字段名为gerentizheng_content

    private String gerentizhengContent;

    /**
     * 下次体检时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="下次体检时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "gerentizheng_time") // 指定数据库字段名为gerentizheng_time

    private Date gerentizhengTime;

    /**
     * 逻辑删除
     */
    @ColumnInfo(comment="逻辑删除",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "gerentizheng_delete") // 指定数据库字段名为gerentizheng_delete

    private Integer gerentizhengDelete;

    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="添加时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "insert_time",fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime;

    /**
     * 创建时间   listShow
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
     * 获取：用户
     */
    public Integer getYonghuId() {
        return yonghuId;
    }
    /**
     * 设置：用户
     */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
     * 获取：当前身高
     */
    public Double getGerentizhengNum() {
        return gerentizhengNum;
    }
    /**
     * 设置：当前身高
     */

    public void setGerentizhengNum(Double gerentizhengNum) {
        this.gerentizhengNum = gerentizhengNum;
    }
    /**
     * 获取：当前体重
     */
    public Double getTizhongNum() {
        return tizhongNum;
    }
    /**
     * 设置：当前体重
     */

    public void setTizhongNum(Double tizhongNum) {
        this.tizhongNum = tizhongNum;
    }
    /**
     * 获取：身体状况
     */
    public Integer getShentiTypes() {
        return shentiTypes;
    }
    /**
     * 设置：身体状况
     */

    public void setShentiTypes(Integer shentiTypes) {
        this.shentiTypes = shentiTypes;
    }
    /**
     * 获取：饮食照片
     */
    public String getMeishiPhoto() {
        return meishiPhoto;
    }
    /**
     * 设置：饮食照片
     */

    public void setMeishiPhoto(String meishiPhoto) {
        this.meishiPhoto = meishiPhoto;
    }
    /**
     * 获取：备注
     */
    public String getGerentizhengContent() {
        return gerentizhengContent;
    }
    /**
     * 设置：备注
     */

    public void setGerentizhengContent(String gerentizhengContent) {
        this.gerentizhengContent = gerentizhengContent;
    }
    /**
     * 获取：下次体检时间
     */
    public Date getGerentizhengTime() {
        return gerentizhengTime;
    }
    /**
     * 设置：下次体检时间
     */

    public void setGerentizhengTime(Date gerentizhengTime) {
        this.gerentizhengTime = gerentizhengTime;
    }
    /**
     * 获取：逻辑删除
     */
    public Integer getGerentizhengDelete() {
        return gerentizhengDelete;
    }
    /**
     * 设置：逻辑删除
     */

    public void setGerentizhengDelete(Integer gerentizhengDelete) {
        this.gerentizhengDelete = gerentizhengDelete;
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
     * 获取：创建时间   listShow
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 设置：创建时间   listShow
     */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    // 重写toString方法，便于调试和日志输出
    public String toString() {
        return "Gerentizheng{" +
            // 添加主键id
            ", id=" + id +
            // 添加用户yonghuId
            ", yonghuId=" + yonghuId +
            // 添加当前身高gerentizhengNum
            ", gerentizhengNum=" + gerentizhengNum +
            // 添加当前体重tizhongNum
            ", tizhongNum=" + tizhongNum +
            // 添加身体状况shentiTypes
            ", shentiTypes=" + shentiTypes +
            // 添加饮食照片meishiPhoto
            ", meishiPhoto=" + meishiPhoto +
            // 添加备注gerentizhengContent
            ", gerentizhengContent=" + gerentizhengContent +
            // 添加下次体检时间gerentizhengTime，并使用DateUtil格式化日期
            ", gerentizhengTime=" + DateUtil.convertString(gerentizhengTime,"yyyy-MM-dd") +
            // 添加逻辑删除gerentizhengDelete
            ", gerentizhengDelete=" + gerentizhengDelete +
            // 添加添加时间insertTime，并使用DateUtil格式化日期
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            // 添加创建时间createTime，并使用DateUtil格式化日期
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
