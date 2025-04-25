package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.DictionaryEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
 * 字典
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("dictionary")
public class DictionaryView extends DictionaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // 定义序列化版本号

    // 当前表

    public DictionaryView() {
        // 默认构造函数
    }

    public DictionaryView(DictionaryEntity dictionaryEntity) {
        try {
            BeanUtils.copyProperties(this, dictionaryEntity);
            // 将传入的DictionaryEntity对象的属性复制到当前对象中
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 打印异常堆栈信息
        }
    }

    @Override
    public String toString() {
        return "DictionaryView{" +
            "} " + super.toString();
        // 重写toString方法，返回当前对象的字符串表示
    }
}
