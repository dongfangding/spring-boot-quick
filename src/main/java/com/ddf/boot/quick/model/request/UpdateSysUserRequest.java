package com.ddf.boot.quick.model.request;

import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 修改系统用户请求类
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:40
 */
@Data
@Accessors(chain = true)
public class UpdateSysUserRequest {

    /**
     * id
     *
     */
    @NotNull(message = "编辑时id不能为空")
    private Long id;

    /**
     * 登陆名
     */
    @NotEmpty(message = "登录名不能为空")
    private String loginName;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    /**
     * 角色id集合
     */
    private Set<String> roleIdList;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别 0 未知  1 男性 2 女性
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 身高，单位cm
     */
    private Integer height;

    /**
     * 体重，单位kg
     */
    private Integer weight;

    /**
     * 微信号
     */
    private String weiXin;

    /**
     * QQ号
     */
    private String qq;
}
