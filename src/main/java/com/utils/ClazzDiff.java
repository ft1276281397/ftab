package com.utils;

// 导入自定义注解ColumnInfo
import com.annotation.ColumnInfo;
// 导入Ant工具类DateUtils，用于日期格式化
import org.apache.tools.ant.util.DateUtils;
// 导入Spring的Nullable注解，用于表示方法参数可以为null
import org.springframework.lang.Nullable;
// 导入Spring的Assert类，用于断言操作
import org.springframework.util.Assert;

// 导入Java反射Field类，用于操作类的字段
import java.lang.reflect.Field;
// 导入Java列表类，用于存储列表数据
import java.util.ArrayList;
// 导入Java数组工具类，用于操作数组
import java.util.Arrays;
// 导入Java日期类，用于日期操作
import java.util.Date;
// 导入Java列表接口，用于存储列表数据
import java.util.List;

/**
 * 对象比较
 */
public class ClazzDiff {

    /**
     * 对象比较,返回出list,obj1的属性值为空的时候自动忽略比对obj2的属性值
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @param ignoreProperties 忽略的属性
     * @return 包含不同属性的列表
     * @throws NoSuchFieldException 如果字段不存在
     * @throws IllegalAccessException 如果无法访问字段
     */
    public List<String> ClazzDiffColumn(Object obj1, Object obj2, @Nullable String... ignoreProperties) throws NoSuchFieldException, IllegalAccessException {

        // 断言obj1不为空
        Assert.notNull(obj1, "obj1不是空的");
        // 断言obj2不为空
        Assert.notNull(obj2, "obj2不是空的");

        // 创建列表存储不同属性
        List<String> list = new ArrayList<>();

        // 获取obj1的类对象
        Class<?> obj1Class = obj1.getClass();
        // 获取obj2的类对象
        Class<?> obj2Class = obj2.getClass();
        // 检查两个对象是否是同一个类的实例
        if (!obj1Class.isInstance(obj2)) {
            throw new IllegalArgumentException("传入的两个类不是同一个类");
        }
        // 将忽略的属性转换为列表
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        /**
         * 当前表
         */
        // 获取obj1的所有声明字段
        Field[] obj1DeclaredFields = obj1Class.getDeclaredFields();
        for (Field f : obj1DeclaredFields) { // 遍历obj1的所有字段
            f.setAccessible(true);
            // 获取obj1的字段
            Field obj1Field = obj1Class.getDeclaredField(f.getName());
            // 获取obj2的字段
            Field obj2Field = obj2Class.getDeclaredField(f.getName());
            obj1Field.setAccessible(true);
            obj2Field.setAccessible(true);

            // 如果obj1的字段不为空，并且不在忽略列表中
            if (obj1Field.get(obj1) != null && (ignoreList == null || !ignoreList.contains(obj1Field.getName()))) {
                // 获取字段的ColumnInfo注解
                ColumnInfo columnInfo = obj1Field.getAnnotation(ColumnInfo.class);
                // 获取obj1字段的值
                Object o1 = obj1Field.get(obj1);
                // 获取obj2字段的值
                Object o2 = obj2Field.get(obj2);

                // 如果字段值不同
                if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                    // 根据字段名判断字段类型并添加到列表中
                    if (f.getName().contains("File")) {
                        list.add(columnInfo.comment() + "-->现在 : <a type='success' style='text-decoration:none' class='el-button' href='" + o1 + "' >文件下载</a>,原先 : <a type='success' style='text-decoration:none' class='el-button' href='" + o2 + "' >文件下载</a>");
                    } else if (f.getName().contains("Video")) {
                        list.add(columnInfo.comment() + "-->现在 : <video src='" + o1 + "' width='100px' height='100px' controls='controls'></video>,原先 : <video src='" + o2 + "' width='100px' height='100px' controls='controls'></video>");
                    } else if (f.getName().contains("Photo")) {
                        list.add(columnInfo.comment() + "-->现在 : <img src='" + o1 + "' width='100px' height='100px'>,原先 : <img src='" + o2 + "' width='100px' height='100px'>");
                    } else if (f.getName().contains("Time")) {
                        list.add(columnInfo.comment() + "-->现在 : [" + DateUtils.format((Date) o1, "yyyy-MM-dd") + "],原先 : [" + DateUtils.format((Date) o2, "yyyy-MM-dd") + "]");
                    } else {
                        list.add(columnInfo.comment() + "-->现在 : [" + o1 + "],原先 : [" + o2 + "]");
                    }
                }
            }
        }

