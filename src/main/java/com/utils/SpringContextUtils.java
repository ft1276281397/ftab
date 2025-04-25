package com.utils;

// 导入BeansException类，用于处理Bean异常
import org.springframework.beans.BeansException;
// 导入ApplicationContext接口，用于Spring应用上下文
import org.springframework.context.ApplicationContext;
// 导入ApplicationContextAware接口，用于感知Spring应用上下文
import org.springframework.context.ApplicationContextAware;
// 导入Component注解，用于将类声明为Spring组件
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    // 静态变量，用于存储Spring应用上下文
    public static ApplicationContext applicationContext;

    /**
     * 设置Spring应用上下文
     *
     * @param applicationContext Spring应用上下文
     * @throws BeansException 如果设置上下文时发生异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 根据名称获取Bean对象
     *
     * @param name Bean的名称
     * @return Bean对象
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据名称和类型获取Bean对象
     *
     * @param name         Bean的名称
     * @param requiredType Bean的类型
     * @param <T>          Bean的泛型类型
     * @return 指定类型的Bean对象
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 检查是否包含指定名称的Bean
     *
     * @param name Bean的名称
     * @return 如果包含指定名称的Bean，返回true；否则返回false
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 检查指定名称的Bean是否为单例
     *
     * @param name Bean的名称
     * @return 如果指定名称的Bean为单例，返回true；否则返回false
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    /**
     * 获取指定名称的Bean的类型
     *
     * @param name Bean的名称
     * @return Bean的类型
     */
    public static Class<? extends Object> getType(String name) {
        return applicationContext.getType(name);
    }
}
