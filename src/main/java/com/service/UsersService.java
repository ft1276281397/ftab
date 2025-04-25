package com.service;

// 导入Java的List接口，用于处理列表数据
import java.util.List;
// 导入Java的Map接口，用于处理键值对数据
import java.util.Map;

// 导入MyBatis-Plus的Wrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.Wrapper;
// 导入MyBatis-Plus的IService接口，提供基本的CRUD操作
import com.baomidou.mybatisplus.service.IService;
// 导入自定义的UsersEntity类，表示系统用户实体
import com.entity.UsersEntity;
// 导入自定义的PageUtils类，用于分页查询
import com.utils.PageUtils;

/**
 * 系统用户服务接口
 * @author yangliyuan
 * @date 2019年10月10日 上午9:18:20
 */
public interface UsersService extends IService<UsersEntity> {
    // 根据参数进行分页查询，并返回分页结果
    PageUtils queryPage(Map<String, Object> params);

    // 根据条件包装器查询用户列表视图
    List<UsersEntity> selectListView(Wrapper<UsersEntity> wrapper);

    // 根据参数和条件包装器进行分页查询，并返回分页结果
    PageUtils queryPage(Map<String, Object> params, Wrapper<UsersEntity> wrapper);
}
