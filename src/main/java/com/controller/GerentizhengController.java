package com.controller;

// 导入用于处理文件操作的类
import java.io.File;
// 导入用于处理高精度数值计算的类
import java.math.BigDecimal;
// 导入用于处理 URL 相关操作的类
import java.net.URL;
// 导入用于格式化日期的类
import java.text.SimpleDateFormat;
// 导入阿里巴巴的 FastJSON 库中的 JSONObject 类，用于处理 JSON 数据
import com.alibaba.fastjson.JSONObject;
// 导入 Java 中的各种集合类
import java.util.*;
// 导入 Spring 框架中用于复制 Bean 属性的类
import org.springframework.beans.BeanUtils;
// 导入用于处理 HTTP 请求的类
import javax.servlet.http.HttpServletRequest;
// 导入 Spring 框架中用于获取 Web 应用上下文的类
import org.springframework.web.context.ContextLoader;
// 导入用于表示 Servlet 上下文的类
import javax.servlet.ServletContext;
// 导入自定义的 Token 服务类
import com.service.TokenService;
// 导入自定义的工具类包中的所有类
import com.utils.*;
// 导入用于处理反射相关异常的类
import java.lang.reflect.InvocationTargetException;

// 导入自定义的字典服务类
import com.service.DictionaryService;
// 导入 Apache Commons Lang 库中用于处理字符串的工具类
import org.apache.commons.lang3.StringUtils;
// 导入自定义的忽略认证注解
import com.annotation.IgnoreAuth;
// 导入用于记录日志的接口
import org.slf4j.Logger;
// 导入用于创建日志记录器的工厂类
import org.slf4j.LoggerFactory;
// 导入 Spring 框架中用于标记控制器的注解
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// 导入 Spring 框架中用于处理请求映射的注解
import org.springframework.web.bind.annotation.*;
// 导入 MyBatis-Plus 框架中用于构建实体包装器的类
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入 MyBatis-Plus 框架中的包装器接口
import com.baomidou.mybatisplus.mapper.Wrapper;
// 导入自定义的各种实体类
import com.entity.*;
// 导入自定义的各种视图类
import com.entity.view.*;
// 导入自定义的各种服务类
import com.service.*;
// 导入自定义的分页工具类
import com.utils.PageUtils;
// 导入自定义的返回结果类
import com.utils.R;
// 再次导入阿里巴巴的 FastJSON 库中的相关类
import com.alibaba.fastjson.*;

/**
 * 体检记录
 * 后端接口
 * @author
 * @email
*/
// 使用 @RestController 注解将该类标记为 RESTful 风格的控制器，用于处理 HTTP 请求并返回 JSON 数据
@RestController
// 使用 @Controller 注解将该类标记为控制器，用于处理 Web 请求
@Controller
// 使用 @RequestMapping 注解将该控制器的所有请求映射到 /gerentizheng 路径
@RequestMapping("/gerentizheng")
public class GerentizhengController {
    // 创建一个日志记录器，用于记录该控制器类的日志信息
    private static final Logger logger = LoggerFactory.getLogger(GerentizhengController.class);

    // 定义一个常量，表示数据库表名
    private static final String TABLE_NAME = "gerentizheng";

    // 使用 @Autowired 注解自动注入 GerentizhengService 实例
    @Autowired
    private GerentizhengService gerentizhengService;

    // 使用 @Autowired 注解自动注入 TokenService 实例
    @Autowired
    private TokenService tokenService;

    // 使用 @Autowired 注解自动注入 DictionaryService 实例，用于处理字典相关业务
    @Autowired
    private DictionaryService dictionaryService;//字典
    // 使用 @Autowired 注解自动注入 ForumService 实例，用于处理论坛相关业务
    @Autowired
    private ForumService forumService;//论坛
    // 使用 @Autowired 注解自动注入 JiankangtieshiService 实例，用于处理健康贴士相关业务
    @Autowired
    private JiankangtieshiService jiankangtieshiService;//健康贴士
    // 使用 @Autowired 注解自动注入 MeirijihuaService 实例，用于处理每日计划相关业务
    @Autowired
    private MeirijihuaService meirijihuaService;//每日计划
    // 使用 @Autowired 注解自动注入 MeishiService 实例，用于处理健康食谱相关业务
    @Autowired
    private MeishiService meishiService;//健康食谱
    // 使用 @Autowired 注解自动注入 MeishiCollectionService 实例，用于处理健康食谱收藏相关业务
    @Autowired
    private MeishiCollectionService meishiCollectionService;//健康食谱收藏
    // 使用 @Autowired 注解自动注入 MeishiLiuyanService 实例，用于处理健康食谱留言相关业务
    @Autowired
    private MeishiLiuyanService meishiLiuyanService;//健康食谱留言
    // 使用 @Autowired 注解自动注入 NewsService 实例，用于处理公告信息相关业务
    @Autowired
    private NewsService newsService;//公告信息
    // 使用 @Autowired 注解自动注入 YaopinService 实例，用于处理药品信息相关业务
    @Autowired
    private YaopinService yaopinService;//药品信息
    // 使用 @Autowired 注解自动注入 YonghuService 实例，用于处理用户相关业务
    @Autowired
    private YonghuService yonghuService;//用户
    // 使用 @Autowired 注解自动注入 YundongService 实例，用于处理运动教程相关业务
    @Autowired
    private YundongService yundongService;//运动教程
    // 使用 @Autowired 注解自动注入 YundongCollectionService 实例，用于处理运动教程收藏相关业务
    @Autowired
    private YundongCollectionService yundongCollectionService;//运动教程收藏
    // 使用 @Autowired 注解自动注入 YundongLiuyanService 实例，用于处理运动教程留言相关业务
    @Autowired
    private YundongLiuyanService yundongLiuyanService;//运动教程留言
    // 使用 @Autowired 注解自动注入 UsersService 实例，用于处理管理员相关业务
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
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

