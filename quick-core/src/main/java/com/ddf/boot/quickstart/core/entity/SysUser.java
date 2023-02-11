package com.ddf.boot.quickstart.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ddf.boot.common.core.model.BaseDomain;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user" )
public class SysUser extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id, 系统内部关联使用
     */
    private String userId;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户状态 0 正常 1 停用
     */
    private Integer status;

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
     * 手机号
     */
    private String mobile;

    /**
     * 性别 0 未知  1 男性 2 女性
     */
    private Integer sex;

}
