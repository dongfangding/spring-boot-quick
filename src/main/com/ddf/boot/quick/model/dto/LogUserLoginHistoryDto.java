package com.ddf.boot.quick.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录日志传输类
 *
 * @author dongfang.ding
 * @date 2019/12/10 0010 20:31
 */
@Data
@Accessors(chain = true)
public class LogUserLoginHistoryDto implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录Ip
     */
    private String loginIp;

    /**
     * 用户token
     */
    private String token;
}
