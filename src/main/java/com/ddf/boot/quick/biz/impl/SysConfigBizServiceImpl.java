package com.ddf.boot.quick.biz.impl;

import com.ddf.boot.common.core.enumration.CommonLogic;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.quick.biz.ISysConfigBizService;
import com.ddf.boot.quick.common.exception.BizCode;
import com.ddf.boot.quick.model.entity.SysConfig;
import com.ddf.boot.quick.model.request.SysConfigCreateRequest;
import com.ddf.boot.quick.model.request.SysConfigUpdateRequest;
import com.ddf.boot.quick.model.response.SysConfigConsoleResponse;
import com.ddf.boot.quick.service.ISysConfigService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/05 22:51
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysConfigBizServiceImpl implements ISysConfigBizService {

    private final ISysConfigService sysConfigService;

    /**
     * 新增配置
     *
     * @param request
     * @return
     */
    @Override
    public SysConfigConsoleResponse create(SysConfigCreateRequest request) {
        PreconditionUtil.checkArgument(Objects.isNull(sysConfigService.getConfigByCode(request.getConfigCode())),
                BizCode.CONFIG_CODE_REPEAT);
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigCode(request.getConfigCode());
        sysConfig.setConfigValue(request.getConfigValue());
        sysConfig.setIsEditable(CommonLogic.TRUE.getLogic());
        sysConfigService.insert(sysConfig);
        
        return null;
    }

    /**
     * 修改配置
     *
     * @param request
     * @return
     */
    @Override
    public SysConfigConsoleResponse update(SysConfigUpdateRequest request) {
        return null;
    }
}
