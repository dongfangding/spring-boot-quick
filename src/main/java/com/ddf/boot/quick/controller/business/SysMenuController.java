package com.ddf.boot.quick.controller.business;

import com.ddf.boot.quick.biz.ISysMenuBizService;
import com.ddf.boot.quick.model.dto.SysMenuDTO;
import com.ddf.boot.quick.model.request.SysMenuCreateRequest;
import com.ddf.boot.quick.model.request.SysMenuUpdateRequest;
import com.ddf.boot.quick.model.response.SysMenuTreeResponse;
import java.util.List;
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
 * 菜单表 前端控制器
 * </p>
 *
 * @menu 权限管理
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@RestController
@RequestMapping("/sysMenu")
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysMenuController {

    private final ISysMenuBizService sysMenuBizService;


    /**
     * 创建系统菜单
     *
     * @param request
     * @return
     */
    @PostMapping("create")
    public SysMenuDTO create(@RequestBody @Validated SysMenuCreateRequest request) {
        return sysMenuBizService.create(request);
    }

    /**
     * 更新菜单
     *
     * @param request
     * @return
     */
    @PostMapping("update")
    public SysMenuDTO update(@RequestBody @Validated SysMenuUpdateRequest request) {
        return sysMenuBizService.update(request);
    }

    /**
     * 构建菜单树
     *
     * @return
     */
    @PostMapping("buildMenuTree")
    public List<SysMenuTreeResponse> buildMenuTree() {
        return sysMenuBizService.buildMenuTree();
    }

}

