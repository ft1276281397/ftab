package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.ForumEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 论坛
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("forum")
public class ForumView extends ForumEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	 * 帖子状态的值
	 */
	@ColumnInfo(comment = "帖子状态的字典表值", type = "varchar(200)")
	private String forumStateValue;

	//级联表 用户
	/**
	 * 用户姓名
	 */

	@ColumnInfo(comment = "用户姓名", type = "varchar(200)")
	private String yonghuName;
	/**
	 * 头像
	 */

	@ColumnInfo(comment = "头像", type = "varchar(200)")
	private String yonghuPhoto;
	/**
	 * 身份证号
	 */

	@ColumnInfo(comment = "身份证号", type = "varchar(200)")
	private String yonghuIdNumber;
	/**
	 * 联系方式
	 */

	@ColumnInfo(comment = "联系方式", type = "varchar(200)")
	private String yonghuPhone;
	/**
	 * 电子邮箱
	 */

	@ColumnInfo(comment = "电子邮箱", type = "varchar(200)")
	private String yonghuEmail;
	/**
	 * 逻辑删除
	 */

	@ColumnInfo(comment = "逻辑删除", type = "int(11)")
	private Integer yonghuDelete;
	//级联表 管理员
	/**
	 * 用户名
	 */
	@ColumnInfo(comment = "用户名", type = "varchar(100)")
	private String uusername;
	/**
	 * 密码
	 */
	@ColumnInfo(comment = "密码", type = "varchar(100)")
	private String upassword;
	/**
	 * 角色
	 */
	@ColumnInfo(comment = "角色", type = "varchar(100)")
	private String urole;
	/**
	 * 新增时间
	 */
	@ColumnInfo(comment = "新增时间", type = "timestamp")
	private Date uaddtime;


	public ForumView() {

	}

	public ForumView(ForumEntity forumEntity) {
		try {
			BeanUtils.copyProperties(this, forumEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//当前表的

	/**
	 * 获取： 帖子状态的值
	 */
	public String getForumStateValue() {
		return forumStateValue;
	}

	/**
	 * 设置： 帖子状态的值
	 */
	public void setForumStateValue(String forumStateValue) {
		this.forumStateValue = forumStateValue;
	}


	//级联表的get和set 用户

	/**
	 * 获取： 用户姓名
	 */
	public String getYonghuName() {
		return yonghuName;
	}

	/**
	 * 设置： 用户姓名
	 */
	public void setYonghuName(String yonghuName) {
		this.yonghuName = yonghuName;
	}

	/**
	 * 获取： 头像
	 */
	public String getYonghuPhoto() {
		return yonghuPhoto;
	}

	/**
	 * 设置： 头像
	 */
	public void setYonghuPhoto(String yonghuPhoto) {
		this.yonghuPhoto = yonghuPhoto;
	}

	/**
	 * 获取： 身份证号
	 */
	public String getYonghuIdNumber() {
		return yonghuIdNumber;
	}

	/**
	 * 设置： 身份证号
	 */
	public void setYonghuIdNumber(String yonghuIdNumber) {
		this.yonghuIdNumber = yonghuIdNumber;
	}

	/**
	 * 获取： 联系方式
	 */
	public String getYonghuPhone() {
		return yonghuPhone;
	}

	/**
	 * 设置： 联系方式
	 */
	public void setYonghuPhone(String yonghuPhone) {
		this.yonghuPhone = yonghuPhone;
	}

	/**
	 * 获取： 电子邮箱
	 */
	public String getYonghuEmail() {
		return yonghuEmail;
	}

	/**
	 * 设置： 电子邮箱
	 */
	public void setYonghuEmail(String yonghuEmail) {
		this.yonghuEmail = yonghuEmail;
	}

	/**
	 * 获取： 逻辑删除
	 */
	public Integer getYonghuDelete() {
		return yonghuDelete;
	}

	/**
	 * 设置： 逻辑删除
	 */
	public void setYonghuDelete(Integer yonghuDelete) {
		this.yonghuDelete = yonghuDelete;
	}
	//级联表的get和set 管理员

	/**
	 * 获取： 用户名
	 */
	public String getUusername() {
		return uusername;
	}

	/**
	 * 设置： 用户名
	 */
	public void setUusername(String uusername) {
		this.uusername = uusername;
	}

	/**
	 * 获取： 密码
	 */
	public String getUpassword() {
		return upassword;
	}

	/**
	 * 设置： 密码
	 */
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}

	/**
	 * 获取： 角色
	 */
	public String getUrole() {
		return urole;
	}

	/**
	 * 设置： 角色
	 */
	public void setUrole(String urole) {
		this.urole = urole;
	}

	/**
	 * 获取： 新增时间
	 */
	public Date getUaddtime() {
		return uaddtime;
	}

	/**
	 * 设置： 新增时间
	 */
	public void setUaddtime(Date uaddtime) {
		this.uaddtime = uaddtime;
	}


	@Override
	public String toString() {
		// 重写toString方法，返回当前对象的字符串表示
		return "ForumView{" +
				// 开始构建字符串表示
				", forumStateValue=" + forumStateValue +
				// 添加forumStateValue字段的值
				", yonghuName=" + yonghuName +
				// 添加yonghuName字段的值
				", yonghuPhoto=" + yonghuPhoto +
				// 添加yonghuPhoto字段的值
				", yonghuIdNumber=" + yonghuIdNumber +
				// 添加yonghuIdNumber字段的值
				", yonghuPhone=" + yonghuPhone +
				// 添加yonghuPhone字段的值
				", yonghuEmail=" + yonghuEmail +
				// 添加yonghuEmail字段的值
				", yonghuDelete=" + yonghuDelete +
				// 添加yonghuDelete字段的值
				"} " + super.toString();
		// 结束字符串表示，并调用父类的toString方法
	}
}
