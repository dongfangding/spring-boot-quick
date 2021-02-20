package com.ddf.boot.quick.converter.mapper;

import com.ddf.boot.quick.model.dto.SysRoleDTO;
import com.ddf.boot.quick.model.entity.SysRole;
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
    SysRoleDTO convert(SysRole sysRole);
}
