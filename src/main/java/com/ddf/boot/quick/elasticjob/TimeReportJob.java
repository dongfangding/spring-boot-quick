package com.ddf.boot.quick.elasticjob;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * elastic-job定时任务，作为简单演示，只打印当前时间毫秒值$
 *
 * @author dongfang.ding
 * @date 2020/9/6 0006 22:33
 */
@Slf4j
@Component
public class TimeReportJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("[{}]任务传递参数: {}, 触发执行: {}", this.getClass()
                .getName(), shardingContext.getJobParameter(), System.currentTimeMillis());
    }
}
