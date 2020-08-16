package com.ddf.boot.quick.controller;

import com.ddf.boot.common.ids.helper.SnowflakeServiceHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快速开始控制器，用于演示某些功能的使用方式$
 *
 * @author dongfang.ding
 * @date 2020/8/15 0015 17:30
 */
@RestController
@RequestMapping("quick-start")
@AllArgsConstructor(onConstructor_=@Autowired)
public class QuickStartController {

    private final SnowflakeServiceHelper snowflakeServiceHelper;


    /**
     * 基于leaf的获取雪花id
     * @return
     */
    @GetMapping("getSnowflakeId")
    public Long getSnowflakeId() {
        return snowflakeServiceHelper.getLongId();
    }

}
