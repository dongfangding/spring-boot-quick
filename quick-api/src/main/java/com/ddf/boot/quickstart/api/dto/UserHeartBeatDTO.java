package com.ddf.boot.quickstart.api.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>用户心跳详情</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/06 16:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHeartBeatDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 上次心跳时间
     */
    private Long lastUpdateTimeSeconds;

}
