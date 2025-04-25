package com.utils;

// 导入Arrays类，用于操作数组
import java.util.Arrays;
// 导入HashMap类，用于存储键值对
import java.util.HashMap;
// 导入Iterator类，用于遍历集合
import java.util.Iterator;
// 导入Map接口，用于存储键值对
import java.util.Map;

// 导入StringUtils类，用于字符串操作
import org.apache.commons.lang3.StringUtils;

// 导入BeanUtil类，用于Bean对象操作
import cn.hutool.core.bean.BeanUtil;

// 导入Wrapper类，用于构建查询条件
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * Mybatis-Plus工具类
 */
public class MPUtil {
    // 定义下划线常量
    public static final char UNDERLINE = '_';

    /**
     * 将对象转换为Map，并将键转换为下划线格式，带有前缀
     *
     * @param bean 对象
     * @param pre  前缀
     * @return 转换后的Map
     */
    public static Map<String, Object> allEQMapPre(Object bean, String pre) {
        // 将对象转换为Map
        Map<String, Object> map = BeanUtil.beanToMap(bean);
        // 将Map的键转换为下划线格式，并添加前缀
        return camelToUnderlineMap(map, pre);
    }

    /**
     * 将对象转换为Map，并将键转换为下划线格式
     *
     * @param bean 对象
     * @return 转换后的Map
     */
    public static Map<String, Object> allEQMap(Object bean) {
        // 将对象转换为Map
        Map<String, Object> map = BeanUtil.beanToMap(bean);
        // 将Map的键转换为下划线格式
        return camelToUnderlineMap(map, "");
    }

    /**
     * 将对象转换为Map，并将键转换为下划线格式，带有前缀，并生成like查询条件
     *
     * @param wrapper 查询条件包装器
     * @param bean    对象
     * @param pre     前缀
     * @return 包含like查询条件的包装器
     */
    public static Wrapper allLikePre(Wrapper wrapper, Object bean, String pre) {
        // 将对象转换为Map
        Map<String, Object> map = BeanUtil.beanToMap(bean);
        // 将Map的键转换为下划线格式，并添加前缀
        Map result = camelToUnderlineMap(map, pre);
        // 生成like查询条件
        return genLike(wrapper, result);
    }

    /**
     * 将对象转换为Map，并生成like查询条件
     *
     * @param wrapper 查询条件包装器
     * @param bean    对象
     * @return 包含like查询条件的包装器
     */
    public static Wrapper allLike(Wrapper wrapper, Object bean) {
        // 将对象转换为Map
        Map result = BeanUtil.beanToMap(bean, true, true);
        // 生成like查询条件
        return genLike(wrapper, result);
    }

    /**
     * 生成like查询条件
     *
     * @param wrapper 查询条件包装器
     * @param param   参数Map
     * @return 包含like查询条件的包装器
     */
    public static Wrapper genLike(Wrapper wrapper, Map param) {
        // 获取Map的迭代器
        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
        int i = 0;
        // 遍历Map中的每个条目
        while (it.hasNext()) {
            if (i > 0) {
                // 添加and条件
                wrapper.and();
            }
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String value = (String) entry.getValue();
            // 添加like查询条件
            wrapper.like(key, value);
            i++;
        }
        // 返回包含like查询条件的包装器
        return wrapper;
    }

    /**
     * 将对象转换为Map，并生成like或eq查询条件
     *
     * @param wrapper 查询条件包装器
     * @param bean    对象
     * @return 包含like或eq查询条件的包装器
     */
    public static Wrapper likeOrEq(Wrapper wrapper, Object bean) {
        // 将对象转换为Map
        Map result = BeanUtil.beanToMap(bean, true, true);
        // 生成like或eq查询条件
        return genLikeOrEq(wrapper, result);
    }

    /**
     * 生成like或eq查询条件
     *
     * @param wrapper 查询条件包装器
     * @param param   参数Map
     * @return 包含like或eq查询条件的包装器
     */
    public static Wrapper genLikeOrEq(Wrapper wrapper, Map param) {
        // 获取Map的迭代器
        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
        int i = 0;
        // 遍历Map中的每个条目
        while (it.hasNext()) {
            if (i > 0) {
                // 添加and条件
                wrapper.and();
            }
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            // 如果值包含%，则生成like查询条件，否则生成eq查询条件
            if (entry.getValue().toString().contains("%")) {
                wrapper.like(key, entry.getValue().toString().replace("%", ""));
            } else {
                wrapper.eq(key, entry.getValue());
            }
            i++;
        }
        // 返回包含like或eq查询条件的包装器
        return wrapper;
    }

