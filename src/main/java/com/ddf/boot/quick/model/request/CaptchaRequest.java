package com.ddf.boot.quick.model.request;

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
     * 图片的宽度
     */
    private Integer width = 200;

    /**
     * 图片的高度
     */
    private Integer height = 80;

}
