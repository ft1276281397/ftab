package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.YundongEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 运动教程
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("yundong")
public class YundongView extends YundongEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	 * 运动类型的值
	 */
	@ColumnInfo(comment = "运动类型的字典表值", type = "varchar(200)")
	private String yundongValue;


	public YundongView() {
		// 默认构造函数
	}

	public YundongView(YundongEntity yundongEntity) {
		// 带参数的构造函数，用于从YundongEntity对象复制属性
		try {
			BeanUtils.copyProperties(this, yundongEntity);
			// 使用BeanUtils工具将传入的YundongEntity对象的属性复制到当前对象中
		} catch (IllegalAccessException | InvocationTargetException e) {
			// 捕获并处理IllegalAccessException和InvocationTargetException异常
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 打印异常堆栈信息
		}
	}


	//当前表的

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


	@Override
	public String toString() {
		// 重写toString方法，返回当前对象的字符串表示
		return "YundongView{" +
				// 开始构建字符串表示
				", yundongValue=" + yundongValue +
				// 添加yundongValue字段的值
				"} " + super.toString();
		// 结束字符串表示，并调用父类的toString方法
	}
}

