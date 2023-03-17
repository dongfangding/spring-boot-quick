package com.ddf.boot.quickstart.core.features.xxljob;

import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.quickstart.core.repository.UserInfoRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JobHandler {

    private final UserInfoRepository userInfoRepository;

    /**
     * 简单bean任务
     *
     * @return
     */
    @XxlJob(value = "helloUser")
    public ReturnT<String> helloUser(String param) throws Exception {
        final int random = RandomUtil.randomInt(10);
        if (random % 2 != 0) {
            // 故意演示错误日志记录
            log.info("xxl-job-helloUser任务执行失败");
            XxlJobHelper.log("天不时，地不利，任务无法执行!");
            return ReturnT.FAIL;
        }
        log.info("xxl-job-helloUser任务执行.....");
        XxlJobHelper.log("{}: hello world!", random);
        return ReturnT.SUCCESS;
    }
}
