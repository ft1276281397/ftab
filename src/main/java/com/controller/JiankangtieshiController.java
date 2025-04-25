package com.controller;
// 声明该类所在的包为 com.controller，包用于组织和管理 Java 类，避免命名冲突
// 导入用于处理文件操作的类，可能用于文件上传、下载等功能
import java.io.File;
// 导入用于高精度数值计算的类，常用于处理货币、金融等领域的数值计算
import java.math.BigDecimal;
// 导入用于处理 URL 相关操作的类，比如获取网络资源等
import java.net.URL;
// 导入用于格式化日期的类，可将日期按照指定格式进行转换
import java.text.SimpleDateFormat;
// 导入阿里巴巴的 FastJSON 库中的 JSONObject 类，用于处理 JSON 数据，如解析、生成 JSON 对象
import com.alibaba.fastjson.JSONObject;
// 导入 Java 中的各种集合类，如 List、Map、Set 等，用于数据存储和处理
import java.util.*;
// 导入 Spring 框架中用于复制 Bean 属性的类，方便在不同对象间复制属性值
import org.springframework.beans.BeanUtils;
// 导入用于处理 HTTP 请求的类，在 Web 开发中获取请求相关信息
import javax.servlet.http.HttpServletRequest;
// 导入 Spring 框架中用于获取 Web 应用上下文的类，可获取应用的全局信息
import org.springframework.web.context.ContextLoader;
// 导入用于表示 Servlet 上下文的类，包含了 Servlet 应用的相关信息
import javax.servlet.ServletContext;
// 导入自定义的 Token 服务类，可能用于处理用户认证、授权中的 Token 相关逻辑
import com.service.TokenService;
// 导入自定义的工具类包中的所有类，工具类通常包含一些通用的方法，如字符串处理、数据验证等
import com.utils.*;
// 导入用于处理反射相关异常的类，反射操作中可能会抛出此类异常
import java.lang.reflect.InvocationTargetException;

// 导入自定义的字典服务类，可能用于处理字典数据的增删改查等操作
import com.service.DictionaryService;
// 导入 Apache Commons Lang 库中用于处理字符串的工具类，提供了丰富的字符串操作方法
import org.apache.commons.lang3.StringUtils;
// 导入自定义的忽略认证注解，用于标记不需要进行认证的方法或接口
import com.annotation.IgnoreAuth;
// 导入用于记录日志的接口，用于记录应用运行过程中的信息
import org.slf4j.Logger;
// 导入用于创建日志记录器的工厂类，通过该类创建具体的日志记录器
import org.slf4j.LoggerFactory;
// 导入 Spring 框架中用于自动注入依赖的注解，简化 Bean 的注入过程
import org.springframework.beans.factory.annotation.Autowired;
// 导入 Spring 框架中用于标记控制器的注解，表明该类是一个处理 Web 请求的控制器
import org.springframework.stereotype.Controller;
// 导入 Spring 框架中用于处理请求映射的注解，如 @RequestMapping、@GetMapping 等
import org.springframework.web.bind.annotation.*;
// 导入 MyBatis-Plus 框架中用于构建实体包装器的类，用于构建查询条件等
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入 MyBatis-Plus 框架中的包装器接口，提供了统一的包装器操作方法
import com.baomidou.mybatisplus.mapper.Wrapper;
// 导入自定义的各种实体类，实体类通常对应数据库中的表结构
import com.entity.*;
// 导入自定义的各种视图类，视图类可能用于封装特定的业务视图数据
import com.entity.view.*;
// 导入自定义的各种服务类，服务类通常包含具体的业务逻辑实现
import com.service.*;
// 导入自定义的分页工具类，用于处理数据的分页操作
import com.utils.PageUtils;
// 导入自定义的返回结果类，用于统一封装接口的返回结果
import com.utils.R;
// 再次导入阿里巴巴的 FastJSON 库中的相关类，可能用于更全面的 JSON 数据处理
import com.alibaba.fastjson.*;

