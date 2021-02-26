package com.ddf.boot.quick.biz.impl;

import com.ddf.boot.quick.biz.ISysMenuBizService;
import com.ddf.boot.quick.model.dto.SysMenuDTO;
import com.ddf.boot.quick.model.request.SysMenuCreateRequest;
import com.ddf.boot.quick.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/26 10:09
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysMenuBizServiceImpl implements ISysMenuBizService {

    private final ISysMenuService sysMenuService;

    /**
     * 创建系统菜单
     *
     * @param request
     * @return
     */
    @Override
    public SysMenuDTO create(SysMenuCreateRequest request) {
        return null;
    }
}
