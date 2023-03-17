package com.ddf.boot.quickstart.core.entity;

import java.io.Serializable;
import lombok.Data;

/**
* <p>服务配置中心</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/18 23:43
*/
@Data
public class GlobalMetadataConfig implements Serializable {
    private Long id;

    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置值
     */
    private String configValue;

    private static final long serialVersionUID = 1L;
}
