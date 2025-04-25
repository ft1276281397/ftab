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
 * 健康食谱实体类
 *
 * @author
 * @email
 */
@TableName("meishi") // 指定数据库表名为meishi
public class MeishiEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    // 无参构造函数
    public MeishiEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public MeishiEntity(T t) {
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
     * 食谱标题
     */
    @ColumnInfo(comment="食谱标题",type="varchar(200)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_name") // 指定数据库字段名为meishi_name

    private String meishiName;

    /**
     * 食谱照片
     */
    @ColumnInfo(comment="食谱照片",type="varchar(200)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_photo") // 指定数据库字段名为meishi_photo

    private String meishiPhoto;

    /**
     * 食谱类型
     */
    @ColumnInfo(comment="食谱类型",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_types") // 指定数据库字段名为meishi_types

    private Integer meishiTypes;

    /**
     * 所含热量
     */
    @ColumnInfo(comment="所含热量",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_num") // 指定数据库字段名为meishi_num

    private Integer meishiNum;

    /**
     * 赞
     */
    @ColumnInfo(comment="赞",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "zan_number") // 指定数据库字段名为zan_number

    private Integer zanNumber;

    /**
     * 踩
     */
    @ColumnInfo(comment="踩",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "cai_number") // 指定数据库字段名为cai_number

    private Integer caiNumber;

    /**
     * 制作教程
     */
    @ColumnInfo(comment="制作教程",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "meishi_content") // 指定数据库字段名为meishi_content

    private String meishiContent;

    /**
     * 逻辑删除
     */
    @ColumnInfo(comment="逻辑删除",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_delete") // 指定数据库字段名为meishi_delete

    private Integer meishiDelete;

    /**
     * 录入时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="录入时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "insert_time",fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime;

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
     * 获取：食谱标题
     */
    public String getMeishiName() {
        return meishiName;
    }
    /**
     * 设置：食谱标题
     */

    public void setMeishiName(String meishiName) {
        this.meishiName = meishiName;
    }
    /**
     * 获取：食谱照片
     */
    public String getMeishiPhoto() {
        return meishiPhoto;
    }
    /**
     * 设置：食谱照片
     */

    public void setMeishiPhoto(String meishiPhoto) {
        this.meishiPhoto = meishiPhoto;
    }
    /**
     * 获取：食谱类型
     */
    public Integer getMeishiTypes() {
        return meishiTypes;
    }
    /**
     * 设置：食谱类型
     */

    public void setMeishiTypes(Integer meishiTypes) {
        this.meishiTypes = meishiTypes;
    }
    /**
     * 获取：所含热量
     */
    public Integer getMeishiNum() {
        return meishiNum;
    }
    /**
     * 设置：所含热量
     */

    public void setMeishiNum(Integer meishiNum) {
        this.meishiNum = meishiNum;
    }
    /**
     * 获取：赞
     */
    public Integer getZanNumber() {
        return zanNumber;
    }
    /**
     * 设置：赞
     */

    public void setZanNumber(Integer zanNumber) {
        this.zanNumber = zanNumber;
    }
    /**
     * 获取：踩
     */
    public Integer getCaiNumber() {
        return caiNumber;
    }
    /**
     * 设置：踩
     */

    public void setCaiNumber(Integer caiNumber) {
        this.caiNumber = caiNumber;
    }
    /**
     * 获取：制作教程
     */
    public String getMeishiContent() {
        return meishiContent;
    }
    /**
     * 设置：制作教程
     */

    public void setMeishiContent(String meishiContent) {
        this.meishiContent = meishiContent;
    }
    /**
     * 获取：逻辑删除
     */
    public Integer getMeishiDelete() {
        return meishiDelete;
    }
    /**
     * 设置：逻辑删除
     */

    public void setMeishiDelete(Integer meishiDelete) {
        this.meishiDelete = meishiDelete;
    }
    /**
     * 获取：录入时间
     */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
     * 设置：录入时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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
        return "Meishi{" +
            // 添加主键id
            ", id=" + id +
            // 添加食谱标题meishiName
            ", meishiName=" + meishiName +
            // 添加食谱照片meishiPhoto
            ", meishiPhoto=" + meishiPhoto +
            // 添加食谱类型meishiTypes
            ", meishiTypes=" + meishiTypes +
            // 添加所含热量meishiNum
            ", meishiNum=" + meishiNum +
            // 添加赞zanNumber
            ", zanNumber=" + zanNumber +
            // 添加踩caiNumber
            ", caiNumber=" + caiNumber +
            // 添加制作教程meishiContent
            ", meishiContent=" + meishiContent +
            // 添加逻辑删除meishiDelete
            ", meishiDelete=" + meishiDelete +
            // 添加录入时间insertTime，并使用DateUtil格式化日期
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            // 添加创建时间createTime，并使用DateUtil格式化日期
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
