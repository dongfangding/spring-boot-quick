package com.ddf.boot.quickstart.core;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationTest {
}
