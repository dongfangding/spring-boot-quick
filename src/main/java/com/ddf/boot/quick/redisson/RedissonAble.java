package com.ddf.boot.quick.redisson;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2020/12/17 0017 23:23
 */
@Data
@AllArgsConstructor
public class RedissonAble implements BucketAble, Serializable {

    private String name;

    private String type;

    private Integer used;
}
