package com.utils;

// 导入ByteArrayOutputStream类，用于将数据写入字节数组
import java.io.ByteArrayOutputStream;
// 导入File类，用于表示文件
import java.io.File;
// 导入FileInputStream类，用于从文件中读取数据
import java.io.FileInputStream;
// 导入IOException类，用于处理输入输出异常
import java.io.IOException;
// 导入InputStream类，用于读取字节流
import java.io.InputStream;

/**
 * @author yangliyuan
 * @version 创建时间：2020年2月7日 下午8:01:14
 * 类说明 : 文件操作工具类
 */

public class FileUtil {

    /**
     * 将文件转换为字节数组
     *
     * @param file 要转换的文件对象
     * @return 文件的字节数组
     * @throws IOException 如果读取文件时发生IO异常
     */
    public static byte[] FileToByte(File file) throws IOException {
        // 将文件内容转换为输入流
        @SuppressWarnings("resource")
        InputStream content = new FileInputStream(file);
        // 创建ByteArrayOutputStream对象，用于存储文件内容的字节数组
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        // 定义缓冲区，用于读取文件内容
        byte[] buff = new byte[100];
        int rc = 0;
        // 循环读取文件内容并写入ByteArrayOutputStream
        while ((rc = content.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        // 返回文件内容的字节数组
        return swapStream.toByteArray();
    }
}
