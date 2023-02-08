package com.ddf.boot.quickstart.api.response.sys;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码返回信息对象
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 12:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse implements Serializable {

    private static final long serialVersionUID = 1498020011951400679L;

    /**
     * 图片的宽度
     */
    private Integer width;

    /**
     * 图片的高度
     */
    private Integer height;

    /**
     * 跟随验证码一起返回的唯一标识符， 在对应表单数据提交时需要带上这个唯一标识符
     */
    private String tokenId;

    /**
     * 图片的base64编码
     */
    private String base64;

    /**
     * 图片编码base64的前缀，如`data:image/jpeg;base64,` + 真实的base64图片编码为一个完整版的格式，可以还原成图片
     */
    private String prefix = "data:image/jpeg;base64,";
}
