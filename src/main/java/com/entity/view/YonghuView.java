package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.YonghuEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 用户
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("yonghu")
public class YonghuView extends YonghuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	 * 性别的值
	 */
	@ColumnInfo(comment = "性别的字典表值", type = "varchar(200)")
	private String sexValue;


	public YonghuView() {
		// 默认构造函数
	}

	public YonghuView(YonghuEntity yonghuEntity) {
		// 带参数的构造函数，用于从YonghuEntity对象复制属性
		try {
			BeanUtils.copyProperties(this, yonghuEntity);
			// 使用BeanUtils工具将传入的YonghuEntity对象的属性复制到当前对象中
		} catch (IllegalAccessException | InvocationTargetException e) {
			// 捕获并处理IllegalAccessException和InvocationTargetException异常
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 打印异常堆栈信息
		}
	}


	//当前表的

	/**
	 * 获取： 性别的值
	 */
	public String getSexValue() {
		return sexValue;
	}

	/**
	 * 设置： 性别的值
	 */
	public void setSexValue(String sexValue) {
		this.sexValue = sexValue;
	}


	@Override
	public String toString() {
		// 重写toString方法，返回当前对象的字符串表示
		return "YonghuView{" +
				// 开始构建字符串表示
				", sexValue=" + sexValue +
				// 添加sexValue字段的值
				"} " + super.toString();
		// 结束字符串表示，并调用父类的toString方法
	}
}

