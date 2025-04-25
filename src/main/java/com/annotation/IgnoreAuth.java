package com.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于标识方法应忽略Token验证。
 * 该注解只能应用于方法，并且在运行时保留，以便框架能够识别并跳过相应的Token验证。
 *
 * 使用示例：
 * <pre>
 * @IgnoreAuth
 * public void someMethod() {
 *     // 无需Token验证的方法逻辑
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {

}
