package com.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ConfigEntity;
import com.service.CommonService;
import com.service.ConfigService;
import com.utils.BaiduUtil;
import com.utils.FileUtil;
import com.utils.R;

/**
 * 通用接口
 */
@RestController
public class
CommonController {
    // 定义日志记录器
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    // 自动注入CommonService
    @Autowired
    private CommonService commonService;


    /**
     * Java代码实现MySQL数据库导出
     *
     * @param mysqlUrl     MySQL安装路径
     * @param hostIP       MySQL数据库所在服务器地址IP
     * @param userName     进入数据库所需要的用户名
     * @param hostPort     数据库端口
     * @param password     进入数据库所需要的密码
     * @param savePath     数据库文件保存路径
     * @param fileName     数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     * @return 返回true表示导出成功，否则返回false。
     */
    @IgnoreAuth
    @RequestMapping("/beifen")
    public R beifen(String mysqlUrl, String hostIP, String userName, String hostPort, String password, String savePath, String fileName, String databaseName) {
        File saveFile = new File(savePath);
        // 如果目录不存在，则创建目录
        if (!saveFile.exists()) {// 如果目录不存在 
            saveFile.mkdirs();// 创建文件夹 
        }
        // 确保保存路径以文件分隔符结尾
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        // 初始化PrintWriter和BufferedReader
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            // 获取Runtime实例
            Runtime runtime = Runtime.getRuntime();
            // 构建mysqldump命令
            String cmd = mysqlUrl + "mysqldump -h" + hostIP + " -u" + userName + " -P" + hostPort + " -p" + password + " " + databaseName;
            // 执行mysqldump命令
            runtime.exec(cmd);
            // 再次执行mysqldump命令并获取进程
            Process process = runtime.exec(cmd);
            // 读取进程的输入流
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            // 创建PrintWriter用于写入导出文件
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            String line;
            // 逐行读取mysqldump的输出并写入文件
            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
            // 刷新PrintWriter
            printWriter.flush();
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回错误信息
            return R.error("备份数据出错");
        } finally {
            try {
                // 关闭BufferedReader
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                // 关闭PrintWriter
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (Exception e) {
                // 打印异常堆栈信息
                e.printStackTrace();
            }
        }
        // 返回成功信息
        return R.ok();
    }


    /**
     * Java实现MySQL数据库导入
     *
     * @param mysqlUrl     MySQL安装路径
     * @param hostIP       MySQL数据库所在服务器地址IP
     * @param userName     进入数据库所需要的用户名
     * @param hostPort     数据库端口
     * @param password     进入数据库所需要的密码
     * @param savePath     数据库文件保存路径
     * @param fileName     数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     */
    @IgnoreAuth
    @RequestMapping("/huanyuan")
    public R huanyuan(String mysqlUrl, String hostIP, String userName, String hostPort, String password, String savePath, String fileName, String databaseName) {
        try {
            // 获取Runtime实例
            Runtime rt = Runtime.getRuntime();
            // 构建mysql命令并执行
            Process child1 = rt.exec(mysqlUrl + "mysql.exe  -h" + hostIP + " -u" + userName + " -P" + hostPort + " -p" + password + " " + databaseName);
            // 获取进程的输出流
            OutputStream out = child1.getOutputStream(); // 控制台的输入信息作为输出流
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 读取导入文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(savePath + "/" + fileName), "utf-8"));
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            // 将文件内容写入数据库
            OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
            writer.write(outStr);
            // 刷新输出流以避免中文乱码
            writer.flush();
            // 关闭输出流
            out.close();
            // 关闭BufferedReader
            br.close();
            // 关闭OutputStreamWriter
            writer.close();
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回错误信息
            return R.error("数据导入出错");
        }
        // 返回成功信息
        return R.ok();
    }


    /**
     * 饼状图求和
     * @return
     */
    @RequestMapping("/pieSum")
    public R pieSum(@RequestParam Map<String, Object> params) {
        // 记录日志
        logger.debug("饼状图求和:,,Controller:{},,params:{}", this.getClass().getName(), params);
        // 调用服务层方法获取结果
        List<Map<String, Object>> result = commonService.pieSum(params);
        // 返回结果
        return R.ok().put("data", result);
    }

    /**
     * 饼状图统计
     * @return
     */
    @RequestMapping("/pieCount")
    public R pieCount(@RequestParam Map<String,Object> params) {
        // 记录日志
        logger.debug("饼状图统计:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用服务层方法获取结果
        List<Map<String, Object>> result = commonService.pieCount(params);
        // 返回结果
        return R.ok().put("data", result);
    }

    /**
     * 柱状图求和单列
     * @return
     */
    @RequestMapping("/barSumOne")
    public R barSumOne(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("柱状图求和单列:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 barSumOne 方法进行柱状图求和单列操作
        List<Map<String, Object>> result = commonService.barSumOne(params);

        // 创建一个 ArrayList 对象，用于存储报表的 x 轴数据
        List<String> xAxis = new ArrayList<>();//报表x轴
        // 创建一个 ArrayList 对象，用于存储报表的 y 轴数据
        List<List<String>> yAxis = new ArrayList<>();//y轴
        // 创建一个 ArrayList 对象，用于存储报表的标题数据
        List<String> legend = new ArrayList<>();//标题
        // 创建一个 ArrayList 对象，用于存储报表的第一个 y 轴数据
        List<String> yAxis0 = new ArrayList<>();
        // 将第一个 y 轴数据添加到 y 轴数据列表中
        yAxis.add(yAxis0);
        // 将空字符串添加到标题数据列表中
        legend.add("");
        // 遍历柱状图求和单列操作的结果
        for(Map<String, Object> map :result){
            // 获取结果中的名称数据，并转换为字符串
            String oneValue = String.valueOf(map.get("name"));
            // 获取结果中的值数据，并转换为字符串
            String value = String.valueOf(map.get("value"));
            // 将名称数据添加到 x 轴数据列表中
            xAxis.add(oneValue);
            // 将值数据添加到第一个 y 轴数据列表中
            yAxis0.add(value);
        }
        // 创建一个 HashMap 对象，用于存储最终的报表数据
        Map<String, Object> resultMap = new HashMap<>();
        // 将 x 轴数据添加到最终的报表数据中
        resultMap.put("xAxis",xAxis);
        // 将 y 轴数据添加到最终的报表数据中
        resultMap.put("yAxis",yAxis);
        // 将标题数据添加到最终的报表数据中
        resultMap.put("legend",legend);
        // 将最终的报表数据封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", resultMap);
    }
    /**
     * 柱状图统计单列
     * @return
     */
    // 使用 @RequestMapping 注解将该方法映射到 /barCountOne 路径
    @RequestMapping("/barCountOne")
    public R barCountOne(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("柱状图统计单列:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 barCountOne 方法进行柱状图统计单列操作
        List<Map<String, Object>> result = commonService.barCountOne(params);

        // 创建一个 ArrayList 对象，用于存储报表的 x 轴数据
        List<String> xAxis = new ArrayList<>();//报表x轴
        // 创建一个 ArrayList 对象，用于存储报表的 y 轴数据
        List<List<String>> yAxis = new ArrayList<>();//y轴
        // 创建一个 ArrayList 对象，用于存储报表的标题数据
        List<String> legend = new ArrayList<>();//标题

        // 创建一个 ArrayList 对象，用于存储报表的第一个 y 轴数据
        List<String> yAxis0 = new ArrayList<>();
        // 将第一个 y 轴数据添加到 y 轴数据列表中
        yAxis.add(yAxis0);
        // 将空字符串添加到标题数据列表中
        legend.add("");
        // 遍历柱状图统计单列操作的结果
        for(Map<String, Object> map :result){
            // 获取结果中的名称数据，并转换为字符串
            String oneValue = String.valueOf(map.get("name"));
            // 获取结果中的值数据，并转换为字符串
            String value = String.valueOf(map.get("value"));
            // 将名称数据添加到 x 轴数据列表中
            xAxis.add(oneValue);
            // 将值数据添加到第一个 y 轴数据列表中
            yAxis0.add(value);
        }
        // 创建一个 HashMap 对象，用于存储最终的报表数据
        Map<String, Object> resultMap = new HashMap<>();
        // 将 x 轴数据添加到最终的报表数据中
        resultMap.put("xAxis",xAxis);
        // 将 y 轴数据添加到最终的报表数据中
        resultMap.put("yAxis",yAxis);
        // 将标题数据添加到最终的报表数据中
        resultMap.put("legend",legend);
        // 将最终的报表数据封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", resultMap);
    }


    /**
     * 柱状图统计双列
     * @return
     */
    @RequestMapping("/barSumTwo")
    public R barSumTwo(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("柱状图统计双列:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 barSumTwo 方法进行柱状图统计双列操作
        List<Map<String, Object>> result = commonService.barSumTwo(params);
        // 创建一个 ArrayList 对象，用于存储报表的 x 轴数据
        List<String> xAxis = new ArrayList<>();//报表x轴
        // 创建一个 ArrayList 对象，用于存储报表的 y 轴数据
        List<List<String>> yAxis = new ArrayList<>();//y轴
        // 创建一个 ArrayList 对象，用于存储报表的标题数据
        List<String> legend = new ArrayList<>();//标题

        // 创建一个 LinkedHashMap 对象，用于存储柱状图统计双列操作的结果数据
        Map<String, HashMap<String, String>> dataMap = new LinkedHashMap<>();
        // 遍历柱状图统计双列操作的结果
        for(Map<String, Object> map :result){
            // 获取结果中的第一个名称数据，并转换为字符串
            String name1Value = String.valueOf(map.get("name1"));
            // 获取结果中的第二个名称数据，并转换为字符串
            String name2Value = String.valueOf(map.get("name2"));
            // 获取结果中的值数据，并转换为字符串
            String value = String.valueOf(map.get("value"));
            // 检查标题数据列表中是否包含第二个名称数据，如果不包含则添加到标题数据列表中
            if(!legend.contains(name2Value)){
                legend.add(name2Value);//添加完成后 就是最全的第二列的类型
            }
            // 检查 dataMap 中是否包含第一个名称数据
            if(dataMap.containsKey(name1Value)){
                // 如果包含，则将第二个名称数据和值数据添加到对应的 HashMap 中
                dataMap.get(name1Value).put(name2Value,value);
            }else{
                // 如果不包含，则创建一个新的 HashMap 对象，并将第二个名称数据和值数据添加到该 HashMap 中，然后将该 HashMap 添加到 dataMap 中
                HashMap<String, String> name1Data = new HashMap<>();
                name1Data.put(name2Value,value);
                dataMap.put(name1Value,name1Data);
            }

        }

        // 遍历标题数据列表，为每个标题创建一个 ArrayList 对象，并添加到 y 轴数据列表中
        for(int i =0; i<legend.size(); i++){
            yAxis.add(new ArrayList<String>());
        }

        // 获取 dataMap 中的所有键
        Set<String> keys = dataMap.keySet();
        // 遍历 dataMap 中的所有键
        for(String key:keys){
            // 将键添加到 x 轴数据列表中
            xAxis.add(key);
            // 获取键对应的 HashMap 对象
            HashMap<String, String> map = dataMap.get(key);
            // 遍历标题数据列表
            for(int i =0; i<legend.size(); i++){
                // 获取 y 轴数据列表中对应的 ArrayList 对象
                List<String> data = yAxis.get(i);
                // 检查 HashMap 中是否包含标题数据列表中的名称数据
                if(StringUtil.isNotEmpty(map.get(legend.get(i)))){
                    // 如果包含，则将对应的值数据添加到 ArrayList 对象中
                    data.add(map.get(legend.get(i)));
                }else{
                    // 如果不包含，则将 0 添加到 ArrayList 对象中
                    data.add("0");
                }
            }
        }
        // 打印空行，用于分隔输出
        System.out.println();

        // 创建一个 HashMap 对象，用于存储最终的报表数据
        Map<String, Object> resultMap = new HashMap<>();
        // 将 x 轴数据添加到最终的报表数据中
        resultMap.put("xAxis",xAxis);
        // 将 y 轴数据添加到最终的报表数据中
        resultMap.put("yAxis",yAxis);
        // 将标题数据添加到最终的报表数据中
        resultMap.put("legend",legend);
        // 将最终的报表数据封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", resultMap);
    }
    /**
     * 柱状图统计双列
     * @return
     */
    @RequestMapping("/barCountTwo")
    public R barCountTwo(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("柱状图统计双列:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 barCountTwo 方法进行柱状图统计双列操作
        List<Map<String, Object>> result = commonService.barCountTwo(params);
        // 创建一个 ArrayList 对象，用于存储报表的 x 轴数据
        List<String> xAxis = new ArrayList<>();//报表x轴
        // 创建一个 ArrayList 对象，用于存储报表的 y 轴数据
        List<List<String>> yAxis = new ArrayList<>();//y轴
        // 创建一个 ArrayList 对象，用于存储报表的标题数据
        List<String> legend = new ArrayList<>();//标题

        // 创建一个 LinkedHashMap 对象，用于存储柱状图统计双列操作的结果数据
        Map<String, HashMap<String, String>> dataMap = new LinkedHashMap<>();
        // 遍历柱状图统计双列操作的结果
        for(Map<String, Object> map :result){
            // 获取结果中的第一个名称数据，并转换为字符串
            String name1Value = String.valueOf(map.get("name1"));
            // 获取结果中的第二个名称数据，并转换为字符串
            String name2Value = String.valueOf(map.get("name2"));
            // 获取结果中的值数据，并转换为字符串
            String value = String.valueOf(map.get("value"));
            // 检查标题数据列表中是否包含第二个名称数据，如果不包含则添加到标题数据列表中
            if(!legend.contains(name2Value)){
                legend.add(name2Value);//添加完成后 就是最全的第二列的类型
            }
            // 检查 dataMap 中是否包含第一个名称数据
            if(dataMap.containsKey(name1Value)){
                // 如果包含，则将第二个名称数据和值数据添加到对应的 HashMap 中
                dataMap.get(name1Value).put(name2Value,value);
            }else{
                // 如果不包含，则创建一个新的 HashMap 对象，并将第二个名称数据和值数据添加到该 HashMap 中，然后将该 HashMap 添加到 dataMap 中
                HashMap<String, String> name1Data = new HashMap<>();
                name1Data.put(name2Value,value);
                dataMap.put(name1Value,name1Data);
            }

        }

        // 遍历标题数据列表，为每个标题创建一个 ArrayList 对象，并添加到 y 轴数据列表中
        for(int i =0; i<legend.size(); i++){
            yAxis.add(new ArrayList<String>());
        }

        // 获取 dataMap 中的所有键
        Set<String> keys = dataMap.keySet();
        // 遍历 dataMap 中的所有键
        for(String key:keys){
            // 将键添加到 x 轴数据列表中
            xAxis.add(key);
            // 获取键对应的 HashMap 对象
            HashMap<String, String> map = dataMap.get(key);
            // 遍历标题数据列表
            for(int i =0; i<legend.size(); i++){
                // 获取 y 轴数据列表中对应的 ArrayList 对象
                List<String> data = yAxis.get(i);
                // 检查 HashMap 中是否包含标题数据列表中的名称数据
                if(StringUtil.isNotEmpty(map.get(legend.get(i)))){
                    // 如果包含，则将对应的值数据添加到 ArrayList 对象中
                    data.add(map.get(legend.get(i)));
                }else{
                    // 如果不包含，则将 0 添加到 ArrayList 对象中
                    data.add("0");
                }
            }
        }
        // 打印空行，用于分隔输出
        System.out.println();

        // 创建一个 HashMap 对象，用于存储最终的报表数据
        Map<String, Object> resultMap = new HashMap<>();
        // 将 x 轴数据添加到最终的报表数据中
        resultMap.put("xAxis",xAxis);
        // 将 y 轴数据添加到最终的报表数据中
        resultMap.put("yAxis",yAxis);
        // 将标题数据添加到最终的报表数据中
        resultMap.put("legend",legend);
        // 将最终的报表数据封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", resultMap);
    }

    /**
     tableName 查询表
     condition1 条件1
     condition1Value 条件1值
     average 计算平均评分

     取值
     有值 Number(res.data.value.toFixed(1))
     无值 if(res.data){}
     * */
    @IgnoreAuth
    // 使用 @RequestMapping 注解将该方法映射到 /queryScore 路径
    @RequestMapping("/queryScore")
    public R queryScore(@RequestParam Map<String, Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("queryScore:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 queryScore 方法进行评分查询操作
        Map<String, Object> queryScore = commonService.queryScore(params);
        // 将评分查询结果封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", queryScore);
    }

    /**
     * 查询字典表的分组统计总条数
     *  tableName  		表名
     *	groupColumn  	分组字段
     * @return
     */
    // 使用 @RequestMapping 注解将该方法映射到 /newSelectGroupCount 路径
    @RequestMapping("/newSelectGroupCount")
    public R newSelectGroupCount(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("newSelectGroupCount:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 newSelectGroupCount 方法进行字典表分组统计总条数操作
        List<Map<String, Object>> result = commonService.newSelectGroupCount(params);
        // 将分组统计总条数结果封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", result);

    }

    /**
     * 查询字典表的分组求和
     * tableName  		表名
     * groupColumn  		分组字段
     * sumCloum			统计字段
     * @return
     */
    // 使用 @RequestMapping 注解将该方法映射到 /newSelectGroupSum 路径
    @RequestMapping("/newSelectGroupSum")
    public R newSelectGroupSum(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("newSelectGroupSum:,,Controller:{},,params:{}",this.getClass().getName(),params);
        // 调用 CommonService 中的 newSelectGroupSum 方法进行字典表分组求和操作
        List<Map<String, Object>> result = commonService.newSelectGroupSum(params);
        // 将分组求和结果封装到返回结果对象中，并返回成功信息
        return R.ok().put("data", result);
    }

    /**
     * 柱状图求和 老的
     */
    // 使用 @RequestMapping 注解将该方法映射到 /barSum 路径
    @RequestMapping("/barSum")
    public R barSum(@RequestParam Map<String,Object> params) {
        // 记录日志信息，包括控制器名称和请求参数
        logger.debug("barSum方法:,,Controller:{},,params:{}",this.getClass().getName(), com.alibaba.fastjson.JSONObject.toJSONString(params));
        // 声明一个布尔变量，用于标记是否有级联表相关
        Boolean isJoinTableFlag =  false;//是否有级联表相关
        // 声明一个字符串变量，用于存储第一优先字段
        String one =  "";//第一优先
        // 声明一个字符串变量，用于存储第二优先字段
        String two =  "";//第二优先

        // 处理 thisTable 和 joinTable 处理内容是把 json 字符串转为 Map 并把带有,的切割为数组
        // 当前表
        // 将请求参数中的 thisTable 字符串转换为 Map 对象
        Map<String,Object> thisTable = JSON.parseObject(String.valueOf(params.get("thisTable")),Map.class);
        // 将转换后的 thisTable Map 对象添加到请求参数中
        params.put("thisTable",thisTable);

        // 级联表
        // 获取请求参数中的 joinTable 字符串
        String joinTableString = String.valueOf(params.get("joinTable"));
        // 检查 joinTable 字符串是否为空
        if(StringUtil.isNotEmpty(joinTableString)) {
            // 如果不为空，则将 joinTable 字符串转换为 Map 对象
            Map<String, Object> joinTable = JSON.parseObject(joinTableString, Map.class);
            // 将转换后的 joinTable Map 对象添加到请求参数中
            params.put("joinTable", joinTable);
            // 将级联表标记设置为 true
            isJoinTableFlag = true;
        }

        // 检查当前表中是否包含日期字段
        if(StringUtil.isNotEmpty(String.valueOf(thisTable.get("date")))){//当前表日期
            // 如果包含，则将日期字段按逗号分割为数组
            thisTable.put("date",String.valueOf(thisTable.get("date")).split(","));
            // 将第一优先字段设置为 thisDate0
            one = "thisDate0";
        }
        // 检查是否有级联表
        if(isJoinTableFlag){//级联表日期
            // 获取级联表的 Map 对象
            Map<String, Object> joinTable = (Map<String, Object>) params.get("joinTable");
            // 检查级联表中是否包含日期字段
            if(StringUtil.isNotEmpty(String.valueOf(joinTable.get("date")))){
                // 如果包含，则将日期字段按逗号分割为数组
                joinTable.put("date",String.valueOf(joinTable.get("date")).split(","));
                // 检查第一优先字段是否为空
                if(StringUtil.isEmpty(one)){
                    // 如果为空，则将第一优先字段设置为 joinDate0
                    one ="joinDate0";
                }else{
                    // 如果不为空，则检查第二优先字段是否为空
                    if(StringUtil.isEmpty(two)){
                        // 如果为空，则将第二优先字段设置为 joinDate0
                        two ="joinDate0";
                    }
                }
            }
        }
        // 检查当前表中是否包含字符串字段
        if(StringUtil.isNotEmpty(String.valueOf(thisTable.get("string")))){//当前表字符串
            // 如果包含，则将字符串字段按逗号分割为数组
            thisTable.put("string",String.valueOf(thisTable.get("string")).split(","));
            // 检查第一优先字段是否为空
            if(StringUtil.isEmpty(one)){
                // 如果为空，则将第一优先字段设置为 thisString0
                one ="thisString0";
            }else{
                // 如果不为空，则检查第二优先字段是否为空
                if(StringUtil.isEmpty(two)){
                    // 如果为空，则将第二优先字段设置为 thisString0
                    two ="thisString0";
                }
            }
        }
        // 检查是否有级联表
        if(isJoinTableFlag){//级联表字符串
            // 获取级联表的 Map 对象
            Map<String, Object> joinTable = (Map<String, Object>) params.get("joinTable");
            // 检查级联表中是否包含字符串字段
            if(StringUtil.isNotEmpty(String.valueOf(joinTable.get("string")))){
                // 如果包含，则将字符串字段按逗号分割为数组
                joinTable.put("string",String.valueOf(joinTable.get("string")).split(","));
                // 检查第一优先字段是否为空
                if(StringUtil.isEmpty(one)){
                    // 如果为空，则将第一优先字段设置为 joinString0
                    one ="joinString0";
                }else{
                    // 如果不为空，则检查第二优先字段是否为空
                    if(StringUtil.isEmpty(two)){
                        // 如果为空，则将第二优先字段设置为 joinString0
                        two ="joinString0";
                    }
                }
            }
        }
        // 检查当前表中是否包含类型字段
        if(StringUtil.isNotEmpty(String.valueOf(thisTable.get("types")))){//当前表类型
            // 如果包含，则将类型字段按逗号分割为数组
            thisTable.put("types",String.valueOf(thisTable.get("types")).split(","));
            // 检查第一优先字段是否为空
            if(StringUtil.isEmpty(one)){
                // 如果为空，则将第一优先字段设置为 thisTypes0
                one ="thisTypes0";
            }else{
                // 如果不为空，则检查第二优先字段是否为空
                if(StringUtil.isEmpty(two)){
                    // 如果为空，则将第二优先字段设置为 thisTypes0
                    two ="thisTypes0";
                }
            }
        }
        // 检查是否有级联表
        if(isJoinTableFlag){//级联表类型
            // 获取级联表的 Map 对象
            Map<String, Object> joinTable = (Map<String, Object>) params.get("joinTable");
            // 检查级联表中是否包含类型字段
            if(StringUtil.isNotEmpty(String.valueOf(joinTable.get("types")))){
                // 如果包含，则将类型字段按逗号分割为数组
                joinTable.put("types",String.valueOf(joinTable.get("types")).split(","));
                // 检查第一优先字段是否为空
                if(StringUtil.isEmpty(one)){
                    // 如果为空，则将第一优先字段设置为 joinTypes0
                    one ="joinTypes0";
                }else{
                    // 如果不为空，则检查第二优先字段是否为空
                    if(StringUtil.isEmpty(two)){
                        // 如果为空，则将第二优先字段设置为 joinTypes0
                        two ="joinTypes0";
                    }
                }

            }
        }
        // 调用 CommonService 中的 barSum 方法进行柱状图求和操作
        List<Map<String, Object>> result = commonService.barSum(params);

// 定义一个列表 xAxis，用于存储报表的 x 轴数据，初始化为空的 ArrayList
        List<String> xAxis = new ArrayList<>();//报表x轴
// 定义一个二维列表 yAxis，用于存储报表的 y 轴数据，每个子列表代表一个系列的数据，初始化为空的 ArrayList
        List<List<String>> yAxis = new ArrayList<>();//y轴
// 定义一个列表 legend，用于存储报表的图例信息，初始化为空的 ArrayList
        List<String> legend = new ArrayList<>();//标题

// 判断第二优先字段 two 是否为空，若为空则表示数据不包含第二列
        if(StringUtil.isEmpty(two)){
            // 创建一个新的列表 yAxis0，用于存储 y 轴的第一个系列的数据
            List<String> yAxis0 = new ArrayList<>();
            // 将 yAxis0 添加到 yAxis 列表中，作为第一个系列的数据
            yAxis.add(yAxis0);
            // 向图例列表 legend 中添加一个空字符串，代表这个系列没有特定的图例名称
            legend.add("");
            // 遍历 result 列表中的每个 Map 对象，每个 Map 代表一条数据记录
            for(Map<String, Object> map :result){
                // 从当前 Map 中获取第一优先字段 one 对应的值，并将其转换为字符串，存储在 oneValue 中
                String oneValue = String.valueOf(map.get(one));
                // 从当前 Map 中获取键为 "value" 对应的值，并将其转换为字符串，存储在 value 中
                String value = String.valueOf(map.get("value"));
                // 将 oneValue 添加到 xAxis 列表中，作为 x 轴的数据
                xAxis.add(oneValue);
                // 将 value 添加到 yAxis0 列表中，作为 y 轴第一个系列的数据
                yAxis0.add(value);
            }
        }else{//包含第二列
            // 若 two 不为空，表示数据包含第二列，创建一个 LinkedHashMap 用于存储数据，保证插入顺序
            Map<String, HashMap<String, String>> dataMap = new LinkedHashMap<>();
            // 检查第二优先字段 two 是否有值
            if(StringUtil.isNotEmpty(two)){
                // 遍历 result 列表中的每个 Map 对象
                for(Map<String, Object> map :result){
                    // 从当前 Map 中获取第一优先字段 one 对应的值，并将其转换为字符串，存储在 oneValue 中
                    String oneValue = String.valueOf(map.get(one));
                    // 从当前 Map 中获取第二优先字段 two 对应的值，并将其转换为字符串，存储在 twoValue 中
                    String twoValue = String.valueOf(map.get(two));
                    // 从当前 Map 中获取键为 "value" 对应的值，并将其转换为字符串，存储在 value 中
                    String value = String.valueOf(map.get("value"));
                    // 检查图例列表 legend 中是否已经包含了 twoValue
                    if(!legend.contains(twoValue)){
                        // 若不包含，则将 twoValue 添加到图例列表 legend 中
                        legend.add(twoValue);
                    }
                    // 检查 dataMap 中是否已经存在以 oneValue 为键的映射
                    if(dataMap.containsKey(oneValue)){
                        // 若存在，则将 twoValue 和 value 作为键值对添加到对应的 HashMap 中
                        dataMap.get(oneValue).put(twoValue,value);
                    } else {
                        // 若不存在，则创建一个新的 HashMap，将 twoValue 和 value 作为键值对添加进去，再将该 HashMap 以 oneValue 为键添加到 dataMap 中
                        HashMap<String, String> oneData = new HashMap<>();
                        oneData.put(twoValue,value);
                        dataMap.put(oneValue,oneData);
                    }
                }
            }

            // 遍历图例列表 legend，为每个图例创建一个新的 ArrayList 并添加到 yAxis 列表中，用于存储对应系列的数据
            for(int i =0; i<legend.size(); i++){
                yAxis.add(new ArrayList<String>());
            }

            // 获取 dataMap 中所有键的集合
            Set<String> keys = dataMap.keySet();
            // 遍历 dataMap 的键集合
            for(String key:keys){
                // 将键添加到 xAxis 列表中，作为 x 轴的数据
                xAxis.add(key);
                // 根据键从 dataMap 中获取对应的 HashMap
                HashMap<String, String> map = dataMap.get(key);
                // 遍历图例列表 legend
                for(int i =0; i<legend.size(); i++){
                    // 从 yAxis 列表中获取对应索引的子列表，用于存储当前系列的数据
                    List<String> data = yAxis.get(i);
                    // 检查 map 中是否包含当前图例对应的值
                    if(StringUtil.isNotEmpty(map.get(legend.get(i)))){
                        // 若包含，则将该值添加到 data 列表中
                        data.add(map.get(legend.get(i)));
                    } else {
                        // 若不包含，则添加 "0" 到 data 列表中
                        data.add("0");
                    }
                }
            }
            // 打印一个空行，可能用于调试输出分隔
            System.out.println();
        }

        // 创建一个 HashMap 用于存储最终的结果数据
        Map<String, Object> resultMap = new HashMap<>();
// 将 x 轴数据添加到结果映射中，键为 "xAxis"
        resultMap.put("xAxis",xAxis);
// 将 y 轴数据添加到结果映射中，键为 "yAxis"
        resultMap.put("yAxis",yAxis);
// 将图例数据添加到结果映射中，键为 "legend"
        resultMap.put("legend",legend);
// 返回一个表示操作成功的响应对象 R，并将结果映射以键 "data" 添加到响应中
        return R.ok().put("data", resultMap);
    }

    /**
     * 柱状图统计 老的
     */
    // 使用 @RequestMapping 注解将该方法映射到 /barCount 路径，用于处理相应的 HTTP 请求
    @RequestMapping("/barCount")
    public R barCount(@RequestParam Map<String,Object> params) {
        // 记录日志，包含当前控制器类名和请求参数信息，用于调试和监控
        logger.debug("barCount方法:,,Controller:{},,params:{}", this.getClass().getName(), com.alibaba.fastjson.JSONObject.toJSONString(params));
        // 声明一个布尔变量，用于标记是否存在级联表相关的数据，初始化为 false
        Boolean isJoinTableFlag = false;//是否有级联表相关
        // 声明一个字符串变量，用于存储第一优先的字段名，初始化为空字符串
        String one = "";//第一优先
        // 声明一个字符串变量，用于存储第二优先的字段名，初始化为空字符串
        String two = "";//第二优先

        // 处理 thisTable 和 joinTable 参数，将其从 JSON 字符串转换为 Map 对象，并对包含逗号分隔值的字段进行分割处理
        // 当前表
        // 从请求参数中获取 "thisTable" 对应的字符串，并解析为 Map 对象
        Map<String, Object> thisTable = JSON.parseObject(String.valueOf(params.get("thisTable")), Map.class);
        // 将解析后的 thisTable 重新放入请求参数中
        params.put("thisTable", thisTable);

        // 级联表
        // 从请求参数中获取 "joinTable" 对应的字符串
        String joinTableString = String.valueOf(params.get("joinTable"));
        // 检查 joinTableString 是否不为空
        if (StringUtil.isNotEmpty(joinTableString)) {
            // 若不为空，则将其解析为 Map 对象
            Map<String, Object> joinTable = JSON.parseObject(joinTableString, Map.class);
            // 将解析后的 joinTable 重新放入请求参数中
            params.put("joinTable", joinTable);
            // 设置级联表标记为 true，表示存在级联表
            isJoinTableFlag = true;
        }

        // 检查当前表中是否包含日期字段
        if (StringUtil.isNotEmpty(String.valueOf(thisTable.get("date")))) {//当前表日期
            // 如果包含，则将日期字段按逗号分割为数组
            thisTable.put("date", String.valueOf(thisTable.get("date")).split(","));
            // 将第一优先字段设置为 thisDate0
            one = "thisDate0";
        }
        // 检查是否存在级联表
        if (isJoinTableFlag) {//级联表日期
            // 从请求参数中获取级联表对应的 Map 对象
            Map<String, Object> joinTable = (Map<String, Object>) params.get("joinTable");
            // 检查级联表中是否包含日期字段
            if (StringUtil.isNotEmpty(String.valueOf(joinTable.get("date")))) {
                // 如果包含，则将日期字段按逗号分割为数组
                joinTable.put("date", String.valueOf(joinTable.get("date")).split(","));
                // 检查第一优先字段是否为空
                if (StringUtil.isEmpty(one)) {
                    // 如果为空，则将第一优先字段设置为 joinDate0
                    one = "joinDate0";
                } else {
                    // 如果不为空，则检查第二优先字段是否为空
                    if (StringUtil.isEmpty(two)) {
                        // 如果为空，则将第二优先字段设置为 joinDate0
                        two = "joinDate0";
                    }
                }
            }
        }
        // 检查当前表中是否包含字符串字段
        if (StringUtil.isNotEmpty(String.valueOf(thisTable.get("string")))) {//当前表字符串
            // 如果包含，则将字符串字段按逗号分割为数组
            thisTable.put("string", String.valueOf(thisTable.get("string")).split(","));
            // 检查第一优先字段是否为空
            if (StringUtil.isEmpty(one)) {
                // 如果为空，则将第一优先字段设置为 thisString0
                one = "thisString0";
            } else {
                // 如果不为空，则检查第二优先字段是否为空
                if (StringUtil.isEmpty(two)) {
                    // 如果为空，则将第二优先字段设置为 thisString0
                    two = "thisString0";
                }
            }
        }
        // 检查是否存在级联表
        if (isJoinTableFlag) {//级联表字符串
            // 从请求参数中获取级联表对应的 Map 对象
            Map<String, Object> joinTable = (Map<String, Object>) params.get("joinTable");
            // 检查级联表中是否包含字符串字段
            if (StringUtil.isNotEmpty(String.valueOf(joinTable.get("string")))) {
                // 如果包含，则将字符串字段按逗号分割为数组
                joinTable.put("string", String.valueOf(joinTable.get("string")).split(","));
                // 检查第一优先字段是否为空
                if (StringUtil.isEmpty(one)) {
                    // 如果为空，则将第一优先字段设置为 joinString0
                    one = "joinString0";
                } else {
                    // 如果不为空，则检查第二优先字段是否为空
                    if (StringUtil.isEmpty(two)) {
                        // 如果为空，则将第二优先字段设置为 joinString0
                        two = "joinString0";
                    }
                }
            }
        }
        // 检查当前表中是否包含类型字段
        if (StringUtil.isNotEmpty(String.valueOf(thisTable.get("types")))) {//当前表类型
            // 如果包含，则将类型字段按逗号分割为数组
            thisTable.put("types", String.valueOf(thisTable.get("types")).split(","));
            // 检查第一优先字段是否为空
            if (StringUtil.isEmpty(one)) {
                // 如果为空，则将第一优先字段设置为 thisTypes0
                one = "thisTypes0";
            } else {
                // 如果不为空，则检查第二优先字段是否为空
                if (StringUtil.isEmpty(two)) {
                    // 如果为空，则将第二优先字段设置为 thisTypes0
                    two = "thisTypes0";
                }
            }
        }
        if (isJoinTableFlag) {//级联表类型
            Map<String, Object> joinTable = (Map<String, Object>) params.get("joinTable");
            if (StringUtil.isNotEmpty(String.valueOf(joinTable.get("types")))) {
                joinTable.put("types", String.valueOf(joinTable.get("types")).split(","));
                if (StringUtil.isEmpty(one)) {
                    one = "joinTypes0";
                } else {
                    if (StringUtil.isEmpty(two)) {
                        two = "joinTypes0";
                    }
                }

            }
        }

        // 调用 commonService 的 barCount 方法，根据传入的参数 params 进行柱状图数据统计操作，得到一个包含多个 Map<String, Object> 的列表结果
        List<Map<String, Object>> result = commonService.barCount(params);

        // 创建一个 ArrayList 用于存储报表的 x 轴数据
        List<String> xAxis = new ArrayList<>();//报表x轴
        // 创建一个 ArrayList 用于存储报表的 y 轴数据，y 轴数据是一个二维列表，每个子列表代表一个系列的数据
        List<List<String>> yAxis = new ArrayList<>();//y轴
        // 创建一个 ArrayList 用于存储报表的图例信息
        List<String> legend = new ArrayList<>();//标题

        // 判断第二优先字段 two 是否为空，若为空则表示数据不包含第二列
        if (StringUtil.isEmpty(two)) {
            // 创建一个新的列表 yAxis0，用于存储 y 轴的第一个系列的数据
            List<String> yAxis0 = new ArrayList<>();
            // 将 yAxis0 添加到 yAxis 列表中，作为第一个系列的数据
            yAxis.add(yAxis0);
            // 向图例列表 legend 中添加一个空字符串，代表这个系列没有特定的图例名称
            legend.add("");
            // 遍历 result 列表中的每个 Map 对象，每个 Map 代表一条数据记录
            for (Map<String, Object> map : result) {
                // 从当前 Map 中获取第一优先字段 one 对应的值，并将其转换为字符串，存储在 oneValue 中
                String oneValue = String.valueOf(map.get(one));
                // 从当前 Map 中获取键为 "value" 对应的值，并将其转换为字符串，存储在 value 中
                String value = String.valueOf(map.get("value"));
                // 将 oneValue 添加到 xAxis 列表中，作为 x 轴的数据
                xAxis.add(oneValue);
                // 将 value 添加到 yAxis0 列表中，作为 y 轴第一个系列的数据
                yAxis0.add(value);
            }
        } else {
            // 若 two 不为空，表示数据包含第二列，创建一个 LinkedHashMap 用于存储数据，保证插入顺序
            Map<String, HashMap<String, String>> dataMap = new LinkedHashMap<>();
            // 检查第二优先字段 two 是否有值
            if (StringUtil.isNotEmpty(two)) {
                // 遍历 result 列表中的每个 Map 对象
                for (Map<String, Object> map : result) {
                    // 从当前 Map 中获取第一优先字段 one 对应的值，并将其转换为字符串，存储在 oneValue 中
                    String oneValue = String.valueOf(map.get(one));
                    // 从当前 Map 中获取第二优先字段 two 对应的值，并将其转换为字符串，存储在 twoValue 中
                    String twoValue = String.valueOf(map.get(two));
                    // 从当前 Map 中获取键为 "value" 对应的值，并将其转换为字符串，存储在 value 中
                    String value = String.valueOf(map.get("value"));
                    // 检查图例列表 legend 中是否已经包含了 twoValue
                    if (!legend.contains(twoValue)) {
                        // 若不包含，则将 twoValue 添加到图例列表 legend 中
                        legend.add(twoValue);
                    }
                    // 检查 dataMap 中是否已经存在以 oneValue 为键的映射
                    if (dataMap.containsKey(oneValue)) {
                        // 若存在，则将 twoValue 和 value 作为键值对添加到对应的 HashMap 中
                        dataMap.get(oneValue).put(twoValue, value);
                    } else {
                        // 若不存在，则创建一个新的 HashMap，将 twoValue 和 value 作为键值对添加进去，再将该 HashMap 以 oneValue 为键添加到 dataMap 中
                        HashMap<String, String> oneData = new HashMap<>();
                        oneData.put(twoValue, value);
                        dataMap.put(oneValue, oneData);
                    }
                }
            }

            // 遍历图例列表 legend，为每个图例创建一个新的 ArrayList 并添加到 yAxis 列表中，用于存储对应系列的数据
            for (int i = 0; i < legend.size(); i++) {
                yAxis.add(new ArrayList<String>());
            }

            // 获取 dataMap 中所有键的集合
            Set<String> keys = dataMap.keySet();
            // 遍历 dataMap 的键集合
            for (String key : keys) {
                // 将键添加到 xAxis 列表中，作为 x 轴的数据
                xAxis.add(key);
                // 根据键从 dataMap 中获取对应的 HashMap
                HashMap<String, String> map = dataMap.get(key);
                // 遍历图例列表 legend
                for (int i = 0; i < legend.size(); i++) {
                    // 从 yAxis 列表中获取对应索引的子列表，用于存储当前系列的数据
                    List<String> data = yAxis.get(i);
                    // 检查 map 中是否包含当前图例对应的值
                    if (StringUtil.isNotEmpty(map.get(legend.get(i)))) {
                        // 若包含，则将该值添加到 data 列表中
                        data.add(map.get(legend.get(i)));
                    } else {
                        // 若不包含，则添加 "0" 到 data 列表中
                        data.add("0");
                    }
                }
            }
            // 打印一个空行，可能用于调试输出分隔
            System.out.println();
        }

        // 创建一个 HashMap 用于存储最终的结果数据
        Map<String, Object> resultMap = new HashMap<>();
        // 将 x 轴数据添加到结果映射中，键为 "xAxis"
        resultMap.put("xAxis", xAxis);
        // 将 y 轴数据添加到结果映射中，键为 "yAxis"
        resultMap.put("yAxis", yAxis);
        // 将图例数据添加到结果映射中，键为 "legend"
        resultMap.put("legend", legend);
        // 返回一个表示操作成功的响应对象 R，并将结果映射以键 "data" 添加到响应中
        return R.ok().put("data", resultMap);
    }
}
