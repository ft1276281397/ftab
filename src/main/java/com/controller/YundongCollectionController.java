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
 * 运动教程收藏
 * 后端接口
 * @author
 * @email
 */
@RestController
@RequestMapping("/yundongCollection")
public class YundongCollectionController {
    private static final Logger logger = LoggerFactory.getLogger(YundongCollectionController.class);

    private static final String TABLE_NAME = "yundongCollection";

    @Autowired
    private YundongCollectionService yundongCollectionService;

    @Autowired
    private TokenService tokenService;

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
    private YundongLiuyanService yundongLiuyanService; // 运动教程留言服务类

    @Autowired
    private UsersService usersService; // 管理员服务类

    /**
     * 后端列表
     * 该方法用于获取运动教程收藏的分页数据。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("page方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色
        if (false)
            // 永远不会进入的条件
            return R.error(511, "永不会进入");
        else if ("用户".equals(role))
            // 如果用户角色为“用户”
            params.put("yonghuId", request.getSession().getAttribute("userId"));
            // 设置用户ID
        CommonUtil.checkMap(params);
        // 检查参数
        PageUtils page = yundongCollectionService.queryPage(params);
        // 查询分页数据

        // 字典表数据转换
        List<YundongCollectionView> list = (List<YundongCollectionView>) page.getList();
        // 获取分页数据列表
        for (YundongCollectionView c : list) {
            // 遍历列表
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
            // 转换字典数据
        }
        return R.ok().put("data", page);
        // 返回结果
    }

    /**
     * 后端详情
     * 该方法用于获取指定ID的运动教程收藏详情信息。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("info方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志
        YundongCollectionEntity yundongCollection = yundongCollectionService.selectById(id);
        // 根据ID查询实体
        if (yundongCollection != null) {
            // 如果查询到数据
            // entity转view
            YundongCollectionView view = new YundongCollectionView();
            BeanUtils.copyProperties(yundongCollection, view);
            // 把实体数据重构到view中

            // 级联表 运动教程
            YundongEntity yundong = yundongService.selectById(yundongCollection.getYundongId());
            // 根据运动教程ID查询运动教程信息
            if (yundong != null) {
                BeanUtils.copyProperties(yundong, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yonghuId"});
                // 把级联的数据添加到view中，并排除id和创建时间字段
                view.setYundongId(yundong.getId());
            }

            // 级联表 用户
            YonghuEntity yonghu = yonghuService.selectById(yundongCollection.getYonghuId());
            // 根据用户ID查询用户信息
            if (yonghu != null) {
                BeanUtils.copyProperties(yonghu, view, new String[]{"id", "createTime", "insertTime", "updateTime", "yonghuId"});
                // 把级联的数据添加到view中，并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }

            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 转换字典数据
            return R.ok().put("data", view);
            // 返回结果
        } else {
            return R.error(511, "查不到数据");
            // 返回错误信息
        }
    }

    /**
     * 后端保存
     * 该方法用于保存新的运动教程收藏信息。
     */
    @RequestMapping("/save")
    public R save(@RequestBody YundongCollectionEntity yundongCollection, HttpServletRequest request) {
        logger.debug("save方法:,,Controller:{},,yundongCollection:{}", this.getClass().getName(), yundongCollection.toString());
        // 记录日志
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色
        if (false)
            // 永远不会进入的条件
            return R.error(511, "永远不会进入");
        else if ("用户".equals(role))
            // 如果用户角色为“用户”
            yundongCollection.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
            // 设置用户ID

        Wrapper<YundongCollectionEntity> queryWrapper = new EntityWrapper<YundongCollectionEntity>()
            .eq("yundong_id", yundongCollection.getYundongId())
            .eq("yonghu_id", yundongCollection.getYonghuId())
            .eq("yundong_collection_types", yundongCollection.getYundongCollectionTypes())
            ;
        // 构建查询条件

        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 记录SQL语句
        YundongCollectionEntity yundongCollectionEntity = yundongCollectionService.selectOne(queryWrapper);
        // 查询是否存在相同数据
        if (yundongCollectionEntity == null) {
            // 如果不存在相同数据
            yundongCollection.setInsertTime(new Date());
            // 设置插入时间
            yundongCollection.setCreateTime(new Date());
            // 设置创建时间
            yundongCollectionService.insert(yundongCollection);
            // 插入数据
            return R.ok();
            // 返回成功结果
        } else {
            return R.error(511, "表中有相同数据");
            // 返回错误信息
        }
    }

    /**
     * 后端修改
     * 该方法用于更新指定ID的运动教程收藏信息。
     */
    @RequestMapping("/update")
    public R update(@RequestBody YundongCollectionEntity yundongCollection, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,yundongCollection:{}", this.getClass().getName(), yundongCollection.toString());
        // 记录日志
        YundongCollectionEntity oldYundongCollectionEntity = yundongCollectionService.selectById(yundongCollection.getId());
        // 查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            yundongCollection.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        yundongCollectionService.updateById(yundongCollection);
        // 根据id更新
        return R.ok();
        // 返回成功结果
    }

