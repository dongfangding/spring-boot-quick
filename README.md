[TOC]

# 依赖

```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>4.1.1</version>
</dependency>
```

# Sharding-Jdbc

## 一、读写分离

### 1. mysql相关配置

#### 1.1 安装

> 安装一主两从， 使用docker desktop版本安装

**先随便安装一个自己想要版本的myql镜像，主要目的是将该版本镜像相关的配置文件拷贝出来**，因为我们要采用目录挂载的方式，但是手里又没有配置文件和数据目录，那就要用人家已经写好的，然后进去将我们想要的文件拷贝到宿主机上

```shell
# 默认拉取最新版本的mysql
docker pull mysql
 
# 启动容器加载镜像
docker run -itd --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
 
# 进入容器
docker exec -it mysql /bin/bash
 
# 容器内部连接测试mysql，输入密码成功后则说明安装成功
mysql -u root -p
```

在本机创建存放一主两从mysql服务的数据目录和配置文件目录, **mysql目录为master, mysqls1和mysqls2为两个slave**

```shell
# 创建本地挂载目录
mkdir E:\docker\mysql\data && mkdir E:\docker\mysql\conf &&
mkdir E:\docker\mysqls1\data && mkdir E:\docker\mysqls1\conf &&
mkdir E:\docker\mysqls2\data && mkdir E:\docker\mysqls2\conf
```

将上面安装好的mysql容器里的配置文件拷贝出来, 首先要确定配置文件所在容器中的目录，这一点可以从dockerfile中查看， 到`dockerhub`上查找指定版本的镜像

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/3352717b-a960-4768-9b6f-b72d1870f2b5.png)

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/ffef3a38-0b09-4372-baf9-b7ef6803d76a.png)

**确定好目录之后执行拷贝动作，拷贝完成后，原容器就没用了，然后停掉并且删除它即可**

```shell
# 将mysql这个容器的配置文件和数据目录拷贝出来,可以看下镜像对应的docker file，查出原始文件所在目录
docker cp mysql:/var/lib/mysql E:\docker\mysql\data &&
docker cp mysql:/etc/mysql/ E:\docker\mysql\conf &&
docker cp mysql:/var/lib/mysql E:\docker\mysqls1\data &&
docker cp mysql:/etc/mysql/ E:\docker\mysqls1\conf &&
docker cp mysql:/var/lib/mysql E:\docker\mysqls2\data &&
docker cp mysql:/etc/mysql/ E:\docker\mysqls2\conf
 
# 停止并删除容器
docker stop mysql
docker rm mysql
```

**挂载本地目录，以默认配置创建三个mysql容器，分别进入容器验证是否运行成功**

```shell
#######################################################################################
# 修改my.conf，更改每个mysql服务的端口号，虽然我们已经做了端口映射，可以保证在容器内每个服务依然用3306，
# 但是不知道为啥，后面映射好之后命令行可以进入，navicat会报错Lost connection to MySQL server at ‘reading initial communication packet。然后还是修改了容器内服务自己的端口号
mysqls1 my.conf port=3307
mysqls2 my.conf port=3308
#######################################################################################
 
# 由于是在本地演示，只有一个主机，而且需要slave容器内连接master, 所以需要给每个容器分配固定ip，方便容器互联
# 创建桥接网络
docker network create --driver=bridge --subnet=172.20.12.0/16 mynet
 
# 挂载本地目录重新构建容器
docker run -itd --name mysql --net mynet --ip 172.20.12.1 -v E:\docker\mysql\data\mysql:/var/lib/mysql -v E:\docker\mysql\conf\mysql:/etc/mysql/ -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
 
#  启动第二个服务
docker run -itd --name mysqls1 --net mynet --ip 172.20.12.2 -v E:\docker\mysqls1\data\mysql:/var/lib/mysql -v E:\docker\mysqls1\conf\mysql:/etc/mysql/ -p 3307:3307 -e MYSQL_ROOT_PASSWORD=123456 mysql
#  启动第三个服务
docker run -itd --name mysqls2 --net mynet --ip 172.20.12.3 -v E:\docker\mysqls2\data\mysql:/var/lib/mysql -v E:\docker\mysqls2\conf\mysql:/etc/mysql/ -p 3308:3308 -e MYSQL_ROOT_PASSWORD=123456 mysql
 
# 进入容器并登陆验证是否安装成功
docker exec -it mysql /bin/bash
mysql -uroot -p
 
docker exec -it mysqls1 /bin/bash
mysql -uroot -p
 
docker exec -it mysqls1 /bin/bash
mysql -uroot -p
```

