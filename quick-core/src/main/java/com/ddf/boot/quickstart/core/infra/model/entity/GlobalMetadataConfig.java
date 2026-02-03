package com.ddf.boot.quickstart.core.infra.model.entity;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */


/**
 * 服务配置中心
 */
public class GlobalMetadataConfig {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置值
     */
    private String configValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
