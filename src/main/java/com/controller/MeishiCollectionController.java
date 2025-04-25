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
 * 健康食谱收藏
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/meishiCollection")
public class MeishiCollectionController {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(MeishiCollectionController.class);

    // 表名常量
    private static final String TABLE_NAME = "meishiCollection";

    // 自动注入健康食谱收藏服务
    @Autowired
    private MeishiCollectionService meishiCollectionService;

    // 自动注入Token服务
    @Autowired
    private TokenService tokenService;

    // 自动注入字典服务
    @Autowired
    private DictionaryService dictionaryService;

    // 自动注入论坛服务
    @Autowired
    private ForumService forumService;

    // 自动注入体检记录服务
    @Autowired
    private GerentizhengService gerentizhengService;

    // 自动注入健康贴士服务
    @Autowired
    private JiankangtieshiService jiankangtieshiService;

    // 自动注入每日计划服务
    @Autowired
    private MeirijihuaService meirijihuaService;

    // 自动注入健康食谱服务
    @Autowired
    private MeishiService meishiService;

    // 自动注入健康食谱留言服务
    @Autowired
    private MeishiLiuyanService meishiLiuyanService;

    // 自动注入公告信息服务
    @Autowired
    private NewsService newsService;

    // 自动注入药品信息服务
    @Autowired
    private YaopinService yaopinService;

    // 自动注入用户服务
    @Autowired
    private YonghuService yonghuService;

    // 自动注入运动教程服务
    @Autowired
    private YundongService yundongService;

    // 自动注入运动教程收藏服务
    @Autowired
    private YundongCollectionService yundongCollectionService;

    // 自动注入运动教程留言服务
    @Autowired
    private YundongLiuyanService yundongLiuyanService;

    // 自动注入管理员服务
    @Autowired
    private UsersService usersService;

