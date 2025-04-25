package com.controller;

// 导入所需的类
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.UsersEntity;
import com.service.TokenService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 登录相关
 */
@RequestMapping("users")
@RestController
public class UsersController {

    // 自动注入UsersService
	@Autowired
	private UsersService usersService;

    // 自动注入TokenService
	@Autowired
	private TokenService tokenService;

    /**
	 * 登录
	 */
	@IgnoreAuth
	@PostMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
        // 根据用户名查询用户
		UsersEntity user = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
        // 如果用户不存在或密码不匹配，返回错误信息
		if(user==null || !user.getPassword().equals(password)) {
			return R.error("账号或密码不正确");
		}
        // 生成令牌
		String token = tokenService.generateToken(user.getId(),username, "users", user.getRole());
        // 创建响应对象
		R r = R.ok();
        // 设置令牌
		r.put("token", token);
        // 设置角色
		r.put("role",user.getRole());
        // 设置用户ID
		r.put("userId",user.getId());
        // 返回响应
		return r;
	}

    /**
	 * 注册
	 */
	@IgnoreAuth
	@PostMapping(value = "/register")
	public R register(@RequestBody UsersEntity user){
//    	ValidatorUtils.validateEntity(user);
        // 检查用户名是否已存在
    	if(usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", user.getUsername())) !=null) {
    		return R.error("用户已存在");
    	}
        // 插入新用户
        usersService.insert(user);
        // 返回成功响应
        return R.ok();
    }

    /**
	 * 退出
	 */
	@GetMapping(value = "logout")
	public R logout(HttpServletRequest request) {
        // 使会话失效
		request.getSession().invalidate();
        // 返回成功响应
		return R.ok("退出成功");
	}

    /**
	 * 修改密码
	 */
	@GetMapping(value = "/updatePassword")
	public R updatePassword(String  oldPassword, String  newPassword, HttpServletRequest request) {
        // 根据会话获取用户
		UsersEntity users = usersService.selectById((Integer)request.getSession().getAttribute("userId"));
        // 如果新密码为空，返回错误信息
		if(newPassword == null){
			return R.error("新密码不能为空") ;
		}
        // 如果原密码不匹配，返回错误信息
		if(!oldPassword.equals(users.getPassword())){
			return R.error("原密码输入错误");
		}
        // 如果新密码和原密码相同，返回错误信息
		if(newPassword.equals(users.getPassword())){
			return R.error("新密码不能和原密码一致") ;
		}
        // 设置新密码
		users.setPassword(newPassword);
        // 更新用户信息
		usersService.updateById(users);
        // 返回成功响应
		return R.ok();
	}

    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
        // 根据用户名查询用户
    	UsersEntity user = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
        // 如果用户不存在，返回错误信息
    	if(user==null) {
    		return R.error("账号不存在");
    	}
        // 设置默认密码
    	user.setPassword("123456");
        // 更新用户信息
        usersService.update(user,null);
        // 返回成功响应
        return R.ok("密码已重置为：123456");
    }

    /**
     * 列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,UsersEntity user){
        // 构建查询条件
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
    	// 查询分页数据
    	PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.allLike(ew, user), params), params));
        // 返回成功响应
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list( UsersEntity user){
       	// 构建查询条件
       	EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
      	// 设置查询条件
      	ew.allEq(MPUtil.allEQMapPre( user, "user"));
        // 返回成功响应
        return R.ok().put("data", usersService.selectListView(ew));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        // 根据ID查询用户
        UsersEntity user = usersService.selectById(id);
        // 返回成功响应
        return R.ok().put("data", user);
    }

    /**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	// 从会话中获取用户ID
    	Integer id = (Integer)request.getSession().getAttribute("userId");
        // 根据ID查询用户
        UsersEntity user = usersService.selectById(id);
        // 返回成功响应
        return R.ok().put("data", user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UsersEntity user){
//    	ValidatorUtils.validateEntity(user);
        // 检查用户名是否已存在
    	if(usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", user.getUsername())) !=null) {
    		return R.error("用户已存在");
    	}
        // 插入新用户
        usersService.insert(user);
        // 返回成功响应
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UsersEntity user){
//        ValidatorUtils.validateEntity(user);
        // 根据ID更新用户信息
        usersService.updateById(user);//全部更新
        // 返回成功响应
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		// 查询所有用户
		List<UsersEntity> user = usersService.selectList(null);
		// 如果用户数量大于1，删除指定用户
		if(user.size() > 1){
			usersService.deleteBatchIds(Arrays.asList(ids));
		}else{
			// 返回错误信息
			return R.error("管理员最少保留一个");
		}
        // 返回成功响应
        return R.ok();
    }
}
