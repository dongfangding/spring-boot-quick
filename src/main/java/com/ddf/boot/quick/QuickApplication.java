package com.ddf.boot.quick;

import com.ddf.boot.common.constant.GlobalConstants;
import com.ddf.boot.common.jwt.config.EnableJwt;
import com.ddf.boot.common.log.EnableLogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @MapperScan 要保证只扫描继承到BaseMapper的类，否则正常的接口会抛出异常Invalid bound statement (not found)
 *
 * @author dongfang.ding
 * @date 2019/12/7 0007 23:28
 */
@SpringBootApplication(scanBasePackages = GlobalConstants.BASE_PACKAGE)
@MapperScan(basePackages = {"com.ddf.boot.quick.mapper", "com.ddf.boot.common.websocket.mapper"})
@EnableJwt
@EnableLogAspect(slowTime = 3000)
public class QuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickApplication.class, args);
    }
}
