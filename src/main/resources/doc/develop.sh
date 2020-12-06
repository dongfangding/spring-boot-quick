#!/bin/bash
# service running script.
echo "开始执行部署脚本>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
source /etc/profile
source /opt/services/boot-quick/.env
action=$1
app=spring-boot-quick-exec.jar

if [ ${action} == 'start' ];then
  echo "start........."
  java -server -Dserver.port=8082 -Xmx512m -Xms512m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -XX:SurvivorRatio=8 -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:MaxGCPauseMillis=150 -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=./ -Xloggc:./gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30M -Duser.timezone=GMT+08 -Dspring.profiles.active=dev -Dredis_host=${redis_host} -Dredis_port=${redis_port} -Dredis_password=${redis_password} -Dzk_addr=${zk_addr} -Dmysql_host=${mysql_host} -Dmysql_port=${mysql_port} -Dmysql_db=${mysql_db} -Dmysql_username=${mysql_username} -Dmysql_password=${mysql_password} -Drabbit_address=${rabbit_address} -Drabbit_username=${rabbit_username} -Drabbit_password=${rabbit_password} -Drocketmq_name_server=${rocketmq_name_server} -Dmongodb_url=${mongodb_url} -jar $app > /dev/null 2>&1 &
elif [ ${action} == 'stop' ];then
  echo "stop.............."
  ps -ef| grep $app | grep -v grep | awk '{print $2}' | xargs kill -15
elif [ ${action} == 'restart' ];then
  echo "restart..................."
  ps -ef| grep $app | grep -v grep | awk '{print $2}' | xargs kill -9
  sleep 2
  java -server -Dserver.port=8082 -Xmx512m -Xms512m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -XX:SurvivorRatio=8 -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:MaxGCPauseMillis=150 -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=./ -Xloggc:./gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30M -Duser.timezone=GMT+08 -Dspring.profiles.active=dev -Dredis_host=${redis_host} -Dredis_port=${redis_port} -Dredis_password=${redis_password} -Dzk_addr=${zk_addr} -Dmysql_host=${mysql_host} -Dmysql_port=${mysql_port} -Dmysql_db=${mysql_db} -Dmysql_username=${mysql_username} -Dmysql_password=${mysql_password} -Drabbit_address=${rabbit_address} -Drabbit_username=${rabbit_username} -Drabbit_password=${rabbit_password} -Drocketmq_name_server=${rocketmq_name_server} -Dmongodb_url=${mongodb_url} -jar $app > /dev/null 2>&1 &
fi
echo "部署脚本执行结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"