package com.ddf.boot.quickstart.core.entity;

import java.io.Serializable;
import lombok.Data;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/17 23:04
*/
@Data
public class UserProgress implements Serializable {
    private Long id;

    /**
    * 玩家id
    */
    private Long userId;

    /**
    * 上次读取公告时间
    */
    private Long latestReadNoticeTime;

    private static final long serialVersionUID = 1L;
}
