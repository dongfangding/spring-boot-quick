package com.ddf.boot.quickstart.api.response.sys;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应值
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 4495348791546988019L;

    /**
     * 登录身份token
     */
    private String token;

}
