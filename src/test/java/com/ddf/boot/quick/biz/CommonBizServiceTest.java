package com.ddf.boot.quick.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.quick.QuickApplicationTest;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.service.ISysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试类
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 20:32
 */
public class CommonBizServiceTest extends QuickApplicationTest {

    @Autowired
    private ISysUserService sysUserService;

    @Test
    public void testLogicDelete() {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getLoginName, "chen");
        sysUserService.getOne(wrapper);


        LambdaUpdateWrapper<SysUser> wrapper1 = Wrappers.lambdaUpdate();
        wrapper1.eq(SysUser::getLoginName, "chen");
        sysUserService.remove(wrapper1);
        System.out.println("wrapper1 = " + wrapper1);
    }
}
