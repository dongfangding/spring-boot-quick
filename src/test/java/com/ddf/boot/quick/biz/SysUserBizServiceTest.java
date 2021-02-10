package com.ddf.boot.quick.biz;
import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.quick.model.vo.CreateSysUserResponse;
import java.time.LocalDateTime;
import com.google.common.collect.Sets;

import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.model.bo.CreateSysUserRequest;
import com.ddf.boot.quick.service.ISysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dongfang.ding
 * @date 2021/2/10 0010 20:32
 */
public class SysUserBizServiceTest extends QuickApplicationTest {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserBizService sysUserBizService;

    @Test
    public void testCreateSysUser() {
        CreateSysUserRequest request = new CreateSysUserRequest();
        request.setLoginName("chen11");
        request.setNickname("村里还没通网");
        request.setPassword("123456");
        request.setRoleIdList(Sets.newHashSet("1", "2", "3"));
        request.setEmail("10@qq.com");
        request.setMobile("187");
        request.setSex(0);
        request.setAvatarUrl("");
        request.setBirthday(LocalDateTime.now());
        request.setHeight(0);
        request.setWeight(0);
        request.setWeiXin("");
        request.setQq("");
        CreateSysUserResponse response = sysUserBizService.create(request);
        System.out.println(JsonUtil.asString(response));
    }

}