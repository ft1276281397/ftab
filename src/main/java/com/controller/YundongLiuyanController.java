
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
 * 运动教程留言
 * 后端接口
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/yundongLiuyan")
public class YundongLiuyanController {
    private static final Logger logger = LoggerFactory.getLogger(YundongLiuyanController.class);
    // 创建日志记录器实例

    private static final String TABLE_NAME = "yundongLiuyan";
    // 定义表名常量

    @Autowired
    private YundongLiuyanService yundongLiuyanService;
    // 自动注入运动教程留言服务类

    @Autowired
    private TokenService tokenService;
    // 自动注入Token服务类

    @Autowired
    private DictionaryService dictionaryService; // 字典服务类
    @Autowired
    private ForumService forumService; // 论坛服务类
    @Autowired
    private GerentizhengService gerentizhengService; // 体检记录服务类
    @Autowired
    private JiankangtieshiService jiankangtieshiService; // 健康贴士服务类
    @Autowired
    private MeirijihuaService meirijihuaService; // 每日计划服务类
    @Autowired
    private MeishiService meishiService; // 健康食谱服务类
    @Autowired
    private MeishiCollectionService meishiCollectionService; // 健康食谱收藏服务类
    @Autowired
    private MeishiLiuyanService meishiLiuyanService; // 健康食谱留言服务类
    @Autowired
    private NewsService newsService; // 公告信息服务类
    @Autowired
    private YaopinService yaopinService; // 药品信息服务类
    @Autowired
    private YonghuService yonghuService; // 用户服务类
    @Autowired
    private YundongService yundongService; // 运动教程服务类
    @Autowired
    private YundongCollectionService yundongCollectionService; // 运动教程收藏服务类
    @Autowired
    private UsersService usersService; // 管理员服务类

    /**
     * 后端列表
     * 该方法用于查询运动教程留言的后端分页列表。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("page方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志，打印当前控制器名称和参数

        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色

        if (false)
            return R.error(511, "永不会进入");
            // 永远不会进入的条件

        else if ("用户".equals(role))
            params.put("yonghuId", request.getSession().getAttribute("userId"));
        // 如果用户角色为“用户”，设置用户ID

        CommonUtil.checkMap(params);
        // 检查并处理请求参数

        PageUtils page = yundongLiuyanService.queryPage(params);
        // 调用服务层方法查询分页数据

        // 字典表数据转换
        List<YundongLiuyanView> list = (List<YundongLiuyanView>) page.getList();
        // 获取分页数据中的列表

        for (YundongLiuyanView c : list) {
            // 遍历列表
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
            // 将字典表字段转换为可读格式
        }

        return R.ok().put("data", page);
        // 返回封装的成功结果，并将分页数据放入返回对象中
    }

    /**
     * 后端详情
     * 该方法用于根据ID查询运动教程留言的详细信息。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("info方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志，打印当前控制器名称和ID参数

        YundongLiuyanEntity yundongLiuyan = yundongLiuyanService.selectById(id);
        // 根据ID查询实体数据

        if (yundongLiuyan != null) {
            // 如果查询到数据

            // entity转view
            YundongLiuyanView view = new YundongLiuyanView();
            BeanUtils.copyProperties(yundongLiuyan, view); // 将实体数据复制到视图对象中

            // 级联表 运动教程
            // 级联表
            YundongEntity yundong = yundongService.selectById(yundongLiuyan.getYundongId());
            // 根据运动教程ID查询运动教程实体

            if (yundong != null) {
                BeanUtils.copyProperties(yundong, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yonghuId"});
                // 将运动教程数据复制到视图对象中，并排除id和创建时间字段
                view.setYundongId(yundong.getId());
                // 设置运动教程ID
            }

            // 级联表 用户
            // 级联表
            YonghuEntity yonghu = yonghuService.selectById(yundongLiuyan.getYonghuId());
            // 根据用户ID查询用户实体

            if (yonghu != null) {
                BeanUtils.copyProperties(yonghu, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yonghuId"});
                // 将用户数据复制到视图对象中，并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
                // 设置用户ID
            }

            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 转换字典数据为可读格式

            return R.ok().put("data", view);
            // 返回封装的成功结果，并将视图对象放入返回对象中
        } else {
            return R.error(511, "查不到数据");
            // 如果未查询到数据，返回错误信息
        }
    }

    /**
     * 后端保存
     * 该方法用于保存新的运动教程留言信息。
     */
    @RequestMapping("/save")
    public R save(@RequestBody YundongLiuyanEntity yundongLiuyan, HttpServletRequest request) {
        logger.debug("save方法:,,Controller:{},,yundongLiuyan:{}", this.getClass().getName(), yundongLiuyan.toString());
        // 记录日志，打印当前控制器名称和传入的实体数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色

        if (false)
            return R.error(511, "永远不会进入");
            // 永远不会进入的条件

        else if ("用户".equals(role))
            yundongLiuyan.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        // 如果用户角色为“用户”，设置用户ID

        yundongLiuyan.setCreateTime(new Date());
        yundongLiuyan.setInsertTime(new Date());
        // 设置创建时间和插入时间

        yundongLiuyanService.insert(yundongLiuyan);
        // 插入新数据

        return R.ok();
        // 返回成功结果
    }

