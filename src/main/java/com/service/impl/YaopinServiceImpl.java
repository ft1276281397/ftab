package com.service.impl;

// 导入StringUtil工具类，用于字符串操作
import com.utils.StringUtil;
// 导入DictionaryService接口，用于字典服务
import com.service.DictionaryService;
// 导入ClazzDiff工具类，用于类差异比较
import com.utils.ClazzDiff;
// 导入Spring的BeanUtils工具类，用于对象属性复制
import org.springframework.beans.BeanUtils;
// 注解，用于自动装配DictionaryService
import org.springframework.beans.factory.annotation.Autowired;
// 注解，用于标识这是一个服务类
import org.springframework.stereotype.Service;
// 导入Java反射Field类，用于操作类的字段
import java.lang.reflect.Field;
// 导入Java集合框架中的Map接口，用于存储键值对
import java.util.*;
// 导入MyBatis-Plus的Page类，用于分页查询
import com.baomidou.mybatisplus.plugins.Page;
// 导入MyBatis-Plus的服务实现基类
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
// 注解，用于声明事务管理
import org.springframework.transaction.annotation.Transactional;
// 导入PageUtils工具类，用于分页结果封装
import com.utils.PageUtils;
// 导入Query工具类，用于构建分页查询条件
import com.utils.Query;
// 导入ContextLoader类，用于获取ServletContext
import org.springframework.web.context.ContextLoader;
// 导入ServletContext类，用于获取Servlet上下文
import javax.servlet.ServletContext;
// 导入HttpServletRequest类，用于处理HTTP请求
import javax.servlet.http.HttpServletRequest;
// 注解，用于表示方法参数可以为null
import org.springframework.lang.Nullable;
// 导入Assert类，用于断言操作
import org.springframework.util.Assert;
// 导入YaopinDao接口，用于数据库操作
import com.dao.YaopinDao;
// 导入YaopinEntity实体类，用于映射药品信息表
import com.entity.YaopinEntity;
// 导入YaopinService接口，用于定义药品信息服务
import com.service.YaopinService;
// 导入YaopinView视图类，用于药品信息视图展示
import com.entity.view.YaopinView;

/**
 * 药品信息 服务实现类
 */
@Service("yaopinService")
// 注解，用于声明事务管理
@Transactional
// 继承ServiceImpl基类，实现YaopinService接口
public class YaopinServiceImpl extends ServiceImpl<YaopinDao, YaopinEntity> implements YaopinService {

    /**
     * 根据分页参数查询药品信息列表视图
     *
     * 该方法用于根据传入的分页参数查询药品信息列表视图，并返回分页结果
     *
     * @param params 分页参数，包含分页信息
     * @return 分页结果封装对象
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 创建Page对象，用于分页查询
        Page<YaopinView> page = new Query<YaopinView>(params).getPage();
        // 设置分页查询结果
        page.setRecords(baseMapper.selectListView(page, params));
        // 返回封装的分页结果
        return new PageUtils(page);
    }

}
