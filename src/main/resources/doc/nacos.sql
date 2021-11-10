SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`
(
    `id`           bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) COLLATE utf8_bin          DEFAULT NULL,
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(50) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL,
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    `c_desc`       varchar(256) COLLATE utf8_bin          DEFAULT NULL,
    `c_use`        varchar(64) COLLATE utf8_bin           DEFAULT NULL,
    `effect`       varchar(64) COLLATE utf8_bin           DEFAULT NULL,
    `type`         varchar(64) COLLATE utf8_bin           DEFAULT NULL,
    `c_schema`     text COLLATE utf8_bin,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 85
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info`
VALUES (37, 'common-datasource', 'DEFAULT_GROUP',
        'spring:\n  datasource:\n    initialization-mode: ALWAYS\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.p6spy.engine.spy.P6SpyDriver  # 优化sql显示插件，对性能有一定影响\n      url: jdbc:p6spy:mysql://${mysql_host}:${mysql_port}/${mysql_db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false\n      username: ${mysql_username}\n      password: ${mysql_password} # https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter可以配置生数据库加密\n      initial-size: 10\n      asyncInit: true\n      max-active: 200\n      min-idle: 10\n      keep-alive: true\n      max-wait: 60000\n      use-unfair-lock: true\n      # 回收连接相关\n      time-between-eviction-runs-millis: 60000\n      min-evictable-idle-time-millis: 600000\n      validation-query: SELECT 1\n      test-while-idle: true\n      test-on-borrow: true\n      test-on-return: false\n      poolPreparedStatements: false\n      max-open-prepared-statements: 20\n      filter:\n        stat:\n          enabled: true\n          log-slow-sql: true\n          slow-sql-millis: 3000\n        wall:\n          enabled: true # 开启WallFilter\n          db-type: mysql\n      ## 开启内置监控界面 访问路径: /context-path/druid/index.html\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        reset-enable: ${druid_stat_enable}\n        login-username: ${druid_state_username}\n        login-password: ${druid_state_password}\n        allow:',
        '381e37c35eb275bcdbcc9610751e7c71', '2021-11-02 12:48:35', '2021-11-10 02:46:43', 'nacos', '114.93.26.102', '',
        'common', '数据库连接属性', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (38, 'common-server', 'DEFAULT_GROUP',
        'server:\n  port: 8080\n  tomcat:\n    max-connections: 20000\n    max-threads: 400\n    min-spare-threads: 20\n  servlet:\n    context-path: /boot-quick\n\n#  application:\n#    name: boot-quick\n  jackson:\n    time-zone: GMT+8\n    default-property-inclusion: ALWAYS\n    date-format: yyyy-MM-dd HH:mm:ss\n    locale: zh_CN\n\n  # 国际化资源文件\n  messages:\n    basename: exception/exception\n    use-code-as-default-message: false\n\ncustoms: # 自定义的属性最好都写在custom前缀下，方便辨认\n  global-properties:\n    snowflakeWorkerId: 1      # worker Id can\'t be greater than 31 or less than 0\n    snowflakeDataCenterId: 1  # dataCenterId Id can\'t be greater than 31 or less than 0\n    ignoreErrorTraceProfile: # 过滤将异常堆栈信息输出打前端接口返回值的环境\n      - pre\n      - prod\n\nlogging:\n  level:\n    root: info\n\nmanagement:\n  health:\n    redis:\n      enabled: false\n    rabbit:\n      enabled: false\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n    jmx:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    shutdown:\n      enabled: false\n    jolokia:\n      enabled: true',
        '4deb88fcaf5e74a3af3f0dd98dea8c7a', '2021-11-02 12:52:27', '2021-11-09 12:58:43', 'nacos', '114.93.26.102', '',
        'common', 'server相关基础配置', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (39, 'common-mybatis-plus', 'DEFAULT_GROUP',
        'mybatis-plus:\n  global-config:\n    db-config:\n      logic-not-delete-value: 0\n      logic-delete-value: null\n      logic-delete-field: isDel\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: true\n    # https://mp.baomidou.com/config/#localcachescope\n    localCacheScope: STATEMENT\n  ## mapper.xml文件所在路径\n  mapper-locations: classpath:mapper/*.xml,classpath:mapper/ext/*.xml',
        '6c9e3dab8228c0b95c19c86af18e0961', '2021-11-02 12:55:56', '2021-11-02 12:55:56', NULL, '114.93.26.102', '',
        'common', 'mybatis-plus配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (42, 'common-redis', 'DEFAULT_GROUP',
        'spring:\n  redis:\n    host: ${redis_host}\n    port: ${redis_port}\n    # 集群多节点配置\n#    cluster:\n#      max-redirects: 3\n#      nodes: ${redis_node}\n    database: ${redis_database}\n    timeout: 3000ms\n    password: ${redis_password}\n    redisson:\n      # com.ddf.boot.common.redis.config.RedisCustomizeAutoConfiguration\n      codec: org.redisson.codec.JsonJacksonCodec',
        'dac2adca100006a31d361fc3fa4b39b0', '2021-11-03 05:07:03', '2021-11-09 12:04:09', 'nacos', '114.93.26.102', '',
        'common', 'redis配置， 其中redisson是使用redisson作为redis客户端的配置属性', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (43, 'common-mail', 'DEFAULT_GROUP',
        'spring:\n  mail:\n    username: ${mail_server_username}                # 用来验证授权的邮件用户名\n    password: ${mail_server_password}               # 根据QQ邮箱设置-账户里生成的第三方登陆授权码，可用来代替密码登陆\n    host: smtp.qq.com                         # 邮件服务器类型\n    properties.mail.smtp.ssl.enable: true # 用以支持授权码登陆',
        '233181560847d169b29b432145b8d313', '2021-11-03 05:08:37', '2021-11-03 05:08:37', NULL, '114.93.26.102', '',
        'common', '邮件服务配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (44, 'common-rabbitmq', 'DEFAULT_GROUP',
        'spring:\n  rabbitmq:\n    addresses: ${rabbit_address}\n    username: ${rabbit_username}\n    password: ${rabbit_password}\n    virtual-host: / # 创建的虚拟主机，可以简单理解为一个实例，所有队列交换器路由等都是在它的基础上，默认为/，最好自己新建一个\n    publisher-confirms: true # 消息被投递之后如何确保一定被正确投递或消费，开启conform模式\n    listener:\n      direct:\n        acknowledge-mode: manual  # 开启手动ack\n      simple:\n        acknowledge-mode: manual # 开启手动ack\n        retry:\n          enabled: false\n          max-attempts: 3\n          initial-interval: 3000 # 第一次和第二次尝试传递消息之间的持续时间\n        default-requeue-rejected: false\n    cache:\n      channel:\n        size: 100\n        checkoutTimeout: 3000\n      connection:\n        mode: connection\n        size:\n    template:\n      retry:\n        enabled: true\n        maxAttempts: 5\n        initialInterval: 2000  # 第一次和第二次尝试传递消息之间的持续时间',
        '591cab8a95dd6808c1e9a7476634a5c7', '2021-11-03 05:09:30', '2021-11-03 05:29:02', 'nacos', '114.93.26.102', '',
        'common', 'rabbitmq配置', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (45, 'common-mongo', 'DEFAULT_GROUP',
        'spring:\n  data:\n    mongodb:\n      uri: ${mongodb_url}\n      autoIndexCreation: true',
        '37aa099eb778d166a79620c7b6b1adaf', '2021-11-03 05:10:27', '2021-11-03 05:10:27', NULL, '114.93.26.102', '',
        'common', 'mongodb配置属性', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (46, 'common-es', 'DEFAULT_GROUP',
        'spring:\n  elasticsearch:\n    rest:\n      uris: ${es_hosts}\n      username: ${es_username}\n      password: ${es_password}\n      connectionTimeout: 3s',
        '991c41a9434b97cf79e576256a23d3a5', '2021-11-03 05:11:03', '2021-11-03 05:11:03', NULL, '114.93.26.102', '',
        'common', 'elastic-search属性配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (48, 'common-rocketmq', 'DEFAULT_GROUP',
        'rocketmq:\n  name-server: ${rocketmq_name_server}\n  producer:\n    group: ${spring.application.name}\n    send-message-timeout: 10000\n    retryNextServer: true',
        '1a650476d9dffef4b56a82f7d85f33c1', '2021-11-03 05:36:20', '2021-11-10 01:51:25', 'nacos', '114.93.26.102', '',
        'common', 'rocketmq属性配置', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (49, 'common-elasticjob', 'DEFAULT_GROUP',
        'elasticjob:\n  reg-center:\n    server-lists: ${zk_addr}\n  tracing:\n    type: RDB # 事件追踪配置,需要配合数据库数据源\n  dump:\n    port: 9999\n  jobs:\n    # https://shardingsphere.apache.org/elasticjob/current/cn/user-manual/elasticjob-lite/configuration/\n    timeReportJob:\n      # https://shardingsphere.apache.org/elasticjob/current/cn/faq/#3-%E4%B8%BA%E4%BB%80%E4%B9%88%E5%9C%A8%E4%BB%A3%E7%A0%81%E6%88%96%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E4%B8%AD%E4%BF%AE%E6%94%B9%E4%BA%86%E4%BD%9C%E4%B8%9A%E9%85%8D%E7%BD%AE%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E9%85%8D%E7%BD%AE%E5%8D%B4%E6%B2%A1%E6%9C%89%E6%9B%B4%E6%96%B0\n      # 本地配置是否可覆盖注册中心配置\n      overwrite: true\n      jobParameter: 我是从配置文件传过来的一个jobParameter\n      # 每次作业执行时间和间隔时间均非常短的情况，建议不监控作业运行时状态以提升效率。 因为是瞬时状态，所以无必要监控。请用户自行增加数据堆积监控。\n      # 并且不能保证数据重复选取，应在作业中实现幂等性。 每次作业执行时间和间隔时间均较长的情况，建议监控作业运行时状态，可保证数据不会重复选取\n      monitorExecution: true\n      # 任务开发的bean class\n      elasticJobClass: com.ddf.boot.quick.features.elasticjob.TimeReportJob\n      cron: 0 0/5 * * * ?\n      # 分片总数， 分片总数如果大于1将会尽量平均分配给多个服务节点（如果存在），然后任务触发的时候多个节点都会执行代码，但如果自己的任务\n      # 只希望同一时刻只有一个节点执行，则要配置为1.否则就是类似于多个服务节点平均分配分片然后来瓜分任务的意思，可以理解为fork-join框架的思想\n      # 所以分片数如果大于1，一定是为了利用服务器的资源然后各自执行不同数据段的代码，这样分片才有意义\n      shardingTotalCount: 1\n      # 个性化分片参数 加入需要分片执行的话，给每个分片传递参数，以便进行数据业务切分隔离，互不影响\n      shardingItemParameters: 0=Beijing,1=Shanghai,2=Guangzhou\n      # 当作业执行过程中服务器宕机，失效转移允许将该次未完成的任务在另一作业节点上补偿执行。失效转移需要与监听作业运行时状态同时开启才可生效。\n      # 开启失效转移功能，ElasticJob 会监控作业每一分片的执行状态，并将其写入注册中心，供其他节点感知。\n      # 在一次运行耗时较长且间隔较长的作业场景，失效转移是提升作业运行实时性的有效手段； 对于间隔较短的作业，会产生大量与注册中心的网络通信，\n      # 对集群的性能产生影响。 而且间隔较短的作业并未见得关注单次作业的实时性，可以通过下次作业执行的重分片使所有的分片正确执行，因此不建议短间隔作业开启失效转移。\n      failover: false\n      # ElasticJob 不允许作业在同一时间内叠加执行。 当作业的执行时长超过其运行间隔，错过任务重执行能够保证作业在完成上次的任务后继续执行逾期的作业。\n      # 在一次运行耗时较长且间隔较长的作业场景，错过任务重执行是提升作业运行实时性的有效手段； 对于未见得关注单次作业的实时性的短间隔的作业来说，开启错过任务重执行并无必要。\n      misfire: false',
        '2c352369c7ccb5da2db0efe4f93713f9', '2021-11-03 05:39:02', '2021-11-03 05:39:02', NULL, '114.93.26.102', '',
        'common', 'elastic-job属性配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (50, 'common-xxl-job', 'DEFAULT_GROUP',
        'xxl-job:\n  adminAddresses: ${xxl_admin_addresses}\n  accessToken: ${xxl_admin_access_token} # 访问token, 需要和xxl-job-admin里的配置一致\n  appName: auto # 以spring.application.name\n  logPath: /opt/services/xxl-job/logs/jobhandler # 日志目录\n  logRetentionDays: 7 # 日志保留天数',
        'c864101e8cf1d67b29b6e8cc6fec6a34', '2021-11-03 05:40:40', '2021-11-03 05:40:40', NULL, '114.93.26.102', '',
        'common', 'xxl-job属性配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (51, 'common-jwt', 'DEFAULT_GROUP',
        'customs: # 自定义的属性最好都写在custom前缀下，方便辨认\n  jwt:\n    secret: ${jwt_secret}\n    expiredMinute: 1080  # jwt token过期时间，单位分钟\n    refreshTokenMinute: 10  # 在jwt token有效时间剩余达到了这个时间（分钟），如果用户在线（即调用了任意接口）则服务端主动刷新用户token并返回，需要前端配合处理重新替换用户token\n    ignores: ## 接口用户认证请求拦截忽略路径\n    - path: /v2/**\n    - path: /swagger-ui.html\n    - path: /swagger-ui.html/**\n    - path: /swagger-resources/**\n    - path: /swagger/**\n    - path: /webjars/**\n    - path: /druid/**\n    - path: /csrf\n    - path: /app.js\n    - path: /websocket.html\n    - path: /js/**\n    - path: /ws/**\n    - path: /pay-ws/**\n    - path: /heartCheck.txt\n    - path: /index.html\n    - path: /favicon.ico\n    - path: /sysUser/generateCaptcha\n    - path: /sysUser/loginByPassword\n    - path: /quick-start/quickTest',
        '52f8a73aaa8334f7ea9c7cce7ca693d1', '2021-11-03 08:30:38', '2021-11-03 08:30:38', NULL, '114.93.26.102', '',
        'common', 'jwt属性配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (52, 'common-global-properties', 'DEFAULT_GROUP',
        'customs: # 自定义的属性最好都写在custom前缀下，方便辨认\n  global-properties:\n    snowflakeWorkerId: 1      # worker Id can\'t be greater than 31 or less than 0\n    snowflakeDataCenterId: 1  # dataCenterId Id can\'t be greater than 31 or less than 0\n    ignoreErrorTraceProfile: # 过滤将异常堆栈信息输出打前端接口返回值的环境\n      - pre\n      - prod\n    exception200: true  # 异常模式， true标识使用异常时保证http状态码为200，使用返回数据标识具体异常\n  authentication:\n    reset-password: 123456\n    white-login-name-list:\n      - admin\n  rsa: # RSA秘钥对\n    primaryKey: ${rsa_primaryKey}\n    publicKey: ${rsa_publicKey}',
        '39d7d8105d545a9eab616eb88c7d5b0e', '2021-11-03 08:33:32', '2021-11-03 08:33:32', NULL, '114.93.26.102', '',
        'common', '通用全局属性', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (53, 'common-ext-oss', 'DEFAULT_GROUP',
        'customs: # 自定义的属性最好都写在custom前缀下，方便辨认\n  ext:\n    ## oss服务配置 com.ddf.boot.common.ext.oss.config.OssProperties\n    oss:\n      endpoint: ${oss_endpoint}\n      accessKeyId: ${oss_accessKeyId}\n      accessKeySecret: ${oss_accessKeySecret}\n      secret: ${oss_secret}\n      stsEndpoint: ${oss_stsEndpoint}\n      roleArn: ${oss_roleArn}\n      roleSessionName: ${spring.application.name}\n      durationSeconds: 1800\n      buckets:\n        - bucketName: ddf-private\n          bucketEndpoint: ddf-private.oss-cn-shanghai.aliyuncs.com\n          primary: true',
        'a7c14e974470befa3400be9d5ac5b405', '2021-11-03 09:39:01', '2021-11-03 09:39:01', NULL, '114.93.26.102', '',
        'common', 'oss属性配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (54, 'common-ext-sms', 'DEFAULT_GROUP',
        'customs: # 自定义的属性最好都写在custom前缀下，方便辨认\n  ext:\n    ## 短信服务配置 com.ddf.boot.common.ext.sms.config.SmsProperties\n    sms:\n      endpoint: dysmsapi.aliyuncs.com\n      protocol: HTTP\n      secretAccessKey: ${oss_secret}\n      accessKeyId: ${oss_accessKeyId}\n      accessKeySecret: ${oss_accessKeySecret}\n      sinaName: ABC商城\n      templateCode: SMS_204455240\n      useRandomCode: true',
        '71c57a48c6cbaa437ad82ca1a45fbe56', '2021-11-03 10:12:02', '2021-11-03 11:56:26', 'nacos', '114.93.26.102', '',
        'common', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (55, 'common-websocket', 'DEFAULT_GROUP',
        'spring:\n  websocket:\n    properties:\n      handshakeTokenSecret: true\n      ignoreAuthTimestamp: true # websocket连接时忽略时间戳校验\n      message_secret: false # 是否加密传输websocket消息数据',
        '476868dfe05d0021fdb8617e515f228b', '2021-11-03 10:24:23', '2021-11-03 12:01:05', 'nacos', '114.93.26.102', '',
        'common', 'websocket属性', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (58, 'common-distributed-lock', 'DEFAULT_GROUP',
        'customs: # 自定义的属性最好都写在custom前缀下，方便辨认\n  distributed:\n    lock:\n      zookeeper:\n        enable: true\n        root: /distributed_lock\n        connectString: ${zk_addr}\n      redis:\n        enable: true',
        'f4246620c658ac12693fa90929396c19', '2021-11-04 01:51:11', '2021-11-04 01:51:11', NULL, '114.93.26.102', '',
        'common', '分布式锁配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (59, 'common-ids', 'DEFAULT_GROUP',
        'customs: # 自定义的属性最好都写在custom前缀下，方便辨认\n  ids:\n    zkAddress: ${zk_addr}\n    zkPort: 2181\n    segmentEnable: true',
        'da955b01fa14bb142abe14a9c7760760', '2021-11-04 02:57:00', '2021-11-04 02:57:00', NULL, '114.93.26.102', '',
        'common', 'id生成器属性配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (60, 'property_source', 'dev',
        'desc=这里面是对上面所有用到的dataId的变量进行赋值，统一管理。如\nnacos_username=nacos\nnacos_password=nacos\n\n',
        '6a1b28e0b737179f9708dddef34249a2', '2021-11-09 12:03:31', '2021-11-10 02:48:21', 'nacos', '114.93.26.102', '',
        'better-together', 'dev环境配置变量值', '', '', 'properties', '');
INSERT INTO `config_info`
VALUES (67, 'config-server', 'dev', 'user-info:\n  name: chen\n  age: 26\n  address: AnHui',
        '9494ffb1f8968ab7a6759e05de990a84', '2021-11-09 12:11:38', '2021-11-09 12:11:38', NULL, '114.93.26.102', '',
        'dev', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (68, 'config-properties', 'dev', 'name=MACBOOK13\nprice=19000225\nsingleValue=测试单属性值注入12345',
        '19a754984c539148c7eeb94999f46d54', '2021-11-09 12:11:38', '2021-11-09 12:11:38', NULL, '114.93.26.102', '',
        'dev', '属性配置文件', NULL, NULL, 'properties', NULL);
INSERT INTO `config_info`
VALUES (69, 'property-source', 'dev',
        'desc=这里面是对上面所有用到的dataId的变量进行赋值，统一管理。如\nnacos_username=nacos\nnacos_password=nacos\n\n',
        'b2224c3079158c94d9bd5052fe5fbf6e', '2021-11-09 12:11:38', '2021-11-09 12:11:38', NULL, '114.93.26.102', '',
        'dev', '配置文件依赖的具体属性值', NULL, NULL, 'properties', NULL);
INSERT INTO `config_info`
VALUES (70, 'config-server', 'dev', 'user-info:\n  name: chen\n  age: 26\n  address: AnHui',
        '9494ffb1f8968ab7a6759e05de990a84', '2021-11-09 12:19:02', '2021-11-09 12:19:02', NULL, '114.93.26.102', '',
        'spring-boot-quick', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (71, 'config-properties', 'dev', 'name=MACBOOK13\nprice=19000225\nsingleValue=测试单属性值注入12345',
        '19a754984c539148c7eeb94999f46d54', '2021-11-09 12:19:02', '2021-11-09 12:19:02', NULL, '114.93.26.102', '',
        'spring-boot-quick', '属性配置文件', NULL, NULL, 'properties', NULL);
INSERT INTO `config_info`
VALUES (72, 'property-source', 'dev',
        'desc=这里面是对上面所有用到的dataId的变量进行赋值，统一管理。如\nnacos_username=nacos\nnacos_password=nacos\n\n',
        '1375b6d6f7039f304fdfc1e4dfd61845', '2021-11-09 12:19:02', '2021-11-10 02:47:53', 'nacos', '114.93.26.102', '',
        'spring-boot-quick', '配置文件依赖的具体属性值', '', '', 'properties', '');
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`
(
    `id`           bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT '内容',
    `gmt_modified` datetime                      NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) COLLATE utf8_bin DEFAULT NULL,
    `tenant_id`    varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='增加租户字段';

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`
(
    `id`           bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `beta_ips`     varchar(1024) COLLATE utf8_bin         DEFAULT NULL COMMENT 'betaIps',
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(50) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info_beta';

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`
(
    `id`           bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(50) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info_tag';

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint                        NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) COLLATE utf8_bin  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint                        NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`),
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_tag_relation';

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`
(
    `id`                bigint unsigned               NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int unsigned                  NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int unsigned                  NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='集群、各Group容量信息表';

-- ----------------------------
-- Records of group_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`
(
    `id`           bigint unsigned               NOT NULL,
    `nid`          bigint unsigned               NOT NULL AUTO_INCREMENT,
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL,
    `group_id`     varchar(128) COLLATE utf8_bin NOT NULL,
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext COLLATE utf8_bin     NOT NULL,
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL,
    `gmt_create`   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `src_user`     text COLLATE utf8_bin,
    `src_ip`       varchar(50) COLLATE utf8_bin           DEFAULT NULL,
    `op_type`      char(10) COLLATE utf8_bin              DEFAULT NULL,
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`),
    KEY `idx_gmt_create` (`gmt_create`),
    KEY `idx_gmt_modified` (`gmt_modified`),
    KEY `idx_did` (`data_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 93
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='多租户改造';


-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `role`     varchar(50) COLLATE utf8mb4_0900_bin  NOT NULL,
    `resource` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
    `action`   varchar(8) COLLATE utf8mb4_0900_bin   NOT NULL,
    UNIQUE KEY `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

-- ----------------------------
-- Records of permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `username` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
    `role`     varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
    UNIQUE KEY `idx_user_role` (`username`, `role`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint unsigned               NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int unsigned                  NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int unsigned                  NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int unsigned                  NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int unsigned                  NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='租户容量信息表';

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`
(
    `id`            bigint                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) COLLATE utf8_bin  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint                        NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint                        NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
INSERT INTO `tenant_info`
VALUES (2, '1', 'common', 'common', '配置文件中的关键信息采用变量来定义通用配置，可以多个应用公用', 'nacos', 1635857294389, 1635857294389);
INSERT INTO `tenant_info`
VALUES (3, '1', 'better-together', 'better-together', 'better-together服务', 'nacos', 1636459303820, 1636459303820);
INSERT INTO `tenant_info`
VALUES (4, '1', 'spring-boot-quick', 'spring-boot-quick',
        'spring-boot-quick项目，命名空间一般适用于隔离环境dev,等，然后使用默认组，这里自己项目使用同一个nacos另类使用', 'nacos', 1636460321869, 1636460321869);
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `username` varchar(50) COLLATE utf8mb4_0900_bin  NOT NULL,
    `password` varchar(500) COLLATE utf8mb4_0900_bin NOT NULL,
    `enabled`  tinyint(1)                            NOT NULL,
    PRIMARY KEY (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users`
VALUES ('nacos', '$2a$10$7lF9NFFyc9swaaGoDxCoz.x6D2yh0IfTS0WeszMX/MOzQ3gJ8ep/q', 1);
INSERT INTO `users`
VALUES ('op', '$2a$10$rCHn201s/GIydTLTRTuPWOflujmjmy15cFg7bWhm/0y2eKl1RtGPG', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
