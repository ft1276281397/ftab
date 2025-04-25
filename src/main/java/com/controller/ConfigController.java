package com.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.service.ConfigService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.ValidatorUtils;

/**
 * 配置相关控制器
 */
@RequestMapping("config")
@RestController
public class ConfigController {

    // 自动注入ConfigService
    @Autowired
    private ConfigService configService;

    /**
     * 分页查询配置列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ConfigEntity config) {
        // 创建EntityWrapper对象用于条件查询
        EntityWrapper<ConfigEntity> ew = new EntityWrapper<ConfigEntity>();
        // 调用服务层方法查询分页数据
        PageUtils page = configService.queryPage(params);
        // 返回查询结果
        return R.ok().put("data", page);
    }

    /**
     * 查询配置列表（无需认证）
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, ConfigEntity config) {
        // 创建EntityWrapper对象用于条件查询
        EntityWrapper<ConfigEntity> ew = new EntityWrapper<ConfigEntity>();
        // 调用服务层方法查询分页数据
        PageUtils page = configService.queryPage(params);
        // 返回查询结果
        return R.ok().put("data", page);
    }

    /**
     * 根据ID查询配置信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        // 调用服务层方法根据ID查询配置信息
        ConfigEntity config = configService.selectById(id);
        // 返回查询结果
        return R.ok().put("data", config);
    }

    /**
     * 根据ID查询配置详情（无需认证）
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id) {
        // 调用服务层方法根据ID查询配置信息
        ConfigEntity config = configService.selectById(id);
        // 返回查询结果
        return R.ok().put("data", config);
    }

    /**
     * 根据name查询配置信息
     */
    @RequestMapping("/info")
    public R infoByName(@RequestParam String name) {
        // 调用服务层方法根据name查询配置信息
        ConfigEntity config = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", name));
        // 返回查询结果
        return R.ok().put("data", config);
    }

    /**
     * 保存配置信息
     */
    @PostMapping("/save")
    public R save(@RequestBody ConfigEntity config) {
        // 验证实体对象（注释掉）
//    	ValidatorUtils.validateEntity(config);
        // 调用服务层方法保存配置信息
        configService.insert(config);
        // 返回成功信息
        return R.ok();
    }

    /**
     * 更新配置信息
     */
    @RequestMapping("/update")
    public R update(@RequestBody ConfigEntity config) {
        // 验证实体对象（注释掉）
//        ValidatorUtils.validateEntity(config);
        // 调用服务层方法更新配置信息
        configService.updateById(config); // 全部更新
        // 返回成功信息
        return R.ok();
    }

    /**
     * 删除配置信息
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        // 调用服务层方法批量删除配置信息
        configService.deleteBatchIds(Arrays.asList(ids));
        // 返回成功信息
        return R.ok();
    }
}
