package com.ddf.boot.quickstart.api.request.user;

import java.io.Serializable;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>完善用户信息</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/27 22:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteUserInfoRequest implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 头像缩略图url
     */
    private String avatarThumbUrl;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    @Email
    private String email;
}
