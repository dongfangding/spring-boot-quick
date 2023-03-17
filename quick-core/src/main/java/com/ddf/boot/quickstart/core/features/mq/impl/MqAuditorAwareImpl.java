package com.ddf.boot.quickstart.core.features.mq.impl;

import com.ddf.boot.common.authentication.exception.UserClaimMissionException;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.common.mq.helper.MqMessageHelper;
import com.ddf.boot.common.mq.interfaces.MqAuditorAware;
import java.util.Optional;
import org.springframework.stereotype.Component;

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
     * @return java.util.Optional<java.lang.String>
     * @author dongfang.ding
     * @date 2019/12/21 0021 14:08
     * @see MqMessageHelper#getCurrentAuditor()
     **/
    @Override
    public Optional<String> getAuditor() {
        try {
            return Optional.of(UserContextUtil.getUserId());
        } catch (UserClaimMissionException e) {
            return Optional.empty();
        }
    }
}
