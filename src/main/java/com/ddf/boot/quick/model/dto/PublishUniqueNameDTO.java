package com.ddf.boot.quick.model.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>发布添加唯一名称事件</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/07/19 16:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishUniqueNameDTO implements Serializable {

    private static final long serialVersionUID = 5838919803056838896L;

    private String uniqueName;

    private Date bizTime;
}
