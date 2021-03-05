package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysConfig;

/**
 * <p>
 * 配置表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 根据主键获取记录
     *
     * @param id
     * @return
     */
    default SysConfig getByPrimaryKey(Long id) {
        return getById(id);
    }

    /**
     * 新增记录
     *
     * @param sysConfig
     * @return
     */
    boolean insert(SysConfig sysConfig);

    /**
     * 更新记录
     * @param sysConfig
     * @return
     */
    boolean update(SysConfig sysConfig);

    /**
     * 根据配置代码获取配置
     *
     * @param configCode
     * @return
     */
    SysConfig getConfigByCode(String configCode);



}
