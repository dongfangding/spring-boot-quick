package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.core.ApplicationTest;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/21 19:27
 */
@Slf4j
public class UserInfoRepositoryTest extends ApplicationTest {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Test
    public void testListUserInfo() {
        while (true) {
            try {
                final Map<Long, UserInfo> map = userInfoRepository.listUserInfoMapFromCache(
                        Lists.newArrayList(1621123535259791363L, 1621123535259791362L, 3L));
                System.out.println("map = " + map);
            } catch (Exception e) {
                log.error("eeee", e);
            }
        }
    }

}
