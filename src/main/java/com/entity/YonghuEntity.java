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
 * 用户
 *
 * @author
 * @email
 */
@TableName("yonghu")
// 指定数据库表名为yonghu
public class YonghuEntity<T> implements Serializable {
    // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L;
    // 序列化版本号

    public YonghuEntity() {
        // 默认构造方法
    }

    public YonghuEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
            // 使用BeanUtils复制属性
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 打印异常堆栈信息
        }
    }

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    // 主键自增
    @ColumnInfo(comment="主键",type="int(11)")
    // 字段注释和类型
    @TableField(value = "id")
    // 指定数据库字段名为id

    private Integer id;
    // 主键ID

    /**
     * 账户
     */
    @ColumnInfo(comment="账户",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "username")
    // 指定数据库字段名为username

    private String username;
    // 用户账户

    /**
     * 密码
     */
    @ColumnInfo(comment="密码",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "password")
    // 指定数据库字段名为password

    private String password;
    // 用户密码

    /**
     * 用户姓名
     */
    @ColumnInfo(comment="用户姓名",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "yonghu_name")
    // 指定数据库字段名为yonghu_name

    private String yonghuName;
    // 用户姓名

    /**
     * 性别
     */
    @ColumnInfo(comment="性别",type="int(11)")
    // 字段注释和类型
    @TableField(value = "sex_types")
    // 指定数据库字段名为sex_types

    private Integer sexTypes;
    // 用户性别

    /**
     * 头像
     */
    @ColumnInfo(comment="头像",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "yonghu_photo")
    // 指定数据库字段名为yonghu_photo

    private String yonghuPhoto;
    // 用户头像路径

    /**
     * 身份证号
     */
    @ColumnInfo(comment="身份证号",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "yonghu_id_number")
    // 指定数据库字段名为yonghu_id_number

    private String yonghuIdNumber;
    // 用户身份证号

    /**
     * 联系方式
     */
    @ColumnInfo(comment="联系方式",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "yonghu_phone")
    // 指定数据库字段名为yonghu_phone

    private String yonghuPhone;
    // 用户联系方式

    /**
     * 电子邮箱
     */
    @ColumnInfo(comment="电子邮箱",type="varchar(200)")
    // 字段注释和类型
    @TableField(value = "yonghu_email")
    // 指定数据库字段名为yonghu_email

    private String yonghuEmail;
    // 用户电子邮箱

    /**
     * 逻辑删除
     */
    @ColumnInfo(comment="逻辑删除",type="int(11)")
    // 字段注释和类型
    @TableField(value = "yonghu_delete")
    // 指定数据库字段名为yonghu_delete

    private Integer yonghuDelete;
    // 逻辑删除标志

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    // JSON格式化
    @DateTimeFormat
    // 日期时间格式化
    @ColumnInfo(comment="创建时间",type="timestamp")
    // 字段注释和类型
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    // 指定数据库字段名为create_time，并在插入时自动填充

    private Date createTime;
    // 创建时间

    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
        // 获取主键ID
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
        // 设置主键ID
    }
    /**
	 * 获取：账户
	 */
    public String getUsername() {
        return username;
        // 获取用户账户
    }
    /**
	 * 设置：账户
	 */

    public void setUsername(String username) {
        this.username = username;
        // 设置用户账户
    }
    /**
	 * 获取：密码
	 */
    public String getPassword() {
        return password;
        // 获取用户密码
    }
    /**
	 * 设置：密码
	 */

    public void setPassword(String password) {
        this.password = password;
        // 设置用户密码
    }
    /**
	 * 获取：用户姓名
	 */
    public String getYonghuName() {
        return yonghuName;
        // 获取用户姓名
    }
    /**
	 * 设置：用户姓名
	 */

    public void setYonghuName(String yonghuName) {
        this.yonghuName = yonghuName;
        // 设置用户姓名
    }
    /**
	 * 获取：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
        // 获取用户性别
    }
    /**
	 * 设置：性别
	 */

    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
        // 设置用户性别
    }
    /**
	 * 获取：头像
	 */
    public String getYonghuPhoto() {
        return yonghuPhoto;
        // 获取用户头像路径
    }
    /**
	 * 设置：头像
	 */

    public void setYonghuPhoto(String yonghuPhoto) {
        this.yonghuPhoto = yonghuPhoto;
        // 设置用户头像路径
    }

    /**
	 * 获取：身份证号
	 */
    public String getYonghuIdNumber() {
        return yonghuIdNumber;
        // 获取用户身份证号
    }
    /**
	 * 设置：身份证号
	 */

    public void setYonghuIdNumber(String yonghuIdNumber) {
        this.yonghuIdNumber = yonghuIdNumber;
        // 设置用户身份证号
    }
    /**
	 * 获取：联系方式
	 */
    public String getYonghuPhone() {
        return yonghuPhone;
        // 获取用户联系方式
    }
    /**
	 * 设置：联系方式
	 */

    public void setYonghuPhone(String yonghuPhone) {
        this.yonghuPhone = yonghuPhone;
        // 设置用户联系方式
    }
    /**
	 * 获取：电子邮箱
	 */
    public String getYonghuEmail() {
        return yonghuEmail;
        // 获取用户电子邮箱
    }
    /**
	 * 设置：电子邮箱
	 */

    public void setYonghuEmail(String yonghuEmail) {
        this.yonghuEmail = yonghuEmail;
        // 设置用户电子邮箱
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getYonghuDelete() {
        return yonghuDelete;
        // 获取逻辑删除标志
    }
    /**
	 * 设置：逻辑删除
	 */

    public void setYonghuDelete(Integer yonghuDelete) {
        this.yonghuDelete = yonghuDelete;
        // 设置逻辑删除标志
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
        // 获取创建时间
    }
    /**
	 * 设置：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        // 设置创建时间
    }

        @Override
    public String toString() {
        return "Yonghu{" +
                // 开始构建对象的字符串表示
            ", id=" + id +
                // 添加主键ID
            ", username=" + username +
                // 添加用户账户
            ", password=" + password +
                // 添加用户密码
            ", yonghuName=" + yonghuName +
                // 添加用户姓名
            ", sexTypes=" + sexTypes +
                // 添加用户性别
            ", yonghuPhoto=" + yonghuPhoto +
                // 添加用户头像路径
            ", yonghuIdNumber=" + yonghuIdNumber +
                // 添加用户身份证号
            ", yonghuPhone=" + yonghuPhone +
                // 添加用户联系方式
            ", yonghuEmail=" + yonghuEmail +
                // 添加用户电子邮箱
            ", yonghuDelete=" + yonghuDelete +
                // 添加逻辑删除标志
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") + // 格式化创建时间为字符串并添加
        "}";
        // 结束构建对象的字符串表示并返回
    }

}
