package com.ddf.boot.quick.oss;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/12 16:28
 */
public class OssUtil {

    public static List<String> staticPicList = Lists.newArrayList("static/img/7dd13d37acaf2edde000d44f811001e93b0193f8.jpg",
            "static/img/8eed93dce71190ef40021a87d91b9d16fffa60c2.jpg", "static/img/24f6cf399b504fc2e4a6385df2dde71192ef6dc2.jpg",
            "static/img/32d9573eb80e7bec7f238bb9382eb93899506bc2.jpg", "static/img/60bb80256b600c331a5dfe910d4c510fd8f9a198.jpg",
            "static/img/76c6a41a0ef41bd58428c8ad46da81cb38db3d4c.jpg", "static/img/449256a5462309f7004b8d4b650e0cf3d6cad698.jpg",
            "static/img/a8140ad88d1001e91ec69c57af0e7bec56e797cd.jpg", "static/img/bdd84cf23a87e950a85cf3f31c385343faf2b43b.jpg",
            "static/img/da80babe6c81800a4e1c0156a63533fa808b47c2.jpg", "static/img/月光.9b95f8a8.jpg");

    /**
     * 不同的内容所在根目录不一样，这里提供一个枚举
     */
    public enum KeyType {
        /**
         * 图片类型
         */
        PIC
    }

    /**
     * 全局私有bucket, 私有
     */
    public static final String DDF_PRIVATE_BUCKET = "ddf-private";

    public static final String PIC_PATH_TEST = "boot-quick/pic/";

    /**
     * 获取全局私有oss实例
     * @return
     */
    public static OSS getGlobalPrivateOss() {
        return OssHelper.getOss(DDF_PRIVATE_BUCKET);
    }

    /**
     * 格式化图片路径
     * @param path
     * @return
     */
    public static String formatPicPath(String path) {
        return PIC_PATH_TEST + path;
    }

    /**
     * 根据类型格式化完整路径
     * @param key
     * @param keyType
     * @return
     */
    public static String formatPath(String key, KeyType keyType) {
        String finalKey = key;
        if (KeyType.PIC.equals(keyType)) {
            finalKey = formatPicPath(key);
        }
        return finalKey;
    }


    // ------------------------------------------------------上传

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param file
     */
    public static String globalPrivatePutObject(String key, File file) {
        getGlobalPrivateOss().putObject(DDF_PRIVATE_BUCKET, key, file);
        return key;
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param file
     * @param keyType
     */
    public static String globalPrivatePutObject(String key, File file, KeyType keyType) {
        String writeKey = formatPath(key, keyType);
        getGlobalPrivateOss().putObject(DDF_PRIVATE_BUCKET, writeKey, file);
        return writeKey;
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param inputStream
     */
    public static void globalPrivatePutObject(String key, InputStream inputStream) {
        getGlobalPrivateOss().putObject(DDF_PRIVATE_BUCKET, key, inputStream);
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param inputStream
     */
    public static String globalPrivatePutObject(String key, InputStream inputStream, KeyType keyType) {
        String writeKey = formatPath(key, keyType);
        getGlobalPrivateOss().putObject(DDF_PRIVATE_BUCKET, writeKey, inputStream);
        return writeKey;
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param file
     */
    @SneakyThrows
    public static void globalPrivateStsPutObject(String key, File file) {
        OSS stsOss = OssHelper.getStsOss();
        stsOss.putObject(DDF_PRIVATE_BUCKET, key, file);
        stsOss.shutdown();
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param file
     */
    @SneakyThrows
    public static String globalPrivateStsPutObject(String key, File file, KeyType keyType) {
        String writeKey = formatPath(key, keyType);
        OSS stsOss = OssHelper.getStsOss();
        stsOss.putObject(DDF_PRIVATE_BUCKET, writeKey, file);
        stsOss.shutdown();
        return writeKey;
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param inputStream
     */
    @SneakyThrows
    public static void globalPrivateStsPutObject(String key, InputStream inputStream) {
        OSS stsOss = OssHelper.getStsOss();
        stsOss.putObject(DDF_PRIVATE_BUCKET, key, inputStream);
        stsOss.shutdown();
    }

    /**
     * 使用全局私有 bucket上传文件
     * @param key
     * @param inputStream
     */
    @SneakyThrows
    public static String globalPrivateStsPutObject(String key, InputStream inputStream, KeyType keyType) {
        String writeKey = formatPath(key, keyType);
        OSS stsOss = OssHelper.getStsOss();
        stsOss.putObject(DDF_PRIVATE_BUCKET, writeKey, inputStream);
        stsOss.shutdown();
        return writeKey;
    }


    /**
     * 生成随机头像
     * @param username
     * @return
     */
    @SneakyThrows
    public static String uploadAvatar(String username) {
        List<String> staticPicList = OssUtil.staticPicList;
        String randomPath = staticPicList.get(RandomUtil.randomInt(0, staticPicList.size()));
        String avatarName = username + randomPath.substring(randomPath.lastIndexOf("."));
        String writeKey = OssUtil.globalPrivatePutObject(avatarName, new ClassPathResource(randomPath).getInputStream(), OssUtil.KeyType.PIC);
        return writeKey;
    }


    // ------------------------------------------------------获取

    /**
     * 获取存储的对象
     * @param key
     * @return
     */
    public static OSSObject globalPrivateGetObject(String key) {
        return globalPrivateGetObject(DDF_PRIVATE_BUCKET, key, null);
    }

    /**
     * 获取存储的对象
     * @param key
     * @return
     */
    public static OSSObject globalPrivateGetObject(String key, KeyType keyType) {
        return globalPrivateGetObject(DDF_PRIVATE_BUCKET, key, keyType);
    }

    /**
     * 获取存储的对象
     * @param key
     * @return
     */
    public static OSSObject globalPrivateGetObject(String bucketName, String key, KeyType keyType) {
        String writeKey = formatPath(key, keyType);
        return getGlobalPrivateOss().getObject(bucketName, writeKey);
    }
}