**如果想要更改mysql的配置，则只要修改宿主机即本机目录下的my.conf目录即可**

* mysql ======> 配置目录: E:\docker\mysql\conf  数据目录: E:\docker\mysql\data
* mysqls1 ====> 配置目录: E:\docker\mysqls1\conf  数据目录: E:\docker\mysqls1\data
* mysqls2 ====> 配置目录: E:\docker\mysqls2\conf  数据目录: E:\docker\mysqls2\data

**到这里基本的安装就完成了，关于主从同步的配置以及需要更改的配置还有坑，则在下面的响应步骤中再细讲**

 

**使用Navicat客户端去连接服务的时候报错`authentication plugin 'caching_sha2_password'`**

```shell
## 进入mysql容器
docker exec -it mysql /bin/bash
 
## 连接到Mysql
mysql -u root -p
 
## 查看当前密码规则, 可以看到为caching_sha2_password
use mysql;
select user,plugin from user where user='root';
 
## 更改密码插件规则，并重新指定密码, root@后面连的是主机，主机是%则所有主机都会被更改规则
alter user 'root'@'%' identified with mysql_native_password by '123456';
# 还是更改密码规则，但是%是不包含localhost的，所以要再授权一下localhost
alter user 'root'@'localhost' identified with mysql_native_password by '123456';
 
## 可以再次查看密码规则，已经看到被更改过来了
select user,plugin from user where user='root';
 
## 刷新权限
FLUSH PRIVILEGES;
 
# 修改另外两个的步骤和上面一样，只是要进入各自不同的容器
```

 

#### 1.2 主从配置文件设置

##### 1.2.1. master服务器配置

> 在mysql的配置文件，my.cnf或my.ini下加入如下内容，在本例中即E:\docker\mysql\conf\my.conf

```ini
# 设置服务id，主从不能一致
server-id=1
# 开启binlog日志
log-bin=mysql-bin
# 设置binlog最多保存7天
expire_logs_days=7
# STATEMENT模式 默认值。每一条会修改数据的sql语句会记录到binlog中,优点是并不需要记录每一条sql语句和每一行的数#                 据变化，减少了binlog日志量，节约IO，提高性能
# ROW模式 不记录每条sql语句的上下文信息，仅需记录哪条数据被修改了，修改成什么样了,缺点是会产生大量的日志
# MIXED模式(以上两种模式混合使用),一般的复制使用STATEMENT模式保存binlog，对于STATEMENT模式无法复制的操作使用#        ROW模式保存binlog，MySQL会根据执行的SQL语句选择日志保存方式。
binlog_format=MIXED
# 设置需要同步的数据库
binlog-do-db=boot-quick
# 屏蔽系统库同步
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
binlog-ignore-db=performance_schema
```

##### 1.2.2. 修改从服务器配置文件

> 修改两个mysql从服务器的配置文件，my.cnf或my.ini下加入如下内容,在本例中即E:\docker\mysqls1\conf\my.conf, E:\docker\mysqls2\conf\my.conf

```ini
#########################E:\docker\mysqls1\conf\my.conf#######################
# 开启日志
log-bin=mysql-bin
# 设置服务id，主从不能一致
server-id=2
# 设置需要同步的数据库
replicate_wild_do_table=boot-quick.%
# 屏蔽系统库同步
replicate_wild_ignore_table=mysql.%
replicate_wild_ignore_table=information_schema.%
replicate_wild_ignore_table=performance_schema.%
 
#########################E:\docker\mysqls2\conf\my.conf#######################
# 开启日志
log-bin=mysql-bin
# 设置服务id，主从不能一致
server‐id=3
# 设置要忽略的表
replicate-ignore-db=boot-quick.user_article
replicate-ignore-db=boot-quick.user_article_1
# 设置需要同步的数据库
replicate_wild_do_table=boot-quick.%
# 屏蔽系统库同步
replicate_wild_ignore_table=mysql.%
replicate_wild_ignore_table=information_schema.%
replicate_wild_ignore_table=performance_schema.%
```

##### 1.23. 重启所有服务器

#### 1.3. 主从同步配置

##### 1.3.1 在主库创建用于同步的账号

**非常重要的一个要注意的点**

