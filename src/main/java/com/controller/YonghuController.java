package com.controller;

// 导入所需的类库，包括Java标准库、第三方库（如FastJSON）、Spring框架相关类、MyBatis-Plus工具类等。
import java.io.File; // 导入File类，用于文件操作
import java.math.BigDecimal; // 导入BigDecimal类，用于高精度计算
import java.net.URL; // 导入URL类，用于处理URL
import java.text.SimpleDateFormat; // 导入SimpleDateFormat类，用于日期格式化
import com.alibaba.fastjson.JSONObject; // 导入JSONObject类，用于JSON处理
import java.util.*; // 导入Java集合框架
import org.springframework.beans.BeanUtils; // 导入BeanUtils类，用于对象属性复制
import javax.servlet.http.HttpServletRequest; // 导入HttpServletRequest类，用于处理HTTP请求
import org.springframework.web.context.ContextLoader; // 导入ContextLoader类，用于获取Web应用上下文
import javax.servlet.ServletContext; // 导入ServletContext类，用于获取Servlet上下文
import com.service.TokenService; // 导入TokenService类，用于处理Token相关操作
import com.utils.*; // 导入自定义工具类
import java.lang.reflect.InvocationTargetException; // 导入InvocationTargetException类，用于处理反射异常

import com.service.DictionaryService; // 导入DictionaryService类，用于处理字典数据
import org.apache.commons.lang3.StringUtils; // 导入StringUtils类，用于字符串操作
import com.annotation.IgnoreAuth; // 导入IgnoreAuth注解，用于忽略权限验证
import org.slf4j.Logger; // 导入Logger类，用于日志记录
import org.slf4j.LoggerFactory; // 导入LoggerFactory类，用于创建Logger实例
import org.springframework.beans.factory.annotation.Autowired; // 导入Autowired注解，用于自动注入依赖
import org.springframework.stereotype.Controller; // 导入Controller注解，用于标识控制器类
import org.springframework.web.bind.annotation.*; // 导入Web绑定注解，用于处理HTTP请求
import com.baomidou.mybatisplus.mapper.EntityWrapper; // 导入EntityWrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.Wrapper; // 导入Wrapper接口，用于构建查询条件
import com.entity.*; // 导入实体类
import com.entity.view.*; // 导入视图类
import com.service.*; // 导入服务类
import com.utils.PageUtils; // 导入PageUtils类，用于分页处理
import com.utils.R; // 导入R类，用于封装返回结果
import com.alibaba.fastjson.*; // 导入FastJSON相关类，用于JSON处理

/**
 * 用户
 * 后端接口
 * @author [作者名]
 * @email [邮箱地址]
 */
@RestController // 标识该类为RESTful控制器
@RequestMapping("/yonghu") // 映射请求路径为/yonghu
public class YonghuController {
    private static final Logger logger = LoggerFactory.getLogger(YonghuController.class); // 创建日志记录器实例

    private static final String TABLE_NAME = "yonghu"; // 定义表名常量

    @Autowired // 自动注入YonghuService
    private YonghuService yonghuService; // 用户服务类

    @Autowired // 自动注入TokenService
    private TokenService tokenService; // Token服务类

    @Autowired // 自动注入DictionaryService
    private DictionaryService dictionaryService; // 字典服务类

    @Autowired // 自动注入ForumService
    private ForumService forumService; // 论坛服务类

    @Autowired // 自动注入GerentizhengService
    private GerentizhengService gerentizhengService; // 体检记录服务类

    @Autowired // 自动注入JiankangtieshiService
    private JiankangtieshiService jiankangtieshiService; // 健康贴士服务类

    @Autowired // 自动注入MeirijihuaService
    private MeirijihuaService meirijihuaService; // 每日计划服务类

    @Autowired // 自动注入MeishiService
    private MeishiService meishiService; // 健康食谱服务类

    @Autowired // 自动注入MeishiCollectionService
    private MeishiCollectionService meishiCollectionService; // 健康食谱收藏服务类

