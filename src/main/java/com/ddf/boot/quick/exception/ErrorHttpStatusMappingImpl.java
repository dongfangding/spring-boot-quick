package com.ddf.boot.quick.exception;

import cn.hutool.http.HttpStatus;
import com.ddf.boot.common.core.exception.ErrorHttpStatusMapping;
import com.ddf.boot.common.jwt.exception.UserClaimMissionException;
import org.springframework.stereotype.Component;

/**
 * 异常类与http状态码的映射$
 * <p>
 * <p>
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ___/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * .............................................
 * 佛曰：bug泛滥，我已瘫痪！
 *
 * @author dongfang.ding
 * @date 2019/12/24 0024 13:35
 */
@Component
public class ErrorHttpStatusMappingImpl implements ErrorHttpStatusMapping {
    /**
     * 实现该接口，返回自定义异常对应的http状态码，{@link ErrorAttributesHandler}会去执行这个接口判断，然后获取
     * 实现方自己的状态码
     *
     * @param error
     * @return
     * @see ErrorAttributesHandler
     */
    @Override
    public Integer getHttpStatus(Throwable error) {
        String errorClassName = error.getClass().getName();
        if (UserClaimMissionException.class.getName().equals(errorClassName)) {
            return HttpStatus.HTTP_UNAUTHORIZED;
        } else if (BadRequestException.class.getName().equals(errorClassName)
                || IllegalArgumentException.class.getName().equals(errorClassName)) {
            return HttpStatus.HTTP_BAD_REQUEST;
        }
        return HttpStatus.HTTP_INTERNAL_ERROR;
    }
}
