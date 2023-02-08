package com.ddf.boot.quickstart.core.model.cqrs.user;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * <p>完善用户信息更新对象</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/27 22:58
 */
@Data
@Builder
public class CompleteUserInfoCommand implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * id，这个是更新条件
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 头像缩略图url
     */
    private String avatarThumbUrl;

    /**
     * 邮箱
     */
    private String email;
}
