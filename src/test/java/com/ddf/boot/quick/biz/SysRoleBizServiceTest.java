package com.ddf.boot.quick.biz;

import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.core.model.PageRequest;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.model.UserClaim;
import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.common.core.util.UserContextUtil;
import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.model.dto.SysRoleDTO;
import com.ddf.boot.quick.model.request.SysRoleCreateRequest;
import com.ddf.boot.quick.model.request.SysRolePageRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/22 11:54
 */
public class SysRoleBizServiceTest extends QuickApplicationTest {

    @Autowired
    private ISysRoleBizService sysRoleBizService;

    @Test
    public void test() {
        UserContextUtil.setUserClaim(new UserClaim("1", "chen"));

        // 保存
        final SysRoleCreateRequest request = new SysRoleCreateRequest();
        request.setRoleName("角色名称" + RandomUtil.randomString(5));
        request.setSort(10);
        SysRoleDTO dto = sysRoleBizService.saveOrUpdate(request);
        System.out.println("保存接口返回值: " + JsonUtil.asString(dto));

        // 修改
        request.setId(dto.getId());
        request.setRoleName("修改" + dto.getRoleName());
        dto = sysRoleBizService.saveOrUpdate(request);
        System.out.println("修改接口返回值: " + JsonUtil.asString(dto));

        // 分页查询
        final SysRolePageRequest pageRequest = new SysRolePageRequest();
        pageRequest.setPageNum(PageRequest.DEFAULT_PAGE_NUM);
        pageRequest.setPageSize(PageRequest.DEFAULT_PAGE_SIZE);
        pageRequest.setRoleName("修改角色名称");
        final PageResult<SysRoleDTO> result = sysRoleBizService.pageList(pageRequest);
        System.out.println("分页接口返回值: " + JsonUtil.asString(result));
    }
}
