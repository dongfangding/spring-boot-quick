package com.ddf.boot.quickstart.core.config;

import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.boot.common.core.exception200.AbstractExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 通过集成通用包的异常逻辑来异常处理
 * <p>
 * 因为通用包无法确定包,所以提供抽象类，让使用方确定包范围，然后继承逻辑
 *
 * @author dongfang.ding
 * @date 2020/11/22 0022 22:10
 */
@RestControllerAdvice(basePackages = "com.ddf.game.xiuxian.core")
public class GlobalExceptionAdvice extends AbstractExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @Override
    public ResponseData<?> handlerException(Exception exception, HttpServletRequest httpServletRequest,
            HttpServletResponse response) {
        return super.handlerException(exception, httpServletRequest, response);
    }

}
