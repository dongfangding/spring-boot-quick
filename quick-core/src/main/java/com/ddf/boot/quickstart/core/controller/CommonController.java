package com.ddf.boot.quickstart.core.controller;

import com.ddf.boot.common.api.model.captcha.request.CaptchaCheckRequest;
import com.ddf.boot.common.api.model.captcha.request.CaptchaRequest;
import com.ddf.boot.common.api.model.captcha.response.ApplicationCaptchaResult;
import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.boot.common.core.resolver.MultiArgumentResolver;
import com.ddf.boot.common.core.util.BeanCopierUtils;
import com.ddf.boot.quickstart.api.request.common.SendSmsCodeRequest;
import com.ddf.boot.quickstart.api.response.common.ApplicationSmsSendResponse;
import com.ddf.boot.quickstart.api.response.common.SysDictResponse;
import com.ddf.boot.quickstart.core.helper.CommonHelper;
import com.ddf.boot.quickstart.core.repository.SysDictRepository;
import comm.ddf.common.vps.dto.UploadResponse;
import comm.ddf.common.vps.helper.VpsClient;
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
    private final SysDictRepository sysDictRepository;
    private final VpsClient vpsClient;

    @GetMapping("listDict")
    public ResponseData<List<SysDictResponse>> listDict(@RequestParam String dictType) {
        return ResponseData.success(BeanCopierUtils.copy(sysDictRepository.listDictByCodeFromCache(dictType), SysDictResponse.class));
//        return CommonConverter.INSTANCE.convert(sysDictRepository.listDictByCodeFromCache(dictType));
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

    /**
     * 发送短信验证码
     *
     * @param sendSmsCodeRequest
     * @return
     */
    @PostMapping("/sendSmsCode")
    public ResponseData<ApplicationSmsSendResponse> sendSmsCode(@RequestBody @Validated SendSmsCodeRequest sendSmsCodeRequest) {
        return ResponseData.success(commonHelper.sendAndLoadSmsCodeWithLimit(sendSmsCodeRequest));
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
    public ResponseData<UploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseData.success(vpsClient.uploadFile(multipartFile));
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
    public ResponseData<List<UploadResponse>> uploadFile(@RequestParam("file") MultipartFile[] multipartFile) {
        return ResponseData.success(vpsClient.batchUploadFile(multipartFile));
    }

}
