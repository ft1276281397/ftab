
package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

/**
 * Mybatis-Plus 配置类，用于配置 Mybatis-Plus 的相关功能。
 * 该类通过 Spring 的 @Configuration 注解标记为配置类，
 * 并通过 @Bean 注解注册相关的组件到 Spring 容器中。
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置分页插件。
     * <p>
     * Mybatis-Plus 提供了 PaginationInterceptor 分页插件，
     * 可以对查询结果进行分页处理，适用于需要分页查询的场景。
     * </p>
     *
     * @return PaginationInterceptor 分页插件实例
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}

