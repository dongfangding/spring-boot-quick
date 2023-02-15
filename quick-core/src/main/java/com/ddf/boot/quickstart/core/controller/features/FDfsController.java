package com.ddf.boot.quickstart.core.controller.features;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/11/30 13:29
 */
@RestController
@RequestMapping("fdfs")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FDfsController {

    private final FastFileStorageClient fastFileStorageClient;

    /**
     * 测试上传
     *
     * @throws FileNotFoundException
     */
    @PostMapping("testUpload")
    public String upload(@RequestParam String filePath) throws FileNotFoundException {
        final File file = new File(filePath);
        String extName = filePath.substring(filePath.lastIndexOf(".") + 1);
        final StorePath storePath = fastFileStorageClient.uploadFile(new FileInputStream(file), file.length(), extName, null);
        log.info("文件上传成功， group = {}, path = {}, fullPath = {}", storePath.getGroup(), storePath.getPath(), storePath.getFullPath());
        return storePath.getFullPath();
    }
}
