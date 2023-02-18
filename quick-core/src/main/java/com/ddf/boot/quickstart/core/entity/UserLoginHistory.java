package com.ddf.boot.quickstart.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
* <p>用户登录历史</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/18 23:43
*/
@Data
public class UserLoginHistory implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录方式
     */
    private String loginType;

    /**
     * 登录Ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 设备号
     */
    private String imei;

    /**
     * 系统
     */
    private String os;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 版本号
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}
