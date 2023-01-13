package com.ddf.boot.quickstart.core.entity;

/**
* <p>用户数据配置表</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/16 14:37
*/
import lombok.Data;
@Data
public class UserMetadataConfig {
    private Long id;

    /**
    * 玩家ip
    */
    private Long playerId;

    /**
    * 配置代码
    */
    private String configCode;

    /**
    * 配置明细
    */
    private String configValue;
}
