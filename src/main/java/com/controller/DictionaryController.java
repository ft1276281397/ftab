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
 * 字典
 * 后端接口
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    // 定义表名常量
    private static final String TABLE_NAME = "dictionary";

    // 自动注入DictionaryService
    @Autowired
    private DictionaryService dictionaryService;

    // 自动注入TokenService
    @Autowired
    private TokenService tokenService;

    // 自动注入ForumService
    @Autowired
    private ForumService forumService; // 论坛

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
    @IgnoreAuth
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        // 记录日志
        logger.debug("page方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 检查参数
        CommonUtil.checkMap(params);
        // 调用服务层方法查询分页数据
        PageUtils page = dictionaryService.queryPage(params);

        // 字典表数据转换
        List<DictionaryView> list = (List<DictionaryView>) page.getList();
        for (DictionaryView c : list) {
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
        // 根据ID查询字典实体
        DictionaryEntity dictionary = dictionaryService.selectById(id);
        if (dictionary != null) {
            // 实体转视图
            DictionaryView view = new DictionaryView();
            BeanUtils.copyProperties(dictionary, view); // 把实体数据重构到视图中
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
    public R save(@RequestBody DictionaryEntity dictionary, HttpServletRequest request) {
        // 记录日志
        logger.debug("save方法:,,Controller:{},,dictionary:{}", this.getClass().getName(), dictionary.toString());

        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (false)
            return R.error(511, "永远不会进入");

        // 构建查询条件
        Wrapper<DictionaryEntity> queryWrapper = new EntityWrapper<DictionaryEntity>()
                .eq("dic_code", dictionary.getDicCode())
                .eq("index_name", dictionary.getIndexName());
        if (dictionary.getDicCode().contains("_erji_types")) {
            queryWrapper.eq("super_id", dictionary.getSuperId());
        }

        // 记录SQL语句
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        DictionaryEntity dictionaryEntity = dictionaryService.selectOne(queryWrapper);
        if (dictionaryEntity == null) {
            // 设置创建时间
            dictionary.setCreateTime(new Date());
            // 插入新数据
            dictionaryService.insert(dictionary);
            // 字典表新增数据,把数据再重新查出,放入监听器中
            List<DictionaryEntity> dictionaryEntities = dictionaryService.selectList(new EntityWrapper<DictionaryEntity>());
            ServletContext servletContext = request.getServletContext();
            Map<String, Map<Integer, String>> map = new HashMap<>();
            for (DictionaryEntity d : dictionaryEntities) {
                Map<Integer, String> m = map.get(d.getDicCode());
                if (m == null || m.isEmpty()) {
                    m = new HashMap<>();
                }
                m.put(d.getCodeIndex(), d.getIndexName());
                map.put(d.getDicCode(), m);
            }
            servletContext.setAttribute("dictionaryMap", map);
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
    public R update(@RequestBody DictionaryEntity dictionary, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 记录日志
        logger.debug("update方法:,,Controller:{},,dictionary:{}", this.getClass().getName(), dictionary.toString());
        // 查询原先数据
        DictionaryEntity oldDictionaryEntity = dictionaryService.selectById(dictionary.getId());

        // 获取当前用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");

        // 根据ID更新数据
        dictionaryService.updateById(dictionary);
        // 如果字典表修改数据的话,把数据再重新查出,放入监听器中
        List<DictionaryEntity> dictionaryEntities = dictionaryService.selectList(new EntityWrapper<DictionaryEntity>());
        ServletContext servletContext = request.getServletContext();
        Map<String, Map<Integer, String>> map = new HashMap<>();
        for (DictionaryEntity d : dictionaryEntities) {
            Map<Integer, String> m = map.get(d.getDicCode());
            if (m == null || m.isEmpty()) {
                m = new HashMap<>();
            }
            m.put(d.getCodeIndex(), d.getIndexName());
            map.put(d.getDicCode(), m);
        }
        servletContext.setAttribute("dictionaryMap", map);
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
        List<DictionaryEntity> oldDictionaryList = dictionaryService.selectBatchIds(Arrays.asList(ids));
        // 批量删除数据
        dictionaryService.deleteBatchIds(Arrays.asList(ids));
        // 返回成功信息
        return R.ok();
    }

    /**
     * 最大值
     */
    @RequestMapping("/maxCodeIndex")
    public R maxCodeIndex(@RequestBody DictionaryEntity dictionary) {
        // 记录日志
        logger.debug("maxCodeIndex:,,Controller:{},,dictionary:{}", this.getClass().getName(), dictionary.toString());
        // 设置排序字段
        List<String> descs = new ArrayList<>();
        descs.add("code_index");
        // 构建查询条件
        Wrapper<DictionaryEntity> queryWrapper = new EntityWrapper<DictionaryEntity>()
                .eq("dic_code", dictionary.getDicCode())
                .orderDesc(descs);
        // 记录SQL语句
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 查询列表
        List<DictionaryEntity> dictionaryEntityList = dictionaryService.selectList(queryWrapper);
        if (dictionaryEntityList.size() > 0) {
            // 返回最大codeIndex + 1
            return R.ok().put("maxCodeIndex", dictionaryEntityList.get(0).getCodeIndex() + 1);
        } else {
            // 返回默认值1
            return R.ok().put("maxCodeIndex", 1);
        }
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
            // 初始化字典实体列表
            List<DictionaryEntity> dictionaryList = new ArrayList<>(); // 上传的东西
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
                            DictionaryEntity dictionaryEntity = new DictionaryEntity();
//                            dictionaryEntity.setDicCode(data.get(0));                    // 字段 要改的
//                            dictionaryEntity.setDicName(data.get(0));                    // 字段名 要改的
//                            dictionaryEntity.setCodeIndex(Integer.valueOf(data.get(0)));   // 编码 要改的
//                            dictionaryEntity.setIndexName(data.get(0));                    // 编码名字 要改的
//                            dictionaryEntity.setSuperId(Integer.valueOf(data.get(0)));   // 父字段id 要改的
//                            dictionaryEntity.setBeizhu(data.get(0));                    // 备注 要改的
//                            dictionaryEntity.setCreateTime(date);// 时间
                            dictionaryList.add(dictionaryEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        dictionaryService.insertBatch(dictionaryList);
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
}
