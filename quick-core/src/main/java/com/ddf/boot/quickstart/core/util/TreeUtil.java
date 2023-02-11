package com.ddf.boot.quickstart.core.util;

import cn.hutool.core.collection.CollectionUtil;
import com.ddf.boot.quickstart.api.consts.BootConstants;
import com.ddf.boot.quickstart.api.response.sys.SysMenuTreeResponse;
import com.ddf.boot.quickstart.core.convert.SysMenuConverter;
import com.ddf.boot.quickstart.core.entity.SysMenu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>构建树结构</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 11:33
 */
public class TreeUtil {


    /**
     * 构建全部菜单树， 且不处理任何选中节点
     *
     * @param menuList
     * @return
     */
    public static List<SysMenuTreeResponse> convertMenuTree(List<SysMenu> menuList) {
        return convertMenuTree(menuList, Collections.emptyList());
    }


    /**
     * 菜单列表转换为菜单树,
     *
     * @param selectMenuIds 要被选中的节点id集合
     * @param menuList      原数据
     * @return
     */
    public static List<SysMenuTreeResponse> convertMenuTree(List<SysMenu> menuList, List<Long> selectMenuIds) {
        if (CollectionUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        Map<Long, SysMenuTreeResponse> dataMap = new LinkedHashMap<>(menuList.size());
        for (SysMenu menu : menuList) {
            final SysMenuTreeResponse curr = SysMenuConverter.INSTANCE.convertTreeResponse(menu);
            if (CollectionUtil.isNotEmpty(selectMenuIds) && selectMenuIds.contains(curr.getId())) {
                curr.setSelected(Boolean.TRUE);
            }
            dataMap.put(menu.getId(), curr);
        }
        List<SysMenuTreeResponse> responseList = new ArrayList<>();
        for (Map.Entry<Long, SysMenuTreeResponse> entry : dataMap.entrySet()) {
            SysMenuTreeResponse currentNode = entry.getValue();
            if (Objects.equals(BootConstants.MENU_ROOT_PARENT_ID, currentNode.getParentId())) {
                responseList.add(currentNode);
            } else {
                if (Objects.nonNull(currentNode.getParentId())) {
                    if (Objects.nonNull(dataMap.get(currentNode.getParentId()))) {
                        dataMap.get(currentNode.getParentId())
                                .getChildren()
                                .add(currentNode);
                    }
                }
            }
        }
        return responseList;
    }
}
