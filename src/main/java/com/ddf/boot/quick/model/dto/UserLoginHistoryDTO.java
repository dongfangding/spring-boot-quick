package com.ddf.boot.quick.model.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * 用户登录数据类
 *
 * @author dongfang.ding
 * @date 2020/9/18 0018 22:55
 */
@Data
@Builder
public class UserLoginHistoryDTO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录时生成的token
     */
    private String token;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录地址
     */
    private String loginAddress;

}
