# 使用 OpenJDK 17镜像作为基础镜像
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/openjdk:17-jdk-alpine

# 将本地构建好的 JAR 文件复制到容器中
# 这已经在本地运行了 `mvn -U clean package -DskipTests` 并生成了 JAR 文件
COPY quick-core/target/spring-boot-quick-exec.jar spring-boot-quick-exec.jar
COPY k8s/arthas.zip arthas.zip


ENV NACOS_USERNAME=0 \
    NACOS_PASSWORD=0 \
    NACOS_SERVER=0 \
    NACOS_KEY=0 \
    HeapSize=512m \
    MetaspaceSize=256m \
    PROFILE=dev \
    # 允许自己附加的VM参数拼接到最后
    VM_OPTIONS=""


EXPOSE 9107

# 配置启动参数
CMD java -jar \
    -Xmx${HeapSize} -Xms${HeapSize} -XX:MetaspaceSize=${MetaspaceSize} -XX:MaxMetaspaceSize=${MetaspaceSize} \
    -Dspring.profiles.active=${PROFILE} \
    -Ddruid.mysql.usePingMethod=false \
    -Duser.timezone=GMT+08 \
    -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -Dnacos_username=${NACOS_USERNAME} \
    -Dnacos_password=${NACOS_PASSWORD} \
    -Dnacos_server=${NACOS_SERVER} \
    -Dnacos_key=${NACOS_KEY} \
    -XX:+IgnoreUnrecognizedVMOptions \
    -XX:+UseZGC \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=logs \
    -XX:+PrintCommandLineFlags \
    -XX:+DisableExplicitGC \
    --add-exports=java.base/sun.nio.ch=ALL-UNNAMED \
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
    --add-opens=java.desktop/javax.swing.plaf.basic=ALL-UNNAMED \
    ${VM_OPTIONS} \
    spring-boot-quick-exec.jar
