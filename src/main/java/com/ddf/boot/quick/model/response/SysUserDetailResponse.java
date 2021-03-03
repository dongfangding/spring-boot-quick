package com.ddf.boot.quick.model.response;

import com.ddf.boot.quick.model.dto.SysUserRoleDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 用户详情
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:44
 */
@Data
public class SysUserDetailResponse implements Serializable {
    
    private Long id;

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
     * 用户状态 0 正常 1 停用
     */
    private Integer status;

    /**
     * 用户状态渲染值
     */
    private String statusName;

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
     * 性别渲染值
     */
    private String sexName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 头像缩略图
     */
    private String avatarShortUrl;

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

    /**
     * 创建人id
     */
    private String createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    private String modifyBy;

    /**
     * 修改人名称
     */
    private String modifyByName;

    /**
     * 修改时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 用户角色列表
     */
    private List<SysUserRoleDTO> roleList;
}
