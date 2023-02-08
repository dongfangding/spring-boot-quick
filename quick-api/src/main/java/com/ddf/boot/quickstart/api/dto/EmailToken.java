package com.ddf.boot.quickstart.api.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>邮箱token</p >
 *
 * @author snowball
 * @version 1.0
 * @date 2022/05/29 17:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmailToken implements Serializable {

    private static final long serialVersionUID = -288404597536320590L;

    /**
     * uid
     */
    private Long userId;

    /**
     * 邮箱
     */
    private String email;

}
