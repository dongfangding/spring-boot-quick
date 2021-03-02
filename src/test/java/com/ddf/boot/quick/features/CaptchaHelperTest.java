package com.ddf.boot.quick.features;

import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.common.captcha.helper.CaptchaHelper;
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
}
