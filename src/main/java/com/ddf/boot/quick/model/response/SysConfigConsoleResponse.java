package com.ddf.boot.quick.model.response;

import com.ddf.boot.common.core.constant.IUserIdCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.Sets;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

/**
 * <p>系统配置控台响应</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/05 22:49
 */
@Data
public class SysConfigConsoleResponse implements IUserIdCollection {
    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置json串
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否可修改 0 否 1 是
     */
    private Integer isEditable;

    /**
     * 创建人id
     */
    private String createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    private String modifyBy;

    /**
     * 修改人名称
     */
    private String modifyByName;

    /**
     * 修改时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime modifyTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 版本号
     */
    private Integer version;

    @Override
    public Set<String> getUserIds() {
        return Sets.newHashSet(createBy, modifyBy);
    }
}