在实际项目中，如果要读写分离，必然是多台服务器，但是我们自己本机搭建环境演示完的话，要么搭建多个虚拟机也可以，否则就是只有一台服务器，然后使用容器进行隔离。

如我们目前搭建的服务器，都是我们自己的主机`localhost`， **但是`localhost`在容器内部和外部确实两个不同的概念**。在容器外面localhost就是我们自己的宿主机，而在容器内部`localhost`则是容器自己的ip。

下面我们会在mysql master创建一个用于同步的账号，授权的时候千万不要授权`localhost`，因为这个帐号是在从库里使用的，在从库的容器内`localhost`指的是自己了，那么这个用户肯定是不存在的。。。这是在本机搭建环境最最要注意的一个点，一不注意就会被同步时说`access deny`搞乱好几天~~

**那么现在我们要先获取mysql master这台容器的ip， 用于创建用户授权时指定ip为这台容器的ip, 当然容器的ip最好先创建网络，然后分配固定ip,我们前面已经给每个容器分配了固定ip，这里也可以来检查一下，看下用作master的mysql**

**docker inspect mysql**

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/a3a7d236-c076-4afd-b4fa-d8956590e0a0.png)

```shell
# 进入mysql主服务器容器
docker exec -it mysql /bin/bash
# 连接到主机
mysql -uroot -p
# 创建用户， 注意mysql的%是不包含localhost的, 'master_slave_sync'@'%'和'master_slave_sync'@'localhost'会# 被认为是两个用户，如果我们在本机演示用localhost就可，如果不是本机，最好直接使用ip,不必要用%，否则就要创建两
# 个帐号，下面演示是说明确实可以创建两个帐号，而且授权也是一样的道理，%和localhost确实是分开的
CREATE USER 'master_slave_sync'@'%' IDENTIFIED with mysql_native_password BY '123456';
# 还是创建用户， mysql的主机%是不包含localhost的，我们如果只有一个主机演示的话，需要再创建一下localhost
CREATE USER 'master_slave_sync'@'172.20.12.1' IDENTIFIED with mysql_native_password BY '123456';
# 授权
GRANT REPLICATION SLAVE ON *.* TO 'master_slave_sync'@'%';
# mysql授权的%并不包含localhost,如果我们演示使用本机的话，localhost主机也必须给
GRANT REPLICATION SLAVE ON *.* TO 'master_slave_sync'@'172.17.0.4';
# 刷新权限
FLUSH PRIVILEGES;
# 查看节点信息, 会返回当前binlog的日志位置记录点，这个点决定了从库想要从哪里开始同步数据
show master status;
 
```

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/0bb903f0-dc33-47bc-8c0a-b203e393a738.png)

 

##### 1.3.2 配置从库向主库的同步连接信息

进入从库数据库

```sql
-- 暂停同步
stop slave;
 
-- 重置slave,正常先不要执行
-- 启动slave报错Slave failed to initialize relay log info structure from the repository时使用
reset slave;
 
-- 指向主库连接信息
-- 修改从库指向到主库，使用上一步master status记录的文件名以及位点
-- 这里有一点需要注意的是，这个文件位置点表示同步的时候从这个点开始同步，那么之前的数据是不会同步的。
-- 所以如果是想要全量同步，请先自行将主库和从库的数据进行一次同步，然后再进行这个操作
CHANGE MASTER TO
master_host = '172.17.0.1',
master_user = 'master_slave_sync',
master_password = '123456',
-- 对应show master status的file
master_log_file = 'mysql-bin.000007',
-- 对应show master status的Position
master_log_pos = 1427;
 
-- 重启slave
start slave;
-- 查看slave状态
show slave status;
```

`show slave status`请注意查看以下字段是否正常，或者错误原因是什么

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/f3e955f2-8d9d-4a00-8fc7-2605b231b405.png)

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/92b756ae-500e-47f2-a02e-68872dcad546.png)

**注意**,下面这个报错，如果排除发现主机和从机的`server_id`确实不一致，那么再检查每个服务的有一个配置文件叫`auto.conf`，里面有一个`server-uuid`可能是一样的，删除这个文件或者更改对应的值即可，这个文件所在目录为mysql的data目录下

>  Fatal error: The slave I/O thread stops because master and slave have equal MySQL server UUIDs; these UUIDs must be different for replication to work.

#### 1.4 sql语句

前面已经配置好了主从同步配置，所以只要在`master`库执行语句即可，slave会自动同步

```sql
	
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
```



### 2. 项目配置

