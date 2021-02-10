package com.ddf.boot.quick.biz;

import com.ddf.boot.quick.model.bo.CaptchaRequest;
import com.ddf.boot.quick.model.vo.CaptchaResponse;

/**
 * 通用业务服务类
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 12:08
 */
public interface CommonBizService {

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    CaptchaResponse generateCaptcha(CaptchaRequest request);
}
