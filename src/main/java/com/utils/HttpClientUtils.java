package com.utils;

// 导入BufferedReader类，用于读取字符输入流
import java.io.BufferedReader;
// 导入InputStreamReader类，用于将字节流转换为字符流
import java.io.InputStreamReader;
// 导入HttpURLConnection类，用于发送HTTP请求
import java.net.HttpURLConnection;
// 导入URL类，用于表示统一资源定位符
import java.net.URL;

/**
 * HttpClient工具类
 */
public class HttpClientUtils {

    /**
     * 发送GET请求并返回响应结果
     *
     * @param uri 请求的URL地址
     * @return 响应结果字符串
     * @description 通过GET请求方式获取数据
     * @author long.he01
     */
    public static String doGet(String uri) {

        // 创建StringBuilder对象，用于存储响应结果
        StringBuilder result = new StringBuilder();
        try {
            // 初始化响应结果字符串
            String res = "";
            // 创建URL对象
            URL url = new URL(uri);
            // 打开URL连接并转换为HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求方法为GET
            conn.setRequestMethod("GET");
            // 创建BufferedReader对象，用于读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            // 循环读取响应内容并添加到StringBuilder中
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            // 关闭BufferedReader
            in.close();
            // 返回响应结果字符串
            return res;
        } catch (Exception e) {
            // 捕获并打印异常信息
            e.printStackTrace();
            // 返回null表示请求失败
            return null;
        }
    }
}
