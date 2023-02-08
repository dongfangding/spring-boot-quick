package com.ddf.boot.quickstart.core.entity;

import lombok.Data;

/**
* <p>用户信息</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/02 16:53
*/
@Data
public class UserInfo {
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
    * 注册ip
    */
    private String registerIp;

    /**
    * 注册设备
    */
    private String registerImei;

    /**
    * 临时邮箱， 当邮箱未验证时存储在这个字段，当验证通过，再复制给正式邮箱字段，这样使用时主要关心email字段即可
    */
    private String tempEmail;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 头像地址url
    */
    private String avatarUrl;

    /**
    * 头像地址缩略图url
    */
    private String avatarThumbUrl;

    /**
    * 注册时间，秒时间戳
    */
    private Long ctime;

    /**
    * 用户状态
    */
    private String status;

}
