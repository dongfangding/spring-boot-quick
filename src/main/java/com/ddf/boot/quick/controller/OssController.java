package com.ddf.boot.quick.controller;

import com.ddf.boot.common.ext.oss.config.StsTokenRequest;
import com.ddf.boot.common.ext.oss.config.StsTokenResponse;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.quick.oss.BootOssClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@AllArgsConstructor(onConstructor_={@Autowired})
public class OssController {

    private final BootOssClient bootOssClient;

    private final OssHelper ossHelper;



    /**
     * 返回STS授权信息, 实际中使用的比较多
     */
    @GetMapping("responseStsCredentials")
    public StsTokenResponse getOssToken(@RequestBody StsTokenRequest stsTokenRequest) {
        return ossHelper.getOssToken(stsTokenRequest);
    }

    /**
     * 本地简单上传，不需要客户端上传文件，直接上传本地文件
     */
    @PostMapping("localSimpleUploadPic")
    public String localSimpleUpload() throws IOException {
        String key = "7dd13d37acaf2edde000d44f811001e93b0193f8.jpg";
        ClassPathResource classPathResource = new ClassPathResource("/static/img/" + key);
        return bootOssClient.putObject(".jpg", classPathResource.getInputStream());
    }

    @PostMapping("localSimpleDownloadPic")
    public void localSimpleDownload(@RequestParam String picPath, HttpServletResponse httpServletResponse) {
        // todo 获取对象授权处理
        /*OSS globalPrivateOss = BootOssClient.getGlobalPrivateOss();
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
        }*/
    }
}
