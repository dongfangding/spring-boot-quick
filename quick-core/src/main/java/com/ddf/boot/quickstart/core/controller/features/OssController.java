package com.ddf.boot.quickstart.core.controller.features;

import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.boot.common.ext.oss.config.StsTokenResponse;
import com.ddf.boot.quickstart.core.client.OssClient;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @menu 阿里云OSS相关
 * @date 2020/10/13 11:44
 */
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OssController {
    private final OssClient ossClient;

    /**
     * 返回STS授权信息, 实际中使用的比较多
     */
    @PostMapping("getOssToken")
    public ResponseData<StsTokenResponse> getOssToken() {
        return ResponseData.success(ossClient.getOssTokenWithApiLimit());
    }

    /**
     * 本地简单上传，不需要客户端上传文件，直接上传本地文件
     */
    @PostMapping("localSimpleUploadPic")
    public ResponseData<String> localSimpleUpload() throws IOException {
        String key = "7dd13d37acaf2edde000d44f811001e93b0193f8.jpg";
        ClassPathResource classPathResource = new ClassPathResource("/static/img/" + key);
        return ResponseData.success(ossClient.putObject(".jpg", classPathResource.getInputStream()));
    }
}
