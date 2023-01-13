package com.ddf.boot.quickstart.api.response.auth;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>登录响应类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 授权token
     */
    private String token;
}
