package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.MeirijihuaEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 每日计划
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("meirijihua")
public class MeirijihuaView extends MeirijihuaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表

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


	public MeirijihuaView() {
		// 默认构造函数
	}

	public MeirijihuaView(MeirijihuaEntity meirijihuaEntity) {
		// 带参数的构造函数，用于从MeirijihuaEntity对象复制属性
		try {
			BeanUtils.copyProperties(this, meirijihuaEntity);
			// 使用BeanUtils工具将传入的MeirijihuaEntity对象的属性复制到当前对象中
		} catch (IllegalAccessException | InvocationTargetException e) {
			// 捕获并处理IllegalAccessException和InvocationTargetException异常
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 打印异常堆栈信息
		}
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

	@Override
	public String toString() {
		// 重写toString方法，返回当前对象的字符串表示
		return "MeirijihuaView{" +
				// 开始构建字符串表示
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

