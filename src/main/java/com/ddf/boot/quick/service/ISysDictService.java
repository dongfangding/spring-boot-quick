package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysDict;
import java.util.List;

/**
 * <p>
 * 字典表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 根据字典代码获取字典明细数据
     *
     * @param dictCode
     * @return
     */
    List<SysDict> getDictByCode(String dictCode);
}
