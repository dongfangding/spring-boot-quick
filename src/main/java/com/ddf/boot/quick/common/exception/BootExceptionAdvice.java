package com.ddf.boot.quick.common.exception;

import com.ddf.boot.common.core.exception200.AbstractExceptionHandler;
import com.ddf.boot.common.core.response.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过集成通用包的异常逻辑来异常处理
 *
 * 因为通用包无法确定包,所以提供抽象类，让使用方确定包范围，然后继承逻辑
 *
 * @author dongfang.ding
 * @date 2020/11/22 0022 22:10
 */
@RestControllerAdvice(basePackages = "com.ddf.boot.quick")
public class BootExceptionAdvice extends AbstractExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @Override
    public ResponseData<?> handlerException(Exception exception, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        return super.handlerException(exception, httpServletRequest, response);
    }

}
