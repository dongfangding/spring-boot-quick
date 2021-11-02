package com.ddf.boot.quick.features;

import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.features.forkjoin.batchinsert.BatchInsertActionExample;
import java.io.IOException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/08/06 11:14
 */
public class BatchInsertActionExampleTest extends QuickApplicationTest {

    @Autowired
    private BatchInsertActionExample batchInsertActionExample;

    @Test
    public void testExecute() throws IOException {
        batchInsertActionExample.execute();
        System.in.read();
    }
}
