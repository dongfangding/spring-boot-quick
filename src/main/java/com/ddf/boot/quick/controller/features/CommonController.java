package com.ddf.boot.quick.controller.features;

import com.ddf.boot.quick.helper.CommonHelper;
import com.ddf.boot.quick.model.request.SendSmsCodeRequest;
import com.ddf.boot.quick.model.response.CaptchaResponse;
import com.ddf.common.captcha.model.CaptchaRequest;
import com.ddf.common.captcha.model.CaptchaResult;
import comm.ddf.common.vps.dto.UploadResponse;
import comm.ddf.common.vps.helper.VpsClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    private final VpsClient vpsClient;


    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/generateCaptcha")
    public CaptchaResponse generateCaptcha(@RequestBody @Validated CaptchaRequest request) {
        final CaptchaResult generate = commonHelper.generateCaptcha(request);
        final CaptchaResponse response = new CaptchaResponse();
        response.setWidth(generate.getWidth());
        response.setHeight(generate.getHeight());
        response.setTokenId(generate.getToken());
        response.setBase64(generate.getImageBase64());
        response.setPrefix(generate.getPrefix());
        return response;
    }

    /**
     * 发送短信验证码
     *
     * @param sendSmsCodeRequest
     * @return
     */
    @PostMapping("/sendSmsCode")
    public void sendSmsCode(@RequestBody @Validated SendSmsCodeRequest sendSmsCodeRequest) {
        commonHelper.sendSmsCode(sendSmsCodeRequest);
    }

    /**
     * 上传文件并生成缩略图
     * 可以使用postman测试，方法选择post, body参数选择form-data, 新增一个key, 属性名为file, 在这个属性名
     * 后面有一个类型， 默认是text, 选择为file, 这个时候value就变成了选择文件了，选择好文件之后就可以上传了
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("uploadFile")
    public UploadResponse uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return vpsClient.uploadFile(multipartFile);
    }

    /**
     * 批量上传文件并生成缩略图
     * 可以使用postman测试，方法选择post, body参数选择form-data, 新增一个key, 属性名为file, 在这个属性名
     * 后面有一个类型， 默认是text, 选择为file, 这个时候value就变成了选择文件了，选择好文件之后就可以上传了
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("batchUploadFile")
    public List<UploadResponse> uploadFile(@RequestParam("file") MultipartFile[] multipartFile) {
        return vpsClient.batchUploadFile(multipartFile);
    }
}
