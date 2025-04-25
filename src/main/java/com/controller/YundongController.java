package com.controller;

import java.io.File;
// 导入File类，用于文件操作
import java.math.BigDecimal;
// 导入BigDecimal类，用于高精度计算
import java.net.URL;
// 导入URL类，用于处理URL
import java.text.SimpleDateFormat;
// 导入SimpleDateFormat类，用于日期格式化
import com.alibaba.fastjson.JSONObject;
// 导入JSONObject类，用于JSON处理
import java.util.*;
// 导入Java集合框架
import org.springframework.beans.BeanUtils;
// 导入BeanUtils类，用于对象属性复制
import javax.servlet.http.HttpServletRequest;
// 导入HttpServletRequest类，用于处理HTTP请求
import org.springframework.web.context.ContextLoader;
// 导入ContextLoader类，用于获取Web应用上下文
import javax.servlet.ServletContext;
// 导入ServletContext类，用于获取Servlet上下文
import com.service.TokenService;
// 导入TokenService类，用于处理Token相关操作
import com.utils.*;
// 导入自定义工具类
import java.lang.reflect.InvocationTargetException;
// 导入InvocationTargetException类，用于处理反射异常

import com.service.DictionaryService;
// 导入DictionaryService类，用于处理字典数据
import org.apache.commons.lang3.StringUtils;
// 导入StringUtils类，用于字符串操作
import com.annotation.IgnoreAuth;
// 导入IgnoreAuth注解，用于忽略权限验证
import org.slf4j.Logger;
// 导入Logger类，用于日志记录
import org.slf4j.LoggerFactory;
// 导入LoggerFactory类，用于创建Logger实例
import org.springframework.beans.factory.annotation.Autowired;
// 导入Autowired注解，用于自动注入依赖
import org.springframework.stereotype.Controller;
// 导入Controller注解，用于标识控制器类
import org.springframework.web.bind.annotation.*;
// 导入Web绑定注解，用于处理HTTP请求
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入EntityWrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.Wrapper;
// 导入Wrapper接口，用于构建查询条件
import com.entity.*;
// 导入实体类
import com.entity.view.*;
// 导入视图类
import com.service.*;
// 导入服务类
import com.utils.PageUtils;
// 导入PageUtils类，用于分页处理
import com.utils.R;
// 导入R类，用于封装返回结果
import com.alibaba.fastjson.*;
// 导入FastJSON相关类，用于JSON处理

/**
 * 运动教程
 * 后端接口
 * @author
 * @email
 */
@RestController
// 标识该类为RESTful控制器
@RequestMapping("/yundong")
// 映射请求路径为/yundong
public class YundongController {
    private static final Logger logger = LoggerFactory.getLogger(YundongController.class);
    // 创建日志记录器实例

    private static final String TABLE_NAME = "yundong";
    // 定义表名常量

    @Autowired
    // 自动注入YundongService
    private YundongService yundongService;
    // 运动教程服务类

    @Autowired
    // 自动注入TokenService
    private TokenService tokenService;
    // Token服务类

    @Autowired
    // 自动注入DictionaryService
    private DictionaryService dictionaryService;
    // 字典服务类

    @Autowired
    // 自动注入ForumService
    private ForumService forumService;
    // 论坛服务类

    @Autowired
    // 自动注入GerentizhengService
    private GerentizhengService gerentizhengService;
    // 体检记录服务类

    @Autowired
    // 自动注入JiankangtieshiService
    private JiankangtieshiService jiankangtieshiService;
    // 健康贴士服务类

    @Autowired
    // 自动注入MeirijihuaService
    private MeirijihuaService meirijihuaService;
    // 每日计划服务类

    @Autowired
    // 自动注入MeishiService
    private MeishiService meishiService;
    // 健康食谱服务类

    @Autowired
    // 自动注入MeishiCollectionService
    private MeishiCollectionService meishiCollectionService;
    // 健康食谱收藏服务类

    @Autowired
    // 自动注入MeishiLiuyanService
    private MeishiLiuyanService meishiLiuyanService;
    // 健康食谱留言服务类

    @Autowired
    // 自动注入NewsService
    private NewsService newsService;
    // 公告信息服务类

    @Autowired
    // 自动注入YaopinService
    private YaopinService yaopinService;
    // 药品信息服务类

    @Autowired
    // 自动注入YonghuService
    private YonghuService yonghuService;
    // 用户服务类

    @Autowired
    // 自动注入YundongCollectionService
    private YundongCollectionService yundongCollectionService;
    // 运动教程收藏服务类

