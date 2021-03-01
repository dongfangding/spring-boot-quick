package com.ddf.boot.quick.model.response;

import com.ddf.boot.quick.model.dto.SysMenuDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * <p>菜单树</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/01 22:24
 */
@Data
public class SysMenuTreeResponse {

    /**
     * 当前节点
     */
    private SysMenuDTO currentNode;

    /**
     * 子节点
     */
    private List<SysMenuDTO> children = new ArrayList<>();
}
