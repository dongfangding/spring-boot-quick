package com.ddf.boot.quick.oss;

import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.quick.QuickApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/12 16:47
 */
public class OssTest extends QuickApplicationTest {

    @Autowired
    private OssHelper ossHelper;

    private static final String PIC_1 = "C:\\Users\\Administrator\\Pictures\\壁纸\\24f6cf399b504fc2e4a6385df2dde71192ef6dc2.jpg";

    @Test
    public void testSimpleUpload() {
        File file = new File(PIC_1);
        OssUtil.globalPrivatePutObject(file.getName(), file);
    }
}
