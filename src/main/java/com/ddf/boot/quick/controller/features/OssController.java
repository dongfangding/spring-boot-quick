package com.ddf.boot.quick.controller.features;

import com.ddf.boot.common.ext.oss.config.StsTokenResponse;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.quick.oss.BootOssClient;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class OssController {
    @Autowired
    private BootOssClient bootOssClient;

    @Autowired
    private OssHelper ossHelper;

    /**
     * 返回STS授权信息, 实际中使用的比较多
     */
    @PostMapping("getOssToken")
    public StsTokenResponse getOssToken() {
        return bootOssClient.getOssTokenWithApiLimit();
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
