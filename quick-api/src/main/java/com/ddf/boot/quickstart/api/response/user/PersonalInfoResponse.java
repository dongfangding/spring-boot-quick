package com.ddf.boot.quickstart.api.response.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>个人中心返回对象</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/29 22:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoResponse implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像地址url
     */
    private String avatarUrl;

    /**
     * 头像地址缩略图
     */
    private String avatarThumbUrl;

    /**
     * 注册秒时间戳
     */
    private Long ctime;
}
