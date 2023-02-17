package com.ddf.boot.quickstart.core.features.nacos.properties;

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
//@NacosPropertySource(dataId = "config-properties", type = ConfigType.PROPERTIES, autoRefreshed = true)
@Component
public class DynamicPropertySource {

//    @NacosValue(value = "${name:}", autoRefreshed = true)
    private String name;

//    @NacosValue(value = "${price:0}")
    private Integer price;

}
