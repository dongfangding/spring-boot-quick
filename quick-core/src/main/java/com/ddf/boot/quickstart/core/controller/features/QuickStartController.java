package com.ddf.boot.quickstart.core.controller.features;

import com.ddf.boot.common.api.exception.BusinessException;
import com.ddf.boot.common.api.model.common.response.ResponseData;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.core.infra.model.entity.GlobalMetadataConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快速开始控制器，用于演示某些功能的使用方式$
 *
 * @author dongfang.ding
 * @menu 项目快速开始演示类
 * @date 2020/8/15 0015 17:30
 */
@RestController
@RequestMapping("quick-start")
@Slf4j
@RequiredArgsConstructor
public class QuickStartController {
    /**
     * 快速测试，空接口，可以用这个接口和业务接口的压测结果对比
     *
     * @return
     */
    @GetMapping("quickTest")
    public ResponseData<Boolean> quickTest() {
        return ResponseData.success(Boolean.TRUE);
    }


    /**
     * 异常演示
     *
     * @return
     */
    @GetMapping("simpleException")
    public ResponseData<Boolean> simpleException() {
        throw new BusinessException("异常演示");
    }

    /**
     * 异常演示
     *
     * @return
     */
    @GetMapping("simpleBizException1")
    public Boolean simpleBizException1() {
        throw new BusinessException(ApplicationExceptionCode.TEST_SIMPLE_BIZ_MESSAGE);
    }

    /**
     * 异常演示
     *
     * @return
     */
    @GetMapping("fillBizException")
    public Boolean fillBizException() {
        throw new BusinessException(ApplicationExceptionCode.TEST_FILL_EXCEPTION, System.currentTimeMillis());
    }

    /**
     * 异常演示
     *
     * @return
     */
    @GetMapping("fillBizException1")
    public Boolean fillBizException1() {
        throw new BusinessException(ApplicationExceptionCode.TEST_FILL_BIZ_EXCEPTION, System.currentTimeMillis());
    }

    /**
     * 异常演示， 演示即使遇到异常，也能返回数据
     *
     * @return
     */
    @GetMapping("exceptionReturnExtra")
    public ResponseData<GlobalMetadataConfig> exceptionReturnExtra() {
        final GlobalMetadataConfig config = new GlobalMetadataConfig();
        config.setId(0L);
        config.setConfigCode("errorCode");
        config.setConfigValue("hello world!");
        throw new BusinessException(
                config, ApplicationExceptionCode.TEST_FILL_BIZ_EXCEPTION, System.currentTimeMillis());
    }
}
