SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `id`           bigint                                 NOT NULL AUTO_INCREMENT,
    `config_code`  varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置代码',
    `config_value` json                                   NOT NULL COMMENT '配置json串',
    `remark`       varchar(255) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '备注',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    `create_time`  timestamp                              NULL                  DEFAULT NULL COMMENT '创建时间',
    `modify_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
    `modify_time`  timestamp                              NULL                  DEFAULT NULL COMMENT '修改时间',
    `is_del`       int                                    NOT NULL              DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`      bigint                                 NOT NULL              DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='配置表';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`             bigint                                 NOT NULL AUTO_INCREMENT,
    `dict_code`      varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典代码',
    `dict_name`      varchar(32) COLLATE utf8mb4_general_ci NOT NULL              DEFAULT '' COMMENT '字典名称',
    `dict_item_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL              DEFAULT '' COMMENT '字典明细代码',
    `dict_item_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典明细名称',
    `sort`           int                                    NOT NULL              DEFAULT '0' COMMENT '排序',
    `create_by`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    `create_time`    timestamp                              NULL                  DEFAULT NULL COMMENT '创建时间',
    `modify_by`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
    `modify_time`    timestamp                              NULL                  DEFAULT NULL COMMENT '修改时间',
    `is_del`         int                                    NOT NULL              DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`        bigint                                 NOT NULL              DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='字典表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint                                 NOT NULL COMMENT '父级菜单id, 0为一级菜单',
    `menu_name`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `sort`        int                                    NOT NULL              DEFAULT '0' COMMENT '排序',
    `route_url`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由地址',
    `menu_type`   int                                    NOT NULL              DEFAULT '0' COMMENT '0 菜单 1 按钮',
    `icon`        varchar(255) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '图标',
    `permission`  varchar(255) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '权限标识，设计之初是使用目标方法的path进行映射， 如一个菜单下有多个接口地址，可以使用逗号分隔，如果分属一个控制器下，可以使用通配符；如/menu/**,',
    `is_active`   int                                    NOT NULL              DEFAULT '1' COMMENT '是否激活 0 否 1 是',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp                              NULL                  DEFAULT NULL COMMENT '创建时间',
    `modify_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
    `modify_time` timestamp                              NULL                  DEFAULT NULL COMMENT '修改时间',
    `is_del`      int                                    NOT NULL              DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`     bigint                                 NOT NULL              DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='菜单表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(32) COLLATE utf8mb4_general_ci NOT NULL              DEFAULT '' COMMENT '角色名称',
    `sort`        int                                    NOT NULL              DEFAULT '0' COMMENT '排序',
    `is_admin`    int(255)                               NOT NULL              DEFAULT 0 COMMENT '是否超管，超管拥有全部权限，但不能通过系统创建 0 否 1是',
    `is_active`   int                                    NOT NULL              DEFAULT '1' COMMENT '是否激活 0 否 1 是',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp                              NULL                  DEFAULT NULL COMMENT '创建时间',
    `modify_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
    `modify_time` timestamp                              NULL                  DEFAULT NULL COMMENT '修改时间',
    `is_del`      int                                    NOT NULL              DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`     bigint                                 NOT NULL              DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`          bigint    NOT NULL AUTO_INCREMENT,
    `role_id`     bigint    NOT NULL COMMENT '角色id',
    `menu_id`     bigint    NOT NULL COMMENT '菜单id',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp NULL                                               DEFAULT NULL COMMENT '创建时间',
    `modify_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
    `modify_time` timestamp NULL                                               DEFAULT NULL COMMENT '修改时间',
    `is_del`      int       NOT NULL                                           DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`     bigint    NOT NULL                                           DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色与菜单关联表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                  bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '仅作主键用，不进行关联使用',
    `user_id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户id, 系统内部关联使用',
    `login_name`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '登陆名',
    `nickname`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '昵称',
    `password`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `status`              int                                                                    DEFAULT '0' COMMENT '用户状态 0 正常 1 停用',
    `last_login_ip`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '最后登陆ip',
    `last_login_time`     timestamp                                                     NULL     DEFAULT NULL COMMENT '最后登陆时间',
    `last_pwd_reset_time` timestamp                                                     NULL     DEFAULT NULL COMMENT '最后一次修改密码时间',
    `email`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '邮箱',
    `mobile`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '手机号',
    `sex`                 int                                                                    DEFAULT '0' COMMENT '性别 0 未知  1 男性 2 女性',
    `avatar_url`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '头像地址',
    `avatar_short_url`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '头像缩略图',
    `birthday`            timestamp                                                     NULL     DEFAULT NULL COMMENT '出生日期',
    `height`              int                                                                    DEFAULT NULL COMMENT '身高，单位cm',
    `weight`              int                                                                    DEFAULT NULL COMMENT '体重，单位kg',
    `wei_xin`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '微信号',
    `qq`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT 'QQ号',
    `create_by`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '创建人id',
    `create_time`         timestamp                                                     NULL     DEFAULT NULL COMMENT '创建时间',
    `modify_by`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '修改人id',
    `modify_time`         timestamp                                                     NULL     DEFAULT NULL COMMENT '修改时间',
    `is_del`              int                                                           NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`             bigint                                                        NOT NULL DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE,
    KEY `idx_user_pass` (`login_name`, `password`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统用户表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          bigint    NOT NULL AUTO_INCREMENT,
    `user_id`     bigint    NOT NULL COMMENT '用户id',
    `role_id`     bigint    NOT NULL COMMENT '角色id',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp NULL                                               DEFAULT NULL COMMENT '创建时间',
    `modify_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
    `modify_time` timestamp NULL                                               DEFAULT NULL COMMENT '修改时间',
    `is_del`      int       NOT NULL                                           DEFAULT '0' COMMENT '是否删除 0 否 1 是',
    `version`     bigint    NOT NULL                                           DEFAULT '1' COMMENT '数据版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户-角色关联表';
