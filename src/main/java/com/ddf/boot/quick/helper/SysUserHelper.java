package com.ddf.boot.quick.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.core.constant.IUserIdCollection;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.service.ISysUserService;
import groovy.util.logging.Slf4j;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/22 10:54
 */
@Component
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysUserHelper {

    private final ISysUserService sysUserService;

    /**
     * 根据用户标记信息收集系统用户
     *
     * @param userIdCollection
     * @return
     */
    public Map<String, SysUser> getUserMap(IUserIdCollection userIdCollection) {
        return getUserMap(Collections.singletonList(userIdCollection));
    }

    /**
     * 根据用户标记信息收集系统用户
     *
     * @param userIdCollections
     * @return
     */
    public <E extends IUserIdCollection> Map<String, SysUser> getUserMap(List<E> userIdCollections) {
        Set<String> ids = new HashSet<>(userIdCollections.size());
        for (IUserIdCollection collection : userIdCollections) {
            ids.addAll(collection.getUserIds());
        }
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        final List<String> collect = ids.stream()
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            return Collections.emptyMap();
        }
        final List<SysUser> userList = sysUserService.getByUserIds(collect);
        return userList.stream().collect(Collectors.toMap(SysUser::getUserId, val -> val));
    }
}