    @Autowired // 自动注入MeishiLiuyanService
    private MeishiLiuyanService meishiLiuyanService; // 健康食谱留言服务类

    @Autowired // 自动注入NewsService
    private NewsService newsService; // 公告信息服务类

    @Autowired // 自动注入YaopinService
    private YaopinService yaopinService; // 药品信息服务类

    @Autowired // 自动注入YundongService
    private YundongService yundongService; // 运动教程服务类

    @Autowired // 自动注入YundongCollectionService
    private YundongCollectionService yundongCollectionService; // 运动教程收藏服务类

    @Autowired // 自动注入YundongLiuyanService
    private YundongLiuyanService yundongLiuyanService; // 运动教程留言服务类

    @Autowired // 自动注入UsersService
    private UsersService usersService; // 管理员服务类

    /**
     * 后端列表
     * 该方法用于获取用户列表的分页数据。
     */
    @RequestMapping("/page") // 映射请求路径为/page
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) { // 处理分页请求
        logger.debug("page方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params)); // 记录日志
        String role = String.valueOf(request.getSession().getAttribute("role")); // 获取用户角色
        if (false) // 永远不会进入的条件
            return R.error(511, "永不会进入"); // 返回错误信息
        else if ("用户".equals(role)) // 如果用户角色为“用户”
            params.put("yonghuId", request.getSession().getAttribute("userId")); // 设置用户ID
        params.put("yonghuDeleteStart", 1); // 设置删除状态参数
        params.put("yonghuDeleteEnd", 1); // 设置删除状态参数
        CommonUtil.checkMap(params); // 检查参数
        PageUtils page = yonghuService.queryPage(params); // 查询分页数据

        // 字典表数据转换
        List<YonghuView> list = (List<YonghuView>) page.getList(); // 获取分页数据列表
        for (YonghuView c : list) { // 遍历列表
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request); // 转换字典数据
        }
        return R.ok().put("data", page); // 返回结果
    }

    /**
     * 后端详情
     * 该方法用于获取指定ID的用户详情信息。
     */
    @RequestMapping("/info/{id}") // 映射请求路径为/info/{id}
    public R info(@PathVariable("id") Long id, HttpServletRequest request) { // 处理详情请求
        logger.debug("info方法:,,Controller:{},,id:{}", this.getClass().getName(), id); // 记录日志
        YonghuEntity yonghu = yonghuService.selectById(id); // 根据ID查询实体
        if (yonghu != null) { // 如果查询到数据
            // entity转view
            YonghuView view = new YonghuView(); // 创建视图对象
            BeanUtils.copyProperties(yonghu, view); // 把实体数据重构到view中
            // 修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request); // 转换字典数据
            return R.ok().put("data", view); // 返回结果
        } else {
            return R.error(511, "查不到数据"); // 返回错误信息
        }
    }

    /**
     * 后端保存
     * 该方法用于保存新的用户信息。
     */
    @RequestMapping("/save") // 映射请求路径为/save
    public R save(@RequestBody YonghuEntity yonghu, HttpServletRequest request) { // 处理保存请求
        logger.debug("save方法:,,Controller:{},,yonghu:{}", this.getClass().getName(), yonghu.toString()); // 记录日志
        String role = String.valueOf(request.getSession().getAttribute("role")); // 获取用户角色
        if (false) // 永远不会进入的条件
            return R.error(511, "永远不会进入"); // 返回错误信息

        Wrapper<YonghuEntity> queryWrapper = new EntityWrapper<YonghuEntity>() // 构建查询条件
                .eq("username", yonghu.getUsername()) // 账户名相等
                .or() // 或者
                .eq("yonghu_phone", yonghu.getYonghuPhone()) // 手机号相等
                .or() // 或者
                .eq("yonghu_id_number", yonghu.getYonghuIdNumber()) // 身份证号相等
                .andNew() // 新的条件
                .eq("yonghu_delete", 1); // 删除状态为1

        logger.info("sql语句:" + queryWrapper.getSqlSegment()); // 记录SQL语句
        YonghuEntity yonghuEntity = yonghuService.selectOne(queryWrapper);
        // 查询是否存在相同数据
        if (yonghuEntity == null) {
            // 如果不存在相同数据
            yonghu.setYonghuDelete(1);
            // 设置删除状态
            yonghu.setCreateTime(new Date());
            // 设置创建时间
            yonghu.setPassword("123456");
            // 设置默认密码
            yonghuService.insert(yonghu);
            // 插入数据
            return R.ok();
            // 返回成功结果
        } else {
            return R.error(511, "账户或者联系方式或者身份证号已经被使用");
            // 返回错误信息
        }
    }

    /**
     * 后端修改
     * 该方法用于更新指定ID的用户信息。
     */
    @RequestMapping("/update")
    // 映射请求路径为/update
    public R update(@RequestBody YonghuEntity yonghu, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 处理修改请求
        logger.debug("update方法:,,Controller:{},,yonghu:{}", this.getClass().getName(), yonghu.toString());
        // 记录日志
        YonghuEntity oldYonghuEntity = yonghuService.selectById(yonghu.getId());
        // 查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 获取用户角色
//        if(false)
//            return R.error(511,"永远不会进入");
        if ("".equals(yonghu.getYonghuPhoto()) || "null".equals(yonghu.getYonghuPhoto())) {
            // 如果药品照片为空或为"null"
            yonghu.setYonghuPhoto(null);
            // 设置为null
        }

        yonghuService.updateById(yonghu);
        // 根据id更新
        return R.ok();
        // 返回成功结果
    }

    /**
     * 删除
     * 该方法用于逻辑删除指定ID的用户。
     */
    @RequestMapping("/delete")
    // 映射请求路径为/delete
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        // 处理删除请求
        logger.debug("delete:,,Controller:{},,ids:{}", this.getClass().getName(), ids.toString());
        // 记录日志
        List<YonghuEntity> oldYonghuList = yonghuService.selectBatchIds(Arrays.asList(ids));
        // 要删除的数据
        ArrayList<YonghuEntity> list = new ArrayList<>();
        // 创建列表存储要更新的实体
        for (Integer id : ids) {
            // 遍历ID数组
            YonghuEntity yonghuEntity = new YonghuEntity();
            // 创建实体对象
            yonghuEntity.setId(id);
            // 设置ID
            yonghuEntity.setYonghuDelete(2);
            // 设置删除状态
            list.add(yonghuEntity);
            // 添加到列表中
        }
        if (list != null && list.size() > 0) {
            // 如果列表不为空且大小大于0
            yonghuService.updateBatchById(list);
            // 更新批量数据
        }

        return R.ok();
        // 返回成功结果
    }

    /**
     * 批量上传
     * 该方法用于批量上传用户信息。
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
            List<YonghuEntity> yonghuList = new ArrayList<>();
            // 定义药品信息列表
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
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName); // 获取文件路径
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
                            // 遍历数据
                            // 循环
                            YonghuEntity yonghuEntity = new YonghuEntity();
                            // 创建实体对象
//                            yonghuEntity.setUsername(data.get(0));
// 账户 要改的
//                            //yonghuEntity.setPassword("123456");
// 密码
//                            yonghuEntity.setYonghuName(data.get(0));
// 用户姓名 要改的
//                            yonghuEntity.setSexTypes(Integer.valueOf(data.get(0)));
// 性别 要改的
//                            yonghuEntity.setYonghuPhoto("");
// 详情和图片
//                            yonghuEntity.setYonghuIdNumber(data.get(0));
// 身份证号 要改的
//                            yonghuEntity.setYonghuPhone(data.get(0));
// 联系方式 要改的
//                            yonghuEntity.setYonghuEmail(data.get(0));
// 电子邮箱 要改的
//                            yonghuEntity.setYonghuDelete(1);
// 逻辑删除字段
//                            yonghuEntity.setCreateTime(date);
// 时间
                            yonghuList.add(yonghuEntity);
                            // 添加到列表中

                            // 把要查询是否重复的字段放入map中
                            // 账户
                            if (seachFields.containsKey("username")) {
                                // 如果map中包含username
                                List<String> username = seachFields.get("username");
                                // 获取username列表
                                username.add(data.get(0));
                                // 添加到列表中
                            } else {
                                List<String> username = new ArrayList<>();
                                // 创建新的username列表
                                username.add(data.get(0));
                                // 添加到列表中
                                seachFields.put("username", username);
                                // 放入map中
                            }
                            // 身份证号
                            if (seachFields.containsKey("yonghuIdNumber")) {
                                // 如果map中包含yonghuIdNumber
                                List<String> yonghuIdNumber = seachFields.get("yonghuIdNumber");
                                // 获取yonghuIdNumber列表
                                yonghuIdNumber.add(data.get(0));
                                // 添加到列表中
                            } else {
                                List<String> yonghuIdNumber = new ArrayList<>();
                                // 创建新的yonghuIdNumber列表
                                yonghuIdNumber.add(data.get(0));
                                // 添加到列表中
                                seachFields.put("yonghuIdNumber", yonghuIdNumber);
                                // 放入map中
                            }
                            // 联系方式
                            if (seachFields.containsKey("yonghuPhone")) {
                                // 如果map中包含yonghuPhone
                                List<String> yonghuPhone = seachFields.get("yonghuPhone");
                                // 获取yonghuPhone列表
                                yonghuPhone.add(data.get(0));
                                // 添加到列表中
                            } else {
                                List<String> yonghuPhone = new ArrayList<>();
                                // 创建新的yonghuPhone列表
                                yonghuPhone.add(data.get(0));
                                // 添加到列表中
                                seachFields.put("yonghuPhone", yonghuPhone);
                                // 放入map中
                            }
                        }

                        // 查询是否重复
                        // 账户
                        List<YonghuEntity> yonghuEntities_username = yonghuService.selectList(new EntityWrapper<YonghuEntity>().in("username", seachFields.get("username")).eq("yonghu_delete", 1));
                        // 查询账户是否重复
                        if (yonghuEntities_username.size() > 0) {
                            // 如果存在重复数据
                            ArrayList<String> repeatFields = new ArrayList<>();
                            // 创建重复字段列表
                            for (YonghuEntity s : yonghuEntities_username) {
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511, "数据库的该表中的 [账户] 字段已经存在 存在数据为:" + repeatFields.toString());
                        }
                        //身份证号
                        List<YonghuEntity> yonghuEntities_yonghuIdNumber = yonghuService.selectList(new EntityWrapper<YonghuEntity>().in("yonghu_id_number", seachFields.get("yonghuIdNumber")).eq("yonghu_delete", 1));
                        if (yonghuEntities_yonghuIdNumber.size() > 0) {
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for (YonghuEntity s : yonghuEntities_yonghuIdNumber) {
                                repeatFields.add(s.getYonghuIdNumber());
                            }
                            return R.error(511, "数据库的该表中的 [身份证号] 字段已经存在 存在数据为:" + repeatFields.toString());
                        }
                        //联系方式
                        List<YonghuEntity> yonghuEntities_yonghuPhone = yonghuService.selectList(new EntityWrapper<YonghuEntity>().in("yonghu_phone", seachFields.get("yonghuPhone")).eq("yonghu_delete", 1));
                        if (yonghuEntities_yonghuPhone.size() > 0) {
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for (YonghuEntity s : yonghuEntities_yonghuPhone) {
                                repeatFields.add(s.getYonghuPhone());
                            }
                            return R.error(511, "数据库的该表中的 [联系方式] 字段已经存在 存在数据为:" + repeatFields.toString());
                        }
                        yonghuService.insertBatch(yonghuList);
                        return R.ok();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(511, "批量插入数据异常，请联系管理员");
        }
    }

    /**
     * 登录
     */
    /**
     * 登录
     * 该方法用于用户登录验证。
     */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        YonghuEntity yonghu = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("username", username));
        // 根据用户名查询用户信息
        if (yonghu == null || !yonghu.getPassword().equals(password))
            // 如果用户不存在或密码不匹配
            return R.error("账号或密码不正确");
        else if (yonghu.getYonghuDelete() != 1)
            // 如果用户已被删除
            return R.error("账户已被删除");
        String token = tokenService.generateToken(yonghu.getId(), username, "yonghu", "用户");
        // 生成Token
        R r = R.ok();
        // 创建返回结果对象
        r.put("token", token);
        // 设置Token
        r.put("role", "用户");
        // 设置角色
        r.put("username", yonghu.getYonghuName());
        // 设置用户名
        r.put("tableName", "yonghu");
        // 设置表名
        r.put("userId", yonghu.getId());
        // 设置用户ID
        return r;
        // 返回成功结果
    }

    /**
     * 注册
     * 该方法用于用户注册。
     */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody YonghuEntity yonghu, HttpServletRequest request) {
//    	ValidatorUtils.validateEntity(user);
        Wrapper<YonghuEntity> queryWrapper = new EntityWrapper<YonghuEntity>()
                .eq("username", yonghu.getUsername())
                .or()
                .eq("yonghu_id_number", yonghu.getYonghuIdNumber())
                .or()
                .eq("yonghu_phone", yonghu.getYonghuPhone())
                .andNew()
                .eq("yonghu_delete", 1);
        // 构建查询条件，检查账户、身份证号或手机号是否已存在
        YonghuEntity yonghuEntity = yonghuService.selectOne(queryWrapper);
        // 查询是否存在相同数据
        if (yonghuEntity != null)
            // 如果存在相同数据
            return R.error("账户或者联系方式或者身份证号已经被使用");
        yonghu.setYonghuDelete(1);
        // 设置删除状态
        yonghu.setCreateTime(new Date());
        // 设置创建时间
        yonghuService.insert(yonghu);
        // 插入新用户数据

        return R.ok();
        // 返回成功结果
    }

    /**
     * 重置密码
     * 该方法用于重置指定ID用户的密码。
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer id, HttpServletRequest request) {
        YonghuEntity yonghu = yonghuService.selectById(id);
        // 根据ID查询用户信息
        yonghu.setPassword("123456");
        // 设置默认密码
        yonghuService.updateById(yonghu);
        // 更新用户信息
        return R.ok();
        // 返回成功结果
    }

    /**
     * 修改密码
     * 该方法用于修改当前登录用户的密码。
     */
    @GetMapping(value = "/updatePassword")
    public R updatePassword(String oldPassword, String newPassword, HttpServletRequest request) {
        YonghuEntity yonghu = yonghuService.selectById((Integer) request.getSession().getAttribute("userId"));
        // 根据会话中的用户ID查询用户信息
        if (newPassword == null) {
            // 如果新密码为空
            return R.error("新密码不能为空");
        }
        if (!oldPassword.equals(yonghu.getPassword())) {
            // 如果旧密码不匹配
            return R.error("原密码输入错误");
        }
        if (newPassword.equals(yonghu.getPassword())) {
            // 如果新密码与旧密码相同
            return R.error("新密码不能和原密码一致");
        }
        yonghu.setPassword(newPassword);
        // 设置新密码
        yonghuService.updateById(yonghu);
        // 更新用户信息
        return R.ok();
        // 返回成功结果
    }

    /**
     * 忘记密码
     * 该方法用于根据用户名重置用户密码。
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        YonghuEntity yonghu = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("username", username));
        // 根据用户名查询用户信息
        if (yonghu != null) {
            // 如果用户存在
            yonghu.setPassword("123456");
            // 设置默认密码
            yonghuService.updateById(yonghu);
            // 更新用户信息
            return R.ok();
        } else {
            // 如果用户不存在
            return R.error("账号不存在");
        }
    }

    /**
     * 获取用户的session用户信息
     * 该方法用于获取当前登录用户的详细信息。
     */
    @RequestMapping("/session")
    public R getCurrYonghu(HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("userId");
        // 从会话中获取用户ID
        YonghuEntity yonghu = yonghuService.selectById(id);
        // 根据ID查询用户信息
        if (yonghu != null) {
            // 如果用户存在
            //entity转view
            YonghuView view = new YonghuView();
            BeanUtils.copyProperties(yonghu, view);//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 转换字典数据
            return R.ok().put("data", view);
            // 返回用户信息
        } else {
            return R.error(511, "查不到数据");
            // 返回错误信息
        }
    }

    /**
     * 退出
     * 该方法用于用户退出登录。
     */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        // 使会话失效
        return R.ok("退出成功");
        // 返回成功结果
    }

    /**
     * 前端列表
     * 该方法用于获取用户列表的分页数据（前端调用）。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("list方法:,,Controller:{},,params:{}", this.getClass().getName(), JSONObject.toJSONString(params));
        // 记录日志
        CommonUtil.checkMap(params);
        // 检查参数
        PageUtils page = yonghuService.queryPage(params);
        // 查询分页数据

        //字典表数据转换
        List<YonghuView> list = (List<YonghuView>) page.getList();
        // 获取分页数据列表
        for (YonghuView c : list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        // 转换字典数据

        return R.ok().put("data", page);
        // 返回结果
    }

    /**
     * 前端详情
     * 该方法用于获取指定ID的用户详情信息（前端调用）。
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.debug("detail方法:,,Controller:{},,id:{}", this.getClass().getName(), id);
        // 记录日志
        YonghuEntity yonghu = yonghuService.selectById(id);
        // 根据ID查询用户信息
        if (yonghu != null) {
            // 如果用户存在
            //entity转view
            YonghuView view = new YonghuView();
            BeanUtils.copyProperties(yonghu, view);//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            // 转换字典数据
            return R.ok().put("data", view);
            // 返回用户信息
        } else {
            return R.error(511, "查不到数据");
            // 返回错误信息
        }
    }

    /**
     * 前端保存
     * 该方法用于保存新的用户信息（前端调用）。
     */
    @RequestMapping("/add")
    public R add(@RequestBody YonghuEntity yonghu, HttpServletRequest request) {
        logger.debug("add方法:,,Controller:{},,yonghu:{}", this.getClass().getName(), yonghu.toString());
        // 记录日志
        Wrapper<YonghuEntity> queryWrapper = new EntityWrapper<YonghuEntity>()
                .eq("username", yonghu.getUsername())
                .or()
                .eq("yonghu_id_number", yonghu.getYonghuIdNumber())
                .or()
                .eq("yonghu_phone", yonghu.getYonghuPhone())
                .andNew()
                .eq("yonghu_delete", 1)
//            .notIn("yonghu_types", new Integer[]{102})
                ;
        // 构建查询条件，检查账户、身份证号或手机号是否已存在
        logger.info("sql语句:" + queryWrapper.getSqlSegment());
        // 记录SQL语句
        YonghuEntity yonghuEntity = yonghuService.selectOne(queryWrapper);
        // 查询是否存在相同数据
        if (yonghuEntity == null) {
            // 如果不存在相同数据
            yonghu.setYonghuDelete(1);
            // 设置删除状态
            yonghu.setCreateTime(new Date());
            // 设置创建时间
            yonghu.setPassword("123456");
            // 设置默认密码
            yonghuService.insert(yonghu);
            // 插入新用户数据

            return R.ok();
            // 返回成功结果
        } else {
            return R.error(511, "账户或者联系方式或者身份证号已经被使用");
            // 返回错误信息
        }
    }
}

