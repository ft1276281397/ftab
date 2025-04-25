package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.YundongCollectionEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 运动教程收藏
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("yundong_collection")
public class YundongCollectionView extends YundongCollectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	 * 类型的值
	 */
	@ColumnInfo(comment = "类型的字典表值", type = "varchar(200)")
	private String yundongCollectionValue;

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
	//级联表 运动教程
	/**
	 * 标题
	 */

	@ColumnInfo(comment = "标题", type = "varchar(200)")
	private String yundongName;
	/**
	 * 运动照片
	 */

	@ColumnInfo(comment = "运动照片", type = "varchar(200)")
	private String yundongPhoto;
	/**
	 * 运动类型
	 */
	@ColumnInfo(comment = "运动类型", type = "int(11)")
	private Integer yundongTypes;
	/**
	 * 运动类型的值
	 */
	@ColumnInfo(comment = "运动类型的字典表值", type = "varchar(200)")
	private String yundongValue;
	/**
	 * 运动视频
	 */

	@ColumnInfo(comment = "运动视频", type = "varchar(200)")
	private String yundongVideo;
	/**
	 * 消耗热量
	 */

	@ColumnInfo(comment = "消耗热量", type = "int(11)")
	private Integer yundongNum;
	/**
	 * 赞
	 */

	@ColumnInfo(comment = "赞", type = "int(11)")
	private Integer zanNumber;
	/**
	 * 踩
	 */

	@ColumnInfo(comment = "踩", type = "int(11)")
	private Integer caiNumber;
	/**
	 * 运动介绍
	 */

	@ColumnInfo(comment = "运动介绍", type = "longtext")
	private String yundongContent;
	/**
	 * 逻辑删除
	 */

	@ColumnInfo(comment = "逻辑删除", type = "int(11)")
	private Integer yundongDelete;


	public YundongCollectionView() {
		// 默认构造函数
	}

	public YundongCollectionView(YundongCollectionEntity yundongCollectionEntity) {
		// 带参数的构造函数，用于从YundongCollectionEntity对象复制属性
		try {
			BeanUtils.copyProperties(this, yundongCollectionEntity);
			// 使用BeanUtils工具将传入的YundongCollectionEntity对象的属性复制到当前对象中
		} catch (IllegalAccessException | InvocationTargetException e) {
			// 捕获并处理IllegalAccessException和InvocationTargetException异常
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 打印异常堆栈信息
		}
	}


	//当前表的

	/**
	 * 获取： 类型的值
	 */
	public String getYundongCollectionValue() {
		return yundongCollectionValue;
	}

	/**
	 * 设置： 类型的值
	 */
	public void setYundongCollectionValue(String yundongCollectionValue) {
		this.yundongCollectionValue = yundongCollectionValue;
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
	//级联表的get和set 运动教程

	/**
	 * 获取： 标题
	 */
	public String getYundongName() {
		return yundongName;
	}

	/**
	 * 设置： 标题
	 */
	public void setYundongName(String yundongName) {
		this.yundongName = yundongName;
	}

	/**
	 * 获取： 运动照片
	 */
	public String getYundongPhoto() {
		return yundongPhoto;
	}

	/**
	 * 设置： 运动照片
	 */
	public void setYundongPhoto(String yundongPhoto) {
		this.yundongPhoto = yundongPhoto;
	}

	/**
	 * 获取： 运动类型
	 */
	public Integer getYundongTypes() {
		return yundongTypes;
	}

	/**
	 * 设置： 运动类型
	 */
	public void setYundongTypes(Integer yundongTypes) {
		this.yundongTypes = yundongTypes;
	}


	/**
	 * 获取： 运动类型的值
	 */
	public String getYundongValue() {
		return yundongValue;
	}

	/**
	 * 设置： 运动类型的值
	 */
	public void setYundongValue(String yundongValue) {
		this.yundongValue = yundongValue;
	}

	/**
	 * 获取： 运动视频
	 */
	public String getYundongVideo() {
		return yundongVideo;
	}

	/**
	 * 设置： 运动视频
	 */
	public void setYundongVideo(String yundongVideo) {
		this.yundongVideo = yundongVideo;
	}

	/**
	 * 获取： 消耗热量
	 */
	public Integer getYundongNum() {
		return yundongNum;
	}

	/**
	 * 设置： 消耗热量
	 */
	public void setYundongNum(Integer yundongNum) {
		this.yundongNum = yundongNum;
	}

	/**
	 * 获取： 赞
	 */
	public Integer getZanNumber() {
		return zanNumber;
	}

	/**
	 * 设置： 赞
	 */
	public void setZanNumber(Integer zanNumber) {
		this.zanNumber = zanNumber;
	}

	/**
	 * 获取： 踩
	 */
	public Integer getCaiNumber() {
		return caiNumber;
	}

	/**
	 * 设置： 踩
	 */
	public void setCaiNumber(Integer caiNumber) {
		this.caiNumber = caiNumber;
	}

	/**
	 * 获取： 运动介绍
	 */
	public String getYundongContent() {
		return yundongContent;
	}

	/**
	 * 设置： 运动介绍
	 */
	public void setYundongContent(String yundongContent) {
		this.yundongContent = yundongContent;
	}

	/**
	 * 获取： 逻辑删除
	 */
	public Integer getYundongDelete() {
		return yundongDelete;
	}

	/**
	 * 设置： 逻辑删除
	 */
	public void setYundongDelete(Integer yundongDelete) {
		this.yundongDelete = yundongDelete;
	}


	@Override
	public String toString() {
		// 重写toString方法，返回当前对象的字符串表示
		return "YundongCollectionView{" +
				// 开始构建字符串表示
				", yundongCollectionValue=" + yundongCollectionValue +
				// 添加yundongCollectionValue字段的值
				", yundongName=" + yundongName +
				// 添加yundongName字段的值
				", yundongPhoto=" + yundongPhoto +
				// 添加yundongPhoto字段的值
				", yundongVideo=" + yundongVideo +
				// 添加yundongVideo字段的值
				", yundongNum=" + yundongNum +
				// 添加yundongNum字段的值
				", zanNumber=" + zanNumber +
				// 添加zanNumber字段的值
				", caiNumber=" + caiNumber +
				// 添加caiNumber字段的值
				", yundongContent=" + yundongContent +
				// 添加yundongContent字段的值
				", yundongDelete=" + yundongDelete +
				// 添加yundongDelete字段的值
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

