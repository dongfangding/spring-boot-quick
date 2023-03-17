CREATE DATABASE IF NOT EXISTS boot_quick DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


use boot_quick;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for global_metadata_config
-- ----------------------------
DROP TABLE IF EXISTS `global_metadata_config`;
CREATE TABLE `global_metadata_config`
(
    `id`           bigint(20)                             NOT NULL AUTO_INCREMENT,
    `config_code`  varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置代码',
    `config_value` text COLLATE utf8mb4_unicode_ci        NOT NULL COMMENT '配置值',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_config` (`config_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='服务配置中心';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`               bigint(20)  NOT NULL AUTO_INCREMENT,
    `dict_type_code`   varchar(64) NOT NULL DEFAULT '' COMMENT '字典类型',
    `dict_type_name`   varchar(64) NOT NULL DEFAULT '' COMMENT '字典名称\n',
    `dict_detail_code` varchar(64) NOT NULL DEFAULT '' COMMENT '字典明细代码\n',
    `dict_detail_name` varchar(64) NOT NULL DEFAULT '' COMMENT '字段明细名称',
    `request_value`    varchar(64) NOT NULL DEFAULT '' COMMENT '请求参数，比如这个字段后台映射为枚举的时候，那么字典是用来渲染的，但是请求的时候却是要用对应属性的枚举名，就是这个字段',
    `sort`             int(11)     NOT NULL DEFAULT '0' COMMENT '排序',
    `active`           varchar(64) NOT NULL DEFAULT '1' COMMENT '是否有效\n',
    PRIMARY KEY (`id`),
    KEY `IDX_dict` (`dict_type_code`, `dict_detail_code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT,
    `nickname`         varchar(32)  NOT NULL DEFAULT '' COMMENT '昵称',
    `mobile`           varchar(11)  NOT NULL DEFAULT '' COMMENT '手机号',
    `password`         varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
    `register_ip`      varchar(32)  NOT NULL DEFAULT '' COMMENT '注册ip',
    `register_imei`    varchar(128) NOT NULL DEFAULT '' COMMENT '注册设备',
    `temp_email`       varchar(64)  NOT NULL DEFAULT '' COMMENT '临时邮箱， 当邮箱未验证时存储在这个字段，当验证通过，再复制给正式邮箱字段，这样使用时主要关心email字段即可',
    `email`            varchar(64)  NOT NULL DEFAULT '' COMMENT '邮箱',
    `avatar_url`       varchar(128) NOT NULL DEFAULT '' COMMENT '头像地址url',
    `avatar_thumb_url` varchar(128) NOT NULL DEFAULT '' COMMENT '头像地址缩略图url',
    `ctime`            bigint(20)   NOT NULL COMMENT '注册时间，秒时间戳',
    `status`           varchar(16)  NOT NULL DEFAULT '' COMMENT '用户状态',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1621123535259791362
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息';

-- ----------------------------
-- Table structure for user_login_history
-- ----------------------------
DROP TABLE IF EXISTS `user_login_history`;
CREATE TABLE `user_login_history`
(
    `id`         bigint(20)                                                   NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(20)                                                   NOT NULL COMMENT '用户id',
    `login_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录方式',
    `login_ip`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '登录Ip',
    `login_time` bigint(20)                                                   NOT NULL COMMENT '登录时间',
    `imei`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '设备号',
    `os`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '系统',
    `longitude`  decimal(10, 6)                                               NOT NULL DEFAULT '0.000000' COMMENT '经度',
    `latitude`   decimal(10, 6)                                               NOT NULL DEFAULT '0.000000' COMMENT '纬度',
    `version`    int(11)                                                      NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`),
    KEY `IDX_user` (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1621146080369430532
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户登录历史';

-- ----------------------------
-- Table structure for user_metadata_config
-- ----------------------------
DROP TABLE IF EXISTS `user_metadata_config`;
CREATE TABLE `user_metadata_config`
(
    `id`           bigint(20)                             NOT NULL AUTO_INCREMENT,
    `user_id`      bigint(20)                             NOT NULL COMMENT '用户id',
    `config_code`  varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置代码',
    `config_value` text COLLATE utf8mb4_unicode_ci        NOT NULL COMMENT '配置明细',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_u_config` (`user_id`, `config_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 616764
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户数据配置表';

SET FOREIGN_KEY_CHECKS = 1;
