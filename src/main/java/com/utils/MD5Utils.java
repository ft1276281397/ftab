package com.utils;

// 导入BigInteger类，用于大整数运算
import java.math.BigInteger;
// 导入MessageDigest类，用于生成消息摘要
import java.security.MessageDigest;
// 导入NoSuchAlgorithmException类，用于处理算法不存在异常
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Utils {

    /**
     * 将字符串进行MD5加密
     *
     * @param plainText 要加密的明文字符串
     * @return 加密后的MD5字符串
     */
    public static String md5(String plainText) {
        // 定义字节数组用于存储密文
        byte[] secretBytes = null;
        try {
            // 获取MD5消息摘要实例并进行加密
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            // 抛出运行时异常，表示没有找到MD5算法
            throw new RuntimeException("没有这个md5算法！");
        }
        // 将字节数组转换为BigInteger对象，并转换为16进制字符串
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成的MD5字符串长度不足32位，则在前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        // 返回32位的MD5字符串
        return md5code;
    }
}
