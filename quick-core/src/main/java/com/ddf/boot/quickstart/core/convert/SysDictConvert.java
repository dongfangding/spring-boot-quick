package com.ddf.boot.quickstart.core.convert;

import com.ddf.boot.quickstart.api.dto.SysDictDTO;
import com.ddf.boot.quickstart.core.entity.SysDict;
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
public interface SysDictConvert {

    SysDictConvert INSTANCE = Mappers.getMapper(SysDictConvert.class);

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
