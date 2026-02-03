package com.ddf.boot.quickstart.core.infra.model.entity;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */


/**
 * 用户数据配置表
 */
public class UserMetadataConfig {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置明细
     */
    private String configValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