/**
 * 健康贴士
 * 后端接口
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/jiankangtieshi")
public class JiankangtieshiController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(JiankangtieshiController.class);

    // 定义表名常量
    private static final String TABLE_NAME = "jiankangtieshi";

    // 自动注入JiankangtieshiService
    @Autowired
    private JiankangtieshiService jiankangtieshiService;

    // 自动注入TokenService
    @Autowired
    private TokenService tokenService;

    // 自动注入DictionaryService
    @Autowired
    private DictionaryService dictionaryService; // 字典

    // 自动注入ForumService
    @Autowired
    private ForumService forumService; // 论坛

    // 自动注入GerentizhengService
    @Autowired
    private GerentizhengService gerentizhengService; // 体检记录

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
        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永不会进入");
        else if ("用户".equals(role))
            params.put("yonghuId", request.getSession().getAttribute("userId"));
        // 检查参数
        CommonUtil.checkMap(params);
        // 调用服务层方法查询分页数据
        PageUtils page = jiankangtieshiService.queryPage(params);

        // 字典表数据转换
        List<JiankangtieshiView> list = (List<JiankangtieshiView>) page.getList();
        for (JiankangtieshiView c : list) {
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
        // 根据ID查询健康贴士实体
        JiankangtieshiEntity jiankangtieshi = jiankangtieshiService.selectById(id);
        if (jiankangtieshi != null) {
            // 实体转视图
            JiankangtieshiView view = new JiankangtieshiView();
            BeanUtils.copyProperties(jiankangtieshi, view); // 把实体数据重构到视图中
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
    public R save(@RequestBody JiankangtieshiEntity jiankangtieshi, HttpServletRequest request) {
        // 记录日志
        logger.debug("save方法:,,Controller:{},,jiankangtieshi:{}", this.getClass().getName(), jiankangtieshi.toString());

        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永远不会进入");

        // 构建查询条件
        Wrapper<JiankangtieshiEntity> queryWrapper = new EntityWrapper<JiankangtieshiEntity>()
                .eq("jiankangtieshi_name", jiankangtieshi.getJiankangtieshiName())
                .eq("jiankangtieshi_types", jiankangtieshi.getJiankangtieshiTypes());

        // 记录SQL语句
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        JiankangtieshiEntity jiankangtieshiEntity = jiankangtieshiService.selectOne(queryWrapper);
        if (jiankangtieshiEntity == null) {
            // 设置插入时间和创建时间
            jiankangtieshi.setInsertTime(new Date());
            jiankangtieshi.setCreateTime(new Date());
            // 插入新数据
            jiankangtieshiService.insert(jiankangtieshi);
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
    public R update(@RequestBody JiankangtieshiEntity jiankangtieshi, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 记录日志
        logger.debug("update方法:,,Controller:{},,jiankangtieshi:{}", this.getClass().getName(), jiankangtieshi.toString());
        // 查询原先数据
        JiankangtieshiEntity oldJiankangtieshiEntity = jiankangtieshiService.selectById(jiankangtieshi.getId());

        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        // 处理图片字段
        if ("".equals(jiankangtieshi.getJiankangtieshiPhoto()) || "null".equals(jiankangtieshi.getJiankangtieshiPhoto())) {
            jiankangtieshi.setJiankangtieshiPhoto(null);
        }

        // 根据ID更新数据
        jiankangtieshiService.updateById(jiankangtieshi);
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
        List<JiankangtieshiEntity> oldJiankangtieshiList = jiankangtieshiService.selectBatchIds(Arrays.asList(ids));
        // 批量删除数据
        jiankangtieshiService.deleteBatchIds(Arrays.asList(ids));

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
            // 初始化健康贴士实体列表
            List<JiankangtieshiEntity> jiankangtieshiList = new ArrayList<>(); // 上传的东西
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
                            JiankangtieshiEntity jiankangtieshiEntity = new JiankangtieshiEntity();
//                            jiankangtieshiEntity.setJiankangtieshiName(data.get(0));                    // 贴士标题 要改的
//                            jiankangtieshiEntity.setJiankangtieshiTypes(Integer.valueOf(data.get(0)));   // 贴士类型 要改的
//                            jiankangtieshiEntity.setJiankangtieshiPhoto("");// 详情和图片
//                            jiankangtieshiEntity.setInsertTime(date);// 时间
//                            jiankangtieshiEntity.setJiankangtieshiContent("");// 详情和图片
//                            jiankangtieshiEntity.setCreateTime(date);// 时间
                            jiankangtieshiList.add(jiankangtieshiEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        jiankangtieshiService.insertBatch(jiankangtieshiList);
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
        PageUtils page = jiankangtieshiService.queryPage(params);

        // 字典表数据转换
        List<JiankangtieshiView> list = (List<JiankangtieshiView>) page.getList();
        for (JiankangtieshiView c : list)
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
        // 根据ID查询健康贴士实体
        JiankangtieshiEntity jiankangtieshi = jiankangtieshiService.selectById(id);
        if (jiankangtieshi != null) {
            // 实体转视图
            JiankangtieshiView view = new JiankangtieshiView();
            BeanUtils.copyProperties(jiankangtieshi, view); // 把实体数据重构到视图中

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
    public R add(@RequestBody JiankangtieshiEntity jiankangtieshi, HttpServletRequest request) {
        // 记录日志
        logger.debug("add方法:,,Controller:{},,jiankangtieshi:{}", this.getClass().getName(), jiankangtieshi.toString());
        // 构建查询条件
        Wrapper<JiankangtieshiEntity> queryWrapper = new EntityWrapper<JiankangtieshiEntity>()
                .eq("jiankangtieshi_name", jiankangtieshi.getJiankangtieshiName())
                .eq("jiankangtieshi_types", jiankangtieshi.getJiankangtieshiTypes());
//            .notIn("jiankangtieshi_types", new Integer[]{102});
        // 记录SQL语句
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        JiankangtieshiEntity jiankangtieshiEntity = jiankangtieshiService.selectOne(queryWrapper);
        if (jiankangtieshiEntity == null) {
            // 设置插入时间和创建时间
            jiankangtieshi.setInsertTime(new Date());
            jiankangtieshi.setCreateTime(new Date());
            // 插入新数据
            jiankangtieshiService.insert(jiankangtieshi);
            // 返回成功信息
            return R.ok();
        } else {
            // 返回错误信息
            return R.error(511, "表中有相同数据");
        }
    }
}
