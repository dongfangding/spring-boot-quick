package com.ddf.boot.quick.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyuncs.exceptions.ClientException;
import com.ddf.boot.common.ext.oss.config.StsCredentials;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.quick.oss.BootOssClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
    @PostMapping("localSimpleUploadPic")
    public String localSimpleUpload() throws IOException {
        String key = "7dd13d37acaf2edde000d44f811001e93b0193f8.jpg";
        ClassPathResource classPathResource = new ClassPathResource("/static/img/" + key);
        return BootOssClient.globalPrivatePutObject(key, classPathResource.getInputStream(), BootOssClient.KeyType.PIC);
    }

    @PostMapping("localSimpleDownloadPic")
    public void localSimpleDownload(@RequestParam String picPath, HttpServletResponse httpServletResponse) {
        OSS globalPrivateOss = BootOssClient.getGlobalPrivateOss();
        OSSObject ossObject = BootOssClient.globalPrivateGetObject(picPath, BootOssClient.KeyType.PIC);

        httpServletResponse.setContentType("multipart/form-data");
        httpServletResponse.setHeader("Content-Disposition","attachment;fileName="+picPath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
             PrintWriter writer = httpServletResponse.getWriter()) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                writer.write(line);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        BootOssClient.globalPrivateStsPutObject(key, classPathResource.getInputStream());
    }


}