    /**
     * 后端修改
     * 该方法用于更新指定ID的运动教程留言信息。
     */
    @RequestMapping("/update")
    public R update(@RequestBody YundongLiuyanEntity yundongLiuyan, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,yundongLiuyan:{}", this.getClass().getName(), yundongLiuyan.toString());
        // 记录日志，打印当前控制器名称和传入的实体数据

        YundongLiuyanEntity oldYundongLiuyanEntity = yundongLiuyanService.selectById(yundongLiuyan.getId());
        // 查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色

        //        if(false)
        //            return R.error(511,"永远不会进入");
        //        else if("用户".equals(role))
        //            yundongLiuyan.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        yundongLiuyan.setUpdateTime(new Date());
        // 设置更新时间

        yundongLiuyanService.updateById(yundongLiuyan);
        // 根据id更新数据

        return R.ok();
        // 返回成功结果
    }

    /**
     * 删除
     * 该方法用于逻辑删除指定ID的运动教程留言。
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        // 记录日志，打印当前控制器名称和ID数组

        List<YundongLiuyanEntity> oldYundongLiuyanList = yundongLiuyanService.selectBatchIds(Arrays.asList(ids));
        // 查询要删除的数据

        yundongLiuyanService.deleteBatchIds(Arrays.asList(ids));
        // 根据ID数组批量删除数据

        return R.ok();
        // 返回成功结果
    }

    /**
     * 批量上传
     * 该方法用于批量上传运动教程留言信息。
     */
    @RequestMapping("/batchInsert")
    public R save(String fileName, HttpServletRequest request) {
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}", this.getClass().getName(), fileName);
        // 记录日志，打印当前控制器名称和文件名

        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        // 获取用户ID

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 初始化日期格式化器