> [参考手册](<https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/configuration/config-spring-boot/>)

#### 2.1 application.yml中配置

> 使用druid连接池， 经过测试， 如果要配置druid的filter还是保持和原来一样的配置将这部分信息配置在spring.datasource.druid下
>
> 现在使用了shardingsphere，所以其他连接具体信息需要配置在spring.shardingsphere.datasource下

```yaml
#######################################################读写分离配置###############################################################

# mysql master连接信息
master.mysql.host: localhost
master.mysql.port: 3306
master.mysql.db: boot-quick
master.mysql.username: root
master.mysql.password: 123456

# mysql slave0连接信息
slave0.mysql.host: localhost
slave0.mysql.port: 3307
slave0.mysql.db: boot-quick
slave0.mysql.username: root
slave0.mysql.password: 123456

# mysql slave0连接信息
slave1.mysql.host: localhost
slave1.mysql.port: 3308
slave1.mysql.db: boot-quick
slave1.mysql.username: root
slave1.mysql.password: 123456


spring:
  main:
    allow-bean-definition-overriding: true

  # 经测试filter需要配置再datasource.druid下，而每个连接的信息还是得配置在sharding-sphere下的datasource每个自己的
  datasource:
    druid:
      filter:
        stat:
          enabled: true
          log-slow-sql: true
          slow-sql-millis: 3000
        wall:
          enabled: true # 开启WallFilter
          db-type: mysql
      ## 开启内置监控界面 访问路径: /context-path/druid/index.html
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        reset-enable: true
        login-username: admin
        login-password: 123456

  shardingsphere:
    datasource:
      names: master,slave0,slave1
      # 配置数据源
      master:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${master.mysql.host}:${master.mysql.port}/${master.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${master.mysql.username}
        password: ${master.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20
      # 配置数据源
      slave0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${slave0.mysql.host}:${slave0.mysql.port}/${slave0.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${slave0.mysql.username}
        password: ${slave0.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20
      # 配置数据源
      slave1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${slave1.mysql.host}:${slave1.mysql.port}/${slave1.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${slave1.mysql.username}
        password: ${slave1.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20

    # 配置主从规则
    masterslave:
      # 从库负载均衡算法
      load-balance-algorithm-type: round_robin
      # 读写分离数据源名称
      name: ms
      # 主库数据源名称， 从上面配置的数据源中选择
      master-data-source-name: master
      # 从库数据源名称列表， 从上面配置的数据源中选择
      slave-data-source-names: slave0,slave1

    props:
      #  显示sql具体信息
      sql.show: true

```

### 3. 项目测试

项目搭建以及crud代码和单表操作没有一点区别，因此测试代码省略

* 插入测试从从库查询， 插入则操作主库

  ![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/3de55803-07e4-4036-b55b-9a912925145e.png)

  ![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/108883f8-b602-4684-b484-a23cc40efed8.png)

* 列表查询测试, 查询从从库查询数据

  ![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/e6d81b03-00f3-47ab-a106-b2033655e096.png)

### 4. 错误汇总

1. 在master删除的数据，在slave节点不存在

   ```
   Could not execute Delete_rows event on table boot-quick.auth_user; Can't find record in 'auth_user', Error_code: 1032; handler error HA_ERR_KEY_NOT_FOUND; the event's master log WIN-NQ0GCAPFCPP-bin.000017, end_log_pos 2234
   ```

   **临时解决**,

   按照上面的错误先在主库查看binlog日志后面要同步的数量

   ```sql
   SHOW BINLOG EVENTS in 'WIN-NQ0GCAPFCPP-bin.000017' from 2997;
   ```

   然后 进入到slave，先停下slave,然后跳过错误数量， 再重启slave

   ```sql
   stop slave;
   set global sql_slave_skip_counter=1;
   start slave;
   ```

   **永久解决**， 根据错误代码(上面那段错误就包含了Error_code: 1032)，在从库配置文件中跳过对应的错误

   ```
   slave-skip-errors=1032
   ```

### 5. 主从同步原理

* 主节点

  1、当主节点上进行 insert、update、delete 操作时，会按照时间先后顺序写入到 binlog 中；
  2、当从节点连接到主节点时，主节点会创建一个叫做 `binlog dump` 的线程；

  3、一个主节点有多少个从节点，就会创建多少个 `binlog dump` 线程；

  4、当主节点的`binlog` 发生变化的时候，也就是进行了更改操作，`binlog dump` 线程就会通知从节点 (Push模式)，并将相应的 `binlog` 内容发送给从节点；

