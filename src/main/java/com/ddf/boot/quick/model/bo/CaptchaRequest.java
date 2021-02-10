package com.ddf.boot.quick.model.bo;

import lombok.Data;

/**
 * 验证码请求参数
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 12:18
 */
@Data
public class CaptchaRequest {

    /**
     * 一个随机值，用来标识一个form表单请求， 返回的验证码会和这个表单值对应起来，后续会作为验证使用
     */
    private String formId;
}
