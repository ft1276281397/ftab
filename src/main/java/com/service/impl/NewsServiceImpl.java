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
import javax.servlet.ServletContext;
// 导入HttpServletRequest类，用于处理HTTP请求
import javax.servlet.http.HttpServletRequest;
// 注解，用于表示方法参数可以为null
import org.springframework.lang.Nullable;
// 导入Assert类，用于断言操作
import org.springframework.util.Assert;
// 导入NewsDao接口，用于数据库操作
import com.dao.NewsDao;
// 导入NewsEntity实体类，用于映射公告信息表
import com.entity.NewsEntity;
// 导入NewsService接口，用于定义公告信息服务
import com.service.NewsService;
// 导入NewsView视图类，用于公告信息视图展示
import com.entity.view.NewsView;

/**
 * 公告信息 服务实现类
 */
@Service("newsService")
// 注解，用于声明事务管理
@Transactional
// 继承ServiceImpl基类，实现NewsService接口
public class NewsServiceImpl extends ServiceImpl<NewsDao, NewsEntity> implements NewsService {

    // 实现queryPage方法，用于分页查询公告信息
    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        // 创建Page对象，用于分页查询
        Page<NewsView> page = new Query<NewsView>(params).getPage();
        // 设置分页查询结果
        page.setRecords(baseMapper.selectListView(page, params));
        // 返回封装的分页结果
        return new PageUtils(page);
    }

}