    /**
     * 将对象转换为Map，并生成eq查询条件
     *
     * @param wrapper 查询条件包装器
     * @param bean    对象
     * @return 包含eq查询条件的包装器
     */
    public static Wrapper allEq(Wrapper wrapper, Object bean) {
        // 将对象转换为Map
        Map result = BeanUtil.beanToMap(bean, true, true);
        // 生成eq查询条件
        return genEq(wrapper, result);
    }

    /**
     * 生成eq查询条件
     *
     * @param wrapper 查询条件包装器
     * @param param   参数Map
     * @return 包含eq查询条件的包装器
     */
    public static Wrapper genEq(Wrapper wrapper, Map param) {
        // 获取Map的迭代器
        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
        int i = 0;
        // 遍历Map中的每个条目
        while (it.hasNext()) {
            if (i > 0) {
                // 添加and条件
                wrapper.and();
            }
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            // 添加eq查询条件
            wrapper.eq(key, entry.getValue());
            i++;
        }
        // 返回包含eq查询条件的包装器
        return wrapper;
    }

    /**
     * 生成between查询条件
     *
     * @param wrapper 查询条件包装器
     * @param params  参数Map
     * @return 包含between查询条件的包装器
     */
    public static Wrapper between(Wrapper wrapper, Map<String, Object> params) {
        // 遍历参数Map中的每个键
        for (String key : params.keySet()) {
            String columnName = "";
            // 如果键以"_start"结尾
            if (key.endsWith("_start")) {
                columnName = key.substring(0, key.indexOf("_start"));
                // 如果值不为空，则添加ge查询条件
                if (StringUtils.isNotBlank(params.get(key).toString())) {
                    wrapper.ge(columnName, params.get(key));
                }
            }
            // 如果键以"_end"结尾
            if (key.endsWith("_end")) {
                columnName = key.substring(0, key.indexOf("_end"));
                // 如果值不为空，则添加le查询条件
                if (StringUtils.isNotBlank(params.get(key).toString())) {
                    wrapper.le(columnName, params.get(key));
                }
            }
        }
        // 返回包含between查询条件的包装器
        return wrapper;
    }

    /**
     * 生成排序查询条件
     *
     * @param wrapper 查询条件包装器
     * @param params  参数Map
     * @return 包含排序查询条件的包装器
     */
    public static Wrapper sort(Wrapper wrapper, Map<String, Object> params) {
        String order = "";
        // 获取order参数
        if (params.get("order") != null && StringUtils.isNotBlank(params.get("order").toString())) {
            order = params.get("order").toString();
        }
        // 获取sort参数
        if (params.get("sort") != null && StringUtils.isNotBlank(params.get("sort").toString())) {
            // 根据order参数生成排序查询条件
            if (order.equalsIgnoreCase("desc")) {
                wrapper.orderDesc(Arrays.asList(params.get("sort")));
            } else {
                wrapper.orderAsc(Arrays.asList(params.get("sort")));
            }
        }
        // 返回包含排序查询条件的包装器
        return wrapper;
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param 驼峰格式字符串
     * @return 下划线格式字符串
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        // 遍历字符串中的每个字符
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            // 如果字符是大写字母，则添加下划线并转换为小写字母
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        // 返回下划线格式字符串
        return sb.toString();
    }

    public static void main(String[] ages) {
        // 测试camelToUnderline方法
        System.out.println(camelToUnderline("ABCddfANM"));
    }

    /**
     * 将Map的键从驼峰格式转换为下划线格式，并添加前缀
     *
     * @param param 参数Map
     * @param pre   前缀
     * @return 转换后的Map
     */
    public static Map<String, Object> camelToUnderlineMap(Map param, String pre) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        // 获取Map的迭代器
        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
        // 遍历Map中的每个条目
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            // 将键转换为下划线格式
            String newKey = camelToUnderline(key);
            // 根据前缀添加键值对到新Map中
            if (pre.endsWith(".")) {
                newMap.put(pre + newKey, entry.getValue());
            } else if (StringUtils.isEmpty(pre)) {
                newMap.put(newKey, entry.getValue());
            } else {
                newMap.put(pre + "." + newKey, entry.getValue());
            }
        }
        // 返回转换后的Map
        return newMap;
    }
}
