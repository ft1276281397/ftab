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
 * 运动教程
 *
 * @author
 * @email
 */
@TableName("yundong") // 指定数据库表名为yundong
public class YundongEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    public YundongEntity() {
        // 默认构造方法
    }

    public YundongEntity(T t) {
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
     * 标题
     */
    @ColumnInfo(comment = "标题", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yundong_name") // 指定数据库字段名为yundong_name

    private String yundongName; // 运动教程标题

    /**
     * 运动照片
     */
    @ColumnInfo(comment = "运动照片", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yundong_photo") // 指定数据库字段名为yundong_photo

    private String yundongPhoto; // 运动照片路径

    /**
     * 运动类型
     */
    @ColumnInfo(comment = "运动类型", type = "int(11)") // 字段注释和类型
    @TableField(value = "yundong_types") // 指定数据库字段名为yundong_types

    private Integer yundongTypes; // 运动类型

    /**
     * 运动视频
     */
    @ColumnInfo(comment = "运动视频", type = "varchar(200)") // 字段注释和类型
    @TableField(value = "yundong_video") // 指定数据库字段名为yundong_video

    private String yundongVideo; // 运动视频路径

    /**
     * 消耗热量
     */
    @ColumnInfo(comment = "消耗热量", type = "int(11)") // 字段注释和类型
    @TableField(value = "yundong_num") // 指定数据库字段名为yundong_num

    private Integer yundongNum; // 消耗热量

    /**
     * 赞
     */
    @ColumnInfo(comment = "赞", type = "int(11)") // 字段注释和类型
    @TableField(value = "zan_number") // 指定数据库字段名为zan_number

    private Integer zanNumber; // 赞的数量

    /**
     * 踩
     */
    @ColumnInfo(comment = "踩", type = "int(11)") // 字段注释和类型
    @TableField(value = "cai_number") // 指定数据库字段名为cai_number

    private Integer caiNumber; // 踩的数量

    /**
     * 运动介绍
     */
    @ColumnInfo(comment = "运动介绍", type = "longtext") // 字段注释和类型
    @TableField(value = "yundong_content") // 指定数据库字段名为yundong_content

    private String yundongContent; // 运动介绍

    /**
     * 逻辑删除
     */
    @ColumnInfo(comment = "逻辑删除", type = "int(11)") // 字段注释和类型
    @TableField(value = "yundong_delete") // 指定数据库字段名为yundong_delete

    private Integer yundongDelete; // 逻辑删除标志

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
     * 获取：标题
     */
    public String getYundongName() {
        return yundongName; // 获取运动教程标题
    }

    /**
     * 设置：标题
     */

    public void setYundongName(String yundongName) {
        this.yundongName = yundongName; // 设置运动教程标题
    }

    /**
     * 获取：运动照片
     */
    public String getYundongPhoto() {
        return yundongPhoto; // 获取运动照片路径
    }

    /**
     * 设置：运动照片
     */

    public void setYundongPhoto(String yundongPhoto) {
        this.yundongPhoto = yundongPhoto; // 设置运动照片路径
    }

    /**
     * 获取：运动类型
     */
    public Integer getYundongTypes() {
        return yundongTypes; // 获取运动类型
    }

    /**
     * 设置：运动类型
     */

    public void setYundongTypes(Integer yundongTypes) {
        this.yundongTypes = yundongTypes; // 设置运动类型
    }

    /**
     * 获取：运动视频
     */
    public String getYundongVideo() {
        return yundongVideo; // 获取运动视频路径
    }

    /**
     * 设置：运动视频
     */

    public void setYundongVideo(String yundongVideo) {
        this.yundongVideo = yundongVideo; // 设置运动视频路径
    }

    /**
     * 获取：消耗热量
     */
    public Integer getYundongNum() {
        return yundongNum; // 获取消耗热量
    }

    /**
     * 设置：消耗热量
     */

    public void setYundongNum(Integer yundongNum) {
        this.yundongNum = yundongNum; // 设置消耗热量
    }

    /**
     * 获取：赞
     */
    public Integer getZanNumber() {
        return zanNumber; // 获取赞的数量
    }

    /**
     * 设置：赞
     */

    public void setZanNumber(Integer zanNumber) {
        this.zanNumber = zanNumber; // 设置赞的数量
    }

    /**
     * 获取：踩
     */
    public Integer getCaiNumber() {
        return caiNumber; // 获取踩的数量
    }

    /**
     * 设置：踩
     */

    public void setCaiNumber(Integer caiNumber) {
        this.caiNumber = caiNumber; // 设置踩的数量
    }

    /**
     * 获取：运动介绍
     */
    public String getYundongContent() {
        return yundongContent; // 获取运动介绍
    }

    /**
     * 设置：运动介绍
     */

    public void setYundongContent(String yundongContent) {
        this.yundongContent = yidongContent; // 设置运动介绍
    }

    /**
     * 获取：逻辑删除
     */
    public Integer getYundongDelete() {
        return yundongDelete; // 获取逻辑删除标志
    }

    /**
     * 设置：逻辑删除
     */

    public void setYundongDelete(Integer yundongDelete) {
        this.yundongDelete = yundongDelete; // 设置逻辑删除标志
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
        return "Yundong{" +
                // 开始构建对象的字符串表示
                ", id=" + id + // 添加主键ID
                ", yundongName=" + yundongName +
                // 添加运动教程标题
                ", yundongPhoto=" + yundongPhoto +
                // 添加运动照片路径
                ", yundongTypes=" + yundongTypes +
                // 添加运动类型
                ", yundongVideo=" + yundongVideo +
                // 添加运动视频路径
                ", yundongNum=" + yundongNum +
                // 添加消耗热量
                ", zanNumber=" + zanNumber +
                // 添加赞的数量
                ", caiNumber=" + caiNumber +
                // 添加踩的数量
                ", yundongContent=" + yundongContent +
                // 添加运动介绍
                ", yundongDelete=" + yundongDelete +
                // 添加逻辑删除标志
                ", insertTime=" + DateUtil.convertString(insertTime, "yyyy-MM-dd") +
                // 格式化录入时间为字符串并添加
                ", createTime=" + DateUtil.convertString(createTime, "yyyy-MM-dd") +
                // 格式化创建时间为字符串并添加
                "}";
        // 结束构建对象的字符串表示并返回
    }
}

