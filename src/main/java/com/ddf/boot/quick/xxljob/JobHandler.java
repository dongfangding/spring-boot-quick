package com.ddf.boot.quick.xxljob;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.quick.model.bo.AuthUserPageBo;
import com.ddf.boot.quick.service.AuthUserService;
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

    private final AuthUserService authUserService;

    /**
     * 简单bean任务
     *
     * @return
     */
    @XxlJob(value = "helloUser")
    public ReturnT<String> helloUser(String param) throws Exception {
        final AuthUserPageBo bo = new AuthUserPageBo();
        final PageResult<AuthUser> result = authUserService.pageList(bo);
        if (CollectionUtil.isEmpty(result.getContent())) {
            // 故意演示错误日志记录
            XxlJobLogger.log("没有用户，无法继续执行");
            return ReturnT.FAIL;
        }

        final int i = RandomUtil.randomInt(10);
        if (i % 2 != 0) {
            // 故意演示错误日志记录
            XxlJobLogger.log("天不时，地不利，任务无法执行!");
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("{}: hello world!", result.getContent().get(0));
        return ReturnT.SUCCESS;
    }
}
