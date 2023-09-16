package com.ddf.boot.quickstart.core.features.es.mapping;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * <p>description</p >
 *
 * 启动的时候才会去创建索引和分片相关数据，如果启动过程中修改， 并且节点有过变化， 会导致分片分配有问题，
 * 所以是否要采用手动创建索引和映射的方式呢？
 *
 * 开发步骤
 * 1. 创建索引，设置分片和副本，创建映射
 * 2. 可选创建索引alias，可以多个索引一个alias，查询时针对alias操作，用以支持后续如果更改mapping的情况，但是alias仅支持查询
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/10/13 14:31
 */
@Data
@Document(indexName = "sys_user")
@TypeAlias("alias_sys_user")
@Setting(shards = 5, replicas = 2)
public class EsSysUser implements Serializable {

    /**
     * 用户id, 系统内部关联使用
     */
    @Id
    private String userId;

    /**
     * 登陆名
     */
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String loginName;

    /**
     * 昵称
     */
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String nickname;

    /**
     * 邮箱
     */
    @Field(type = FieldType.Keyword)
    private String email;

    /**
     * 手机号
     */
    @Field(type = FieldType.Keyword)
    private String mobile;

    /**
     * 性别 0 未知  1 男性 2 女性
     */
    @Field(type = FieldType.Integer, index = false)
    private Integer sex;

    /**
     * 头像地址
     */
    @Field(type = FieldType.Keyword, index = false)
    private String avatarUrl;

    /**
     * 头像缩略图
     */
    @Field(type = FieldType.Keyword, index = false)
    private String avatarShortUrl;

}
