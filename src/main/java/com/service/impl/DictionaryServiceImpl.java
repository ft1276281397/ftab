package com.service.impl;

import com.utils.StringUtil;
import com.service.DictionaryService;
import com.utils.ClazzDiff;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import com.dao.DictionaryDao;
import com.entity.DictionaryEntity;
import com.service.DictionaryService;
import com.entity.view.DictionaryView;

/**
 * 字典 服务实现类
 */
@Service("dictionaryService")
// 将类声明为Spring服务组件，名称为dictionaryService
@Transactional
// 声明事务管理
public class DictionaryServiceImpl extends ServiceImpl<DictionaryDao, DictionaryEntity> implements DictionaryService {
    // 继承ServiceImpl并实现DictionaryService接口

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        // 实现分页查询方法
        Page<DictionaryView> page = new Query<DictionaryView>(params).getPage();
        // 使用Query工具类获取分页对象
        page.setRecords(baseMapper.selectListView(page, params));
        // 调用baseMapper的selectListView方法获取分页数据
        return new PageUtils(page);
        // 返回PageUtils对象，包含分页数据
    }

    /**
     * 赋值给字典表
     * @param obj view对象
     * @param request HttpServletRequest对象
     */
    public void dictionaryConvert(Object obj, HttpServletRequest request) {
        try {
            if (obj == null) return;
            // 如果对象为空，直接返回

            // 当前view和entity中的所有types的字段
            List<String> fieldNameList = new ArrayList<>();
            Class<?> tempClass = obj.getClass();
            while (tempClass != null) {
                Field[] declaredFields = tempClass.getDeclaredFields();
                // 获取当前类的所有字段
                for (Field f : declaredFields) {
                    f.setAccessible(true);
                    // 设置字段可访问
                    if (f.getType().getName().equals("java.lang.Integer") && f.getName().contains("Types")) {
                        fieldNameList.add(f.getName());
                        // 如果字段类型为Integer且名称包含"Types"，添加到列表中
                    }
                }
                tempClass = tempClass.getSuperclass();
                // 获取父类，继续处理
            }

            // 获取监听器中的字典表
            ServletContext servletContext = request.getServletContext();
            // 从请求中获取ServletContext
            Map<String, Map<Integer, String>> dictionaryMap = (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap"); // 从ServletContext中获取字典表

            // 通过Types的值给Value字段赋值
            for (String s : fieldNameList) {
                Field types = null;
                if (hasField(obj.getClass(), s)) {
                    // 判断view中有没有这个字段,有就通过反射取出字段
                    types = obj.getClass().getDeclaredField(s);
                    // 获取Types私有字段
                } else {
                    // 本表中没有这个字段,说明它是父表中的字段,也就是entity中的字段,从entity中取值
                    types = obj.getClass().getSuperclass().getDeclaredField(s); // 获取父类中的Types私有字段
                }
                Field value = obj.getClass().getDeclaredField(s.replace("Types", "Value")); // 获取value私有字段
                // 设置权限
                types.setAccessible(true);
                value.setAccessible(true);

                // 赋值
                if (StringUtil.isNotEmpty(String.valueOf(types.get(obj)))) {
                    // types的值不为空
                    int i = Integer.parseInt(String.valueOf(types.get(obj)));
                    // type
                    // 把s1字符中的所有大写转小写,并在前面加 _
                    char[] chars = s.toCharArray();
                    StringBuffer sbf = new StringBuffer();
                    for (int b = 0; b < chars.length; b++) {
                        char ch = chars[b];
                        if (ch <= 90 && ch >= 65) {
                            // 如果字符是大写字母
                            sbf.append("_");
                            ch += 32;
                            // 转换为小写字母
                        }
                        sbf.append(ch);
                    }
                    String s2 = dictionaryMap.get(sbf.toString()).get(i);
                    // 从字典表中获取对应的值
                    value.set(obj, s2); // 设置value字段的值
                } else {
                    new Exception("字典表赋值出现问题::::" + value.getName());
                    // 创建异常对象
                    value.set(obj, "");
                    // 设置value字段为空字符串
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // 打印异常堆栈信息
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 打印异常堆栈信息
        } catch (Exception e) {
            e.printStackTrace();
            // 打印异常堆栈信息
        }
    }

    /**
     * 判断本实体有没有这个字段
     * @param c 类对象
     * @param fieldName 字段名称
     * @return 是否包含该字段
     */
    public boolean hasField(Class<?> c, String fieldName) {
        Field[] fields = c.getDeclaredFields();
        // 获取类的所有字段

        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
                // 如果找到字段，返回true
            }
        }

        return false;
        // 如果未找到字段，返回false
    }
}
