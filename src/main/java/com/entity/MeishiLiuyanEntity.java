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
 * 健康食谱留言实体类
 *
 * @author
 * @email
 */
@TableName("meishi_liuyan") // 指定数据库表名为meishi_liuyan
public class MeishiLiuyanEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    // 无参构造函数
    public MeishiLiuyanEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public MeishiLiuyanEntity(T t) {
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
     * 健康食谱
     */
    @ColumnInfo(comment="健康食谱",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "meishi_id") // 指定数据库字段名为meishi_id

    private Integer meishiId;

    /**
     * 用户
     */
    @ColumnInfo(comment="用户",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "yonghu_id") // 指定数据库字段名为yonghu_id

    private Integer yonghuId;

    /**
     * 留言内容
     */
    @ColumnInfo(comment="留言内容",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "meishi_liuyan_text") // 指定数据库字段名为meishi_liuyan_text

    private String meishiLiuyanText;

    /**
     * 留言时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="留言时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "insert_time",fill = FieldFill.INSERT) // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime;

    /**
     * 回复内容
     */
    @ColumnInfo(comment="回复内容",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "reply_text") // 指定数据库字段名为reply_text

    private String replyText;

    /**
     * 回复时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="回复时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "update_time",fill = FieldFill.UPDATE) // 指定数据库字段名为update_time，并在更新时自动填充

    private Date updateTime;

    /**
     * 创建时间  listShow
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
     * 获取：健康食谱
     */
    public Integer getMeishiId() {
        return meishiId;
    }
    /**
     * 设置：健康食谱
     */

    public void setMeishiId(Integer meishiId) {
        this.meishiId = meishiId;
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
     * 获取：留言内容
     */
    public String getMeishiLiuyanText() {
        return meishiLiuyanText;
    }
    /**
     * 设置：留言内容
     */

    public void setMeishiLiuyanText(String meishiLiuyanText) {
        this.meishiLiuyanText = meishiLiuyanText;
    }
    /**
     * 获取：留言时间
     */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
     * 设置：留言时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
     * 获取：回复内容
     */
    public String getReplyText() {
        return replyText;
    }
    /**
     * 设置：回复内容
     */

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
    /**
     * 获取：回复时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    /**
     * 设置：回复时间
     */

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * 获取：创建时间  listShow
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 设置：创建时间  listShow
     */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    // 重写toString方法，便于调试和日志输出
    public String toString() {
        return "MeishiLiuyan{" +
            // 添加主键id
            ", id=" + id +
            // 添加健康食谱meishiId
            ", meishiId=" + meishiId +
            // 添加用户yonghuId
            ", yonghuId=" + yonghuId +
            // 添加留言内容meishiLiuyanText
            ", meishiLiuyanText=" + meishiLiuyanText +
            // 添加留言时间insertTime，并使用DateUtil格式化日期
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            // 添加回复内容replyText
            ", replyText=" + replyText +
            // 添加回复时间updateTime，并使用DateUtil格式化日期
            ", updateTime=" + DateUtil.convertString(updateTime,"yyyy-MM-dd") +
            // 添加创建时间createTime，并使用DateUtil格式化日期
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
