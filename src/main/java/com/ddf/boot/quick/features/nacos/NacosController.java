package com.ddf.boot.quick.features.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ddf.boot.common.core.config.GlobalProperties;
import com.ddf.boot.quick.common.config.GlobalPropertiesExt;
import com.ddf.boot.quick.features.nacos.properties.DynamicProperties;
import com.ddf.boot.quick.features.nacos.properties.DynamicPropertySource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/11/01 19:56
 */
@RestController
@RequestMapping("/nacos")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class NacosController {

    private final DynamicProperties dynamicProperties;

    private final DynamicPropertySource dynamicPropertySource;

    private final GlobalProperties globalProperties;

    private final GlobalPropertiesExt globalPropertiesExt;

    @NacosValue(value = "${singleValue: nothing}", autoRefreshed = true)
    private String singleValue;

    @NacosInjected
    private ConfigService configService;

    /**
     * 演示单字段注入
     *
     * @return
     */
    @GetMapping("singleValue")
    public String singleValue() {
        return singleValue;
    }

    /**
     * 演示动态属性注入
     *
     * @return
     */
    @GetMapping("dynamicProperties")
    public DynamicProperties dynamicProperties() {
        return dynamicProperties;
    }

    /**
     * 演示动态PropertySource注入
     *
     * @return
     */
    @GetMapping("dynamicPropertySource")
    public DynamicPropertySource dynamicPropertySource() {
        return dynamicPropertySource;
    }

    /**
     * 演示动态PropertySource注入
     *
     * @return
     */
    @GetMapping("globalProperties")
    public GlobalProperties globalProperties() {
        return globalProperties;
    }

    /**
     * 演示动态PropertySource注入
     *
     * @return
     */
    @GetMapping("globalPropertiesExt")
    public GlobalPropertiesExt globalPropertiesExt() {
        return globalPropertiesExt;
    }
}
