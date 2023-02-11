package com.ddf.boot.quickstart.core.convert;

import com.ddf.boot.quickstart.api.dto.SysMenuDTO;
import com.ddf.boot.quickstart.api.request.sys.SysMenuCreateRequest;
import com.ddf.boot.quickstart.api.response.sys.SysMenuTreeResponse;
import com.ddf.boot.quickstart.core.entity.SysMenu;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 系统菜单相关转换器
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:46
 */
@Mapper
public interface SysMenuConverter {

    SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);

    /**
     * 系统菜单创建请求参数转换为实体
     *
     * @param request
     * @return
     */
    @Mappings({})
    SysMenu convert(SysMenuCreateRequest request);

    /**
     * 菜单实体对象转换为传输类对象
     *
     * @param sysMenu
     * @return
     */
    @Mappings({})
    SysMenuDTO convert(SysMenu sysMenu);

    /**
     * 转换为树形实体
     *
     * @param sysMenu
     * @return
     */
    @Mappings({})
    SysMenuTreeResponse convertTreeResponse(SysMenu sysMenu);

    /**
     * 转换为树形实体
     *
     * @param sysMenu
     * @return
     */
    @Mappings({})
    List<SysMenuTreeResponse> convertTreeResponse(List<SysMenu> sysMenu);
}
