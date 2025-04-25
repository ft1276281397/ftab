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
 * 每日计划实体类
 *
 * @author
 * @email
 */
@TableName("meirijihua") // 指定数据库表名为meirijihua
public class MeirijihuaEntity<T> implements Serializable { // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L; // 序列化版本号

    // 无参构造函数
    public MeirijihuaEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public MeirijihuaEntity(T t) {
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
     * 计划标题
     */
    @ColumnInfo(comment="计划标题",type="varchar(200)") // 自定义注解，描述字段信息
    @TableField(value = "meirijihua_name") // 指定数据库字段名为meirijihua_name

    private String meirijihuaName;

    /**
     * 饮食计划
     */
    @ColumnInfo(comment="饮食计划",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "yinshi_text") // 指定数据库字段名为yinshi_text

    private String yinshiText;

    /**
     * 运动计划
     */
    @ColumnInfo(comment="运动计划",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "yundong_text") // 指定数据库字段名为yundong_text

    private String yundongText;

    /**
     * 药品计划
     */
    @ColumnInfo(comment="药品计划",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "yaopin_text") // 指定数据库字段名为yaopin_text

    private String yaopinText;

    /**
     * 备注
     */
    @ColumnInfo(comment="备注",type="longtext") // 自定义注解，描述字段信息
    @TableField(value = "meirijihua_content") // 指定数据库字段名为meirijihua_content

    private String meirijihuaContent;

    /**
     * 计划时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment="计划时间",type="timestamp") // 自定义注解，描述字段信息
    @TableField(value = "meirijihua_time") // 指定数据库字段名为meirijihua_time

    private Date meirijihuaTime;

    /**
     * 逻辑删除
     */
    @ColumnInfo(comment="逻辑删除",type="int(11)") // 自定义注解，描述字段信息
    @TableField(value = "meirijihua_delete") // 指定数据库字段名为meirijihua_delete

    private Integer meirijihuaDelete;

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
     * 获取：计划标题
     */
    public String getMeirijihuaName() {
        return meirijihuaName;
    }
    /**
     * 设置：计划标题
     */

    public void setMeirijihuaName(String meirijihuaName) {
        this.meirijihuaName = meirijihuaName;
    }
    /**
     * 获取：饮食计划
     */
    public String getYinshiText() {
        return yinshiText;
    }
    /**
     * 设置：饮食计划
     */

    public void setYinshiText(String yinshiText) {
        this.yinshiText = yinshiText;
    }
    /**
     * 获取：运动计划
     */
    public String getYundongText() {
        return yundongText;
    }
    /**
     * 设置：运动计划
     */

    public void setYundongText(String yundongText) {
        this.yundongText = yundongText;
    }
    /**
     * 获取：药品计划
     */
    public String getYaopinText() {
        return yaopinText;
    }
    /**
     * 设置：药品计划
     */

    public void setYaopinText(String yaopinText) {
        this.yaopinText = yaopinText;
    }
    /**
     * 获取：备注
     */
    public String getMeirijihuaContent() {
        return meirijihuaContent;
    }
    /**
     * 设置：备注
     */

    public void setMeirijihuaContent(String meirijihuaContent) {
        this.meirijihuaContent = meirijihuaContent;
    }
    /**
     * 获取：计划时间
     */
    public Date getMeirijihuaTime() {
        return meirijihuaTime;
    }
    /**
     * 设置：计划时间
     */

    public void setMeirijihuaTime(Date meirijihuaTime) {
        this.meirijihuaTime = meirijihuaTime;
    }
    /**
     * 获取：逻辑删除
     */
    public Integer getMeirijihuaDelete() {
        return meirijihuaDelete;
    }
    /**
     * 设置：逻辑删除
     */

    public void setMeirijihuaDelete(Integer meirijihuaDelete) {
        this.meirijihuaDelete = meirijihuaDelete;
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
        return "Meirijihua{" +
            // 添加主键id
            ", id=" + id +
            // 添加用户yonghuId
            ", yonghuId=" + yonghuId +
            // 添加计划标题meirijihuaName
            ", meirijihuaName=" + meirijihuaName +
            // 添加饮食计划yinshiText
            ", yinshiText=" + yinshiText +
            // 添加运动计划yundongText
            ", yundongText=" + yundongText +
            // 添加药品计划yaopinText
            ", yaopinText=" + yaopinText +
            // 添加备注meirijihuaContent
            ", meirijihuaContent=" + meirijihuaContent +
            // 添加计划时间meirijihuaTime，并使用DateUtil格式化日期
            ", meirijihuaTime=" + DateUtil.convertString(meirijihuaTime,"yyyy-MM-dd") +
            // 添加逻辑删除meirijihuaDelete
            ", meirijihuaDelete=" + meirijihuaDelete +
            // 添加添加时间insertTime，并使用DateUtil格式化日期
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            // 添加创建时间createTime，并使用DateUtil格式化日期
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
