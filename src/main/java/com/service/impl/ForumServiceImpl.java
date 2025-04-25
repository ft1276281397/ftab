package com.service.impl;

import com.utils.StringUtil;
import com.service.DictionaryService;
import com.utils.ClazzDiff;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import com.dao.ForumDao;
import com.entity.ForumEntity;
import com.service.ForumService;
import com.entity.view.ForumView;

/**
 * 论坛 服务实现类
 */
@Service("forumService")
// 将类声明为Spring服务组件，名称为forumService
@Transactional // 声明事务管理
public class ForumServiceImpl extends ServiceImpl<ForumDao, ForumEntity> implements ForumService {
    // 继承ServiceImpl并实现ForumService接口

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 实现分页查询方法
        Page<ForumView> page = new Query<ForumView>(params).getPage();
        // 使用Query工具类获取分页对象
        page.setRecords(baseMapper.selectListView(page, params));
        // 调用baseMapper的selectListView方法获取分页数据
        return new PageUtils(page);
        // 返回PageUtils对象，包含分页数据
    }
}
