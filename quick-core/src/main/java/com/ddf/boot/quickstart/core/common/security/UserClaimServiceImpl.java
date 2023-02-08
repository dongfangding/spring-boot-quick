package com.ddf.boot.quickstart.core.common.security;


import cn.hutool.core.convert.Convert;
import com.ddf.boot.common.authentication.interfaces.UserClaimService;
import com.ddf.boot.common.authentication.model.UserClaim;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.service.ISysUserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 获取数据库最新用户信息$
 *
 * @author dongfang.ding
 * @date 2019/12/7 0007 15:57
 */
@Service
@Component
public class UserClaimServiceImpl implements UserClaimService {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 正常环境下能够获取到HttpServletRequest，但如果有些项目使用了RPC框架，请求被转发到另一个服务后，HttpServletRequest
     * 则无法正常获取，这里提供一个接口，使用者可以自行将对象放在一些上下文中；如RpcContext，则自己在对应的服务中按照自己 存入的方式再获取到
     *
     * @param request
     * @param host    客户端请求ip
     * @return void
     * @author dongfang.ding
     * @date 2019/12/7 0007 16:04
     **/
    @Override
    public void storeRequest(HttpServletRequest request, String host) {
    }

    /**
     * Jwt将token中的用户信息，传递给调用方，需要调用方实现这个接口来将数据库中的最新用户数据返回过来
     *
     * @param userClaim
     * @return
     */
    @Override
    public UserClaim getStoreUserInfo(UserClaim userClaim) {
        UserClaim dbUserClaim = new UserClaim();
        SysUser dbUser = sysUserService.getByUserId(userClaim.getUserId());
        // 将当前token对应的用户查询出来返回，给调用方用户将数据库数据和token进行比较
        dbUserClaim.setUserId(Convert.toStr(dbUser.getUserId())).setUsername(dbUser.getLoginName());
        return dbUserClaim;
    }

    /**
     * 验证通过后蒋用户信息放到spring-security上下文中
     *
     * @param userClaim
     * @return void
     * @author dongfang.ding
     * @date 2019/12/7 0007 15:58
     **/
    @Override
    public void afterVerifySuccess(UserClaim userClaim) {
    }
}
