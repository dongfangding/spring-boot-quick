package com.ddf.boot.quickstart.core.features.nacos.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/11/01 20:44
 */
//@NacosConfigurationProperties(prefix = "user-info", dataId = "config-server", type = ConfigType.YAML, autoRefreshed = true)
@Component
@Data
public class DynamicProperties {

    private String name;

    private Integer age;

    private String address;
}