        /**
         * 父表
         */
        // 获取obj1的父类的所有声明字段
        Field[] obj1DeclaredFields2 = obj1Class.getSuperclass().getDeclaredFields();
        for (Field f : obj1DeclaredFields2) { // 遍历obj1的父类的所有字段
            f.setAccessible(true);
            // 获取obj1的父类的字段
            Field obj1Field = obj1Class.getSuperclass().getDeclaredField(f.getName());
            // 获取obj2的父类的字段
            Field obj2Field = obj2Class.getSuperclass().getDeclaredField(f.getName());
            obj1Field.setAccessible(true);
            obj2Field.setAccessible(true);

            // 如果obj1的字段不为空，并且不在忽略列表中
            if (obj1Field.get(obj1) != null && (ignoreList == null || !ignoreList.contains(obj1Field.getName()))) {
                // 获取字段的ColumnInfo注解
                ColumnInfo columnInfo = obj1Field.getAnnotation(ColumnInfo.class);
                // 获取obj1字段的值
                Object o1 = obj1Field.get(obj1);
                // 获取obj2字段的值
                Object o2 = obj2Field.get(obj2);

                // 如果字段值不同
                if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                    // 根据字段名判断字段类型并添加到列表中
                    if (f.getName().contains("File")) {
                        list.add(columnInfo.comment() + "-->现在 : <a type='success' style='text-decoration:none' class='el-button' href='" + o1 + "' >文件下载</a>,原先 : <a type='success' style='text-decoration:none' class='el-button' href='" + o2 + "' >文件下载</a>");
                    } else if (f.getName().contains("Video")) {
                        list.add(columnInfo.comment() + "-->现在 : <video src='" + o1 + "' width='100px' height='100px' controls='controls'></video>,原先 : <video src='" + o2 + "' width='100px' height='100px' controls='controls'></video>");
                    } else if (f.getName().contains("Photo")) {
                        list.add(columnInfo.comment() + "-->现在 : <img src='" + o1 + "' width='100px' height='100px'>,原先 : <img src='" + o2 + "' width='100px' height='100px'>");
                    } else if (f.getName().contains("Time")) {
                        list.add(columnInfo.comment() + "-->现在 : [" + DateUtils.format((Date) o1, "yyyy-MM-dd") + "],原先 : [" + DateUtils.format((Date) o2, "yyyy-MM-dd") + "]");
                    } else {
                        list.add(columnInfo.comment() + "-->现在 : [" + o1 + "],原先 : [" + o2 + "]");
                    }
                }
            }
        }
        // 返回包含不同属性的列表
        return list;
    }

    /**
     * 判断本实体有没有这个字段
     *
     * @param c 类对象
     * @param fieldName 字段名
     * @return 如果存在返回true，否则返回false
     */
    public boolean hasField(Class c, String fieldName) {
        // 获取类的所有声明字段
        Field[] fields = c.getDeclaredFields();

        // 遍历所有字段
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 示例代码，用于测试ClazzDiffColumn方法
//        ChengpinEntity chengpinEntity1 = new ChengpinEntity();
//
//        chengpinEntity1.setId(2);
//        chengpinEntity1.setXiaoshourenyuanId(3);
//        chengpinEntity1.setChengpinUuidNumber("4");
//        chengpinEntity1.setChengpinName("6");
//        chengpinEntity1.setChengpinSchool("7");
//        chengpinEntity1.setChengpinLianxifangshi("8");
//        chengpinEntity1.setChengpinXiaoshouJine(9.0);
//        chengpinEntity1.setChengpinTichengJine(10.0);
//        chengpinEntity1.setLunwenTypes(11);
//        chengpinEntity1.setLunwenXiaoshouJine(12.1);
//        chengpinEntity1.setLunwenTichengJine(13.1);
//        chengpinEntity1.setChengpinZhuangtaiTypes(14);
//        chengpinEntity1.setChengpinFile("15");
//        chengpinEntity1.setChengpinText("16");
//        chengpinEntity1.setChengpinDelete(1);
//        chengpinEntity1.setInsertTime(new Date());
//        chengpinEntity1.setUpdateTime(null);
//        chengpinEntity1.setCreateTime(null);
//
//
//
//
//        ChengpinEntity chengpinEntity2 = new ChengpinEntity();
//
//        chengpinEntity2.setId(3);
//        chengpinEntity2.setXiaoshourenyuanId(4);
//        chengpinEntity2.setChengpinUuidNumber("4");
//        chengpinEntity2.setChengpinName("6");
//        chengpinEntity2.setChengpinSchool("7");
//        chengpinEntity2.setChengpinLianxifangshi("8");
//        chengpinEntity2.setChengpinXiaoshouJine(9.0);
//        chengpinEntity2.setChengpinTichengJine(10.0);
//        chengpinEntity2.setLunwenTypes(11);
//        chengpinEntity2.setLunwenXiaoshouJine(12.1);
//        chengpinEntity2.setLunwenTichengJine(13.1);
//        chengpinEntity2.setChengpinZhuangtaiTypes(14);
//        chengpinEntity2.setChengpinFile("16");
//        chengpinEntity2.setChengpinText("16");
//        chengpinEntity2.setChengpinDelete(1);
//        chengpinEntity2.setInsertTime(null);
//        chengpinEntity2.setUpdateTime(new Date());
//        chengpinEntity2.setCreateTime(null);

//        List<String> strings = new ClazzDiff<ChengpinEntity>().ClazzDiffColumn(chengpinEntity1, chengpinEntity2,new String[]{"serialVersionUID"});
//        List<String> strings = new ClazzDiff().ClazzDiffColumn(chengpinEntity1, chengpinEntity2);
//        System.out.println(strings);

    }

}
