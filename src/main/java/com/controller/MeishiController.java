
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
 * 健康食谱
 * 后端接口
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/meishi")
public class MeishiController {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(MeishiController.class);

    // 表名常量
    private static final String TABLE_NAME = "meishi";

    // 自动注入健康食谱服务
    @Autowired
    private MeishiService meishiService;

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

    // 自动注入健康食谱收藏服务
    @Autowired
    private MeishiCollectionService meishiCollectionService;

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
        // 设置删除状态
        params.put("meishiDeleteStart",1);params.put("meishiDeleteEnd",1);
        // 检查参数
        CommonUtil.checkMap(params);
        // 查询分页数据
        PageUtils page = meishiService.queryPage(params);

        // 字典表数据转换
        List<MeishiView> list =(List<MeishiView>)page.getList();
        for(MeishiView c:list){
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     * @param id 健康食谱ID
     * @param request HTTP请求对象
     * @return 健康食谱详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        // 记录日志
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        // 根据ID查询健康食谱
        MeishiEntity meishi = meishiService.selectById(id);
        if(meishi !=null){
            // entity转view
            MeishiView view = new MeishiView();
            BeanUtils.copyProperties( meishi , view );
            // 把实体数据重构到view中
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
     * 后端保存
     * @param meishi 健康食谱实体
     * @param request HTTP请求对象
     * @return 保存结果
     */
    @RequestMapping("/save")
    public R save(@RequestBody MeishiEntity meishi, HttpServletRequest request){
        // 记录日志
        logger.debug("save方法:,,Controller:{},,meishi:{}",this.getClass().getName(),meishi.toString());

        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 根据角色设置用户ID
        if(false)
            return R.error(511,"永远不会进入");

        // 构建查询条件
        Wrapper<MeishiEntity> queryWrapper = new EntityWrapper<MeishiEntity>()
                .eq("meishi_name", meishi.getMeishiName())
                .eq("meishi_types", meishi.getMeishiTypes())
                .eq("meishi_num", meishi.getMeishiNum())
                .eq("zan_number", meishi.getZanNumber())
                .eq("cai_number", meishi.getCaiNumber())
                .eq("meishi_delete", meishi.getMeishiDelete())
                ;

        // 记录SQL语句
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        MeishiEntity meishiEntity = meishiService.selectOne(queryWrapper);
        if(meishiEntity==null){
            // 设置删除状态和时间戳
            meishi.setMeishiDelete(1);
            meishi.setInsertTime(new Date());
            meishi.setCreateTime(new Date());
            // 插入数据
            meishiService.insert(meishi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
     * 后端修改
     * @param meishi 健康食谱实体
     * @param request HTTP请求对象
     * @return 修改结果
     */
    @RequestMapping("/update")
    public R update(@RequestBody MeishiEntity meishi, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 记录日志
        logger.debug("update方法:,,Controller:{},,meishi:{}",this.getClass().getName(),meishi.toString());
        // 查询原先数据
        MeishiEntity oldMeishiEntity = meishiService.selectById(meishi.getId());

        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        // 如果图片字段为空，设置为null
        if("".equals(meishi.getMeishiPhoto()) || "null".equals(meishi.getMeishiPhoto())){
            meishi.setMeishiPhoto(null);
        }

        // 根据ID更新数据
        meishiService.updateById(meishi);
        return R.ok();
    }

    /**
     * 删除
     * @param ids 健康食谱ID数组
     * @param request HTTP请求对象
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        // 记录日志
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        // 要删除的数据
        List<MeishiEntity> oldMeishiList =meishiService.selectBatchIds(Arrays.asList(ids));
        ArrayList<MeishiEntity> list = new ArrayList<>();
        for(Integer id:ids){
            MeishiEntity meishiEntity = new MeishiEntity();
            meishiEntity.setId(id);
            meishiEntity.setMeishiDelete(2);
            list.add(meishiEntity);
        }
        if(list != null && list.size() >0){
            meishiService.updateBatchById(list);
        }

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
            List<MeishiEntity> meishiList = new ArrayList<>();
            // 上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();
            // 要查询的字段
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
                        dataList.remove(0);
                        // 删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            // 循环
                            MeishiEntity meishiEntity = new MeishiEntity();
//                            meishiEntity.setMeishiName(data.get(0));
// 食谱标题 要改的
//                            meishiEntity.setMeishiPhoto("");// 详情和图片
//                            meishiEntity.setMeishiTypes(Integer.valueOf(data.get(0)));
// 食谱类型 要改的
//                            meishiEntity.setMeishiNum(Integer.valueOf(data.get(0)));
// 所含热量 要改的
//                            meishiEntity.setZanNumber(Integer.valueOf(data.get(0)));
// 赞 要改的
//                            meishiEntity.setCaiNumber(Integer.valueOf(data.get(0)));
// 踩 要改的
//                            meishiEntity.setMeishiContent("");
// 详情和图片
//                            meishiEntity.setMeishiDelete(1);
// 逻辑删除字段
//                            meishiEntity.setInsertTime(date);
// 时间
//                            meishiEntity.setCreateTime(date);
// 时间
                            meishiList.add(meishiEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        meishiService.insertBatch(meishiList);
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
     * 个性推荐
     * @param params 请求参数
     * @param request HTTP请求对象
     * @return 推荐结果
     */
    @IgnoreAuth
    @RequestMapping("/gexingtuijian")
    public R gexingtuijian(@RequestParam Map<String, Object> params, HttpServletRequest request){
        // 记录日志
        logger.debug("gexingtuijian方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        // 检查参数
        CommonUtil.checkMap(params);
        List<MeishiView> returnMeishiViewList = new ArrayList<>();

        // 查看收藏
        Map<String, Object> params1 = new HashMap<>(params);params1.put("sort","id");params1.put("yonghuId",request.getSession().getAttribute("userId"));
        PageUtils pageUtils = meishiCollectionService.queryPage(params1);
        List<MeishiCollectionView> collectionViewsList =(List<MeishiCollectionView>)pageUtils.getList();
        Map<Integer,Integer> typeMap=new HashMap<>();
        // 收藏的类型列表
        for(MeishiCollectionView collectionView:collectionViewsList){
            Integer meishiTypes = collectionView.getMeishiTypes();
            if(typeMap.containsKey(meishiTypes)){
                typeMap.put(meishiTypes,typeMap.get(meishiTypes)+1);
            }else{
                typeMap.put(meishiTypes,1);
            }
        }
        List<Integer> typeList = new ArrayList<>();
        // 排序后的有序类型 按最多到最少
        typeMap.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).forEach(e -> typeList.add(e.getKey()));
        // 排序
        Integer limit = Integer.valueOf(String.valueOf(params.get("limit")));
        for(Integer type:typeList){
            Map<String, Object> params2 = new HashMap<>(params);params2.put("meishiTypes",type);
            PageUtils pageUtils1 = meishiService.queryPage(params2);
            List<MeishiView> meishiViewList =(List<MeishiView>)pageUtils1.getList();
            returnMeishiViewList.addAll(meishiViewList);
            if(returnMeishiViewList.size()>= limit) break;
            // 返回的推荐数量大于要的数量 跳出循环
        }
        // 正常查询出来商品,用于补全推荐缺少的数据
        PageUtils page = meishiService.queryPage(params);
        if(returnMeishiViewList.size()<limit){
            // 返回数量还是小于要求数量
            int toAddNum = limit - returnMeishiViewList.size();
            // 要添加的数量
            List<MeishiView> meishiViewList =(List<MeishiView>)page.getList();
            for(MeishiView meishiView:meishiViewList){
                Boolean addFlag = true;
                for(MeishiView returnMeishiView:returnMeishiViewList){
                    if(returnMeishiView.getId().intValue() ==meishiView.getId().intValue()) addFlag=false;
                    // 返回的数据中已存在此商品
                }
                if(addFlag){
                    toAddNum=toAddNum-1;
                    returnMeishiViewList.add(meishiView);
                    if(toAddNum==0) break;
                    // 够数量了
                }
            }
        }else {
            returnMeishiViewList = returnMeishiViewList.subList(0, limit);
        }

        for(MeishiView c:returnMeishiViewList)
            dictionaryService.dictionaryConvert(c, request);
        page.setList(returnMeishiViewList);
        return R.ok().put("data", page);
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
        PageUtils page = meishiService.queryPage(params);

        // 字典表数据转换
        List<MeishiView> list =(List<MeishiView>)page.getList();
        for(MeishiView c:list)
            dictionaryService.dictionaryConvert(c, request);
        // 修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     * @param id 健康食谱ID
     * @param request HTTP请求对象
     * @return 健康食谱详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        // 记录日志
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        // 根据ID查询健康食谱
        MeishiEntity meishi = meishiService.selectById(id);
        if(meishi !=null){
            // entity转view
            MeishiView view = new MeishiView();
            BeanUtils.copyProperties( meishi , view );
            // 把实体数据重构到view中

            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }

    /**
     * 前端保存
     * @param meishi 健康食谱实体
     * @param request HTTP请求对象
     * @return 保存结果
     */
    @RequestMapping("/add")
    public R add(@RequestBody MeishiEntity meishi, HttpServletRequest request){
        // 记录日志
        logger.debug("add方法:,,Controller:{},,meishi:{}",this.getClass().getName(),meishi.toString());
        // 构建查询条件
        Wrapper<MeishiEntity> queryWrapper = new EntityWrapper<MeishiEntity>()
                .eq("meishi_name", meishi.getMeishiName())
                .eq("meishi_types", meishi.getMeishiTypes())
                // 添加查询条件：所含热量
                .eq("meishi_num", meishi.getMeishiNum())
                // 添加查询条件：赞的数量
                .eq("zan_number", meishi.getZanNumber())
                // 添加查询条件：踩的数量
                .eq("cai_number", meishi.getCaiNumber())
                // 添加查询条件：删除状态
                .eq("meishi_delete", meishi.getMeishiDelete())
//            .notIn("meishi_types", new Integer[]{102}) // 排除特定类型的健康食谱
                ;
        // 记录SQL语句
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        MeishiEntity meishiEntity = meishiService.selectOne(queryWrapper);
        if(meishiEntity==null){
            // 设置默认赞的数量为1
            meishi.setZanNumber(1);
            // 设置默认踩的数量为1
            meishi.setCaiNumber(1);
            // 设置删除状态为1（未删除）
            meishi.setMeishiDelete(1);
            // 设置插入时间
            meishi.setInsertTime(new Date());
            // 设置创建时间
            meishi.setCreateTime(new Date());
            // 插入新数据
            meishiService.insert(meishi);

            // 返回成功响应
            return R.ok();
        }else {
            // 返回错误响应，提示表中已有相同数据
            return R.error(511,"表中有相同数据");
        }
    }
}
