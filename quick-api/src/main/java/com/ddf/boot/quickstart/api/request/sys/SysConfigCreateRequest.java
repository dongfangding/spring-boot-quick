package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * <p配置创建请求参数类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/05 22:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigCreateRequest implements Serializable {
    private static final long serialVersionUID = -3979160322495321898L;

    /**
     * 配置代码
     */
    @NotBlank(message = "配置代码不能为空")
    private String configCode;

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