* 从节点

  当开启主从同步的时候，从节点会创建两个线程用来完成数据同步的工作。

  **I/O线程：** 此线程连接到主节点，主节点上的 `binlog dump` 线程会将 `binlog` 的内容发送给此线程。此线程接收到 binlog 内容后，再将内容写入到本地的 `relay log`。

  **SQL线程：** 该线程读取 I/O 线程写入的 `relay log`，并且根据 `relay log` 的内容对从数据库做对应的操作。

 

## 二、 水平分表+读写分离

由于读写分批和分表的配置是不冲突的，所以也就在配置水平分表的时候将前面配置的读写分离一块配置上了

目标总结：

现在已经有了数据库`boot-quick`，每个库都有一个表为auth_user, 我们现在在`master`在增加一个表`auth_user`，用来做水平分表。这里其实是给自己找事， 如果表命名为auth_user_1, auth_user_2，后面有些表达式会好写一些，可是我就是刚开始没分表，就叫auth_user， 我后来才要分的呀，我就是要犟。

### 1. sql语句

增加水平分表的sql语句

```sql
use `boot-quick`;
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
```

### 2. 项目配置

```yaml
######################################水平分表+读写分离配置############################################################

# mysql master连接信息
master.mysql.host: localhost
master.mysql.port: 3306
master.mysql.db: boot-quick
master.mysql.username: root
master.mysql.password: 123456

# mysql slave0连接信息
slave0.mysql.host: localhost
slave0.mysql.port: 3307
slave0.mysql.db: boot-quick
slave0.mysql.username: root
slave0.mysql.password: 123456

# mysql slave0连接信息
slave1.mysql.host: localhost
slave1.mysql.port: 3308
slave1.mysql.db: boot-quick
slave1.mysql.username: root
slave1.mysql.password: 123456

spring:
  main:
    allow-bean-definition-overriding: true

  # 经测试filter需要配置再datasource.druid下，而每个连接的信息还是得配置在sharding-sphere下的datasource每个自己的
  datasource:
    druid:
      filter:
        stat:
          enabled: true
          log-slow-sql: true
          slow-sql-millis: 3000
        wall:
          enabled: true # 开启WallFilter
          db-type: mysql
      ## 开启内置监控界面 访问路径: /context-path/druid/index.html
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        reset-enable: true
        login-username: admin
        login-password: 123456
        allow:

  shardingsphere:
    datasource:
      names: master,slave0,slave1
      # 配置数据源
      master:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${master.mysql.host}:${master.mysql.port}/${master.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${master.mysql.username}
        password: ${master.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20
      # 配置数据源
      slave0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${slave0.mysql.host}:${slave0.mysql.port}/${slave0.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${slave0.mysql.username}
        password: ${slave0.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20
      # 配置数据源
      slave1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${slave1.mysql.host}:${slave1.mysql.port}/${slave1.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${slave1.mysql.username}
        password: ${slave1.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20

    # 分支分片策略
    sharding:
      # https://shardingsphere.apache.org/document/legacy/4.x/document/cn/faq/#6-%E5%A6%82%E6%9E%9C%E5%8F%AA%E6%9C%89%E9%83%A8%E5%88%86%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E6%98%AF%E5%90%A6%E9%9C%80%E8%A6%81%E5%B0%86%E4%B8%8D%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E7%9A%84%E8%A1%A8%E4%B9%9F%E9%85%8D%E7%BD%AE%E5%9C%A8%E5%88%86%E7%89%87%E8%A7%84%E5%88%99%E4%B8%AD
      # 为了解决不分库分表的表不需要配置分片规则
      default-data-source-name: master
      # 分表
      tables:
        # 逻辑表名， 即原表名比如有auth_user，现在分为实际的auth_user_1, auth_user_2, auth_user即为逻辑表名
        auth_user:
          # 配置数据表分布情况，哪个表在哪个数据源里,有哪些表
          actual-data-nodes: master.auth_user,master.auth_user_1
          table-strategy:
            # 指定当前表的分片策略
            inline:
              sharding-column: id
              # 配置分表策略， id对2（分表数量）进行取模，为0则使用则为auth_user， 其它则增加后缀"_" + 取模后的值
              algorithm-expression: auth_user$->{id % 2 == 0 ? "":"_" + id % 2}

      # 配置分片之后的读写分离，分片之后的读写分离配置和只有读写分离的匹配属性是一样的，但是配置的位置是不同的，分片后读写分离配置要在sharding下
      master-slave-rules:
        # 对上面配置的actual-data-nodes的库配置读写分离策略
        master:
          # 从库负载均衡算法
          load-balance-algorithm-type: round_robin
          # 主库数据源名称， 从上面配置的数据源中选择
          master-data-source-name: master
          # 从库数据源名称列表， 从上面配置的数据源中选择
          slave-data-source-names: slave0,slave1

    props:
      #  显示sql具体信息
      sql.show: true

```



