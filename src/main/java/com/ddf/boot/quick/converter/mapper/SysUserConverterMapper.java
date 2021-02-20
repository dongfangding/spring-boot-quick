package com.ddf.boot.quick.converter.mapper;

import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.request.CreateSysUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户相关转换器
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:46
 */
@Mapper
public interface SysUserConverterMapper {

    SysUserConverterMapper INSTANCE = Mappers.getMapper(SysUserConverterMapper.class);

    /**
     * 将用户信息转换为创建用户时的新增对象属性返回
     *
     * @param sysUser
     * @return
     */
    @Mappings({
        @Mapping(target = "statusName", expression = "java(com.ddf.boot.quick.constants.enumration.SysUserStatusEnum.instanceOfCodeDefaultUnknown(sysUser.getStatus()).getDesc())")
    })
    SysUserDTO convert(SysUser sysUser);

    /**
     *
     * 创建系统用户请求对象转换为系统用户实体对象
     *
     * @param request
     * @return
     */
    @Mappings({})
    SysUser requestConvert(CreateSysUserRequest request);
}
