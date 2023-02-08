package com.ddf.boot.quickstart.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>应用相关属性类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/28 14:41
 */
@Data
@ConfigurationProperties(prefix = "customs.authentication")
@Component
public class ApplicationProperties {

    /**
     * 邮件激活接口地址
     */
    private String mailActiveUrl;

    /**
     * 短信验证码每日限额数量
     */
    private Integer smsDailyLimit = 10;

    /**
     * 默认用户头像地址
     */
    private String defaultAvatarUrl = "https://www.snowball.fans/group1/M00/00/01/ag8Kh2MpIrmARypcAAA0fj3w3lU69.jpeg";

    /**
     * 短信功能Mock是否开启， 如果开启的话，不会真实发送短信，通过控制台查看短信验证码来进行调试
     */
    private boolean smsCodeMockEnabled = true;

    /**
     * 应用名称
     */
    private String applicationChineseName;

    /**
     * 用户心跳接口间隔时间
     */
    private Long heartBeatIntervalSeconds = 60L;

    /**
     * 获取心跳最大间隔时间
     *
     * @return
     */
    public Long getHeartBeatMaxIntervalSeconds() {
        return heartBeatIntervalSeconds * 2 + 2;
    }

}