        try {
            List<YundongLiuyanEntity> yundongLiuyanList = new ArrayList<>();
            // 定义运动教程留言信息列表

            Map<String, List<String>> seachFields = new HashMap<>();
            // 定义要查询的字段

            Date date = new Date();
            // 获取当前时间

            int lastIndexOf = fileName.lastIndexOf(".");
            // 获取文件后缀

            if (lastIndexOf == -1) {
                // 如果文件没有后缀
                return R.error(511, "该文件没有后缀");
                // 返回错误信息
            } else {
                String suffix = fileName.substring(lastIndexOf);
                // 获取文件后缀

                if (!".xls".equals(suffix)) {
                    // 如果不是xls文件
                    return R.error(511, "只支持后缀为xls的excel文件");
                    // 返回错误信息
                } else {
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);
                    // 获取文件路径

                    File file = new File(resource.getFile());
                    // 获取文件对象

                    if (!file.exists()) {
                        // 如果文件不存在
                        return R.error(511, "找不到上传文件，请联系管理员");
                        // 返回错误信息
                    } else {
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());
                        // 读取Excel数据并解析

                        dataList.remove(0);
                        // 删除第一行，因为第一行是提示

                        for (List<String> data : dataList) {
                            // 循环

                            YundongLiuyanEntity yundongLiuyanEntity = new YundongLiuyanEntity();
                            // 创建实体对象

                            //                            yundongLiuyanEntity.setYundongId(Integer.valueOf(data.get(0)));   //运动教程 要改的
                            //                            yundongLiuyanEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
                            //                            yundongLiuyanEntity.setYundongLiuyanText(data.get(0));                    //留言内容 要改的
                            //                            yundongLiuyanEntity.setInsertTime(date);//时间
                            //                            yundongLiuyanEntity.setReplyText(data.get(0));                    //回复内容 要改的
                            //                            yundongLiuyanEntity.setUpdateTime(sdf.parse(data.get(0)));          //回复时间 要改的
                            //                            yundongLiuyanEntity.setCreateTime(date);//时间
                            yundongLiuyanList.add(yundongLiuyanEntity);
                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        yundongLiuyanService.insertBatch(yundongLiuyanList);
                        // 批量插入数据

                        return R.ok();
                        // 返回成功结果
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(511, "批量插入数据异常，请联系管理员");
            // 返回错误信息
        }
    }

    /**
     * 前端列表
     * 该方法用于查询运动教程留言的前端分页列表。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("list方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志，打印当前控制器名称和参数

        CommonUtil.checkMap(params);
        // 检查并处理请求参数

        PageUtils page = yundongLiuyanService.queryPage(params);
        // 调用服务层方法查询分页数据

        // 字典表数据转换
        List<YundongLiuyanView> list = (List<YundongLiuyanView>) page.getList();
        // 获取分页数据中的列表

        for (YundongLiuyanView c : list)
            dictionaryService.dictionaryConvert(c, request); // 修改对应字典表字段

        return R.ok().put("data", page);
        // 返回封装的成功结果，并将分页数据放入返回对象中
    }

    /**
     * 前端详情
     * 该方法用于根据ID查询运动教程留言的详细信息。
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志，打印当前控制器名称和ID参数

        YundongLiuyanEntity yundongLiuyan = yundongLiuyanService.selectById(id);
        // 根据ID查询实体数据

        if (yundongLiuyan != null) {
            // 如果查询到数据

            // entity转view
            YundongLiuyanView view = new YundongLiuyanView();
            BeanUtils.copyProperties(yundongLiuyan, view); // 把实体数据重构到view中

            // 级联表
            YundongEntity yundong = yundongService.selectById(yundongLiuyan.getYundongId());
            // 根据运动教程ID查询运动教程实体

            if (yundong != null) {
                BeanUtils.copyProperties(yundong, view, new String[]{"id", "createDate"});
                // 把运动教程数据添加到view中，并排除id和创建时间字段
                view.setYundongId(yundong.getId());
                // 设置运动教程ID
            }

            // 级联表
            YonghuEntity yonghu = yonghuService.selectById(yundongLiuyan.getYonghuId());
            // 根据用户ID查询用户实体

            if (yonghu != null) {
                BeanUtils.copyProperties(yonghu, view, new String[]{"id", "createDate"});
                // 把用户数据添加到view中，并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
                // 设置用户ID
            }

            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 转换字典数据为可读格式

            return R.ok().put("data", view);
            // 返回封装的成功结果，并将视图对象放入返回对象中
        } else {
            return R.error(511, "查不到数据");
            // 如果未查询到数据，返回错误信息
        }
    }


    /**
     * 前端保存
     * 该方法用于保存新的运动教程留言信息。
     */
    @RequestMapping("/add")
    public R add(@RequestBody YundongLiuyanEntity yundongLiuyan, HttpServletRequest request) {
        logger.debug("add方法:,,Controller:{},,yundongLiuyan:{}", this.getClass().getName(), yundongLiuyan.toString());
        // 记录日志，打印当前控制器名称和传入的实体数据

        yundongLiuyan.setCreateTime(new Date());
        // 设置创建时间为当前时间

        yundongLiuyan.setInsertTime(new Date());
        // 设置插入时间为当前时间

        yundongLiuyanService.insert(yundongLiuyan);
        // 调用服务层方法插入新数据

        return R.ok();
        // 返回成功结果
    }
}

