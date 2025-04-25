package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 论坛
 * 后端接口
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/forum")
public class ForumController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ForumController.class);

    // 定义表名常量
    private static final String TABLE_NAME = "forum";

    // 自动注入ForumService
    @Autowired
    private ForumService forumService;

    // 自动注入TokenService
    @Autowired
    private TokenService tokenService;

    // 自动注入DictionaryService
    @Autowired
    private DictionaryService dictionaryService; // 字典

    // 自动注入GerentizhengService
    @Autowired
    private GerentizhengService gerentizhengService; // 体检记录

    // 自动注入JiankangtieshiService
    @Autowired
    private JiankangtieshiService jiankangtieshiService; // 健康贴士

    // 自动注入MeirijihuaService
    @Autowired
    private MeirijihuaService meirijihuaService; // 每日计划

    // 自动注入MeishiService
    @Autowired
    private MeishiService meishiService; // 健康食谱

    // 自动注入MeishiCollectionService
    @Autowired
    private MeishiCollectionService meishiCollectionService; // 健康食谱收藏

    // 自动注入MeishiLiuyanService
    @Autowired
    private MeishiLiuyanService meishiLiuyanService; // 健康食谱留言

    // 自动注入NewsService
    @Autowired
    private NewsService newsService; // 公告信息

    // 自动注入YaopinService
    @Autowired
    private YaopinService yaopinService; // 药品信息

    // 自动注入YonghuService
    @Autowired
    private YonghuService yonghuService; // 用户

    // 自动注入YundongService
    @Autowired
    private YundongService yundongService; // 运动教程

    // 自动注入YundongCollectionService
    @Autowired
    private YundongCollectionService yundongCollectionService; // 运动教程收藏

    // 自动注入YundongLiuyanService
    @Autowired
    private YundongLiuyanService yundongLiuyanService; // 运动教程留言

    // 自动注入UsersService
    @Autowired
    private UsersService usersService; // 管理员

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        // 记录日志
        logger.debug("page方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 检查参数
        CommonUtil.checkMap(params);
        // 调用服务层方法查询分页数据
        PageUtils page = forumService.queryPage(params);

        // 字典表数据转换
        List<ForumView> list = (List<ForumView>) page.getList();
        for (ForumView c : list) {
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        // 返回查询结果
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        // 记录日志
        logger.debug("info方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 根据ID查询论坛实体
        ForumEntity forum = forumService.selectById(id);
        if (forum != null) {
            // 实体转视图
            ForumView view = new ForumView();
            BeanUtils.copyProperties(forum, view); // 把实体数据重构到视图中
            // 级联表 用户
            YonghuEntity yonghu = yonghuService.selectById(forum.getYonghuId());
            if (yonghu != null) {
                BeanUtils.copyProperties(yonghu, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yonghuId", "usersId"}); // 把级联的数据添加到视图中，并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }
            // 级联表 管理员
            UsersEntity users = usersService.selectById(forum.getUsersId());
            if (users != null) {
                view.setUsersId(users.getId());
                view.setUusername(users.getUsername());
                view.setUpassword(users.getPassword());
                view.setUrole(users.getRole());
                view.setUaddtime(users.getAddtime());
            }
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 返回查询结果
            return R.ok().put("data", view);
        } else {
            // 返回错误信息
            return R.error(511, "查不到数据");
        }
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ForumEntity forum, HttpServletRequest request) {
        // 记录日志
        logger.debug("save方法:,,Controller:{},,forum:{}", this.getClass().getName(), forum.toString());

        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永远不会进入");
        else if ("用户".equals(role))
            forum.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        else if ("管理员".equals(role))
            forum.setUsersId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        // 构建查询条件
        Wrapper<ForumEntity> queryWrapper = new EntityWrapper<ForumEntity>()
                .eq("forum_name", forum.getForumName())
                .eq("yonghu_id", forum.getYonghuId())
                .eq("users_id", forum.getUsersId())
                .eq("super_ids", forum.getSuperIds())
                .eq("forum_state_types", forum.getForumStateTypes());

        // 记录SQL语句
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        ForumEntity forumEntity = forumService.selectOne(queryWrapper);
        if (forumEntity == null) {
            // 设置插入时间和创建时间
            forum.setInsertTime(new Date());
            forum.setCreateTime(new Date());
            // 插入新数据
            forumService.insert(forum);
            // 返回成功信息
            return R.ok();
        } else {
            // 返回错误信息
            return R.error(511, "表中有相同数据");
        }
    }

    /**
     * 后端修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ForumEntity forum, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 记录日志
        logger.debug("update方法:,,Controller:{},,forum:{}", this.getClass().getName(), forum.toString());
        // 查询原先数据
        ForumEntity oldForumEntity = forumService.selectById(forum.getId());

        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            forum.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
//        else if("管理员".equals(role))
//            forum.setUsersId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        // 设置更新时间
        forum.setUpdateTime(new Date());

        // 根据ID更新数据
        forumService.updateById(forum);
        // 返回成功信息
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        // 记录日志
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        // 查询要删除的数据
        List<ForumEntity> oldForumList = forumService.selectBatchIds(Arrays.asList(ids));
        // 批量删除数据
        forumService.deleteBatchIds(Arrays.asList(ids));
        // 返回成功信息
        return R.ok();
    }

    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save(String fileName, HttpServletRequest request) {
        // 记录日志
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}", this.getClass().getName(), fileName);
        // 获取当前用户ID
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        // 设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 初始化论坛实体列表
            List<ForumEntity> forumList = new ArrayList<>(); // 上传的东西
            // 初始化要查询的字段
            Map<String, List<String>> seachFields = new HashMap<>(); // 要查询的字段
            // 获取当前时间
            Date date = new Date();
            // 获取文件后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            if (lastIndexOf == -1) {
                // 返回错误信息
                return R.error(511, "该文件没有后缀");
            } else {
                // 获取文件后缀
                String suffix = fileName.substring(lastIndexOf);
                if (!".xls".equals(suffix)) {
                    // 返回错误信息
                    return R.error(511, "只支持后缀为xls的excel文件");
                } else {
                    // 获取文件路径
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);
                    File file = new File(resource.getFile());
                    if (!file.exists()) {
                        // 返回错误信息
                        return R.error(511, "找不到上传文件，请联系管理员");
                    } else {
                        // 读取xls文件
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());
                        dataList.remove(0); // 删除第一行，因为第一行是提示
                        for (List<String> data : dataList) {
                            // 循环
                            ForumEntity forumEntity = new ForumEntity();
//                            forumEntity.setForumName(data.get(0));                    // 帖子标题 要改的
//                            forumEntity.setYonghuId(Integer.valueOf(data.get(0)));   // 用户 要改的
//                            forumEntity.setUsersId(Integer.valueOf(data.get(0)));   // 管理员 要改的
//                            forumEntity.setForumContent("");// 详情和图片
//                            forumEntity.setSuperIds(Integer.valueOf(data.get(0)));   // 父id 要改的
//                            forumEntity.setForumStateTypes(Integer.valueOf(data.get(0)));   // 帖子状态 要改的
//                            forumEntity.setInsertTime(date);// 时间
//                            forumEntity.setUpdateTime(sdf.parse(data.get(0)));          // 修改时间 要改的
//                            forumEntity.setCreateTime(date);// 时间
                            forumList.add(forumEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        forumService.insertBatch(forumList);
                        // 返回成功信息
                        return R.ok();
                    }
                }
            }
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回错误信息
            return R.error(511, "批量插入数据异常，请联系管理员");
        }
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        // 记录日志
        logger.debug("list方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));

        // 检查参数
        CommonUtil.checkMap(params);
        // 调用服务层方法查询分页数据
        PageUtils page = forumService.queryPage(params);

        // 字典表数据转换
        List<ForumView> list = (List<ForumView>) page.getList();
        for (ForumView c : list)
            dictionaryService.dictionaryConvert(c, request); // 修改对应字典表字段

        // 返回查询结果
        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        // 记录日志
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 根据ID查询论坛实体
        ForumEntity forum = forumService.selectById(id);
        if (forum != null) {
            // 实体转视图
            ForumView view = new ForumView();
            BeanUtils.copyProperties(forum, view); // 把实体数据重构到视图中

            // 级联表 用户
            YonghuEntity yonghu = yonghuService.selectById(forum.getYonghuId());
            if (yonghu != null) {
                BeanUtils.copyProperties(yonghu, view, new String[]{"id", "createDate"}); // 把级联的数据添加到视图中，并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }
            // 级联表 管理员
            UsersEntity users = usersService.selectById(forum.getUsersId());
            if (users != null) {
                view.setUsersId(users.getId());
                view.setUusername(users.getUsername());
                view.setUpassword(users.getPassword());
                view.setUrole(users.getRole());
                view.setUaddtime(users.getAddtime());
            }
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 返回查询结果
            return R.ok().put("data", view);
        } else {
            // 返回错误信息
            return R.error(511, "查不到数据");
        }
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ForumEntity forum, HttpServletRequest request) {
        // 记录日志
        logger.debug("add方法:,,Controller:{},,forum:{}", this.getClass().getName(), forum.toString());
        // 构建查询条件
        Wrapper<ForumEntity> queryWrapper = new EntityWrapper<ForumEntity>()
                .eq("forum_name", forum.getForumName())
                .eq("yonghu_id", forum.getYonghuId())
                .eq("users_id", forum.getUsersId())
                .eq("super_ids", forum.getSuperIds())
                .eq("forum_state_types", forum.getForumStateTypes());
//            .notIn("forum_types", new Integer[]{102});
        // 记录SQL语句
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        ForumEntity forumEntity = forumService.selectOne(queryWrapper);
        if (forumEntity == null) {
            // 设置插入时间和创建时间
            forum.setInsertTime(new Date());
            forum.setCreateTime(new Date());
            // 插入新数据
            forumService.insert(forum);
            // 返回成功信息
            return R.ok();
        } else {
            // 返回错误信息
            return R.error(511, "表中有相同数据");
        }
    }
}
