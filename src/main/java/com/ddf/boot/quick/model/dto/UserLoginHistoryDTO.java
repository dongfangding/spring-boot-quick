package com.ddf.boot.quick.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
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
public class UserLoginHistoryDTO implements Serializable {

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
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
