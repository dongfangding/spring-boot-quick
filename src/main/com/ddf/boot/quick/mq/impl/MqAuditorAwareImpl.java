package com.ddf.boot.quick.mq.impl;

import com.ddf.boot.common.jwt.exception.UserClaimMissionException;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.ddf.boot.common.mq.helper.MqMessageHelper;
import com.ddf.boot.common.mq.interfaces.MqAuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 设置当前用户信息给mq工具类使用$
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
 * @date 2019/12/21 0021 13:59
 */
@Component
public class MqAuditorAwareImpl implements MqAuditorAware {

    /**
     * 获取当前用户信息，在持久化消息的时候保存进去，但如果时异步发送的话，这里就会获取不到
     *
     * @see MqMessageHelper#getCurrentAuditor()
     * @return java.util.Optional<java.lang.String>
     * @author dongfang.ding
     * @date 2019/12/21 0021 14:08
     **/
    @Override
    public Optional<String> getAuditor() {
        try {
            return Optional.of(JwtUtil.getByContext().getUsername());
        } catch (UserClaimMissionException e) {
            return Optional.empty();
        }
    }
}
