package com.ServletContextListener;

// 导入MyBatis-Plus的EntityWrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入DictionaryEntity实体类，用于映射字典表
import com.entity.DictionaryEntity;
// 导入DictionaryService接口，用于定义字典服务
import com.service.DictionaryService;
// 导入MyThreadMethod类，用于自定义线程方法
import com.thread.MyThreadMethod;
// 导入SLF4J的日志记录器
import org.slf4j.Logger;
// 导入SLF4J的日志记录器工厂
import org.slf4j.LoggerFactory;
// 导入Spring的ApplicationContext接口，用于获取Spring上下文
import org.springframework.context.ApplicationContext;
// 导入Spring的WebApplicationContextUtils类，用于从ServletContext获取Spring上下文
import org.springframework.web.context.support.WebApplicationContextUtils;

// 导入Servlet的ServletContextListener接口，用于监听Servlet上下文事件
import javax.servlet.ServletContextListener;
// 导入Servlet的ServletContextEvent类，用于获取Servlet上下文事件信息
import javax.servlet.ServletContextEvent;
// 注解，用于声明这是一个Servlet监听器
import javax.servlet.annotation.WebListener;
// 导入Java的HashMap类，用于存储键值对
import java.util.HashMap;
// 导入Java的List类，用于存储列表数据
import java.util.List;
// 导入Java的Map接口，用于存储键值对
import java.util.Map;

/**
 * 字典初始化监视器  用的是服务器监听,每次项目启动,都会调用这个类
 */
@WebListener
// 实现ServletContextListener接口，用于监听Servlet上下文事件
public class DictionaryServletContextListener implements ServletContextListener {

    // 创建日志记录器实例
    private static final Logger logger = LoggerFactory.getLogger(DictionaryServletContextListener.class);
    // 声明MyThreadMethod实例
    private MyThreadMethod myThreadMethod;

    /**
     * 服务器停止时调用的方法
     *
     * @param sce Servlet上下文事件对象
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 记录服务器停止日志
        logger.info("----------服务器停止----------");
    }

    /**
     * 服务器启动时调用的方法
     *
     * @param sce Servlet上下文事件对象
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取Spring上下文
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

        // 记录字典表初始化开始日志
        logger.info("----------字典表初始化开始----------");
        // 获取DictionaryService实例
        DictionaryService dictionaryService = (DictionaryService) appContext.getBean("dictionaryService");
        // 查询所有字典实体
        List<DictionaryEntity> dictionaryEntities = dictionaryService.selectList(new EntityWrapper<DictionaryEntity>());
        // 创建字典映射
        Map<String, Map<Integer, String>> map = new HashMap<>();
        // 遍历字典实体列表
        for (DictionaryEntity d : dictionaryEntities) {
            // 获取当前字典代码对应的映射
            Map<Integer, String> m = map.get(d.getDicCode());
            // 如果映射为空，则创建新的映射
            if (m == null || m.isEmpty()) {
                m = new HashMap<>();
            }
            // 将字典索引和名称添加到映射中
            m.put(d.getCodeIndex(), d.getIndexName());
            // 将映射添加到字典映射中
            map.put(d.getDicCode(), m);
        }
        // 将字典映射设置到ServletContext中
        sce.getServletContext().setAttribute("dictionaryMap", map);
        // 记录字典表初始化完成日志
        logger.info("----------字典表初始化完成----------");

        // 记录线程执行开始日志
        logger.info("----------线程执行开始----------");
        // 如果MyThreadMethod实例为空，则创建并启动线程
        if (myThreadMethod == null) {
            myThreadMethod = new MyThreadMethod();
            myThreadMethod.start(); // servlet 上下文初始化时启动线程myThreadMethod
        }
        // 记录线程执行结束日志
        logger.info("----------线程执行结束----------");
    }
}
