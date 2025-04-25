package com.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/** 
 * 用户实体类
 */
@TableName("users") // 指定数据库表名为users
public class UsersEntity implements Serializable { // 实现Serializable接口以支持序列化
	private static final long serialVersionUID = 1L; // 序列化版本号

	@TableId(type = IdType.AUTO) // 主键自增
	private Integer id; // 主键ID

	/**
	 * 用户账号
	 */
	private String username; // 用户账号

	/**
	 * 密码
	 */
	private String password; // 用户密码

	/**
	 * 用户类型
	 */
	private String role; // 用户角色

	private Date addtime; // 用户新增时间

	public String getUsername() {
		return username; // 获取用户账号
	}

	public void setUsername(String username) {
		this.username = username; // 设置用户账号
	}

	public String getPassword() {
		return password; // 获取用户密码
	}

	public void setPassword(String password) {
		this.password = password; // 设置用户密码
	}

	public String getRole() {
		return role; // 获取用户角色
	}

	public void setRole(String role) {
		this.role = role; // 设置用户角色
	}

	public Date getAddtime() {
		return addtime; // 获取用户新增时间
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime; // 设置用户新增时间
	}

	public Integer getId() {
		return id; // 获取主键ID
	}

	public void setId(Integer id) {
		this.id = id; // 设置主键ID
	}

}
