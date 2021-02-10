package com.ddf.boot.quick.converter.mapper;

import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.vo.CreateSysUserResponse;
import org.mapstruct.Mapper;
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
    CreateSysUserResponse convert(SysUser sysUser);

}
