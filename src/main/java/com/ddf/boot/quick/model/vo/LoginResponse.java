package com.ddf.boot.quick.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录响应值
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:36
 */
@Data
@Accessors(chain = true)
public class LoginResponse {

    private String token;

}
