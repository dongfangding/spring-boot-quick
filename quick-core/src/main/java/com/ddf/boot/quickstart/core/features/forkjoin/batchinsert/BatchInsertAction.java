package com.ddf.boot.quickstart.core.features.forkjoin.batchinsert;

import java.util.concurrent.RecursiveAction;


/**
 * 批量插入数据任务拆分计算类
 *
 * @author dongfang.ding
 * @date 2021/4/25 16:54
 **/
public class BatchInsertAction extends RecursiveAction {

    /**
     * 任务拆分开始标志
     */
    private final long start;

    /**
     * 任务拆分结束标志
     */
    private final long end;

    /**
     * 具体要做的任务类
     */
    private final ActionDatasource task;

    /**
     * 任务拆分临界值，小于这个值，任务不再继续拆分
     */
    private final long thurshold;

    public BatchInsertAction(ActionDatasource task, long start, long end, long thurshold) {
        this.task = task;
        this.start = start;
        this.end = end;
        this.thurshold = thurshold;
    }

    @Override
    protected void compute() {
        if ((end - start) < thurshold) {
            // 达到临界点开始执行任务，具体情况具体代码；这里由于是到集合中取要执行的sql, 所以会将当前角标传过去
            for (long i = start; i <= end; i++) {
                task.execute(i);
            }
        } else {
            long middle = (end + start) / 2;

            BatchInsertAction left = new BatchInsertAction(task, start, middle, thurshold);
            left.fork();

            BatchInsertAction right = new BatchInsertAction(task, middle + 1, end, thurshold);
            right.fork();
        }
    }
}
