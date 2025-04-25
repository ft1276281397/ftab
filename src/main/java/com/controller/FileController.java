package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.R;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController {
    // 自动注入ConfigService
    @Autowired
    private ConfigService configService;

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, String type) throws Exception {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new EIException("上传文件不能为空");
        }
        // 获取文件扩展名
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // 获取静态资源路径
        File path = new File(ResourceUtils.getURL("classpath:static").getPath());
        // 如果路径不存在，则设置为当前路径
        if (!path.exists()) {
            path = new File("");
        }
        // 创建上传文件夹路径
        File upload = new File(path.getAbsolutePath(), "/upload/");
        // 如果上传文件夹不存在，则创建
        if (!upload.exists()) {
            upload.mkdirs();
        }
        // 生成文件名（时间戳+扩展名）
        String fileName = new Date().getTime() + "." + fileExt;
        // 创建目标文件对象
        File dest = new File(upload.getAbsolutePath() + "/" + fileName);
        // 将上传的文件保存到目标文件
        file.transferTo(dest);
        // 如果类型为1，则更新配置表中的faceFile字段
        if (StringUtils.isNotBlank(type) && type.equals("1")) {
            ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
            if (configEntity == null) {
                configEntity = new ConfigEntity();
                configEntity.setName("faceFile");
                configEntity.setValue(fileName);
            } else {
                configEntity.setValue(fileName);
            }
            // 插入或更新配置实体
            configService.insertOrUpdate(configEntity);
        }
        // 返回成功信息，包含文件名
        return R.ok().put("file", fileName);
    }

    /**
     * 下载文件
     */
    @IgnoreAuth
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        try {
            // 获取静态资源路径
            File path = new File(ResourceUtils.getURL("classpath:static").getPath());
            // 如果路径不存在，则设置为当前路径
            if (!path.exists()) {
                path = new File("");
            }
            // 创建上传文件夹路径
            File upload = new File(path.getAbsolutePath(), "/upload/");
            // 如果上传文件夹不存在，则创建
            if (!upload.exists()) {
                upload.mkdirs();
            }
            // 创建目标文件对象
            File file = new File(upload.getAbsolutePath() + "/" + fileName);
            // 检查文件是否存在
            if (file.exists()) {
                /*if(!fileService.canRead(file, SessionManager.getSessionUser())){
                    getResponse().sendError(403);
                }*/
                // 设置HTTP头信息
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileName);
                // 读取文件内容并返回
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            // 打印异常堆栈信息
            e.printStackTrace();
        }
        // 返回内部服务器错误
        return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
