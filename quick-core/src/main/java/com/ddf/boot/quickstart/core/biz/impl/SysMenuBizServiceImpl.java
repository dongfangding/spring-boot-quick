package com.ddf.boot.quickstart.core.biz.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.quickstart.api.consts.BootConstants;
import com.ddf.boot.quickstart.api.dto.SysMenuDTO;
import com.ddf.boot.quickstart.api.request.sys.SysMenuCreateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysMenuUpdateRequest;
import com.ddf.boot.quickstart.api.response.sys.SysMenuTreeResponse;
import com.ddf.boot.quickstart.core.biz.ISysMenuBizService;
import com.ddf.boot.quickstart.core.common.exception.BizCode;
import com.ddf.boot.quickstart.core.convert.SysMenuConverter;
import com.ddf.boot.quickstart.core.entity.SysMenu;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.helper.SysUserHelper;
import com.ddf.boot.quickstart.core.service.ISysMenuService;
import com.ddf.boot.quickstart.core.util.TreeUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/26 10:09
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysMenuBizServiceImpl implements ISysMenuBizService {

    private final ISysMenuService sysMenuService;

    private final SysUserHelper sysUserHelper;

    /**
     * 创建系统菜单
     *
     * @param request
     * @return
     */
    @Override
    public SysMenuDTO create(SysMenuCreateRequest request) {
        PreconditionUtil.checkArgument(
                Objects.isNull(sysMenuService.getByMenuName(request.getMenuName())), BizCode.MENU_NAME_REPEAT);
        if (!Objects.equals(BootConstants.MENU_ROOT_PARENT_ID, request.getParentId())) {
            PreconditionUtil.checkArgument(Objects.nonNull(sysMenuService.getByPrimaryKey(request.getParentId())),
                    BizCode.MENU_RECORD_NOT_EXIST
            );
        }
        SysMenu menu = SysMenuConverter.INSTANCE.convert(request);
        sysMenuService.insert(menu);
        menu = sysMenuService.getByPrimaryKey(menu.getId());

        final SysMenuDTO sysMenuDTO = SysMenuConverter.INSTANCE.convert(menu);
        final Map<String, SysUser> collectUserMap = sysUserHelper.getUserMap(sysMenuDTO);
        SysUser tempUser;
        if (CollectionUtil.isNotEmpty(collectUserMap)) {
            tempUser = collectUserMap.get(sysMenuDTO.getCreateBy());
            if (Objects.nonNull(tempUser)) {
                sysMenuDTO.setCreateByName(tempUser.getLoginName());
            }
            tempUser = collectUserMap.get(sysMenuDTO.getModifyBy());
            if (Objects.nonNull(tempUser)) {
                sysMenuDTO.setModifyByName(tempUser.getLoginName());
            }
        }
        return sysMenuDTO;
    }

    /**
     * 更新菜单
     *
     * @param request
     * @return
     */
    @Override
    public SysMenuDTO update(SysMenuUpdateRequest request) {
        final SysMenu sysMenu = sysMenuService.getByPrimaryKey(request.getId());
        PreconditionUtil.checkArgument(Objects.nonNull(sysMenu), BizCode.MENU_RECORD_NOT_EXIST);

        if (Objects.equals(BootConstants.MENU_ROOT_PARENT_ID, request.getParentId())) {
            PreconditionUtil.checkArgument(Objects.nonNull(
                    sysMenuService.getByPrimaryKey(request.getParentId())), BizCode.MENU_RECORD_NOT_EXIST);
        }

        // 校验菜单名称不能重复
        final SysMenu menuNameMenu = sysMenuService.getByMenuName(request.getMenuName());
        PreconditionUtil.checkArgument(
                Objects.isNull(menuNameMenu) || !Objects.equals(sysMenu.getId(), menuNameMenu.getId()),
                BizCode.MENU_NAME_REPEAT
        );

        // 修改时只允许不允许修改菜单类型
        sysMenu.setParentId(request.getParentId());
        sysMenu.setMenuName(request.getMenuName());
        sysMenu.setSort(request.getSort());
        sysMenu.setRouteUrl(request.getRouteUrl());
        sysMenu.setIcon(request.getIcon());
        sysMenu.setPermission(request.getPermission());
        sysMenu.setIsActive(request.getIsActive());
        sysMenuService.update(sysMenu);
        return SysMenuConverter.INSTANCE.convert(sysMenu);
    }

    /**
     * 构建菜单树
     *
     * @return
     */
    @Override
    public List<SysMenuTreeResponse> buildMenuTree() {
        final List<SysMenu> all = sysMenuService.listAll();
        if (CollectionUtil.isEmpty(all)) {
            return Collections.emptyList();
        }
        return TreeUtil.convertMenuTree(all);
    }
}
