package com.ddf.boot.quickstart.core.mapper;

import com.ddf.boot.quickstart.api.dto.SysRoleDTO;
import com.ddf.boot.quickstart.core.entity.SysRole;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 16:30
 */
@Mapper
public interface SysRoleConvertMapper {

    SysRoleConvertMapper INSTANCE = Mappers.getMapper(SysRoleConvertMapper.class);


    /**
     * 角色转换
     *
     * @param sysRole
     * @return
     */
    @Mappings({})
    List<SysRoleDTO> convert(List<SysRole> sysRole);

    /**
     * 角色转换
     *
     * @param sysRole
     * @return
     */
    @Mappings({})
    SysRoleDTO convert(SysRole sysRole);
}
