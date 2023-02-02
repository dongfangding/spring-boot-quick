package com.ddf.boot.quickstart.api.event;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>用户登录数据载荷</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/17 22:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginEventPayload implements Serializable {
    private static final long serialVersionUID = 1516322558409231083L;

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

}