### 3. 项目演示结果

根据逻辑表`auth_user`的id进行水平分表， 对分表数量进行取模， 为0则使用则为auth_user， 其它则增加后缀"_" + 取模后的值，当前分表数量为2.

至于代码开发部分，其实就是简单的mvc的代码，和使用单表并无区别，这里并不打算用大量的内容去粘贴这些代码

即如果取模后的值为0， 则路由到auth_user；取模后的值为1， 则路由到auth_user_1

路由到auth_user的效果, 查询的时候到两个库查询是因为无法通过userName来确定到底该路由到哪张表，因为我们是通过id分表的

![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/0e67c252-2325-4f7d-bfca-be407d24e7b8.png)

路由到auth_user_1的情况, 查询的时候到两个库查询是因为无法通过userName来确定到底该路由到哪张表，因为我们是通过id分表的

![image-20200908153752650](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200908153752650.png)



## 三、水平分库+水平分表+读写分离

ShardingJdbc本身的核心代码是支持上面这个操作的，但是有一些配置代码，拦截了这个处理，导致无法再配置了读写分离的库上再配置水平分库，需要修改一些额外源码才能达到这个效果，所以这个模块的核心其实是水平分库+水平分表

**预期目标**

* 只演示配置的多样性以求理解用法，不讨论分片的合理性

* auth_user保留上面的水平分表原则，（其实也想保留它的读写分离，但后面为了演示核心逻辑，会把这部分配置注释掉）
* 再增加一个user_article和user_article_1，水平分表规则使用user_id取模法
* 读写分离配置屏蔽对user_article和user_article的同步，然后再根据user_id进行分库操作

### 1. mysql准备

#### 1.1 sql

暂时还没修改mysql的配置文件，在master服务执行语句后会自动同步到从库，所以目前从库也会有这两张表

```sql
use `boot-quick`;
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
```

#### 1.2 修改my.conf

由于user_article要进行分库操作，所以不能进行主从同步，现在要修改两个从库的my.conf，来忽略这两张表的主从同步，修改后重启两个从库的服务，然后自行验证在主表加入数据，从库是否真的停了这两张表的同步操作

```cnf
replicate-ignore-table=boot-quick.user_article
replicate-ignore-table=boot-quick.user_article_1
```

### 2. 项目配置

同样，实体的代码操作，依然不是这里的范畴

#### 2.1 user_article分库策略

之前分片策略都是采用的inline, 然后使用的是Groovy的表达式。但现在是有三个库， 然后通过user_id进行取模，

如果为0 则对应数据源为master；如果为1， 则对应数据源为slave1; 如果为2，则对应数据源为slave2;

然后这个Groovy表达式我不会写，所以就只能写java代码了

参考官网支持的分片规则

https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/sharding/concept/sharding/

简单说明对应的配置是，具体要看后面的全部核心配置文件

```yaml
          database-strategy:
            standard:
              sharding-column: user_id
              preciseAlgorithmClassName: com.ddf.boot.quick.sharding.UserArticleDatabasePreciseShardingAlgorithm
```

对应的分库代码类

```java
package com.ddf.boot.quick.sharding;

import cn.hutool.core.collection.CollUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * user_article的数据库分库策略$
 *
 * @author dongfang.ding
 * @date 2020/9/5 0005 13:16
 */
public class UserArticleDatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data source or table's name
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        if (CollUtil.isEmpty(availableTargetNames)) {
            throw new RuntimeException("无可用的数据源");
        }
        // 目前配置了三个数据库,master,slave0,slave1
        // 当前这个分库策略是给user_article用的，分库字段是user_id，userId对可用数据库进行取模
        Long value = shardingValue.getValue();
        long index = value % availableTargetNames.size();

        int i = 0;
        for (String availableTargetName : availableTargetNames) {
            if (i == index) {
                return availableTargetName;
            }
            i ++;
        }
        return null;
    }
}

```



