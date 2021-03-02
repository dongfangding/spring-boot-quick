package com.ddf.boot.quick.converter.mapper;

import com.ddf.boot.quick.model.dto.SysMenuDTO;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.model.request.SysMenuCreateRequest;
import com.ddf.boot.quick.model.response.SysMenuTreeResponse;
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
public interface SysMenuConverterMapper {

    SysMenuConverterMapper INSTANCE = Mappers.getMapper(SysMenuConverterMapper.class);

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
