package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.SysConfigMapper;
import com.ddf.boot.quick.model.entity.SysConfig;
import com.ddf.boot.quick.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>配置类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/05 22:35
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
@Primary
public class SysConfigServiceProxyImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    private final ISysConfigService sysConfigServiceImpl;

    /**
     * 新增记录
     *
     * @param sysConfig
     * @return
     */
    @Override
    public boolean insert(SysConfig sysConfig) {
        return sysConfigServiceImpl.insert(sysConfig);
    }

    /**
     * 更新记录
     *
     * @param sysConfig
     * @return
     */
    @Override
    public boolean update(SysConfig sysConfig) {
        return sysConfigServiceImpl.update(sysConfig);
    }

    /**
     * 根据配置代码获取配置
     *
     * @param configCode
     * @return
     */
    @Override
    public SysConfig getConfigByCode(String configCode) {
        return sysConfigServiceImpl.getConfigByCode(configCode);
    }
}
