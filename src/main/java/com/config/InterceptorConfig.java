// 声明该类所在的包名为 com.config，包是 Java 中用于组织类和接口的一种机制，有助于避免命名冲突和管理代码结构
package com.config;

// 导入 Spring 框架中的 @Bean 注解，此注解用于将方法返回的对象注册为 Spring 容器中的 Bean
import org.springframework.context.annotation.Bean;
// 导入 Spring 框架中的 @Configuration 注解，用于标记一个类为配置类，Spring 会对该类进行解析以获取 Bean 定义等信息
import org.springframework.context.annotation.Configuration;
// 导入 Spring MVC 中用于配置拦截器注册的 InterceptorRegistry 类，借助它能对拦截器进行注册和配置
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// 导入 Spring MVC 中用于配置静态资源处理器的 ResourceHandlerRegistry 类，可利用它来配置静态资源的访问路径
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// 导入 Spring MVC 中支持 Web MVC 配置的基类 WebMvcConfigurationSupport，通过继承它可对 Web MVC 进行自定义配置
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// 导入自定义的授权拦截器类，该类负责处理授权相关的拦截逻辑
import com.interceptor.AuthorizationInterceptor;

/**
 * 配置拦截器和静态资源处理的类。
 * 该配置类继承自 {@link WebMvcConfigurationSupport}，用于注册自定义拦截器和静态资源处理。
 *
 * @author [你的名字]
 */
// 使用 @Configuration 注解，表明这个类是一个 Spring 配置类，Spring 会扫描该类并处理其中的配置信息
@Configuration
// 定义一个名为 InterceptorConfig 的类，继承自 WebMvcConfigurationSupport，目的是对 Web MVC 相关的配置进行定制
public class InterceptorConfig extends WebMvcConfigurationSupport {

    /**
     * 创建并返回一个 {@link AuthorizationInterceptor} 实例。
     *
     * @return {@link AuthorizationInterceptor} 实例
     */
    // 使用 @Bean 注解，把这个方法返回的 AuthorizationInterceptor 实例注册为 Spring 容器中的 Bean
    @Bean
    // 定义一个名为 getAuthorizationInterceptor 的公共方法，返回类型为 AuthorizationInterceptor
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        // 创建 AuthorizationInterceptor 类的一个新实例并返回
        return new AuthorizationInterceptor();
    }

    /**
     * 注册拦截器。
     * 将 {@link AuthorizationInterceptor} 拦截器添加到所有路径（"/**"），并排除静态资源路径（"/static/**"）。
     *
     * @param registry 拦截器注册表
     */
    // 重写父类 WebMvcConfigurationSupport 中的 addInterceptors 方法，用于注册拦截器
    @Override
    // 定义 addInterceptors 方法，接收一个 InterceptorRegistry 类型的参数 registry
    public void addInterceptors(InterceptorRegistry registry) {
        // 调用 registry 的 addInterceptor 方法，将之前创建的 AuthorizationInterceptor Bean 添加到拦截器链中
        registry.addInterceptor(getAuthorizationInterceptor())
                // 设置拦截的路径模式为 "/**"，意味着拦截所有请求
                .addPathPatterns("/**")
                // 设置排除的路径模式为 "/static/**"，即静态资源请求不会被该拦截器拦截
                .excludePathPatterns("/static/**");
        // 调用父类的 addInterceptors 方法，确保父类中的拦截器配置也能生效
        super.addInterceptors(registry);
    }

    /**
     * 配置静态资源处理。
     * 由于继承了 {@link WebMvcConfigurationSupport}，默认的静态资源处理会被覆盖，因此需要重写此方法来配置静态资源路径。
     *
     * @param registry 静态资源注册表
     */
    // 重写父类的 addResourceHandlers 方法，用于配置静态资源的处理
    @Override
    // 定义 addResourceHandlers 方法，接收一个 ResourceHandlerRegistry 类型的参数 registry
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 调用 registry 的 addResourceHandler 方法，设置要处理的资源路径模式为 "/**"，表示处理所有资源请求
        registry.addResourceHandler("/**")
                // 添加资源位置，这里表示从类路径下的 resources 目录查找静态资源
                .addResourceLocations("classpath:/resources/")
                // 添加资源位置，从类路径下的 static 目录查找静态资源
                .addResourceLocations("classpath:/static/")
                // 添加资源位置，从类路径下的 admin 目录查找静态资源
                .addResourceLocations("classpath:/admin/")
                // 添加资源位置，从类路径下的 img 目录查找静态资源
                .addResourceLocations("classpath:/img/")
                // 添加资源位置，从类路径下的 front 目录查找静态资源
                .addResourceLocations("classpath:/front/")
                // 添加资源位置，从类路径下的 public 目录查找静态资源
                .addResourceLocations("classpath:/public/");
        // 调用父类的 addResourceHandlers 方法，保证父类中的静态资源配置依然有效
        super.addResourceHandlers(registry);
    }
}