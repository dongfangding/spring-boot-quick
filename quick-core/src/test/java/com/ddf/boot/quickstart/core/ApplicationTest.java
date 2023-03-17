package com.ddf.boot.quickstart.core;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/21 19:28
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = Application.class, properties = {
        "server.port=8081",
        "xxx=xxx"
})
@Transactional
public class ApplicationTest {
}
