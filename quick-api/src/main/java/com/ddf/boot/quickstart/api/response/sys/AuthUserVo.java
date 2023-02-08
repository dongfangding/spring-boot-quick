package com.ddf.boot.quickstart.api.response.sys;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户展示层对象
 *
 * @author dongfang.ding
 * @date 2019/10/9 11:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserVo implements Serializable {

    private static final long serialVersionUID = 1128029951849763568L;

    /**
     * 用户id
     */
    private String id;

    /**
     * 姓名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 最后一次修改密码的时间
     */
    private Long lastModifyPassword;

    /**
     * 上次登录密码的时间
     */
    private Long lastLoginTime;
}
