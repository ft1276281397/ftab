package com.service.impl;

// 导入Java列表类，用于存储列表数据
import java.util.List;
// 导入Java映射类，用于存储键值对
import java.util.Map;

// 导入UsersService接口，用于定义用户服务
import com.service.UsersService;
// 注解，用于标识这是一个服务类
import org.springframework.stereotype.Service;

// 导入MyBatis-Plus的EntityWrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入MyBatis-Plus的Wrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.Wrapper;
// 导入MyBatis-Plus的Page类，用于分页查询
import com.baomidou.mybatisplus.plugins.Page;
// 导入MyBatis-Plus的服务实现基类
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
// 导入UsersDao接口，用于数据库操作
import com.dao.UsersDao;
// 导入UsersEntity实体类，用于映射用户表
import com.entity.UsersEntity;
// 导入PageUtils工具类，用于分页结果封装
import com.utils.PageUtils;
// 导入Query工具类，用于构建分页查询条件
import com.utils.Query;

/**
 * 系统用户
 * @author
 */
@Service("userService")
// 继承ServiceImpl基类，实现UsersService接口
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    /**
     * 根据分页参数查询UsersEntity列表
     *
     * 该方法用于根据传入的分页参数查询UsersEntity列表，并返回分页结果
     *
     * @param params 分页参数，包含分页信息
     * @return 分页结果封装对象
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 创建Page对象，用于分页查询
        Page<UsersEntity> page = this.selectPage(
                new Query<UsersEntity>(params).getPage(),
                new EntityWrapper<UsersEntity>()
        );
        // 返回封装的分页结果
        return new PageUtils(page);
    }

    /**
     * 根据条件查询UsersEntity列表视图
     *
     * 该方法用于根据传入的条件封装对象查询UsersEntity列表视图
     *
     * @param wrapper 条件封装对象，用于指定查询条件
     * @return 满足条件的UsersEntity列表
     */
    @Override
    public List<UsersEntity> selectListView(Wrapper<UsersEntity> wrapper) {
        // 返回查询结果
        return baseMapper.selectListView(wrapper);
    }

    /**
     * 根据分页参数和条件查询UsersEntity列表
     *
     * 该方法用于根据传入的分页参数和条件封装对象查询UsersEntity列表，并返回分页结果
     *
     * @param params 分页参数，包含分页信息
     * @param wrapper 条件封装对象，用于指定查询条件
     * @return 分页结果封装对象
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<UsersEntity> wrapper) {
        // 创建Page对象，用于分页查询
        Page<UsersEntity> page = new Query<UsersEntity>(params).getPage();
        // 设置分页查询结果
        page.setRecords(baseMapper.selectListView(page, wrapper));
        // 返回封装的分页结果
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }
}
