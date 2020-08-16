# spring-boot-quick

# 多数据源方案

1. 基于`https://github.com/baomidou/dynamic-datasource-spring-boot-starter`，下载源码安装本地后使用
2. 配置druid参考application.yml 慢日志和stat-view-servlet与原生druid有区别
3. **监控数据源只能查询到一个数据源**，不知道啥原因
4. 主启动类@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
5. 切换数据库的时候使用DS注解, 详细使用请到源码去看文档
6. 至于事务，不跨库直接@Transaction是支持本地事务的，至于官方文档说不知道spring事务可能指的是
在一个操作中跨库操作不支持事务，所以才要求使用分布式事务吧