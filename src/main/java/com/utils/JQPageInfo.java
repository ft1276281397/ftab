package com.utils;

/**
 * 分页信息类
 */
public class JQPageInfo {
    // 当前页码
    private Integer page;

    // 每页显示的记录数
    private Integer limit;

    // 排序列名
    private String sidx;

    // 排序方式（asc或desc）
    private String order;

    // 偏移量（计算公式：(page - 1) * limit）
    private Integer offset;

    /**
     * 获取当前页码
     *
     * @return 当前页码
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 设置当前页码
     *
     * @param page 当前页码
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 获取每页显示的记录数
     *
     * @return 每页显示的记录数
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * 设置每页显示的记录数
     *
     * @param limit 每页显示的记录数
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 获取排序列名
     *
     * @return 排序列名
     */
    public String getSidx() {
        return sidx;
    }

    /**
     * 设置排序列名
     *
     * @param sidx 排序列名
     */
    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    /**
     * 获取排序方式
     *
     * @return 排序方式（asc或desc）
     */
    public String getOrder() {
        return order;
    }

    /**
     * 设置排序方式
     *
     * @param order 排序方式（asc或desc）
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 获取偏移量
     *
     * @return 偏移量
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * 设置偏移量
     *
     * @param offset 偏移量
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
