package com.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/** 
 * token表实体类
 */
@TableName("token")
// 指定数据库表名为token
public class TokenEntity implements Serializable {
	// 实现Serializable接口以支持序列化
	private static final long serialVersionUID = 1L;
	// 序列化版本号

	@TableId(type = IdType.AUTO)
	// 主键自增
	private Integer id;
	// 主键ID

	/**
	 * 用户id
	 */
	private Integer userid;
	// 用户ID

	/**
	 * 用户名
	 */

	private String username;
	// 用户名

	/**
	 * 表名
	 */
	private String tablename;
	// 表名

	/**
	 * 角色
	 */
	private String role;
	// 角色

	/**
	 * token
	 */
	private String token;
	// token字符串

	/**
	 * 过期时间
	 */
	private Date expiratedtime;
	// token过期时间

	/**
	 * 新增时间
	 */
	private Date addtime;
	// token新增时间

	public Integer getId() {
		return id;
		// 获取主键ID
	}

	public void setId(Integer id) {
		this.id = id;
		// 设置主键ID
	}

	public Integer getUserid() {
		return userid;
		// 获取用户ID
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
		// 设置用户ID
	}

	public String getRole() {
		return role;
		// 获取角色
	}

	public void setRole(String role) {
		this.role = role;
		// 设置角色
	}

	public String getToken() {
		return token;
		// 获取token
	}

	public String getTablename() {
		return tablename;
		// 获取表名
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
		// 设置表名
	}

	public void setToken(String token) {
		this.token = token;
		// 设置token
	}

	public Date getExpiratedtime() {
		return expiratedtime;
		// 获取过期时间
	}

	public void setExpiratedtime(Date expiratedtime) {
		this.expiratedtime = expiratedtime;
		// 设置过期时间
	}

	public Date getAddtime() {
		return addtime;
		// 获取新增时间
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
		// 设置新增时间
	}

	public String getUsername() {
		return username;
		// 获取用户名
	}

	public void setUsername(String username) {
		this.username = username;
		// 设置用户名
	}

	public TokenEntity(Integer userid, String username, String tablename,String role, String token, Date expiratedtime) {
		super();
		// 调用父类构造方法
		this.userid = userid;
		// 初始化用户ID
		this.username = username;
		// 初始化用户名
		this.tablename = tablename;
		// 初始化表名
		this.role = role;
		// 初始化角色
		this.token = token;
		// 初始化token
		this.expiratedtime = expiratedtime;
		// 初始化过期时间
	}

	public TokenEntity() {
		// 默认构造方法
	}
	
}
