package com.ddf.boot.quick.oss;

import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.ext.oss.config.StsTokenRequest;
import com.ddf.boot.common.ext.oss.helper.OssHelper;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/12 16:28
 */
@Component
@AllArgsConstructor(onConstructor_={@Autowired})
public class BootOssClient {

    private final OssHelper ossHelper;

    public static final String OSS_PLATFORM = "boot-quick";

    public static List<String> staticPicList = Lists.newArrayList("static/img/7dd13d37acaf2edde000d44f811001e93b0193f8.jpg",
            "static/img/8eed93dce71190ef40021a87d91b9d16fffa60c2.jpg", "static/img/24f6cf399b504fc2e4a6385df2dde71192ef6dc2.jpg",
            "static/img/32d9573eb80e7bec7f238bb9382eb93899506bc2.jpg", "static/img/60bb80256b600c331a5dfe910d4c510fd8f9a198.jpg",
            "static/img/76c6a41a0ef41bd58428c8ad46da81cb38db3d4c.jpg", "static/img/449256a5462309f7004b8d4b650e0cf3d6cad698.jpg",
            "static/img/a8140ad88d1001e91ec69c57af0e7bec56e797cd.jpg", "static/img/bdd84cf23a87e950a85cf3f31c385343faf2b43b.jpg",
            "static/img/da80babe6c81800a4e1c0156a63533fa808b47c2.jpg", "static/img/月光.9b95f8a8.jpg");




    /**
     * 生成随机头像
     * @param username
     * @return
     */
    @SneakyThrows
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
            dto.getOss().putObject(dto.getStsTokenResponse().getBucketName(), dto.getStsTokenResponse().getObjectPrefix() + "_" + avatarName,
                    new ClassPathResource(randomPath).getInputStream());
        });
        return fileName.get();
    }
}
