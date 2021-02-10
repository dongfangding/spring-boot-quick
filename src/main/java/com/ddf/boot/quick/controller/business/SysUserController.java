package com.ddf.boot.quick.controller.business;

import com.ddf.boot.quick.biz.ICommonBizService;
import com.ddf.boot.quick.model.bo.CaptchaRequest;
import com.ddf.boot.quick.model.vo.CaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @menu 系统用户控制器
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@RestController
@RequestMapping("/sysUser")
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserController {

    private final ICommonBizService commonBizService;


    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @RequestMapping("generateCaptcha")
    public CaptchaResponse generateCaptcha(@RequestBody @Validated CaptchaRequest request) {
        return commonBizService.generateCaptcha(request);
    }

}

