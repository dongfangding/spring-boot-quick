package com.ddf.boot.quick.features;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.common.captcha.constants.CaptchaType;
import com.ddf.common.captcha.helper.CaptchaHelper;
import com.ddf.common.captcha.model.CaptchaCheckRequest;
import com.ddf.common.captcha.model.CaptchaRequest;
import com.ddf.common.captcha.model.CaptchaResult;
import java.io.IOException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 16:14
 */
public class CaptchaHelperTest extends QuickApplicationTest {

    @Autowired
    private CaptchaHelper captchaHelper;

    @Test
    public void test() throws IOException {
        CaptchaResult result = captchaHelper.generateText();
        System.out.println("result = " + result);
        result = captchaHelper.generateMath();
        System.out.println("result = " + result);
    }

    @Test
    public void test2() {
        final CaptchaRequest request = new CaptchaRequest();
        request.setCaptchaType(CaptchaType.TEXT);
        CaptchaResult generate;
        CaptchaCheckRequest checkRequest = new CaptchaCheckRequest();
        try {
            generate = captchaHelper.generate(request);
            System.out.println(JsonUtil.toJson(generate));

            checkRequest.setToken(generate.getToken());
            checkRequest.setVerifyCode(generate.getVerifyCode());
            System.out.println("文本校验: ");
            captchaHelper.check(checkRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            System.out.println("=======================");
            request.setCaptchaType(CaptchaType.MATH);
            generate = captchaHelper.generate(request);
            System.out.println(JsonUtil.toJson(generate));
            checkRequest = new CaptchaCheckRequest();
            checkRequest.setToken(generate.getToken());
            checkRequest.setVerifyCode(generate.getVerifyCode());
            System.out.println("数学校验: ");
            captchaHelper.check(checkRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            System.out.println("=======================");
            request.setCaptchaType(CaptchaType.CLICK_WORDS);
            generate = captchaHelper.generate(request);
            System.out.println(JsonUtil.toJson(generate));
            checkRequest = new CaptchaCheckRequest();
            checkRequest.setToken(generate.getToken());
            checkRequest.setVerifyCode(captchaHelper.getVerifyCodeByToken(generate.getToken()));
            checkRequest.setCaptchaType(CaptchaTypeEnum.CLICKWORD.getCodeValue());
            System.out.println("文字点选校验: ");
            captchaHelper.check(checkRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("=======================");
            request.setCaptchaType(CaptchaType.PIC_SLIDE);
            generate = captchaHelper.generate(request);
            System.out.println(JsonUtil.toJson(generate));
            checkRequest = new CaptchaCheckRequest();
            checkRequest.setToken(generate.getToken());
            checkRequest.setVerifyCode(captchaHelper.getVerifyCodeByToken(generate.getToken()));
            checkRequest.setCaptchaType(CaptchaTypeEnum.BLOCKPUZZLE.getCodeValue());
            System.out.println("图片滑块校验: ");
            captchaHelper.check(checkRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
