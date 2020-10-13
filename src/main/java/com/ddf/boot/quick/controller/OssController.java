package com.ddf.boot.quick.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ddf.boot.common.ext.oss.config.StsCredentials;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.quick.oss.OssUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/13 11:44
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    /**
     * 本地简单上传，不需要客户端上传文件，直接上传本地文件
     */
    @PostMapping("localSimpleUpload")
    public void localSimpleUpload() throws IOException {
        String key = "7dd13d37acaf2edde000d44f811001e93b0193f8.jpg";
        ClassPathResource classPathResource = new ClassPathResource("/static/img/" + key);
        OssUtil.globalPrivatePutObject(key, classPathResource.getInputStream());
    }


    /**
     * 返回STS授权信息
     */
    @GetMapping("responseStsCredentials")
    public StsCredentials responseStsCredentials() throws ClientException {
        return OssHelper.getStsCredentials();
    }


    /**
     * 使用STS上传文件
     */
    @PostMapping("stsSimpleUpload")
    public void stsSimpleUpload() throws IOException {
        String key = "8eed93dce71190ef40021a87d91b9d16fffa60c2.jpg";
        ClassPathResource classPathResource = new ClassPathResource("/static/img/" + key);
        OssUtil.globalPrivateStsPutObject(key, classPathResource.getInputStream());
    }
}
