package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * <p配置创建请求参数类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/05 22:41
 */
@Data
public class SysConfigUpdateRequest {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 配置值
     */
    @NotBlank(message = "配置值")
    @Length(max = 5000, message = "超过配置最大值")
    private String configValue;

    /**
     * 配置备注
     */
    @Length(max = 255, message = "备注最多255个字符")
    private String remark;
}
