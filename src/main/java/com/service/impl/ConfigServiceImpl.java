package com.service.impl;

// 导入Java映射类，用于存储键值对
import java.util.Map;

// 注解，用于标识这是一个服务类
import org.springframework.stereotype.Service;

// 导入MyBatis-Plus的EntityWrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入MyBatis-Plus的Page类，用于分页查询
import com.baomidou.mybatisplus.plugins.Page;
// 导入MyBatis-Plus的服务实现基类
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
// 导入ConfigDao接口，用于数据库操作
import com.dao.ConfigDao;
// 导入ConfigEntity实体类，用于映射配置表
import com.entity.ConfigEntity;
// 导入ConfigService接口，用于定义配置服务
import com.service.ConfigService;
// 导入PageUtils工具类，用于分页结果封装
import com.utils.PageUtils;
// 导入Query工具类，用于构建分页查询条件
import com.utils.Query;

/**
 * 系统用户
 * @author yangliyuan
 * @date 2019年10月10日 上午9:17:59
 */
@Service("configService")
// 将类声明为Spring服务组件，名称为configService
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigEntity> implements ConfigService { // 继承ServiceImpl并实现ConfigService接口

    /**
     * 根据分页参数查询配置信息列表
     *
     * 该方法用于根据传入的分页参数查询配置信息列表，并返回分页结果
     *
     * @param params 分页参数，包含分页信息和其他查询条件
     * @return 分页结果封装对象
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 实现分页查询方法
        Page<ConfigEntity> page = this.selectPage(
                // 调用selectPage方法进行分页查询
                new Query<ConfigEntity>(params).getPage(),
                // 使用Query工具类获取分页对象
                new EntityWrapper<ConfigEntity>()
                // 使用EntityWrapper进行条件封装
        );
        // 返回PageUtils对象，包含分页数据
        return new PageUtils(page);
    }
}
