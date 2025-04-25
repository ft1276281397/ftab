package com.utils;

// 导入Serializable接口，用于序列化
import java.io.Serializable;
// 导入List接口，用于存储列表数据
import java.util.List;
// 导入Map接口，用于存储键值对
import java.util.Map;

// 导入MyBatis-Plus的Page类，用于分页查询
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 分页工具类
 */
public class PageUtils implements Serializable {
    // 序列化版本号
    private static final long serialVersionUID = 1L;
    // 总记录数
    private long total;
    // 每页记录数
    private int pageSize;
    // 总页数
    private long totalPage;
    // 当前页数
    private int currPage;
    // 列表数据
    private List<?> list;

    /**
     * 分页构造函数
     *
     * @param list        列表数据
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param currPage    当前页数
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.total = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        // 计算总页数
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页构造函数
     *
     * @param page MyBatis-Plus的Page对象
     */
    // 构造函数，用于通过MyBatis-Plus的Page对象初始化PageUtils对象
    public PageUtils(Page<?> page) {
        // 将Page对象中的记录列表赋值给当前对象的list属性
        this.list = page.getRecords();
        // 将Page对象中的总记录数赋值给当前对象的total属性
        this.total = page.getTotal();
        // 将Page对象中的每页记录数赋值给当前对象的pageSize属性
        this.pageSize = page.getSize();
        // 将Page对象中的当前页码赋值给当前对象的currPage属性
        this.currPage = page.getCurrent();
        // 将Page对象中的总页数赋值给当前对象的totalPage属性
        this.totalPage = page.getPages();
    }

    /**
     * 空数据的分页构造函数
     *
     * @param params 分页参数Map
     */
      // 构造函数，用于通过分页参数Map初始化PageUtils对象
    public PageUtils(Map<String, Object> params) {
        // 根据分页参数Map创建Query对象，并获取对应的Page对象
        Page page = new Query(params).getPage();
        // 调用另一个构造函数进行初始化
        new PageUtils(page);
    }


    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize 每页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取当前页数
     *
     * @return 当前页数
     */
    public int getCurrPage() {
        return currPage;
    }

    /**
     * 设置当前页数
     *
     * @param currPage 当前页数
     */
    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    /**
     * 获取列表数据
     *
     * @return 列表数据
     */
    public List<?> getList() {
        return list;
    }

    /**
     * 设置列表数据
     *
     * @param list 列表数据
     */
    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * 设置总页数
     *
     * @param totalPage 总页数
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     *
     * @param total 总记录数
     */
    public void setTotal(long total) {
        this.total = total;
    }
}