    @Autowired
    // 自动注入YundongLiuyanService
    private YundongLiuyanService yundongLiuyanService;
    // 运动教程留言服务类

    @Autowired
    // 自动注入UsersService
    private UsersService usersService;
    // 管理员服务类


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    // 映射请求路径为/page
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        // 处理分页请求
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
        params.put("yundongDeleteStart", 1);
        // 设置删除状态参数
        params.put("yundongDeleteEnd", 1);
        // 设置删除状态参数
        CommonUtil.checkMap(params);
        // 检查参数
        PageUtils page = yundongService.queryPage(params);
        // 查询分页数据

        // 字典表数据转换
        List<YundongView> list = (List<YundongView>) page.getList();
        // 获取分页数据列表
        for (YundongView c : list) {
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
     * 该方法用于获取指定ID的运动教程详情信息。
     */
    @RequestMapping("/info/{id}")
    // 映射请求路径为/info/{id}
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        // 处理详情请求
        logger.debug("info方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志
        YundongEntity yundong = yundongService.selectById(id);
        // 根据ID查询实体
        if (yundong != null) {
            // 如果查询到数据
            // entity转view
            YundongView view = new YundongView();
            BeanUtils.copyProperties(yundong, view);
            // 把实体数据重构到view中
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
     * 该方法用于保存新的运动教程信息。
     */
    @RequestMapping("/save")
    // 映射请求路径为/save
    public R save(@RequestBody YundongEntity yundong, HttpServletRequest request) {
        // 处理保存请求
        logger.debug("save方法:,,Controller:{},,yundong:{}", this.getClass().getName(), yundong.toString());
        // 记录日志
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色
        if (false)
            // 永远不会进入的条件
            return R.error(511, "永远不会进入");

        Wrapper<YundongEntity> queryWrapper = new EntityWrapper<YundongEntity>()
                .eq("yundong_name", yundong.getYundongName())
                .eq("yundong_types", yundong.getYundongTypes())
                .eq("yundong_video", yundong.getYundongVideo())
                .eq("yundong_num", yundong.getYundongNum())
                .eq("zan_number", yundong.getZanNumber())
                .eq("cai_number", yundong.getCaiNumber())
                .eq("yundong_delete", yundong.getYundongDelete());
        // 构建查询条件

        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 记录SQL语句
        YundongEntity yundongEntity = yundongService.selectOne(queryWrapper);
        // 查询是否存在相同数据
        if (yundongEntity == null) {
            // 如果不存在相同数据
            yundong.setYundongDelete(1);
            // 设置删除状态
            yundong.setInsertTime(new Date());
            // 设置插入时间
            yundong.setCreateTime(new Date());
            // 设置创建时间
            yundongService.insert(yundong);
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
     * 该方法用于更新指定ID的运动教程信息。
     */
    @RequestMapping("/update")
    // 映射请求路径为/update
    public R update(@RequestBody YundongEntity yundong, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 处理修改请求
        logger.debug("update方法:,,Controller:{},,yundong:{}", this.getClass().getName(), yundong.toString());
        // 记录日志
        YundongEntity oldYundongEntity = yundongService.selectById(yundong.getId());
        // 查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色
//        if(false)
//            return R.error(511,"永远不会进入");
        if ("".equals(yundong.getYundongPhoto()) || "null".equals(yundong.getYundongPhoto())) {
            // 如果运动教程图片为空或为"null"
            yundong.setYundongPhoto(null);
            // 设置为null
        }
        if ("".equals(yundong.getYundongVideo()) || "null".equals(yundong.getYundongVideo())) {
            // 如果运动教程视频为空或为"null"
            yundong.setYundongVideo(null);
            // 设置为null
        }

        yundongService.updateById(yundong);
        // 根据id更新
        return R.ok();
        // 返回成功结果
    }

    /**
     * 删除
     * 该方法用于逻辑删除指定ID的运动教程。
     */
    @RequestMapping("/delete")
    // 映射请求路径为/delete
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        // 处理删除请求
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        // 记录日志
        List<YundongEntity> oldYundongList = yundongService.selectBatchIds(Arrays.asList(ids));
        // 要删除的数据
        ArrayList<YundongEntity> list = new ArrayList<>();
        // 创建列表存储要更新的实体
        for (Integer id : ids) {
            // 遍历ID数组
            YundongEntity yundongEntity = new YundongEntity();
            // 创建实体对象
            yundongEntity.setId(id);
            // 设置ID
            yundongEntity.setYundongDelete(2);
            // 设置删除状态
            list.add(yundongEntity);
            // 添加到列表中
        }
        if (list != null && list.size() > 0) {
            // 如果列表不为空且大小大于0
            yundongService.updateBatchById(list);
            // 更新批量数据
        }

        return R.ok();
        // 返回成功结果
    }

    /**
     * 批量上传
     * 该方法用于批量上传运动教程信息。
     */
    @RequestMapping("/batchInsert")
    // 映射请求路径为/batchInsert
    public R save(String fileName, HttpServletRequest request) {
        // 处理批量上传请求
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}", this.getClass().getName(), fileName);
        // 记录日志
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        // 获取用户ID
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 初始化日期格式化器
        try {
            List<YundongEntity> yundongList = new ArrayList<>();
            // 定义运动教程信息列表
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
                            YundongEntity yundongEntity = new YundongEntity();
                            // 创建实体对象
//                            yundongEntity.setYundongName(data.get(0));                    //标题 要改的
//                            yundongEntity.setYundongPhoto("");//详情和图片
//                            yundongEntity.setYundongTypes(Integer.valueOf(data.get(0)));   //运动类型 要改的
//                            yundongEntity.setYundongVideo(data.get(0));                    //运动视频 要改的
//                            yundongEntity.setYundongNum(Integer.valueOf(data.get(0)));   //消耗热量 要改的
//                            yundongEntity.setZanNumber(Integer.valueOf(data.get(0)));   //赞 要改的
//                            yundongEntity.setCaiNumber(Integer.valueOf(data.get(0)));   //踩 要改的
//                            yundongEntity.setYundongContent("");//详情和图片
//                            yundongEntity.setYundongDelete(1);//逻辑删除字段
//                            yundongEntity.setInsertTime(date);//时间
//                            yundongEntity.setCreateTime(date);//时间
                            yundongList.add(yundongEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        yundongService.insertBatch(yundongList);
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
     * 个性推荐
     * 该方法用于根据用户的收藏记录推荐运动教程。
     */
    @IgnoreAuth
    // 忽略权限验证
    @RequestMapping("/gexingtuijian")
    // 映射请求路径为/gexingtuijian
    public R gexingtuijian(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        // 处理个性推荐请求
        logger.debug("gexingtuijian方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志
        CommonUtil.checkMap(params);
        // 检查参数
        List<YundongView> returnYundongViewList = new ArrayList<>();
        // 定义返回的运动教程视图列表

        // 查看收藏
        Map<String, Object> params1 = new HashMap<>(params);
        params1.put("sort", "id");
        // 设置排序参数
        params1.put("yonghuId", request.getSession().getAttribute("userId"));
        // 设置用户ID
        PageUtils pageUtils = yundongCollectionService.queryPage(params1);
        // 查询用户收藏的运动教程
        List<YundongCollectionView> collectionViewsList = (List<YundongCollectionView>) pageUtils.getList();
        // 获取收藏列表
        Map<Integer, Integer> typeMap = new HashMap<>();
        // 定义一个Map来统计每种类型的收藏数量
        for (YundongCollectionView collectionView : collectionViewsList) {
            // 遍历收藏列表
            Integer yundongTypes = collectionView.getYundongTypes();
            // 获取运动类型
            if (typeMap.containsKey(yundongTypes)) {
                // 如果类型已存在
                typeMap.put(yundongTypes, typeMap.get(yundongTypes) + 1);
                // 增加计数
            } else {
                typeMap.put(yundongTypes, 1);
                // 初始化计数
            }
        }
        List<Integer> typeList = new ArrayList<>();
        // 定义一个列表来存储排序后的类型
        typeMap.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).forEach(e -> typeList.add(e.getKey()));
        // 按照收藏数量从多到少排序，并添加到typeList中
        Integer limit = Integer.valueOf(String.valueOf(params.get("limit")));
        // 获取推荐数量限制
        for (Integer type : typeList) {
            // 遍历排序后的类型列表
            Map<String, Object> params2 = new HashMap<>(params);
            params2.put("yundongTypes", type);
            // 设置运动类型参数
            PageUtils pageUtils1 = yundongService.queryPage(params2);
            // 查询该类型的运动教程
            List<YundongView> yundongViewList = (List<YundongView>) pageUtils1.getList();
            // 获取查询结果
            returnYundongViewList.addAll(yundongViewList);
            // 添加到返回列表中
            if (returnYundongViewList.size() >= limit) break;
            // 如果返回列表数量达到限制，跳出循环
        }
        // 正常查询出来商品,用于补全推荐缺少的数据
        PageUtils page = yundongService.queryPage(params);
        // 查询所有运动教程
        if (returnYundongViewList.size() < limit) {
            // 如果返回列表数量小于限制
            int toAddNum = limit - returnYundongViewList.size();
            // 计算需要添加的数量
            List<YundongView> yundongViewList = (List<YundongView>) page.getList();
            // 获取所有运动教程列表
            for (YundongView yundongView : yundongViewList) {
                // 遍历所有运动教程
                Boolean addFlag = true;
                for (YundongView returnYundongView : returnYundongViewList) {
                    // 遍历返回列表
                    if (returnYundongView.getId().intValue() == yundongView.getId().intValue()) addFlag = false;
                    // 如果返回列表中已存在此运动教程，设置标志为false
                }
                if (addFlag) {
                    // 如果标志为true
                    toAddNum = toAddNum - 1;
                    // 减少需要添加的数量
                    returnYundongViewList.add(yundongView);
                    // 添加到返回列表中
                    if (toAddNum == 0) break;
                    // 如果需要添加的数量为0，跳出循环
                }
            }
        } else {
            returnYundongViewList = returnYundongViewList.subList(0, limit);
            // 如果返回列表数量大于限制，截取前limit个
        }

        for (YundongView c : returnYundongViewList)
            dictionaryService.dictionaryConvert(c, request);
        // 转换字典数据
        page.setList(returnYundongViewList);
        // 设置返回的运动教程列表
        return R.ok().put("data", page);
        // 返回结果
    }


    /**
     * 前端列表
     * 该方法用于查询运动教程的前端分页列表。
     */
    @IgnoreAuth
// 忽略权限验证
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("list方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志，打印当前控制器名称和参数

        CommonUtil.checkMap(params);
        // 检查并处理请求参数

        PageUtils page = yundongService.queryPage(params);
        // 调用服务层方法查询分页数据

        // 字典表数据转换
        List<YundongView> list = (List<YundongView>) page.getList();
        // 获取分页数据中的列表
        for (YundongView c : list)
            dictionaryService.dictionaryConvert(c, request); // 将字典表字段转换为可读格式

        return R.ok().put("data", page);
        // 返回封装的成功结果，并将分页数据放入返回对象中
    }

    /**
     * 前端详情
     * 该方法用于根据ID查询运动教程的详细信息。
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志，打印当前控制器名称和ID参数

        YundongEntity yundong = yundongService.selectById(id);
        // 根据ID查询实体数据

        if (yundong != null) {
            // 如果查询到数据

            // entity转view
            YundongView view = new YundongView();
            BeanUtils.copyProperties(yundong, view); // 将实体数据复制到视图对象中

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
     * 该方法用于保存新的运动教程信息。
     */
    @RequestMapping("/add")
    public R add(@RequestBody YundongEntity yundong, HttpServletRequest request) {
        logger.debug("add方法:,,Controller:{},,yundong:{}", this.getClass().getName(), yundong.toString());
        // 记录日志，打印当前控制器名称和传入的实体数据

        Wrapper<YundongEntity> queryWrapper = new EntityWrapper<YundongEntity>()
                .eq("yundong_name", yundong.getYundongName())
                .eq("yundong_types", yundong.getYundongTypes())
                .eq("yundong_video", yundong.getYundongVideo())
                .eq("yundong_num", yundong.getYundongNum())
                .eq("zan_number", yundong.getZanNumber())
                .eq("cai_number", yundong.getCaiNumber())
                .eq("yundong_delete", yundong.getYundongDelete());
        // 构建查询条件，检查是否已存在相同数据

        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 打印生成的SQL语句

        YundongEntity yundongEntity = yundongService.selectOne(queryWrapper);
        // 查询是否存在相同数据

        if (yundongEntity == null) {
            // 如果不存在相同数据

            yundong.setZanNumber(1);
            yundong.setCaiNumber(1);
            // 初始化赞和踩的数量为1

            yundong.setYundongDelete(1);
            // 设置逻辑删除状态为未删除

            yundong.setInsertTime(new Date());
            yundong.setCreateTime(new Date());
            // 设置插入时间和创建时间

            yundongService.insert(yundong);
            // 插入新数据

            return R.ok();
            // 返回成功结果
        } else {
            return R.error(511, "表中有相同数据");
            // 如果已存在相同数据，返回错误信息
        }
    }
}
