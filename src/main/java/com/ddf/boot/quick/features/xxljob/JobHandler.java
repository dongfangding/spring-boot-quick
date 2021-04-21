package com.ddf.boot.quick.features.xxljob;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.biz.ISysUserBizService;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
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

    private final ISysUserBizService sysUserBizService;

    /**
     * 简单bean任务
     *
     * @return
     */
    @XxlJob(value = "helloUser")
    public ReturnT<String> helloUser(String param) throws Exception {
        final SysUserPageRequest request = new SysUserPageRequest();
        request.setPageNum(1);
        request.setPageSize(20);
        final PageResult<SysUserDTO> result = sysUserBizService.pageList(request);
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
