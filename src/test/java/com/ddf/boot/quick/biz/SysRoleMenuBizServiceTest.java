package com.ddf.boot.quick.biz;

import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import com.ddf.boot.quick.model.request.SysRoleMenuBuildRoleMenuRequest;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:47
 */
public class SysRoleMenuBizServiceTest extends QuickApplicationTest {

    @Autowired
    private ISysRoleMenuBizService sysRoleMenuBizService;


    /**
     * 
     * 测试角色菜单树
     */
    @Test
    public void testBuildRoleMenuTree() {
        final SysRoleMenuBuildRoleMenuRequest request = new SysRoleMenuBuildRoleMenuRequest();
        request.setRoleId(25L);
        System.out.println(JsonUtil.asString(sysRoleMenuBizService.buildRoleMenuTree(request)));
    }

    /**
     * 测试给角色授权
     *
     */
    @Test
    public void testAuthorization() {
        final SysRoleMenuAuthorizationRequest request = new SysRoleMenuAuthorizationRequest();
        request.setRoleId(25L);
        request.setMenuIds(Sets.newHashSet(1L, 2L, 4L, 5L));
        System.out.println(sysRoleMenuBizService.authorization(request));
    }

    @Test
    public void testBuildUserMenuTree() {
        System.out.println(JsonUtil.asString(sysRoleMenuBizService.buildUserMenuTree("1363788686914756639")));
        System.out.println(JsonUtil.asString(sysRoleMenuBizService.buildUserMenuTree("1364033536494956642")));
    }
}
