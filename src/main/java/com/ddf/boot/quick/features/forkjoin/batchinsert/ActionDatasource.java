package com.ddf.boot.quick.features.forkjoin.batchinsert;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>任务数据源</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/04/25 15:55
 */
public class ActionDatasource {

    /**
     * sql操作对象
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * 存储所有要执行的sql的集合
     */
    private final List<String> executeSql;

    public ActionDatasource(JdbcTemplate jdbcTemplate, List<String> executeSql) {
        this.jdbcTemplate = jdbcTemplate;
        this.executeSql = executeSql;
    }

    /**
     * 执行对应角标的任务
     *
     * @param index
     */
    public void execute(long index) {
        if (index < executeSql.size()) {
            jdbcTemplate.execute(executeSql.get((int) index));
        }
    }
}
