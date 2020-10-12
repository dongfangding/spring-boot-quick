package com.ddf.boot.quick.oss;

import com.aliyun.oss.OSS;
import com.ddf.boot.common.core.util.SpringContextHolder;
import com.ddf.boot.common.ext.oss.helper.OssHelper;

import java.io.File;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/12 16:28
 */
public class OssUtil {

    private static final OssHelper OSS_HELPER = SpringContextHolder.getBean(OssHelper.class);

    /**
     * 全局私有bucket, 私有
     */
    public static final String DDF_PRIVATE_BUCKET = "ddf-private";

    public static final String PIC_PATH_TEST = "/pic/boot-quick/";

    /**
     * 获取全局私有oss实例
     * @return
     */
    public static OSS getGlobalPrivateOss() {
        return OSS_HELPER.getOss(DDF_PRIVATE_BUCKET);
    }

    /**
     * 格式化图片路径
     * @param path
     * @return
     */
    public static String formatPicTestPath(String path) {
        return PIC_PATH_TEST + path;
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param file
     */
    public static void globalPrivatePutObject(String key, File file) {
        getGlobalPrivateOss().putObject(DDF_PRIVATE_BUCKET, formatPicTestPath(key), file);
    }
}
