package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.SysDictMapper;
import com.ddf.boot.quick.model.entity.SysDict;
import com.ddf.boot.quick.service.ISysDictService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>字典层代理类， 用来加缓存处理等功能</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 22:08
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
@Primary
public class SysDictServiceProxyImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final ISysDictService sysDictServiceImpl;

    /**
     * 根据字典代码获取字典明细数据
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<SysDict> getDictByCode(String dictCode) {
        return sysDictServiceImpl.getDictByCode(dictCode);
    }
}
