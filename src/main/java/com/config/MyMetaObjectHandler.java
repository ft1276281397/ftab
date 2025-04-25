package com.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

/**
 * 自定义填充处理器
 */
public class MyMetaObjectHandler extends MetaObjectHandler {
    /**
     * 插入操作时的字段填充方法
     * 在插入数据时，自动设置ctime字段为当前时间
     *
     * @param metaObject 元对象，包含插入操作的实体类信息
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("ctime", new Date(), metaObject);
    }
    /**
     * 判断是否开启更新填充
     * 由于不需要在更新操作时填充字段，返回false
     *
     * @return boolean 布尔值，表示是否开启更新填充
     */
    @Override
    public boolean openUpdateFill() {
        return false;
    }
    /**
     * 更新操作时的字段填充方法
     * 由于openUpdateFill方法返回false，此方法不会被执行
     *
     * @param metaObject 元对象，包含更新操作的实体类信息
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 关闭更新填充、这里不执行
    }
}