#### 2.1 项目核心配置

```yaml
######################################水平分库分表+读写分离配置############################################################

# 如果既要支持读写分离又要水平分库，
# 那么得最少两个master,否则的话master的数据就会被同步到从库里。这里没有搞多个master，
# 但又要演示水平分库，所以下面配置的前提是目前用来演示水平分库的表是没有配置主从同步的
# auth_user 配置水平分表+主从同步+读写分离
# user_article 配置水平分库分表+特意在主从同步配置文件中忽略了该表的主从同步


# mysql master连接信息
master.mysql.host: localhost
master.mysql.port: 3306
master.mysql.db: boot-quick
master.mysql.username: root
master.mysql.password: 123456

# mysql slave0连接信息
slave0.mysql.host: localhost
slave0.mysql.port: 3307
slave0.mysql.db: boot-quick
slave0.mysql.username: root
slave0.mysql.password: 123456

# mysql slave0连接信息
slave1.mysql.host: localhost
slave1.mysql.port: 3308
slave1.mysql.db: boot-quick
slave1.mysql.username: root
slave1.mysql.password: 123456

spring:
  main:
    allow-bean-definition-overriding: true

  # 经测试filter需要配置再datasource.druid下，而每个连接的信息还是得配置在sharding-sphere下的datasource每个自己的
  datasource:
    druid:
      filter:
        stat:
          enabled: true
          log-slow-sql: true
          slow-sql-millis: 3000
        wall:
          enabled: true # 开启WallFilter
          db-type: mysql
      ## 开启内置监控界面 访问路径: /context-path/druid/index.html
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        reset-enable: true
        login-username: admin
        login-password: 123456
        allow:

  shardingsphere:
    datasource:
      names: master,slave0,slave1

      # 配置数据源
      master:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${master.mysql.host}:${master.mysql.port}/${master.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${master.mysql.username}
        password: ${master.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20
      # 配置数据源
      slave0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${slave0.mysql.host}:${slave0.mysql.port}/${slave0.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${slave0.mysql.username}
        password: ${slave0.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20
      # 配置数据源
      slave1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${slave1.mysql.host}:${slave1.mysql.port}/${slave1.mysql.db}?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&tinyInt1isBit=false
        username: ${slave1.mysql.username}
        password: ${slave1.mysql.password}
        initial-size: 5
        asyncInit: true
        max-active: 30
        min-idle: 10
        keep-alive: true
        max-wait: 60000
        use-unfair-lock: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 600000
        validation-query: SELECT 1
        test-while-idle: true
        test-on-borrow: true
        test-on-return: false
        poolPreparedStatements: false
        max-open-prepared-statements: 20

    # 分支分片策略
    sharding:
      # https://shardingsphere.apache.org/document/legacy/4.x/document/cn/faq/#6-%E5%A6%82%E6%9E%9C%E5%8F%AA%E6%9C%89%E9%83%A8%E5%88%86%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E6%98%AF%E5%90%A6%E9%9C%80%E8%A6%81%E5%B0%86%E4%B8%8D%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E7%9A%84%E8%A1%A8%E4%B9%9F%E9%85%8D%E7%BD%AE%E5%9C%A8%E5%88%86%E7%89%87%E8%A7%84%E5%88%99%E4%B8%AD
      # 为了解决不分库分表的表不需要配置分片规则
      default-data-source-name: master
      # 分表
      tables:
        # 逻辑表名， 即原表名比如有auth_user，现在以这个为依据分为实际的auth_user_1, auth_user_2, auth_user即为逻辑表名
        auth_user:
          # 配置数据表分布情况，哪个表在哪个数据源里,有哪些表，只要分配要写的表即可，这个因为要做读写分离和水平分表，所以只配了master的auth_user表分配情况
          actual-data-nodes: master.auth_user,master.auth_user_1
          table-strategy:
            # 指定当前表的分片策略
            inline:
              sharding-column: id
              # 配置分表策略， id对2（分表数量）进行取模，为0则使用则为auth_user， 其它则增加后缀"_" + 取模后的值
              algorithm-expression: auth_user$->{id % 2 == 0 ? "":"_" + id % 2}

        # 逻辑表名， user_article，现在以这个为依据分为实际的auth_user, auth_user_2, user_article即为逻辑表名
        user_article:
          # https://github.com/apache/shardingsphere/issues/7269
          # 目前发现是有这个问题的，后续会跟踪最终结果。为了解决这个问题，目前本地提供的解决方案
          useMasterSlaveRulesDatasourceNameIfExist: false
          # 配置数据表分布情况，哪个表在哪个数据源里,有哪些表，只要配置要写的表，这个因为不做读写分离，只做水平分库分表，所以要把该逻辑表对应的所有数据库和数据表都配置出来
          # 这里已经明确告知了框架，有这么多的库和表是对应这个逻辑表的，如果只配置了分表策略，而不配置分库策略，则插入时按照分表策略，则会将这个表对应的所有库都会执行插入语句
          # 所以千万不要忘记配置了分库策略
          actual-data-nodes: master.user_article,master.user_article_1,slave$->{0..1}.user_article,slave$->{0..1}.user_article_1

          # 对actual-data-nodes配置的表的数据库的分布情况做分库策略
          database-strategy:
            # 分库文档 https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/sharding/concept/sharding/
            standard:
              # 根据哪个字段进行分库
              sharding-column: user_id
              # 配置分库策略对应的类，由于库名取的无法（或者我不会写）直接使用inline支持的Groovy表达式，所以需要自己开发实现一个类
              # 参考官网文档https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/sharding/concept/sharding/
              preciseAlgorithmClassName: com.ddf.boot.quick.sharding.UserArticleDatabasePreciseShardingAlgorithm

          # 对actual-data-nodes配置的表的分布情况做分表策略
          table-strategy:
            # 指定当前表的分片策略
            # 分库文档 https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/sharding/concept/sharding/
            inline:
              sharding-column: user_id
              # 配置分表策略， user_id对2（分表数量）进行取模，为0则使用则为user_article， 其它则增加后缀"_" + 取模后的值
              algorithm-expression: user_article$->{user_id % 2 == 0 ? "":"_" + user_id % 2}

      # 这个是配置默认分库策略的，但是我们现在没有一个全局的默认分库策略，所以不用配置，我们现在只有具体表来分库的策略，所有要配置在sharding.tables下
      # default-database-strategy:

      # 配置分片之后的读写分离，分片之后的读写分离配置和只有读写分离的匹配属性是一样的，但是配置的位置是不同的，分片后读写分离配置要在sharding下
      master-slave-rules:
        # 对上面配置的actual-data-nodes的库配置读写分离策略
        master:
          # 从库负载均衡算法
          load-balance-algorithm-type: round_robin
          # 主库数据源名称， 从上面配置的数据源中选择
          master-data-source-name: master
          # 从库数据源名称列表， 从上面配置的数据源中选择
          slave-data-source-names: slave0,slave1
    props:
      #  显示sql具体信息
      sql.show: true
    enabled: true

```

