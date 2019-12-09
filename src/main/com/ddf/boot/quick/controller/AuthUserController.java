package com.ddf.boot.quick.controller;

import com.ddf.boot.quick.model.bo.AuthUserRegistryVo;
import com.ddf.boot.quick.model.vo.AuthUserVo;
import com.ddf.boot.quick.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 13:36
 */
@RestController
@RequestMapping("authUser")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/registry")
    public AuthUserVo registry(@RequestBody AuthUserRegistryVo authUserRegistryVo) {
        return authUserService.registry(authUserRegistryVo);
    }
}
