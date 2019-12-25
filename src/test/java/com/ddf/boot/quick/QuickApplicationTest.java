package com.ddf.boot.quick;

import com.ddf.boot.quick.model.bo.AuthUserRegistryBo;
import com.ddf.boot.quick.service.AuthUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 12:02
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class QuickApplicationTest {

    @Autowired
    private AuthUserService authUserService;

    @Test
    public void test() {
        AuthUserRegistryBo authUserRegistryBo = new AuthUserRegistryBo();

        authUserRegistryBo.setUserName("ddf").setPassword("123456");

        authUserService.registry(authUserRegistryBo);
    }
}