    /**
     * 删除
     * 该方法用于逻辑删除指定ID的运动教程收藏。
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        // 记录日志
        List<YundongCollectionEntity> oldYundongCollectionList = yundongCollectionService.selectBatchIds(Arrays.asList(ids));
        // 要删除的数据
        yundongCollectionService.deleteBatchIds(Arrays.asList(ids));
        // 删除批量数据

        return R.ok();
        // 返回成功结果
    }

    /**
     * 批量上传
     * 该方法用于批量上传运动教程收藏信息。
     */
    @RequestMapping("/batchInsert")
    public R save(String fileName, HttpServletRequest request) {
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}", this.getClass().getName(), fileName);
        // 记录日志
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        // 获取用户ID
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 初始化日期格式化器
        try {
            List<YundongCollectionEntity> yundongCollectionList = new ArrayList<>();
            // 定义运动教程收藏信息列表
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
                            YundongCollectionEntity yundongCollectionEntity = new YundongCollectionEntity();
//                            yundongCollectionEntity.setYundongId(Integer.valueOf(data.get(0)));   //运动教程 要改的
//                            yundongCollectionEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            yundongCollectionEntity.setYundongCollectionTypes(Integer.valueOf(data.get(0)));   //类型 要改的
//                            yundongCollectionEntity.setInsertTime(date);//时间
//                            yundongCollectionEntity.setCreateTime(date);//时间
                            yundongCollectionList.add(yundongCollectionEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        yundongCollectionService.insertBatch(yundongCollectionList);
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
     * 该方法用于获取运动教程收藏的分页数据（前端调用）。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("list方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志
        CommonUtil.checkMap(params);
        // 检查参数
        PageUtils page = yundongCollectionService.queryPage(params);
        // 查询分页数据

        // 字典表数据转换
        List<YundongCollectionView> list = (List<YundongCollectionView>) page.getList();
        // 获取分页数据列表
        for (YundongCollectionView c : list)
            dictionaryService.dictionaryConvert(c, request); // 修改对应字典表字段
        // 转换字典数据

        return R.ok().put("data", page);
        // 返回结果
    }

    /**
     * 前端详情
     * 该方法用于获取指定ID的运动教程收藏详情信息（前端调用）。
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志
        YundongCollectionEntity yundongCollection = yundongCollectionService.selectById(id);
        // 根据ID查询实体
        if (yundongCollection != null) {
            // 如果查询到数据

            // entity转view
            YundongCollectionView view = new YundongCollectionView();
            BeanUtils.copyProperties(yundongCollection, view);
            // 把实体数据重构到view中

            // 级联表
            YundongEntity yundong = yundongService.selectById(yundongCollection.getYundongId());
            // 根据运动教程ID查询运动教程信息
            if (yundong != null) {
                BeanUtils.copyProperties(yundong, view, new String[]{"id", "createDate"});
                // 把级联的数据添加到view中，并排除id和创建时间字段
                view.setYundongId(yundong.getId());
            }

            // 级联表
            YonghuEntity yonghu = yonghuService.selectById(yundongCollection.getYonghuId());
            // 根据用户ID查询用户信息
            if (yonghu != null) {
                BeanUtils.copyProperties(yonghu, view, new String[]{"id", "createDate"});
                // 把级联的数据添加到view中，并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }

            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 转换字典数据
            return R.ok().put("data", view);
            // 返回结果
        } else {
            return R.error(511, "查不到数据");
            // 返回错误信息
        }
    }

    /**
     * 前端保存
     * 该方法用于保存新的运动教程收藏信息（前端调用）。
     */
    @RequestMapping("/add")
    public R add(@RequestBody YundongCollectionEntity yundongCollection, HttpServletRequest request) {
        logger.debug("add方法:,,Controller:{},,yundongCollection:{}", this.getClass().getName(), yundongCollection.toString());
        // 记录日志
        Wrapper<YundongCollectionEntity> queryWrapper = new EntityWrapper<YundongCollectionEntity>()
            .eq("yundong_id", yundongCollection.getYundongId())
            .eq("yonghu_id", yundongCollection.getYonghuId())
            .eq("yundong_collection_types", yundongCollection.getYundongCollectionTypes())
//            .notIn("yundong_collection_types", new Integer[]{102})
            ;
        // 构建查询条件

        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 记录SQL语句
        YundongCollectionEntity yundongCollectionEntity = yundongCollectionService.selectOne(queryWrapper);
        // 查询是否存在相同数据
        if (yundongCollectionEntity == null) {
            // 如果不存在相同数据
            yundongCollection.setInsertTime(new Date());
            // 设置插入时间
            yundongCollection.setCreateTime(new Date());
            // 设置创建时间
            yundongCollectionService.insert(yundongCollection);
            // 插入数据
            return R.ok();
            // 返回成功结果
        } else {
            return R.error(511, "您已经收藏过了");
            // 返回错误信息
        }
    }
}
