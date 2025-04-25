package com.dao;

import com.entity.GerentizhengEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.GerentizhengView;

/**
 * 体检记录 Dao 接口
 *
 * @author 
 */
/**
 * 根据分页参数和查询条件获取体检记录视图列表
 *
 * @param page   分页对象，包含当前页码和每页大小等信息
 * @param params 查询参数，以键值对的形式传递查询条件
 * @return 符合条件的体检记录视图列表
 */
public interface GerentizhengDao extends BaseMapper<GerentizhengEntity> {

   List<GerentizhengView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
