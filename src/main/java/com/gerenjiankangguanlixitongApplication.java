package com;

// 导入MapperScan注解，用于扫描MyBatis的Mapper接口
import org.mybatis.spring.annotation.MapperScan;
// 导入SpringApplication类，用于启动Spring Boot应用
import org.springframework.boot.SpringApplication;
// 导入SpringBootApplication注解，用于标识Spring Boot应用
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 导入SpringApplicationBuilder类，用于构建Spring Boot应用
import org.springframework.boot.builder.SpringApplicationBuilder;
// 导入ServletComponentScan注解，用于扫描Servlet组件
import org.springframework.boot.web.servlet.ServletComponentScan;
// 导入SpringBootServletInitializer类，用于支持传统的WAR部署
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring Boot应用启动类
 */
@SpringBootApplication
// 扫描Servlet组件，指定包路径为"com.ServletContextListener"
@ServletComponentScan(value = "com.ServletContextListener")
// 扫描MyBatis的Mapper接口，指定包路径为"com.dao"
@MapperScan(basePackages = {"com.dao"})
public class gerenjiankangguanlixitongApplication extends SpringBootServletInitializer {

    /**
     * 应用的主方法，用于启动Spring Boot应用
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(gerenjiankangguanlixitongApplication.class, args);
    }

    /**
     * 配置SpringApplicationBuilder，用于支持传统的WAR部署
     *
     * @param applicationBuilder SpringApplicationBuilder对象
     * @return 配置后的SpringApplicationBuilder对象
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        // 设置应用的源类
        return applicationBuilder.sources(gerenjiankangguanlixitongApplication.class);
    }
}
