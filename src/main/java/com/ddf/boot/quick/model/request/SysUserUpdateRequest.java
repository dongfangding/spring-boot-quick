package com.ddf.boot.quick.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class SysUserUpdateRequest {

    /**
     * id
     */
    @NotNull(message = "编辑时id不能为空")
    private Long id;

    /**
     * 登陆名
     */
    @NotBlank(message = "登录名不能为空")
    @Size(min = 1, max = 32, message = "登录名长度必须位于1到32个字符之间")
    private String loginName;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Size(min = 1, max = 32, message = "昵称长度必须位于1到32个字符之间")
    private String nickname;

    /**
     * 角色id集合
     */
    private Set<Long> roleIdList;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
