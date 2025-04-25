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
 * 字典实体类
 *
 * @author
 * @email
 */
@TableName("dictionary")
// 指定数据库表名为dictionary
public class DictionaryEntity<T> implements Serializable {
    // 实现Serializable接口以支持序列化
    private static final long serialVersionUID = 1L;
    // 序列化版本号

    // 无参构造函数
    public DictionaryEntity() {

    }

    // 带参数的构造函数，用于从其他对象复制属性
    public DictionaryEntity(T t) {
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
    @ColumnInfo(comment = "主键", type = "bigint(20)")
    // 自定义注解，描述字段信息
    @TableField(value = "id")
    // 指定数据库字段名为id

    private Long id;

    /**
     * 字段
     */
    @ColumnInfo(comment = "字段", type = "varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "dic_code")
    // 指定数据库字段名为dic_code

    private String dicCode;

    /**
     * 字段名
     */
    @ColumnInfo(comment = "字段名", type = "varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "dic_name")
    // 指定数据库字段名为dic_name

    private String dicName;

    /**
     * 编码
     */
    @ColumnInfo(comment = "编码", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "code_index")
    // 指定数据库字段名为code_index

    private Integer codeIndex;

    /**
     * 编码名字
     */
    @ColumnInfo(comment = "编码名字", type = "varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "index_name")
    // 指定数据库字段名为index_name

    private String indexName;

    /**
     * 父字段id
     */
    @ColumnInfo(comment = "父字段id", type = "int(11)")
    // 自定义注解，描述字段信息
    @TableField(value = "super_id")
    // 指定数据库字段名为super_id

    private Integer superId;

    /**
     * 备注
     */
    @ColumnInfo(comment = "备注", type = "varchar(200)")
    // 自定义注解，描述字段信息
    @TableField(value = "beizhu")
    // 指定数据库字段名为beizhu

    private String beizhu;

    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // JSON日期格式化
    @DateTimeFormat // 日期格式化注解
    @ColumnInfo(comment = "创建时间", type = "timestamp")
    // 自定义注解，描述字段信息
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    // 指定数据库字段名为create_time，并在插入时自动填充

    private Date createTime;

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：字段
     */
    public String getDicCode() {
        return dicCode;
    }

    /**
     * 设置：字段
     */

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    /**
     * 获取：字段名
     */
    public String getDicName() {
        return dicName;
    }

    /**
     * 设置：字段名
     */

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    /**
     * 获取：编码
     */
    public Integer getCodeIndex() {
        return codeIndex;
    }

    /**
     * 设置：编码
     */

    public void setCodeIndex(Integer codeIndex) {
        this.codeIndex = codeIndex;
    }

    /**
     * 获取：编码名字
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * 设置：编码名字
     */

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * 获取：父字段id
     */
    public Integer getSuperId() {
        return superId;
    }

    /**
     * 设置：父字段id
     */

    public void setSuperId(Integer superId) {
        this.superId = superId;
    }

    /**
     * 获取：备注
     */
    public String getBeizhu() {
        return beizhu;
    }

    /**
     * 设置：备注
     */

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
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

    // 重写toString方法，便于调试和日志输出
    @Override
    // 重写Object类的toString方法，用于返回对象的字符串表示
    public String toString() {
        // 返回一个包含对象属性的字符串
        return "Dictionary{" +
                // 添加主键id
                ", id=" + id +
                // 添加字段dicCode
                ", dicCode=" + dicCode +
                // 添加字段名dicName
                ", dicName=" + dicName +
                // 添加编码codeIndex
                ", codeIndex=" + codeIndex +
                // 添加编码名字indexName
                ", indexName=" + indexName +
                // 添加父字段id superId
                ", superId=" + superId +
                // 添加备注beizhu
                ", beizhu=" + beizhu +
                // 添加创建时间createTime，并使用DateUtil格式化日期
                ", createTime=" + DateUtil.convertString(createTime, "yyyy-MM-dd") +
                "}";
    }
}
