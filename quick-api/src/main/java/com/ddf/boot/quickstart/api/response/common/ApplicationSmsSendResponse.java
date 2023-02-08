package com.ddf.boot.quickstart.api.response.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 19:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSmsSendResponse implements Serializable {

    private static final long serialVersionUID = -2432015599266630430L;

    /**
     * 表单id， 回传这个服务端来验证验证码
     */
    private String uuid;
}
