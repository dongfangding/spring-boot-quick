package com.ddf.boot.quickstart.core.entity;

/**
* <p>服务配置中心</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/16 14:37
*/
import lombok.Data;
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
