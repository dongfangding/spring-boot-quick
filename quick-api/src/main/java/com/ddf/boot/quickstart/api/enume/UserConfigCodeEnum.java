package com.ddf.boot.quickstart.api.enume;

import com.ddf.boot.common.api.enums.IEnum;

/**
 * <p>用户配置枚举</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:41
 */
public enum UserConfigCodeEnum implements IEnum {

    /**
     * 账号资料全备份
     */
    ACCOUNT_METADATA("account_metadata", "账号资料全备份");


    private final String code;
    private final String desc;

    UserConfigCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return code;
    }
}