    /**
     * 后端列表
     * @param params 请求参数
     * @param request HTTP请求对象
     * @return 分页数据
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        // 记录日志
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 根据角色设置查询参数
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        // 检查参数
        CommonUtil.checkMap(params);
        // 查询分页数据
        PageUtils page = meishiCollectionService.queryPage(params);

        // 字典表数据转换
        List<MeishiCollectionView> list =(List<MeishiCollectionView>)page.getList();
        for(MeishiCollectionView c:list){
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     * @param id 健康食谱收藏ID
     * @param request HTTP请求对象
     * @return 健康食谱收藏详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        // 记录日志
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        // 根据ID查询健康食谱收藏
        MeishiCollectionEntity meishiCollection = meishiCollectionService.selectById(id);
        if(meishiCollection !=null){
            // entity转view
            MeishiCollectionView view = new MeishiCollectionView();
            BeanUtils.copyProperties( meishiCollection , view );
            // 把实体数据重构到view中
            // 级联表 健康食谱
            // 级联表
            MeishiEntity meishi = meishiService.selectById(meishiCollection.getMeishiId());
            if(meishi != null){
                BeanUtils.copyProperties( meishi , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});
                // 把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
                view.setMeishiId(meishi.getId());
            }
            // 级联表 用户
            // 级联表
            YonghuEntity yonghu = yonghuService.selectById(meishiCollection.getYonghuId());
            if(yonghu != null){
                BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});
                // 把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
                view.setYonghuId(yonghu.getId());
            }
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
     * 后端保存
     * @param meishiCollection 健康食谱收藏实体
     * @param request HTTP请求对象
     * @return 保存结果
     */
    @RequestMapping("/save")
    public R save(@RequestBody MeishiCollectionEntity meishiCollection, HttpServletRequest request){
        // 记录日志
        logger.debug("save方法:,,Controller:{},,meishiCollection:{}",this.getClass().getName(),meishiCollection.toString());

        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 根据角色设置用户ID
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            meishiCollection.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        // 构建查询条件
        Wrapper<MeishiCollectionEntity> queryWrapper = new EntityWrapper<MeishiCollectionEntity>()
            .eq("meishi_id", meishiCollection.getMeishiId())
            .eq("yonghu_id", meishiCollection.getYonghuId())
            .eq("meishi_collection_types", meishiCollection.getMeishiCollectionTypes())
            ;

        // 记录SQL语句
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        MeishiCollectionEntity meishiCollectionEntity = meishiCollectionService.selectOne(queryWrapper);
        if(meishiCollectionEntity==null){
            // 设置时间戳
            meishiCollection.setInsertTime(new Date());
            meishiCollection.setCreateTime(new Date());
            // 插入数据
            meishiCollectionService.insert(meishiCollection);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
     * 后端修改
     * @param meishiCollection 健康食谱收藏实体
     * @param request HTTP请求对象
     * @return 修改结果
     */
    @RequestMapping("/update")
    public R update(@RequestBody MeishiCollectionEntity meishiCollection, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 记录日志
        logger.debug("update方法:,,Controller:{},,meishiCollection:{}",this.getClass().getName(),meishiCollection.toString());
        // 查询原先数据
        MeishiCollectionEntity oldMeishiCollectionEntity = meishiCollectionService.selectById(meishiCollection.getId());

        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            meishiCollection.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        // 根据ID更新数据
        meishiCollectionService.updateById(meishiCollection);
        return R.ok();
    }

    /**
     * 删除
     * @param ids 健康食谱收藏ID数组
     * @param request HTTP请求对象
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        // 记录日志
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        // 要删除的数据
        List<MeishiCollectionEntity> oldMeishiCollectionList =meishiCollectionService.selectBatchIds(Arrays.asList(ids));
        // 批量删除数据
        meishiCollectionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 批量上传
     * @param fileName 文件名
     * @param request HTTP请求对象
     * @return 上传结果
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        // 记录日志
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        // 获取用户ID
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<MeishiCollectionEntity> meishiCollectionList = new ArrayList<>();// 上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();// 要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);
                    // 获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());
                        // 读取xls文件
                        dataList.remove(0);// 删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            // 循环
                            MeishiCollectionEntity meishiCollectionEntity = new MeishiCollectionEntity();
//                            meishiCollectionEntity.setMeishiId(Integer.valueOf(data.get(0)));
// 健康食谱 要改的
//                            meishiCollectionEntity.setYonghuId(Integer.valueOf(data.get(0)));
// 用户 要改的
//                            meishiCollectionEntity.setMeishiCollectionTypes(Integer.valueOf(data.get(0)));
// 类型 要改的
//                            meishiCollectionEntity.setInsertTime(date);// 时间
//                            meishiCollectionEntity.setCreateTime(date);// 时间
                            meishiCollectionList.add(meishiCollectionEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        meishiCollectionService.insertBatch(meishiCollectionList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }

    /**
     * 前端列表
     * @param params 请求参数
     * @param request HTTP请求对象
     * @return 分页数据
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        // 记录日志
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 检查参数
        CommonUtil.checkMap(params);
        // 查询分页数据
        PageUtils page = meishiCollectionService.queryPage(params);

        // 字典表数据转换
        List<MeishiCollectionView> list =(List<MeishiCollectionView>)page.getList();
        for(MeishiCollectionView c:list)
            dictionaryService.dictionaryConvert(c, request);
        // 修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     * @param id 健康食谱收藏ID
     * @param request HTTP请求对象
     * @return 健康食谱收藏详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        // 记录日志
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        // 根据ID查询健康食谱收藏
        MeishiCollectionEntity meishiCollection = meishiCollectionService.selectById(id);
        if(meishiCollection !=null){
            // entity转view
            MeishiCollectionView view = new MeishiCollectionView();
            BeanUtils.copyProperties( meishiCollection , view );
            // 把实体数据重构到view中

            // 级联表
            MeishiEntity meishi = meishiService.selectById(meishiCollection.getMeishiId());
            if(meishi != null){
                BeanUtils.copyProperties( meishi , view ,new String[]{ "id", "createDate"});
                // 把级联的数据添加到view中,并排除id和创建时间字段
                view.setMeishiId(meishi.getId());
            }
            // 级联表
            YonghuEntity yonghu = yonghuService.selectById(meishiCollection.getYonghuId());
            if(yonghu != null){
                BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});
                // 把级联的数据添加到view中,并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }

    /**
     * 前端保存
     * @param meishiCollection 健康食谱收藏实体
     * @param request HTTP请求对象
     * @return 保存结果
     */
    @RequestMapping("/add")
    public R add(@RequestBody MeishiCollectionEntity meishiCollection, HttpServletRequest request){
        // 记录日志
        logger.debug("add方法:,,Controller:{},,meishiCollection:{}",this.getClass().getName(),meishiCollection.toString());
        // 构建查询条件
        Wrapper<MeishiCollectionEntity> queryWrapper = new EntityWrapper<MeishiCollectionEntity>()
            .eq("meishi_id", meishiCollection.getMeishiId())
            .eq("yonghu_id", meishiCollection.getYonghuId())
            .eq("meishi_collection_types", meishiCollection.getMeishiCollectionTypes())
//            .notIn("meishi_collection_types", new Integer[]{102})
            ;
        // 记录SQL语句
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        MeishiCollectionEntity meishiCollectionEntity = meishiCollectionService.selectOne(queryWrapper);
        if(meishiCollectionEntity==null){
            // 设置时间戳
            meishiCollection.setInsertTime(new Date());
            meishiCollection.setCreateTime(new Date());
            // 插入数据
            meishiCollectionService.insert(meishiCollection);

            return R.ok();
        }else {
            return R.error(511,"您已经收藏过了");
        }
    }

}
