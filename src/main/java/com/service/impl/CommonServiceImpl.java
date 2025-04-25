package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CommonDao;
import com.service.CommonService;

/**
 * 系统用户
 * @author
 * @date
 */
@Service("commonService")
// 将类声明为Spring服务组件，名称为commonService
public class CommonServiceImpl implements CommonService {
	// 实现CommonService接口

	@Autowired
	private CommonDao commonDao;
	// 注入CommonDao

	public List<Map<String, Object>> pieSum(Map<String, Object> params){
		return commonDao.pieSum(params);
		// 调用CommonDao的pieSum方法，返回饼图数据（求和）
	}
	/**
	 * 查询饼图数据（求和）
	 *
	 * @param params 查询参数
	 * @return 饼图数据列表
	 */
	public List<Map<String, Object>> pieCount(Map<String, Object> params){
		return commonDao.pieCount(params);
		// 调用CommonDao的pieCount方法，返回饼图数据（计数）
	}
	/**
	 * 查询饼图数据（计数）
	 *
	 * @param params 查询参数
	 * @return 饼图数据列表
	 */
	public List<Map<String, Object>> barSumOne(Map<String, Object> params){
		return commonDao.barSumOne(params);
		// 调用CommonDao的barSumOne方法，返回柱状图数据（单个求和）
	}

	/**
	 * 查询柱状图数据（单个求和）
	 *
	 * @param params 查询参数
	 * @return 柱状图数据列表
	 */
	public List<Map<String, Object>> barCountOne(Map<String, Object> params){
		return commonDao.barCountOne(params);
		// 调用CommonDao的barCountOne方法，返回柱状图数据（单个计数）
	}
	/**
	 * 查询柱状图数据（单个计数）
	 *
	 * @param params 查询参数
	 * @return 柱状图数据列表
	 */
	public List<Map<String, Object>> barSumTwo(Map<String, Object> params){
		return commonDao.barSumTwo(params);
		// 调用CommonDao的barSumTwo方法，返回柱状图数据（两个求和）
	}

	/**
	 * 查询柱状图数据（两个求和）
	 *
	 * @param params 查询参数
	 * @return 柱状图数据列表
	 */
	public List<Map<String, Object>> barCountTwo(Map<String, Object> params){
		return commonDao.barCountTwo(params);
		// 调用CommonDao的barCountTwo方法，返回柱状图数据（两个计数）
	}
	/**
	 * 查询评分
	 *
	 * @param params 查询参数，包含tableName（查询表）、condition1（条件1）、condition1Value（条件1值）、average（计算平均评分）
	 * @return 评分数据
	 */
	public Map<String, Object> queryScore(Map<String, Object> params){
		return commonDao.queryScore(params);
		// 调用CommonDao的queryScore方法，返回评分数据
	}
	/**
	 * 新的级联字典表的 分组求和统计
	 *
	 * @param params 查询参数
	 * @return 分组求和统计数据列表
	 */
	@Override
	public List<Map<String, Object>> newSelectGroupSum(Map<String, Object> params) {
		return commonDao.newSelectGroupSum(params);
		// 调用CommonDao的newSelectGroupSum方法，返回分组求和统计数据
	}
	/**
	 * 新的级联字典表的 分组条数统计
	 *
	 * @param params 查询参数
	 * @return 分组条数统计数据列表
	 */
	@Override
	public List<Map<String, Object>> newSelectGroupCount(Map<String, Object> params) {
		return commonDao.newSelectGroupCount(params);
		// 调用CommonDao的newSelectGroupCount方法，返回分组条数统计数据
	}
	/**
	 * 柱状图求和
	 *
	 * @param params 查询参数
	 * @return 柱状图求和数据列表
	 */
	public List<Map<String, Object>> barSum(Map<String, Object> params){
		return commonDao.barSum(params);
		// 调用CommonDao的barSum方法，返回柱状图求和数据
	}

	/**
	 * 柱状图统计
	 *
	 * @param params 查询参数
	 * @return 柱状图统计数据列表
	 */
	public List<Map<String, Object>> barCount(Map<String, Object> params){
		return commonDao.barCount(params);
		// 调用CommonDao的barCount方法，返回柱状图统计数据
	}

}
