package com.ddf.boot.quickstart.core.features.xxljob;

import com.ddf.boot.quickstart.core.repository.impl.UserInfoRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/12/07 17:22
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JobHandler {

    private final UserInfoRepository userInfoRepository;

    /**
     * 简单bean任务
     *
     * @return
     */
    @XxlJob(value = "helloUser")
    public ReturnT<String> helloUser(String param) throws Exception {
        final long timestamp = System.currentTimeMillis();
        if (timestamp % 2 != 0) {
            // 故意演示错误日志记录
            XxlJobLogger.log("天不时，地不利，任务无法执行!");
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("{}: hello world!", timestamp);
        return ReturnT.SUCCESS;
    }
}
