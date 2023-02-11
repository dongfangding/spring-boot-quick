package com.ddf.boot.quickstart.core.biz.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.ddf.boot.quickstart.api.dto.SysDictDTO;
import com.ddf.boot.quickstart.api.request.sys.SysDictGetByCodeRequest;
import com.ddf.boot.quickstart.core.biz.ISysDictBizService;
import com.ddf.boot.quickstart.core.convert.SysDictConvert;
import com.ddf.boot.quickstart.core.entity.SysDict;
import com.ddf.boot.quickstart.core.service.ISysDictService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>字典业务实现类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/15 22:16
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysDictBizServiceImpl implements ISysDictBizService {

    private final ISysDictService sysDictService;

    /**
     * 根据字典代码获取字典明细数据
     *
     * @param request
     * @return
     */
    @Override
    public List<SysDictDTO> getDictByCode(SysDictGetByCodeRequest request) {
        final List<SysDict> response = sysDictService.getDictByCode(request.getDictCode());
        if (CollectionUtil.isEmpty(response)) {
            return Collections.emptyList();
        }
        return SysDictConvert.INSTANCE.convert(response);
    }
}