#### 3. 演示效果

* 读写分离和分库后的查询依然有冲突问题，保持关注，所以还是先注释掉这块的读写分离配置

* 新增user_article, user_id为99，按照规则， 99 % 3 = 0, 则路由到master数据库；99 % 2 = 1， 则路由到user_article_1

  ![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/3b8a649e-ef54-4de3-9ae1-13319b6f9c9b.png)

* 新增user_article， user_id为100， 按照规则， 100 % 3 = 1, 则路由到slave0数据库； 100 % 2 = 0， 则路由到user_article表

  ![](https://vipkshttps4.wiz.cn/ks/share/resources/581c88a2-8001-4020-87a9-fb85b53ffcbf/01ca975c-4a2c-4593-b8fd-87640091d3dc/index_files/0e67c252-2325-4f7d-bfca-be407d24e7b8.png)

* 

## 项目通用配置和项目错误汇总

* 默认连接未配置，刚开始使用的版本没有这个问题，后来升到了4.1.1有这个问题

  ```
  Action:
  
  Consider the following:
  	If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.
  	If you have database settings to be loaded from a particular profile you may need to activate it (no profiles are currently active).
  ```

  则需要在主启动类上排除当前项目所依赖的连接池的自动配置类，如使用的`druid`

  ```java
  @SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
  ```

* datasource已定义

  ```
  Description:
   
  The bean 'dataSource', defined in class path resource [org/apache/shardingsphere/shardingjdbc/spring/boot/SpringBootConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [com/alibaba/druid/spring/boot/autoconfigure/DruidDataSourceAutoConfigure.class] and overriding is disabled.
   
  Action:
   
  Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
  ```

  则按照上面的提示，加上配置

  ```yaml
  spring:
    main:
      allow-bean-definition-overriding: true
  ```