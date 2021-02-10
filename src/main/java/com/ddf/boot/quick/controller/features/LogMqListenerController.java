package com.ddf.boot.quick.controller.features;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.common.mq.entity.LogMqListener;
import com.ddf.boot.common.mq.mapper.LogMqListenerMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mq消息消费日志记录$
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
 * @menu mq消息消费日志记录
 * @date 2019/12/21 0021 15:00
 */
@RestController
@RequestMapping("logMqListener")
public class LogMqListenerController {
    @Autowired
    private LogMqListenerMapper logMqListenerMapper;


    /**
     * 列表查询
     *
     * @return
     */
    @GetMapping("/list")
    public List<LogMqListener> list() {
        LambdaQueryWrapper<LogMqListener> query = Wrappers.lambdaQuery();
        query.orderByDesc(LogMqListener::getEventTimestamp);
        return logMqListenerMapper.selectList(query);
    }
}
