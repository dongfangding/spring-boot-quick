package com.ddf.boot.quick.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 验证码返回信息对象
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 12:09
 */
@Data
@Accessors(chain = true)
public class CaptchaResponse {

    /**
     * 图片的宽度
     */
    private int width;

    /**
     * 图片的高度
     */
    private int height;

    /**
     * 图片的base64编码
     */
    private String base64;

    /**
     * 图片编码base64的前缀，如`data:image/jpeg;base64,` + 真实的base64图片编码为一个完整版的格式，可以还原成图片
     */
    private String prefix = "data:image/jpeg;base64,";
}
