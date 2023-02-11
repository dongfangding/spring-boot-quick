package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>字典获取请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/15 22:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDictGetByCodeRequest implements Serializable {

    private static final long serialVersionUID = -8846320147721003035L;

    /**
     * 字典代码
     */
    @NotBlank(message = "字典代码不能为空")
    private String dictCode;
}
