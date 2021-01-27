CREATE DATABASE IF NOT EXISTS `boot-quick` default charset utf8mb4 COLLATE utf8mb4_general_ci;

use `boot-quick`;

DROP TABLE IF EXISTS sys_user;

CREATE TABLE `boot-quick`.`sys_user`
(
    `id` bigint(0) NOT NULL COMMENT '仅作主键用，不进行关联使用',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id, 系统内部关联使用',
    `login_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登陆名',
    `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `status` int(0) NULL DEFAULT 0 COMMENT '用户状态 0 正常 1 停用',
    `last_login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登陆ip',
    `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
    `last_pwd_reset_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次修改密码时间',
    `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `sex` int(0) NULL DEFAULT 0 COMMENT '性别 0 未知  1 男性 2 女性',
    `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
    `avatar_short_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像缩略图',
    `birthday` timestamp(0) NULL DEFAULT NULL COMMENT '出生日期',
    `height` int(0) NULL DEFAULT NULL COMMENT '身高，单位cm',
    `weight` int(0) NULL DEFAULT NULL COMMENT '体重，单位kg',
    `wei_xin` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信号',
    `qq` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ号',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modify_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人id',
    `modify_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
    `is_del` int(0) NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
    `version` bigint(0) NOT NULL DEFAULT 1 COMMENT '数据版本号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_user_id`(`user_id`) USING BTREE,
    INDEX `idx_user_pass`(`login_name`, `password`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统用户表'
  ROW_FORMAT = Dynamic;