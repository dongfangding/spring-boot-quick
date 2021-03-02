package com.ddf.boot.quick.biz;

import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.constants.BootConstants;
import com.ddf.boot.quick.constants.enumration.SysMenuTypeEnum;
import com.ddf.boot.quick.model.dto.SysMenuDTO;
import com.ddf.boot.quick.model.request.SysMenuCreateRequest;
import com.ddf.boot.quick.model.request.SysMenuUpdateRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 11:49
 */
public class SysMenuBizServiceTest extends QuickApplicationTest {
    @Autowired
    private ISysMenuBizService sysMenuBizService;

    @Test
    public void testCreate() {
        final SysMenuCreateRequest request = new SysMenuCreateRequest();
        request.setMenuName("权限管理");
        request.setParentId(BootConstants.MENU_ROOT_PARENT_ID);
        request.setMenuType(SysMenuTypeEnum.DIRECTION.getCode());
        request.setRouteUrl("route_url");
        request.setIcon("icon");
        final SysMenuDTO dto = sysMenuBizService.create(request);
        System.out.println("保存接口返回值: " + JsonUtil.asString(dto));


        final SysMenuUpdateRequest updateRequest = new SysMenuUpdateRequest();
        updateRequest.setId(dto.getId());
        updateRequest.setMenuName(dto.getMenuName() + "修改");
        updateRequest.setSort(100);
        final SysMenuDTO update = sysMenuBizService.update(updateRequest);
        System.out.println("修改接口返回值: " + JsonUtil.asString(update));
    }

    /**
     * 测试菜单树
     *
     */
    @Test
    public void testBuildMenuTree() {
        System.out.println(JsonUtil.asString(sysMenuBizService.buildMenuTree()));
    }
}
