package com.controller;

// 导入所需的类
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

// 导入服务类
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
 * 公告信息
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/news")
public class NewsController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    // 定义表名常量
    private static final String TABLE_NAME = "news";

    // 自动注入NewsService
    @Autowired
    private NewsService newsService;

    // 自动注入TokenService
    @Autowired
    private TokenService tokenService;

    // 自动注入DictionaryService
    @Autowired
    private DictionaryService dictionaryService;//字典

    // 自动注入ForumService
    @Autowired
    private ForumService forumService;//论坛

    // 自动注入GerentizhengService
    @Autowired
    private GerentizhengService gerentizhengService;//体检记录

    // 自动注入JiankangtieshiService
    @Autowired
    private JiankangtieshiService jiankangtieshiService;//健康贴士

    // 自动注入MeirijihuaService
    @Autowired
    private MeirijihuaService meirijihuaService;//每日计划

    // 自动注入MeishiService
    @Autowired
    private MeishiService meishiService;//健康食谱

    // 自动注入MeishiCollectionService
    @Autowired
    private MeishiCollectionService meishiCollectionService;//健康食谱收藏

    // 自动注入MeishiLiuyanService
    @Autowired
    private MeishiLiuyanService meishiLiuyanService;//健康食谱留言

    // 自动注入YaopinService
    @Autowired
    private YaopinService yaopinService;//药品信息

    // 自动注入YonghuService
    @Autowired
    private YonghuService yonghuService;//用户

    // 自动注入YundongService
    @Autowired
    private YundongService yundongService;//运动教程

    // 自动注入YundongCollectionService
    @Autowired
    private YundongCollectionService yundongCollectionService;//运动教程收藏

    // 自动注入YundongLiuyanService
    @Autowired
    private YundongLiuyanService yundongLiuyanService;//运动教程留言

    // 自动注入UsersService
    @Autowired
    private UsersService usersService;//管理员

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        // 记录日志
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 永远不会进入的条件
        if(false)
            return R.error(511,"永不会进入");
        // 如果用户角色为“用户”，则设置用户ID
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        // 检查参数
        CommonUtil.checkMap(params);
        // 查询分页数据
        PageUtils page = newsService.queryPage(params);

        // 字典表数据转换
        List<NewsView> list =(List<NewsView>)page.getList();
        for(NewsView c:list){
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        // 返回结果
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        // 记录日志
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        // 根据ID查询实体
        NewsEntity news = newsService.selectById(id);
        if(news !=null){
            // entity转view
            NewsView view = new NewsView();
            BeanUtils.copyProperties( news , view );//把实体数据重构到view中
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 返回结果
            return R.ok().put("data", view);
        }else {
            // 返回错误信息
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody NewsEntity news, HttpServletRequest request){
        // 记录日志
        logger.debug("save方法:,,Controller:{},,news:{}",this.getClass().getName(),news.toString());

        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 永远不会进入的条件
        if(false)
            return R.error(511,"永远不会进入");

        // 构建查询条件
        Wrapper<NewsEntity> queryWrapper = new EntityWrapper<NewsEntity>()
            .eq("news_name", news.getNewsName())
            .eq("news_types", news.getNewsTypes())
            ;

        // 记录SQL语句
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        NewsEntity newsEntity = newsService.selectOne(queryWrapper);
        if(newsEntity==null){
            // 设置创建时间和插入时间
            news.setInsertTime(new Date());
            news.setCreateTime(new Date());
            // 插入数据
            newsService.insert(news);
            // 返回结果
            return R.ok();
        }else {
            // 返回错误信息
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody NewsEntity news, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 记录日志
        logger.debug("update方法:,,Controller:{},,news:{}",this.getClass().getName(),news.toString());
        // 查询原先数据
        NewsEntity oldNewsEntity = newsService.selectById(news.getId());

        // 获取用户角色
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        // 如果新闻照片为空或为"null"，则设置为null
        if("".equals(news.getNewsPhoto()) || "null".equals(news.getNewsPhoto())){
                news.setNewsPhoto(null);
        }

            // 根据id更新
            newsService.updateById(news);
            // 返回结果
            return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        // 记录日志
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        // 要删除的数据
        List<NewsEntity> oldNewsList =newsService.selectBatchIds(Arrays.asList(ids));
        // 删除数据
        newsService.deleteBatchIds(Arrays.asList(ids));

        // 返回结果
        return R.ok();
    }

    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        // 记录日志
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        // 获取用户ID
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 上传的东西
            List<NewsEntity> newsList = new ArrayList<>();
            // 要查询的字段
            Map<String, List<String>> seachFields= new HashMap<>();
            // 获取当前时间
            Date date = new Date();
            // 获取文件后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                // 返回错误信息
                return R.error(511,"该文件没有后缀");
            }else{
                // 获取文件后缀
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    // 返回错误信息
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    // 获取文件路径
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);
                    // 获取文件对象
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        // 返回错误信息
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        // 读取xls文件
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());
                        // 删除第一行，因为第一行是提示
                        dataList.remove(0);
                        for(List<String> data:dataList){
                            // 循环
                            NewsEntity newsEntity = new NewsEntity();
//                            newsEntity.setNewsName(data.get(0));                    //公告标题 要改的
//                            newsEntity.setNewsTypes(Integer.valueOf(data.get(0)));   //公告类型 要改的
//                            newsEntity.setNewsPhoto("");//详情和图片
//                            newsEntity.setInsertTime(date);//时间
//                            newsEntity.setNewsContent("");//详情和图片
//                            newsEntity.setCreateTime(date);//时间
                            // 添加到列表中
                            newsList.add(newsEntity);

                            // 把要查询是否重复的字段放入map中
                        }

                        // 查询是否重复
                        newsService.insertBatch(newsList);
                        // 返回结果
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            // 打印堆栈跟踪
            e.printStackTrace();
            // 返回错误信息
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }

    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        // 记录日志
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 检查参数
        CommonUtil.checkMap(params);
        // 查询分页数据
        PageUtils page = newsService.queryPage(params);

        // 字典表数据转换
        List<NewsView> list =(List<NewsView>)page.getList();
        for(NewsView c:list)
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);

        // 返回结果
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        // 记录日志
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        // 根据ID查询实体
        NewsEntity news = newsService.selectById(id);
            if(news !=null){

                // entity转view
                NewsView view = new NewsView();
                BeanUtils.copyProperties( news , view );//把实体数据重构到view中

                // 修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                // 返回结果
                return R.ok().put("data", view);
            }else {
                // 返回错误信息
                return R.error(511,"查不到数据");
            }
    }

    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody NewsEntity news, HttpServletRequest request){
        // 记录日志
        logger.debug("add方法:,,Controller:{},,news:{}",this.getClass().getName(),news.toString());
        // 构建查询条件
        Wrapper<NewsEntity> queryWrapper = new EntityWrapper<NewsEntity>()
            .eq("news_name", news.getNewsName())
            .eq("news_types", news.getNewsTypes())
//            .notIn("news_types", new Integer[]{102})
            ;
        // 记录SQL语句
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        // 查询是否存在相同数据
        NewsEntity newsEntity = newsService.selectOne(queryWrapper);
        if(newsEntity==null){
            // 设置创建时间和插入时间
            news.setInsertTime(new Date());
            news.setCreateTime(new Date());
            // 插入数据
            newsService.insert(news);

            // 返回结果
            return R.ok();
        }else {
            // 返回错误信息
            return R.error(511,"表中有相同数据");
        }
    }

}
