package com.ddf.boot.quickstart.api.enume;

import com.ddf.boot.common.api.enums.IEnum;

/**
 * <p>全局配置枚举</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:41
 */
public enum GlobalConfigCodeEnum implements IEnum<String> {
    ;



    private final String code;
    private final String desc;

    GlobalConfigCodeEnum(String code, String desc) {
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
