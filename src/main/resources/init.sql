
CREATE DATABASE IF NOT EXISTS `boot-quick` default charset utf8mb4 COLLATE utf8mb4_general_ci;

use `boot-quick`;

DROP TABLE IF EXISTS auth_user;

CREATE TABLE auth_user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID' AUTO_INCREMENT,
	user_name VARCHAR(30) NOT NULL COMMENT '姓名',
	user_token varchar(64) NOT NULL COMMENT '用户随机码，生成密钥的盐，注册时生成且不可变！',
	password VARCHAR(32) NOT NULL COMMENT '密码',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	birthday DATE NULL DEFAULT NULL COMMENT '生日',
    last_modify_password bigint  COMMENT '最后一次修改密码的时间',
    last_login_time bigint COMMENT '最后一次使用密码登录的时间',

	create_by VARCHAR(32) NULL,
	create_time DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	modify_by VARCHAR(32) NULL,
	modify_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	removed INT NOT NULL DEFAULT 0,
	version INT NOT NULL DEFAULT 1,

	PRIMARY KEY (id)

);

CREATE TABLE auth_user_1
(
    id BIGINT(20) NOT NULL COMMENT '主键ID' AUTO_INCREMENT,
    user_name VARCHAR(30) NOT NULL COMMENT '姓名',
    user_token varchar(64) NOT NULL COMMENT '用户随机码，生成密钥的盐，注册时生成且不可变！',
    password VARCHAR(32) NOT NULL COMMENT '密码',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    birthday DATE NULL DEFAULT NULL COMMENT '生日',
    last_modify_password bigint  COMMENT '最后一次修改密码的时间',
    last_login_time bigint COMMENT '最后一次使用密码登录的时间',

    create_by VARCHAR(32) NULL,
    create_time DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    modify_by VARCHAR(32) NULL,
    modify_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    removed INT NOT NULL DEFAULT 0,
    version INT NOT NULL DEFAULT 1,

    PRIMARY KEY (id)

);


CREATE TABLE `boot-quick`.`user_article`  (
`id` bigint(0) NOT NULL,
`user_id` bigint(0) NOT NULL,
`title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
`content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
`create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
`modify_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
`modify_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
`removed` int(0) NOT NULL DEFAULT 0,
`version` int(0) NOT NULL DEFAULT 1,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `boot-quick`.`user_article_1`  (
  `id` bigint(0) NOT NULL,
  `user_id` bigint(0) NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `removed` int(0) NOT NULL DEFAULT 0,
  `version` int(0) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;