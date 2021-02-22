package com.ddf.boot.quick.controller.business;

import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.biz.ISysRoleBizService;
import com.ddf.boot.quick.model.dto.SysRoleDTO;
import com.ddf.boot.quick.model.request.CreateSysRoleRequest;
import com.ddf.boot.quick.model.request.SysRolePageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@RestController
@RequestMapping("/sysRole")
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysRoleController {

    private final ISysRoleBizService sysRoleBizService;

    /**
     * 创建系统角色
     *
     * @param request
     * @return
     */
    @PostMapping("saveOrUpdate")
    public SysRoleDTO saveOrUpdate(@RequestBody @Validated CreateSysRoleRequest request) {
        return sysRoleBizService.saveOrUpdate(request);
    }

    /**
     * 系统角色分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("pageList")
    public PageResult<SysRoleDTO> pageList(@RequestBody @Validated SysRolePageRequest request) {
        return sysRoleBizService.pageList(request);
    }
}

