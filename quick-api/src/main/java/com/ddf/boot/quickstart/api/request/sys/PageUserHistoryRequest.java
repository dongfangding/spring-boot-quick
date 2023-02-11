package com.ddf.boot.quickstart.api.request.sys;

import com.ddf.boot.common.api.model.common.PageRequest;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录日志分页查询参数$
 *
 * @author dongfang.ding
 * @date 2020/9/19 0019 14:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageUserHistoryRequest implements PageRequest {

    private static final long serialVersionUID = -8846320147721003035L;

    /**
     * 页数, 从1开始
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum;

    /**
     * 每页显示条数
     */
    @Min(value = 1, message = "每页显示条数不能小于1")
    private Integer pageSize;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;
}
