package com.ddf.boot.quick.biz;

import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.core.model.PageRequest;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.request.CreateSysUserRequest;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.model.request.UpdateSysUserRequest;
import com.ddf.boot.quick.service.ISysUserService;
import com.google.common.collect.Sets;
import java.time.LocalDateTime;
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
    public void test() {
        CreateSysUserRequest request = new CreateSysUserRequest();
        request.setLoginName("chen" + RandomUtil.randomString(6));
        request.setNickname("村里还没通网" + RandomUtil.randomString(6));
        request.setPassword("123456");
        request.setRoleIdList(Sets.newHashSet(1L, 2L, 3L));
        request.setEmail("10@qq.com");
        request.setMobile("187" + RandomUtil.randomInt(1000000, 99999999));
        request.setSex(0);
        request.setAvatarUrl("");
        request.setBirthday(LocalDateTime.now());
        request.setHeight(0);
        request.setWeight(0);
        request.setWeiXin("");
        request.setQq("");
        SysUserDTO response = sysUserBizService.create(request);
        System.out.println("保存接口返回值: " + JsonUtil.asString(response));

        final UpdateSysUserRequest updateSysUserRequest = new UpdateSysUserRequest();
        updateSysUserRequest.setId(response.getId());
        updateSysUserRequest.setLoginName("修改" + response.getLoginName());
        updateSysUserRequest.setNickname("修改" + request.getNickname());
        updateSysUserRequest.setMobile("修改" + request.getMobile());
        updateSysUserRequest.setRoleIdList(Sets.newHashSet(1L, 3L, 5L));
        final SysUserDTO update = sysUserBizService.update(updateSysUserRequest);
        System.out.println("修改接口返回值: " + JsonUtil.asString(update));

        final SysUserPageRequest pageRequest = new SysUserPageRequest();
        pageRequest.setPage(PageRequest.DEFAULT_PAGE_NUM);
        pageRequest.setPageSize(PageRequest.DEFAULT_PAGE_SIZE);
        pageRequest.setLoginName("修改");
        pageRequest.setNickname("修改");
        final PageResult<SysUserDTO> result = sysUserBizService.pageList(pageRequest);
        System.out.println("分页列表接口返回值: " + JsonUtil.asString(result));
    }

}
