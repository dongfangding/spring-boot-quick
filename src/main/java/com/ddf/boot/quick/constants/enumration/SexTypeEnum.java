package com.ddf.boot.quick.constants.enumration;

import com.ddf.boot.common.core.exception200.BusinessException;
import com.ddf.boot.common.core.exception200.GlobalCallbackCode;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>性别类型枚举</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/15 22:24
 */
@AllArgsConstructor
public enum SexTypeEnum {

    /**
     * 状态
     */
    UNKNOWN(-1, "未知"),
    UNKNOWN_2(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女"),
    ;

    private static final Map<Integer, SexTypeEnum> MAPPINGS;

    static {
        MAPPINGS = Arrays.stream(SexTypeEnum.values()).collect(Collectors.toMap(SexTypeEnum::getCode, val -> val));
    }


    /**
     * 获取枚举实例
     *
     * @param type
     * @return
     */
    public static SexTypeEnum instanceOfCode(Integer type) {
        return instanceOfCode(type, false, false);
    }


    /**
     * 获取实例
     * @param type
     * @return
     */
    public static SexTypeEnum instanceOfCodeDefaultUnknown(Integer type) {
        return instanceOfCode(type, false, true);
    }

    /**
     * 获取实例, 强一致性
     * @param type
     * @return
     */
    public static SexTypeEnum instanceOfCodeConsistency(Integer type) {
        return instanceOfCode(type, true, false);
    }


    /**
     * 获取枚举实例
     * @param type
     * @param consistency 是否校验强一致性
     * @param defaultUnknown 映射错误时 是否使用默认known对象
     * @return
     */
    public static SexTypeEnum instanceOfCode(Integer type, boolean consistency, boolean defaultUnknown) {
        final SexTypeEnum clubStatus = MAPPINGS.get(type);
        if (clubStatus == null) {
            if (consistency) {
                throw new BusinessException(GlobalCallbackCode.ENUM_CODE_NOT_MAPPING);
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
