package com.ddf.boot.quick.oss;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.core.exception200.ServerErrorException;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.common.ext.oss.config.StsTokenRequest;
import com.ddf.boot.common.ext.oss.config.StsTokenResponse;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/12 16:28
 */
@Component
@Slf4j
public class BootOssClient {

    @Autowired
    private OssHelper ossHelper;

    public static final String OSS_PLATFORM = "boot-quick";

    public static List<String> staticPicList = Lists.newArrayList("static/img/7dd13d37acaf2edde000d44f811001e93b0193f8.jpg",
            "static/img/8eed93dce71190ef40021a87d91b9d16fffa60c2.jpg", "static/img/24f6cf399b504fc2e4a6385df2dde71192ef6dc2.jpg",
            "static/img/32d9573eb80e7bec7f238bb9382eb93899506bc2.jpg", "static/img/60bb80256b600c331a5dfe910d4c510fd8f9a198.jpg",
            "static/img/76c6a41a0ef41bd58428c8ad46da81cb38db3d4c.jpg", "static/img/449256a5462309f7004b8d4b650e0cf3d6cad698.jpg",
            "static/img/a8140ad88d1001e91ec69c57af0e7bec56e797cd.jpg", "static/img/bdd84cf23a87e950a85cf3f31c385343faf2b43b.jpg",
            "static/img/da80babe6c81800a4e1c0156a63533fa808b47c2.jpg", "static/img/月光.9b95f8a8.jpg");


    public static TimedCache<String, AtomicInteger> ipApiTotalMap = CacheUtil.newTimedCache(TimeUnit.DAYS.toHours(24));

    public static Integer maxIpTotalPerDay = 20;

    static {
        ipApiTotalMap.schedulePrune(TimeUnit.MINUTES.toMillis(10));
    }

    public StsTokenResponse getOssTokenWithApiLimit() {
        final String host = WebUtil.getHost();
        AtomicInteger currCount;
        synchronized (host.intern()) {
            currCount = ipApiTotalMap.get(host, false);
            if (currCount == null) {
                currCount = new AtomicInteger(1);
            } else {
                currCount.getAndIncrement();
            }
            PreconditionUtil.checkArgument(currCount.get() <= maxIpTotalPerDay, "24小时内同一ip调用次数超限");
            ipApiTotalMap.put(host, currCount);
        }
        final StsTokenResponse token = getOssToken();
        ipApiTotalMap.put(host, currCount);
        return token;
    }


    /**
     * 获取当前项目的oss token属性信息
     * @return
     */
    public StsTokenResponse getOssToken() {
        StsTokenRequest request = new StsTokenRequest();
        request.setPlatform(OSS_PLATFORM);
        request.setIdentity(JwtUtil.getByContextNotNecessary().getUserId());
        return ossHelper.getOssToken(request);
    }

    /**
     * 上传对象, 并返回对象key
     * @param inputStream
     */
    public String putObject(String suffix, InputStream inputStream) {
        final StsTokenRequest stsTokenRequest = new StsTokenRequest();
        stsTokenRequest.setPlatform(OSS_PLATFORM);
        stsTokenRequest.setIdentity(JwtUtil.getByContextNotNecessary().getUserId());
        AtomicReference<String> objectKey = new AtomicReference<>();
        ossHelper.getStsOss(stsTokenRequest, (dto) -> {
            objectKey.set(dto.getStsTokenResponse().getObjectPrefix() + "." + suffix);
            dto.getOss().putObject(dto.getStsTokenResponse().getBucketName(), objectKey.get(),
                    inputStream);
        });
        return objectKey.get();
    }


    /**
     * 生成随机头像
     * @param username
     * @return
     */
    public String uploadAvatar(String username) {
        List<String> staticPicList = BootOssClient.staticPicList;
        String randomPath = staticPicList.get(RandomUtil.randomInt(0, staticPicList.size()));
        String avatarName = username + randomPath.substring(randomPath.lastIndexOf("."));
        final StsTokenRequest stsTokenRequest = new StsTokenRequest();
        stsTokenRequest.setPlatform(OSS_PLATFORM);
        stsTokenRequest.setIdentity(JwtUtil.getByContextNotNecessary().getUserId());
        AtomicReference<String> fileName = new AtomicReference<>();
        ossHelper.getStsOss(stsTokenRequest, (dto) -> {
            fileName.set(dto.getStsTokenResponse().getObjectPrefix() + "_" + avatarName);
            InputStream inputStream;
            try {
                inputStream = new ClassPathResource(randomPath).getInputStream();
            } catch (IOException ioException) {
                log.error("获取文件失败", ioException);
                throw new ServerErrorException("获取文件失败");
            }
            dto.getOss().putObject(dto.getStsTokenResponse().getBucketName(), dto.getStsTokenResponse().getObjectPrefix() + "_" + avatarName,
                    inputStream);
        });
        return fileName.get();
    }
}
