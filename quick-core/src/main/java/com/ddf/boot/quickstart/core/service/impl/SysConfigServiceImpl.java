package com.ddf.boot.quickstart.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quickstart.core.entity.SysConfig;
import com.ddf.boot.quickstart.core.mapper.SysConfigMapper;
import com.ddf.boot.quickstart.core.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    /**
     * 新增记录
     *
     * @param sysConfig
     * @return
     */
    @Override
    public boolean insert(SysConfig sysConfig) {
        return super.save(sysConfig);
    }

    /**
     * 更新记录
     *
     * @param sysConfig
     * @return
     */
    @Override
    public boolean update(SysConfig sysConfig) {
        return super.updateById(sysConfig);
    }

    /**
     * 根据配置代码获取配置
     *
     * @param configCode
     * @return
     */
    @Override
    public SysConfig getConfigByCode(String configCode) {
        final LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysConfig::getConfigCode, configCode);
        return getOne(wrapper);
    }
}
