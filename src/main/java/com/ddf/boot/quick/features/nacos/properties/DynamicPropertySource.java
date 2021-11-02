package com.ddf.boot.quick.features.nacos.properties;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/11/02 09:44
 */
@Data
@NacosPropertySource(dataId = "config-properties", type = ConfigType.PROPERTIES, autoRefreshed = true)
@Component
public class DynamicPropertySource {

    @NacosValue(value = "${name:}", autoRefreshed = true)
    private String name;

    @NacosValue(value = "${price:0}")
    private Integer price;

}
