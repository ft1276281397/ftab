package com.utils;

// 导入BufferedReader类，用于读取字符输入流
import java.io.BufferedReader;
// 导入InputStreamReader类，用于将字节流转换为字符流
import java.io.InputStreamReader;
// 导入HttpURLConnection类，用于发送HTTP请求
import java.net.HttpURLConnection;
// 导入URL类，用于表示统一资源定位符
import java.net.URL;
// 导入HashMap类，用于存储键值对
import java.util.HashMap;
// 导入List接口，用于存储列表数据
import java.util.List;
// 导入Map接口，用于存储键值对
import java.util.Map;

// 导入JSONObject类，用于处理JSON数据
import org.json.JSONObject;

/**
 * @author yangliyuan
 * @version 创建时间：2020年2月7日 下午9:37:05
 * 类说明 : 提供百度地图相关的工具方法
 */

public class BaiduUtil {

    /**
     * 根据经纬度获得省市区信息
     *
     * @param key 百度地图API密钥
     * @param lng 经度
     * @param lat 纬度
     * @return 包含省市区信息的Map对象
     */
    public static Map<String, String> getCityByLonLat(String key, String lng, String lat) {
        // 拼装经纬度字符串
        String location = lat + "," + lng;
        try {
            // 拼装请求URL
            String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=" + key + "&output=json&coordtype=wgs84ll&location=" + location;
            // 发送GET请求并获取结果
            String result = HttpClientUtils.doGet(url);
            // 解析JSON结果
            JSONObject o = new JSONObject(result);
            // 创建Map对象存储省市区信息
            Map<String, String> area = new HashMap<>();
            // 获取并存储省份信息
            area.put("province", o.getJSONObject("result").getJSONObject("addressComponent").getString("province"));
            // 获取并存储城市信息
            area.put("city", o.getJSONObject("result").getJSONObject("addressComponent").getString("city"));
            // 获取并存储区县信息
            area.put("district", o.getJSONObject("result").getJSONObject("addressComponent").getString("district"));
            // 获取并存储街道信息
            area.put("street", o.getJSONObject("result").getJSONObject("addressComponent").getString("street"));
            // 返回包含省市区信息的Map对象
            return area;
        } catch (Exception e) {
            // 捕获并打印异常信息
            e.printStackTrace();
        }
        // 返回null表示获取失败
        return null;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak 百度云官网获取的 API Key
     * @param sk 百度云官网获取的 Securet Key
     * @return access_token
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            // 创建URL对象
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置请求方法为GET
            connection.setRequestMethod("GET");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            // 读取响应内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            // 解析JSON结果
            org.json.JSONObject jsonObject = new org.json.JSONObject(result);
            // 获取access_token
            String access_token = jsonObject.getString("access_token");
            // 返回access_token
            return access_token;
        } catch (Exception e) {
            // 捕获并打印异常信息
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        // 返回null表示获取失败
        return null;
    }

}
