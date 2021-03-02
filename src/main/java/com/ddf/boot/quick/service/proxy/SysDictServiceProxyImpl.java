package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.SysDictMapper;
import com.ddf.boot.quick.model.entity.SysDict;
import com.ddf.boot.quick.service.ISysDictService;

/**
 * <p>字典层代理类， 用来加缓存处理等功能</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 22:08
 */
public class SysDictServiceProxyImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
}
