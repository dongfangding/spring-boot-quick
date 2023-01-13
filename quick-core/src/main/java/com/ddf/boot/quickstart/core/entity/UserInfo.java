package com.ddf.boot.quickstart.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;

/**
* <p>用户信息表</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/17 23:04
*/
@Data
public class UserInfo implements Serializable {
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
     * 密码
     */
    private String password;

    /**
     * 注册Ip
     */
    private String registerIp;

    /**
     * 注册设备
     */
    private String registerImei;

    /**
     * 临时邮箱， 当邮箱未验证时存储在这个字段，当验证通过，再复制给正式邮箱字段，这样使用时方便
     */
    private String tempEmail;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱是否已验证
     */
    @TableField(value = "email_verified")
    private Boolean emailVerified;

    /**
     * 头像地址url
     */
    private String avatarUrl;

    /**
     * 头像地址缩略图
     */
    private String avatarThumbUrl;

    /**
     * 注册时间
     */
    private Long ctime;

    /**
     * 用户状态
     */
    private String status;

    private static final long serialVersionUID = 1L;
}
