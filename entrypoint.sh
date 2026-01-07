#!/bin/sh
# 默认 JVM 基础参数
DEFAULT_JVM_OPTS="-Xmx${HeapSize:-512m} -Xms${HeapSize:-512m} \
-XX:MetaspaceSize=${MetaspaceSize:-256m} -XX:MaxMetaspaceSize=${MetaspaceSize:-256m} \
-Dspring.profiles.active=${PROFILE:-dev} \
-Ddruid.mysql.usePingMethod=false \
-Duser.timezone=GMT+08 \
-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
-Dnacos_username=${NACOS_USERNAME:-0} \
-Dnacos_password=${NACOS_PASSWORD:-0} \
-Dnacos_server=${NACOS_SERVER:-0} \
-Dnacos_key=${NACOS_KEY:-0} \
-XX:+IgnoreUnrecognizedVMOptions -XX:+UseZGC \
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=.heapdump \
-XX:+PrintCommandLineFlags -XX:+DisableExplicitGC \
-Xlog:gc*,gc+heap=info,gc+age=trace:file=gc-%t.log:time,uptime,level,tags:filecount=10,filesize=10M"

# JDK 17 强一致性模块开放参数 (模块化解封)
MODULE_OPTS="--add-exports=java.base/sun.nio.ch=ALL-UNNAMED \
--add-opens=java.base/java.lang=ALL-UNNAMED \
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
--add-opens=java.base/java.lang.ref=ALL-UNNAMED \
--add-opens=java.base/java.io=ALL-UNNAMED \
--add-opens=java.base/java.nio=ALL-UNNAMED \
--add-opens=java.base/java.net=ALL-UNNAMED \
--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED \
--add-opens=java.base/java.math=ALL-UNNAMED \
--add-opens=java.base/java.security=ALL-UNNAMED \
--add-opens=java.base/java.text=ALL-UNNAMED \
--add-opens=java.base/java.time=ALL-UNNAMED \
--add-opens=java.base/java.util=ALL-UNNAMED \
--add-opens=java.base/jdk.internal.access=ALL-UNNAMED \
--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED \
--add-exports=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED \
--add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED \
--add-exports=java.desktop/sun.awt=ALL-UNNAMED \
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor.event=ALL-UNNAMED \
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED \
--add-exports=java.desktop/sun.swing=ALL-UNNAMED \
--add-exports=jdk.attach/sun.tools.attach=ALL-UNNAMED \
--add-opens=java.desktop/javax.swing.plaf.synth=ALL-UNNAMED \
--add-opens=java.desktop/javax.swing=ALL-UNNAMED \
--add-opens=java.desktop/javax.swing.plaf.basic=ALL-UNNAMED"

# 执行 Java 进程 (使用 exec 确保 java 进程为 PID 1，以便接收退出信号)
exec java ${DEFAULT_JVM_OPTS} ${MODULE_OPTS} ${VM_OPTIONS} -jar spring-boot-quick.jar
