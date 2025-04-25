package com.service.impl;

// 导入Java日历类，用于日期操作
import java.util.Calendar;
// 导入Java日期类，用于日期操作
import java.util.Date;
// 导入Java列表类，用于存储列表数据
import java.util.List;
// 导入Java映射类，用于存储键值对
import java.util.Map;

// 注解，用于标识这是一个服务类
import org.springframework.stereotype.Service;

// 导入MyBatis-Plus的EntityWrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.EntityWrapper;
// 导入MyBatis-Plus的Wrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.Wrapper;
// 导入MyBatis-Plus的Page类，用于分页查询
import com.baomidou.mybatisplus.plugins.Page;
// 导入MyBatis-Plus的服务实现基类
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
// 导入TokenDao接口，用于数据库操作
import com.dao.TokenDao;
// 导入TokenEntity实体类，用于映射token表
import com.service.TokenService;
import com.entity.TokenEntity;
// 导入CommonUtil工具类，用于通用操作
import com.utils.CommonUtil;
// 导入PageUtils工具类，用于分页结果封装
import com.utils.PageUtils;
// 导入Query工具类，用于构建分页查询条件
import com.utils.Query;


/**
 * token
 * @author
 */
@Service("tokenService")
// 继承ServiceImpl基类，实现TokenService接口
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {

	// 实现queryPage方法，用于分页查询token
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		// 创建Page对象，用于分页查询
		Page<TokenEntity> page = this.selectPage(
				new Query<TokenEntity>(params).getPage(),
				new EntityWrapper<TokenEntity>()
		);
		// 返回封装的分页结果
		return new PageUtils(page);
	}

	// 实现selectListView方法，用于查询token列表视图
	@Override
	public List<TokenEntity> selectListView(Wrapper<TokenEntity> wrapper) {
		// 返回查询结果
		return baseMapper.selectListView(wrapper);
	}

	// 实现queryPage方法，用于分页查询token，并支持自定义查询条件
	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<TokenEntity> wrapper) {
		// 创建Page对象，用于分页查询
		Page<TokenEntity> page = new Query<TokenEntity>(params).getPage();
		// 设置分页查询结果
		page.setRecords(baseMapper.selectListView(page, wrapper));
		// 返回封装的分页结果
		PageUtils pageUtil = new PageUtils(page);
		return pageUtil;
	}

	// 实现generateToken方法，用于生成token
	@Override
	public String generateToken(Integer userid, String username, String tableName, String role) {
		// 查询已存在的token实体
		TokenEntity tokenEntity = this.selectOne(new EntityWrapper<TokenEntity>().eq("userid", userid).eq("role", role));
		// 生成新的token字符串
		String token = CommonUtil.getRandomString(32);
		// 获取当前时间并设置过期时间为1小时后
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		// 如果token实体存在，则更新token和过期时间
		if (tokenEntity != null) {
			tokenEntity.setToken(token);
			tokenEntity.setExpiratedtime(cal.getTime());
			this.updateById(tokenEntity);
		} else {
			// 如果token实体不存在，则插入新的token实体
			this.insert(new TokenEntity(userid, username, tableName, role, token, cal.getTime()));
		}
		// 返回生成的token
		return token;
	}

	// 实现getTokenEntity方法，用于获取token实体
	@Override
	public TokenEntity getTokenEntity(String token) {
		// 查询token实体
		TokenEntity tokenEntity = this.selectOne(new EntityWrapper<TokenEntity>().eq("token", token));
		// 如果token实体不存在或已过期，则返回null
		if (tokenEntity == null || tokenEntity.getExpiratedtime().getTime() < new Date().getTime()) {
			return null;
		}
		// 返回有效的token实体
		return tokenEntity;
	}
}