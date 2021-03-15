package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>字典获取请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/15 22:20
 */
@Data
public class SysDictGetByCodeRequest {

    /**
     * 字典代码
     */
    @NotBlank(message = "字典代码不能为空")
    private String dictCode;
}
