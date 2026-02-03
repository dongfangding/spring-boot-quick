package com.ddf.boot.quickstart.core.infra.model.entity;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */


/**
 * 用户信息
 */
public class UserInfo {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String mobileZone;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileZone() {
        return mobileZone;
    }

    public void setMobileZone(String mobileZone) {
        this.mobileZone = mobileZone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getRegisterImei() {
        return registerImei;
    }

    public void setRegisterImei(String registerImei) {
        this.registerImei = registerImei;
    }

    public String getTempEmail() {
        return tempEmail;
    }

    public void setTempEmail(String tempEmail) {
        this.tempEmail = tempEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarThumbUrl() {
        return avatarThumbUrl;
    }

    public void setAvatarThumbUrl(String avatarThumbUrl) {
        this.avatarThumbUrl = avatarThumbUrl;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
