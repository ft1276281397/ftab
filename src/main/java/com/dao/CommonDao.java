package com.dao;

import java.util.List;
import java.util.Map;

/**
 * 通用接口
 * 该接口定义了通用的数据访问方法，用于处理各种统计和查询操作。
 */
public interface CommonDao {
    /**
     * 饼图求和
     * 该方法用于根据传入的参数进行饼图数据的求和操作。
     * @param params 查询参数
     * @return 包含求和结果的列表
     */
    List<Map<String, Object>> pieSum(Map<String, Object> params);

    /**
     * 饼图统计
     * 该方法用于根据传入的参数进行饼图数据的统计操作。
     * @param params 查询参数
     * @return 包含统计结果的列表
     */
    List<Map<String, Object>> pieCount(Map<String, Object> params);

    /**
     * 单柱状图求和
     * 该方法用于根据传入的参数进行单柱状图数据的求和操作。
     * @param params 查询参数
     * @return 包含求和结果的列表
     */
    List<Map<String, Object>> barSumOne(Map<String, Object> params);

    /**
     * 单柱状图统计
     * 该方法用于根据传入的参数进行单柱状图数据的统计操作。
     * @param params 查询参数
     * @return 包含统计结果的列表
     */
    List<Map<String, Object>> barCountOne(Map<String, Object> params);

    /**
     * 双柱状图求和
     * 该方法用于根据传入的参数进行双柱状图数据的求和操作。
     * @param params 查询参数
     * @return 包含求和结果的列表
     */
    List<Map<String, Object>> barSumTwo(Map<String, Object> params);

    /**
     * 双柱状图统计
     * 该方法用于根据传入的参数进行双柱状图数据的统计操作。
     * @param params 查询参数
     * @return 包含统计结果的列表
     */
    List<Map<String, Object>> barCountTwo(Map<String, Object> params);

    /**
     * 查询分数
     * 该方法用于根据传入的参数查询分数。
     * @param params 查询参数
     * @return 包含分数结果的Map
     */
    Map<String, Object> queryScore(Map<String, Object> params);

    /**
     * 新的级联字典表的分组求和方法
     * 该方法用于根据传入的参数进行新的级联字典表的分组求和操作。
     * @param params 查询参数
     * @return 包含分组求和结果的列表
     */
    List<Map<String, Object>> newSelectGroupSum(Map<String, Object> params);

    /**
     * 新的级联字典表的分组条数统计方法
     * 该方法用于根据传入的参数进行新的级联字典表的分组条数统计操作。
     * @param params 查询参数
     * @return 包含分组统计结果的列表
     */
    List<Map<String, Object>> newSelectGroupCount(Map<String, Object> params);

    /**
     * 柱状图求和
     * 该方法用于根据传入的参数进行柱状图数据的求和操作。
     * @param params 查询参数
     * @return 包含求和结果的列表
     */
    List<Map<String, Object>> barSum(Map<String, Object> params);

    /**
     * 柱状图统计
     * 该方法用于根据传入的参数进行柱状图数据的统计操作。
     * @param params 查询参数
     * @return 包含统计结果的列表
     */
    List<Map<String, Object>> barCount(Map<String, Object> params);
}
