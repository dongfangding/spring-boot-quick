package com.ddf.boot.quick.features.forkjoin.batchinsert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/04/25 17:00
 */
@Component
public class BatchInsertActionExample {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 要插入的sql定义语句， 到values即可
     * 如
     * insert into t_user(id, name) values
     */
    private String dmlSql;

    /**
     * 要执行的每条插入语句sql，不需要处理各种分隔符
     * 如
     * (1, 'ddf')
     */
    private String valueSq;

    public void execute() {
        String sql = "INSERT INTO `t_user`(id, name) VALUES ";
        String varSql = "('%s', '%s')";

        StringBuilder sbl = new StringBuilder();

        // 预期要插入的数据行数
        int expectRecordSize = 1000000;
        // 循环结束角标规则
        int loopEndIndex = 2 * expectRecordSize;
        // 批量sql的数量，会将指定数量的sql合并成一条sql执行
        int batchSize = 1000;
        List<String> executeSql = new ArrayList<>(expectRecordSize);

        for (int i = expectRecordSize + 1; i <= loopEndIndex; i++) {
            if (sbl.length() == 0) {
                sbl.append(sql);
            }
            sbl.append(String.format(varSql, i, "ddf" + i));
            if (i == loopEndIndex || i % batchSize == 0) {
                sbl.append(";");
                executeSql.add(sbl.toString());
                sbl.delete(0, sbl.length());
            } else {
                sbl.append(",");
            }
        }

        executeSql.parallelStream().forEach((val) -> jdbcTemplate.execute(val));

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        final ActionDatasource task = new ActionDatasource(jdbcTemplate, executeSql);
        final BatchInsertAction action = new BatchInsertAction(task,0L, 10000, 1000);
        forkJoinPool.invoke(action);
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        long start = System.currentTimeMillis();
        for (String s : list) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("s = " + s);
        }
        System.out.println("传统耗时: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        list.parallelStream().forEach(v -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + v);
        });
        System.out.println("并行耗时: " + (System.currentTimeMillis() - start));
    }
}
