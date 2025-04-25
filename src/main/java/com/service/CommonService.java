package com.service;

// 导入Java列表类，用于存储列表数据
import java.util.List;
// 导入Java映射类，用于存储键值对
import java.util.Map;

/**
 * 公共服务接口
 */
public interface CommonService {
	/**
	 * 查询饼图求和数据
	 *
	 * @param params 查询参数，包含分组条件和其他查询条件
	 * @return 包含求和结果的列表
	 */
	List<Map<String, Object>> pieSum(Map<String, Object> params);

	/**
	 * 查询饼图统计数据
	 *
	 * @param params 查询参数，包含分组条件和其他查询条件
	 * @return 包含统计结果的列表
	 */
	List<Map<String, Object>> pieCount(Map<String, Object> params);

	/**
	 * 查询柱状图求和数据（第一组）
	 *
	 * @param params 查询参数，包含分组条件和其他查询条件
	 * @return 包含求和结果的列表
	 */
	List<Map<String, Object>> barSumOne(Map<String, Object> params);

	/**
	 * 查询柱状图统计数据（第一组）
	 *
	 * @param params 查询参数，包含分组条件和其他查询条件
	 * @return 包含统计结果的列表
	 */
	List<Map<String, Object>> barCountOne(Map<String, Object> params);

	/**
	 * 查询柱状图求和数据（第二组）
	 *
	 * @param params 查询参数，包含分组条件和其他查询条件
	 * @return 包含求和结果的列表
	 */
	List<Map<String, Object>> barSumTwo(Map<String, Object> params);

	List<Map<String, Object>> barCountTwo(Map<String, Object> params);

	/**
	 tableName 查询表
	 condition1 条件1
	 condition1Value 条件1值
	 average 计算平均评分
	 * */
	Map<String, Object> queryScore(Map<String, Object> params);
	/**
	 * 新的级联字典表的  分组求和统计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> newSelectGroupSum(Map<String, Object> params);
	/**
	 * 新的级联字典表的  分组求和统计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> newSelectGroupCount(Map<String, Object> params);
	/**
	 * 柱状图求和
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> barSum(Map<String, Object> params);
	/**
	 * 柱状图统计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> barCount(Map<String, Object> params);

}
