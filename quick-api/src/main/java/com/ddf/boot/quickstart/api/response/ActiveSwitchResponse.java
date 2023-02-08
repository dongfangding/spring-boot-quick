package com.ddf.boot.quickstart.api.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>状态切换之后响应， 返回最新值，可以避免前端重新请求数据， 而只要更新这个字段即可</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/13 19:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveSwitchResponse implements Serializable {

    private static final long serialVersionUID = 7709073043245096849L;

    /**
     * 最新状态值
     */
    private Integer newStatus;

    /**
     * 构建对象
     *
     * @param newStatus
     * @return
     */
    public static ActiveSwitchResponse of(Integer newStatus) {
        final ActiveSwitchResponse response = new ActiveSwitchResponse();
        response.setNewStatus(newStatus);
        return response;
    }
}
