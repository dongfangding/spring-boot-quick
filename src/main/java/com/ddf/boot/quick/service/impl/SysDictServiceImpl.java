package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.core.model.BaseDomain;
import com.ddf.boot.quick.mapper.SysDictMapper;
import com.ddf.boot.quick.model.entity.SysDict;
import com.ddf.boot.quick.service.ISysDictService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    /**
     * 根据字典代码获取字典明细数据
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<SysDict> getDictByCode(String dictCode) {
        final LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getDictCode, dictCode)
                .eq(SysDict::getIsDel, BaseDomain.IS_DEL_LOGIC_VALID_VALUE)
                .orderByAsc(SysDict::getSort);
        return super.list(wrapper);
    }
}
