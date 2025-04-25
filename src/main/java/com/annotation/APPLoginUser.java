package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录用户信息
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface APPLoginUser {
/**
 * 自定义注解，用于标识方法参数应被注入当前登录用户的信息。
 * 该注解只能应用于方法参数，并且在运行时保留，以便框架能够识别并注入相应的用户信息。
 *
 * 使用示例：
 * <pre>
 * public void someMethod(@APPLoginUser User loginUser) {
 *     // 使用 loginUser 进行业务处理
 * }
 * </pre>
 */
}
