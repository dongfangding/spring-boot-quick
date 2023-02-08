package com.ddf.boot.quickstart.api.request.sys;

import com.ddf.boot.common.api.model.common.PageRequest;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>系统用户分页查询</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 16:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPageRequest implements PageRequest {

    /**
     * 页数, 从1开始
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = 1;

    /**
     * 每页显示条数
     */
    @Min(value = 1, message = "每页显示条数不能小于1")
    private Integer pageSize = 20;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickname;

}
