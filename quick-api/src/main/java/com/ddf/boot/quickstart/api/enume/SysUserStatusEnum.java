package com.ddf.boot.quickstart.api.enume;

import com.ddf.boot.common.api.exception.BaseErrorCallbackCode;
import com.ddf.boot.common.api.exception.BusinessException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统用户状态
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:49
 */
@AllArgsConstructor
public enum SysUserStatusEnum {

    /**
     * 状态
     */
    UNKNOWN(-1, "未知"),
    ACTIVE(0, "正常"),
    DISABLE(1, "停用"),


    ;

    private static final Map<Integer, SysUserStatusEnum> MAPPINGS;

    static {
        MAPPINGS = Arrays.stream(SysUserStatusEnum.values()).collect(Collectors.toMap(SysUserStatusEnum::getCode, val -> val));
    }


    /**
     * 获取枚举实例
     *
     * @param type
     * @return
     */
    public static SysUserStatusEnum instanceOfCode(Integer type) {
        return instanceOfCode(type, false, false);
    }


    /**
     * 获取实例
     * @param type
     * @return
     */
    public static SysUserStatusEnum instanceOfCodeDefaultUnknown(Integer type) {
        return instanceOfCode(type, false, true);
    }

    /**
     * 获取实例, 强一致性
     * @param type
     * @return
     */
    public static SysUserStatusEnum instanceOfCodeConsistency(Integer type) {
        return instanceOfCode(type, true, false);
    }


    /**
     * 获取枚举实例
     * @param type
     * @param consistency 是否校验强一致性
     * @param defaultUnknown 映射错误时 是否使用默认known对象
     * @return
     */
    public static SysUserStatusEnum instanceOfCode(Integer type, boolean consistency, boolean defaultUnknown) {
        final SysUserStatusEnum clubStatus = MAPPINGS.get(type);
        if (clubStatus == null) {
            if (consistency) {
                throw new BusinessException(BaseErrorCallbackCode.ENUM_CODE_NOT_MAPPING);
            } else if (defaultUnknown) {
                return UNKNOWN;
            }
            return null;
        }
        return clubStatus;
    }


    /**
     * 代码
     */
    @Getter
    private final Integer code;

    /**
     * 含义
     */
    @Getter
    private final String desc;
}
