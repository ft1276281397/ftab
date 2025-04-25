package com.utils;

// 导入LinkedHashMap类，用于存储有序的键值对
import java.util.LinkedHashMap;
// 导入Map接口，用于存储键值对
import java.util.Map;

// 导入StringUtils类，用于字符串操作
import org.apache.commons.lang3.StringUtils;

// 导入Page类，用于分页查询
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 查询参数
 */
public class Query<T> extends LinkedHashMap<String, Object> {
    // 序列化版本号
    private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    /**
     * 构造函数，使用JQPageInfo对象初始化查询参数
     *
     * @param pageInfo JQPageInfo对象，包含分页和排序信息
     */
    public Query(JQPageInfo pageInfo) {
        // 分页参数
        if (pageInfo.getPage() != null) {
            currPage = pageInfo.getPage();
        }
        if (pageInfo.getLimit() != null) {
            limit = pageInfo.getLimit();
        }

        // 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject(pageInfo.getSidx());
        String order = SQLFilter.sqlInject(pageInfo.getOrder());

        // mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        // 排序
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
            this.page.setOrderByField(sidx);
            this.page.setAsc("ASC".equalsIgnoreCase(order));
        }
    }

    /**
     * 构造函数，使用Map对象初始化查询参数
     *
     * @param params 包含分页和排序信息的Map对象
     */
    public Query(Map<String, Object> params) {
        // 将所有参数添加到LinkedHashMap中
        this.putAll(params);

        // 分页参数
        if (params.get("page") != null) {
            currPage = Integer.parseInt(String.valueOf(params.get("page")));
        }
        if (params.get("limit") != null) {
            limit = Integer.parseInt(String.valueOf(params.get("limit")));
        }

        // 计算偏移量
        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        // 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String) params.get("sidx"));
        String order = SQLFilter.sqlInject((String) params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        // mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        // 排序
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
            this.page.setOrderByField(sidx);
            this.page.setAsc("ASC".equalsIgnoreCase(order));
        }
    }

    /**
     * 获取mybatis-plus分页对象
     *
     * @return 分页对象
     */
    public Page<T> getPage() {
        return page;
    }

    /**
     * 获取当前页码
     *
     * @return 当前页码
     */
    public int getCurrPage() {
        return currPage;
    }

    /**
     * 获取每页条数
     *
     * @return 每页条数
     */
    public int getLimit() {
        return limit;
    }
}
