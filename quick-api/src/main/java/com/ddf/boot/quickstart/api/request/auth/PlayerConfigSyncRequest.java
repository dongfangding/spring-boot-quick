package com.ddf.boot.quickstart.api.request.auth;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>玩家配置同步请求</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerConfigSyncRequest implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 配置内容
     */
    private String configValue;
}
