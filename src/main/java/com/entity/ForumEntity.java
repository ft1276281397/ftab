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
 * 论坛实体类
 *
 * @author
 * @email
 */
@TableName("forum")
// 指定数据库表名为forum
public class ForumEntity<T> implements Serializable {
    // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L;
    // 序列化版本号

    // 无参构造函数
    public ForumEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public ForumEntity(T t) {
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
    @ColumnInfo(comment = "主键", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "id")
    // 指定数据库字段名为id

    private Integer id;

    /**
     * 帖子标题
     */
    @ColumnInfo(comment = "帖子标题", type = "varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "forum_name")
    // 指定数据库字段名为forum_name

    private String forumName;

    /**
     * 用户
     */
    @ColumnInfo(comment = "用户", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "yonghu_id")
    // 指定数据库字段名为yonghu_id

    private Integer yonghuId;

    /**
     * 管理员
     */
    @ColumnInfo(comment = "管理员", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "users_id")
    // 指定数据库字段名为users_id

    private Integer usersId;

    /**
     * 发布内容
     */
    @ColumnInfo(comment = "发布内容", type = "longtext")
    // 自定义注解，描述字段信息
    @TableField(value = "forum_content")
    // 指定数据库字段名为forum_content

    private String forumContent;

    /**
     * 父id
     */
    @ColumnInfo(comment = "父id", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "super_ids")
    // 指定数据库字段名为super_ids

    private Integer superIds;

    /**
     * 帖子状态
     */
    @ColumnInfo(comment = "帖子状态", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "forum_state_types")
    // 指定数据库字段名为forum_state_types

    private Integer forumStateTypes;

    /**
     * 发帖时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    // JSON日期格式化
    @DateTimeFormat
    // 日期格式化注解
    @ColumnInfo(comment = "发帖时间", type = "timestamp")
    // 自定义注解，描述字段信息
    @TableField(value = "insert_time", fill = FieldFill.INSERT)
    // 指定数据库字段名为insert_time，并在插入时自动填充

    private Date insertTime;

    /**
     * 修改时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    // JSON日期格式化
    @DateTimeFormat
    // 日期格式化注解
    @ColumnInfo(comment = "修改时间", type = "timestamp")
    // 自定义注解，描述字段信息
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    // 指定数据库字段名为update_time，并在更新时自动填充

    private Date updateTime;

    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat
    // 日期格式化注解
    @ColumnInfo(comment = "创建时间", type = "timestamp")
    // 自定义注解，描述字段信息
    @TableField(value = "create_time", fill = FieldFill.INSERT)
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
     * 获取：帖子标题
     */
    public String getForumName() {
        return forumName;
    }

    /**
     * 设置：帖子标题
     */

    public void setForumName(String forumName) {
        this.forumName = forumName;
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
     * 获取：管理员
     */
    public Integer getUsersId() {
        return usersId;
    }

    /**
     * 设置：管理员
     */

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    /**
     * 获取：发布内容
     */
    public String getForumContent() {
        return forumContent;
    }

    /**
     * 设置：发布内容
     */

    public void setForumContent(String forumContent) {
        this.forumContent = forumContent;
    }

    /**
     * 获取：父id
     */
    public Integer getSuperIds() {
        return superIds;
    }

    /**
     * 设置：父id
     */

    public void setSuperIds(Integer superIds) {
        this.superIds = superIds;
    }

    /**
     * 获取：帖子状态
     */
    public Integer getForumStateTypes() {
        return forumStateTypes;
    }

    /**
     * 设置：帖子状态
     */

    public void setForumStateTypes(Integer forumStateTypes) {
        this.forumStateTypes = forumStateTypes;
    }

    /**
     * 获取：发帖时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * 设置：发帖时间
     */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * 获取：修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置：修改时间
     */

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    // 重写Object类的toString方法，用于返回对象的字符串表示
    public String toString() {
        // 返回一个包含对象属性的字符串
        return "Forum{" +
                // 添加主键id
                ", id=" + id +
                // 添加帖子标题forumName
                ", forumName=" + forumName +
                // 添加用户yonghuId
                ", yonghuId=" + yonghuId +
                // 添加管理员usersId
                ", usersId=" + usersId +
                // 添加发布内容forumContent
                ", forumContent=" + forumContent +
                // 添加父id superIds
                ", superIds=" + superIds +
                // 添加帖子状态forumStateTypes
                ", forumStateTypes=" + forumStateTypes +
                // 添加发帖时间insertTime，并使用DateUtil格式化日期
                ", insertTime=" + DateUtil.convertString(insertTime, "yyyy-MM-dd") +
                // 添加修改时间updateTime，并使用DateUtil格式化日期
                ", updateTime=" + DateUtil.convertString(updateTime, "yyyy-MM-dd") +
                // 添加创建时间createTime，并使用DateUtil格式化日期
                ", createTime=" + DateUtil.convertString(createTime, "yyyy-MM-dd") +
                "}";
    }
}

