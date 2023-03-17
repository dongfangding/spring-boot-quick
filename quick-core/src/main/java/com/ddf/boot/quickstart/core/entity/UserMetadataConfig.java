package com.ddf.boot.quickstart.core.entity;

import java.io.Serializable;
import lombok.Data;

/**
* <p>用户数据配置表</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/18 23:43
*/
@Data
public class UserMetadataConfig implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置明细
     */
    private String configValue;

    private static final long serialVersionUID = 1L;
}
