package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识实体类字段在数据库中的相关信息。
 * 该注解只能应用于字段，并且在运行时保留，以便框架能够识别并处理相应的字段信息。
 *
 * @param comment 字段的注释说明。
 * @param type 字段在数据库中的数据类型。
 *
 * 使用示例：
 * <pre>
 * public class User {
 *     @ColumnInfo(comment = "用户ID", type = "BIGINT")
 *     private Long id;
 *
 *     @ColumnInfo(comment = "用户名", type = "VARCHAR(255)")
 *     private String username;
 * }
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnInfo {
    String comment();
    String type();
}
