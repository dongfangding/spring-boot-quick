package com.ddf.boot.quickstart.core.convert;

import com.ddf.boot.quickstart.api.response.sys.SysDictResponse;
import com.ddf.boot.quickstart.core.entity.SysDict;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/08/29 16:57
 */
@Mapper
public interface CommonConverter {

    CommonConverter INSTANCE = Mappers.getMapper(CommonConverter.class);


    @Mappings({
//            @Mapping(source = "dictDetailCode", target="dictDetailCode")
    })
    SysDictResponse convert(SysDict sysDict);

    @Mappings({
    })
    List<SysDictResponse> convert(List<SysDict> sysDictList);
}
