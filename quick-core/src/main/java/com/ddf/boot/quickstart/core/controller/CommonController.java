package com.ddf.boot.quickstart.core.controller;

import com.ddf.boot.common.api.model.captcha.request.CaptchaCheckRequest;
import com.ddf.boot.common.api.model.captcha.request.CaptchaRequest;
import com.ddf.boot.common.api.model.captcha.response.ApplicationCaptchaResult;
import com.ddf.boot.common.api.model.common.response.ResponseData;
import com.ddf.boot.common.core.util.BeanCopierUtils;
import com.ddf.boot.common.mvc.resolver.MultiArgumentResolver;
import com.ddf.boot.quickstart.api.response.common.SysDictResponse;
import com.ddf.boot.quickstart.core.helper.CommonHelper;
import com.ddf.boot.quickstart.core.repository.SysDictRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>通用工具类</p >
 *
 * @menu 通用工具类
 * @author Snowball
 * @version 1.0
 * @date 2022/05/15 23:02
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class CommonController {

    private final CommonHelper commonHelper;
    private final SysDictRepository sysDictRepository;

    @GetMapping("listDict")
    public ResponseData<List<SysDictResponse>> listDict(@RequestParam String dictType) {
        return ResponseData.success(BeanCopierUtils.copy(sysDictRepository.listDictByCodeFromCache(dictType), SysDictResponse.class));
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @PostMapping("generateCaptcha")
    public ResponseData<ApplicationCaptchaResult> generateCaptcha(@MultiArgumentResolver @Validated CaptchaRequest request) {
        return ResponseData.success(commonHelper.generateCaptcha(request));
    }

    /**
     * 验证码校验
     *
     * @param request
     */
    @PostMapping("checkCaptcha")
    public ResponseData<Void> checkCaptcha(@RequestBody @Validated CaptchaCheckRequest request) {
        commonHelper.verifyCaptcha(request);
        return ResponseData.empty();
    }
}
