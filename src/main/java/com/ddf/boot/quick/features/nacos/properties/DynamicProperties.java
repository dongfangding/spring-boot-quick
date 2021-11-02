package com.ddf.boot.quick.features.nacos.properties;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/11/01 20:44
 */
@NacosConfigurationProperties(prefix = "user-info", dataId = "config-server", type = ConfigType.YAML, autoRefreshed = true)
@Component
@Data
public class DynamicProperties {

    private String name;

    private Integer age;

    private String address;
}
