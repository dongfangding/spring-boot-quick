package com.ddf.boot.quickstart.core.entity;

import lombok.Data;

/**
* <p>服务配置中心</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/02 16:53
*/
@Data
public class GlobalMetadataConfig {
    private Long id;

    /**
    * 配置代码
    */
    private String configCode;

    /**
    * 配置值
    */
    private String configValue;
}
