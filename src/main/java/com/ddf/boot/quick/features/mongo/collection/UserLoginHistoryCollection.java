package com.ddf.boot.quick.features.mongo.collection;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Mongo 用户登录日志
 *
 * @author dongfang.ding
 * @date 2020/9/18 0018 22:55
 */
@Document
@Data
@Accessors(chain = true)
public class UserLoginHistoryCollection {

    @Id
    private String id;

    @Indexed(unique = false)
    private String userId;

    /**
     * 用户名
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
