package com.ddf.boot.quickstart.api.response.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>玩家配置内容同步响应</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerConfigSyncResponse implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 配置内容
     */
    private String configValue;
}
