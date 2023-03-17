#!/bin/bash
echo "开始执行部署脚本>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
source /etc/profile
source /opt/services/spring-boot-quick/.env

DATE=$(date +%Y%m%d%H%M)
# 基础路径
BASE_PATH=/opt/services/spring-boot-quick
# 服务名称。同时约定部署服务的 jar 包名字也为它。
SERVER_NAME=spring-boot-quick
# 环境
PROFILES_ACTIVE=dev
# heapError 存放路径
TEMP_LOG_PATH=$BASE_PATH/logs
# JVM 参数
JAVA_OPS="-Xmx512m -Xms512m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -XX:SurvivorRatio=8 -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:MaxGCPauseMillis=150 -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$TEMP_LOG_PATH -Xloggc:$TEMP_LOG_PATH/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30M -Duser.timezone=GMT+08 -Dredis_host=${redis_host} -Dredis_port=${redis_port} -Dredis_database=${redis_database} -Dredis_password=${redis_password} -Dmysql_host=${mysql_host} -Dmysql_port=${mysql_port} -Dmysql_db=${mysql_db} -Dmysql_username=${mysql_username} -Dmysql_password=${mysql_password} -Drsa_primaryKey=${rsa_primaryKey} -Drsa_publicKey=${rsa_publicKey} -Ddruid_stat_enable=${druid_stat_enable} -Ddruid_state_username=${druid_state_username} -Ddruid_state_password=${druid_state_password}"
PROFILES_ACTIVE=dev

# 如果不存在，则无需备份
if [ ! -f "$BASE_PATH/$SERVER_NAME.jar" ]; then
    echo "[backup] $BASE_PATH/$SERVER_NAME.jar 不存在，跳过备份"
# 如果存在，则备份到 backup 目录下，使用时间作为后缀
else
    echo "[backup] 开始备份 $SERVER_NAME ..."
    cp $BASE_PATH/$SERVER_NAME.jar $BASE_PATH/backup/$SERVER_NAME-$DATE.jar
    echo "[backup] 备份 $SERVER_NAME 完成"
fi

# 开始启动, 这里没有用后台启动，因为会用到别的进程管理工具
java -server $JAVA_OPS -jar $BASE_PATH/$SERVER_NAME.jar --spring.profiles.active=$PROFILES_ACTIVE
echo "[start] 启动 $BASE_PATH/$SERVER_NAME 完成"

