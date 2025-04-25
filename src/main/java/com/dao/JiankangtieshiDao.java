package com.dao;

import com.entity.JiankangtieshiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.JiankangtieshiView;

/**
 * 健康贴士 Dao 接口
 *
 * @author 
 */
/**
 * 根据分页参数和查询条件获取健康贴士视图列表
 *
 * @param page   分页对象，包含当前页码和每页大小等信息
 * @param params 查询参数，以键值对的形式传递查询条件
 * @return 符合条件的健康贴士视图列表
 */
public interface JiankangtieshiDao extends BaseMapper<JiankangtieshiEntity> {

   List<JiankangtieshiView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
