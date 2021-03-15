package com.ddf.boot.quick.converter.mapper;

import com.ddf.boot.quick.model.dto.SysDictDTO;
import com.ddf.boot.quick.model.entity.SysDict;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>字典相关转换类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/15 22:18
 */
@Mapper
public interface SysDictConvertMapper {

    SysDictConvertMapper INSTANCE = Mappers.getMapper(SysDictConvertMapper.class);

    /**
     * 转换
     *
     * @param sysDict
     * @return
     */
    @Mappings({})
    SysDictDTO convert(SysDict sysDict);

    /**
     * 转换
     *
     * @param sysDict
     * @return
     */
    @Mappings({})
    List<SysDictDTO> convert(List<SysDict> sysDict);
}
