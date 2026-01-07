FROM amazoncorretto:17-alpine

# 设置时区并安装字体支持库（核心增加部分）
# fontconfig: 字体配置管理
# ttf-dejavu: 一种常用的开源字体，防止验证码找不到默认字体
RUN apk add --no-cache tzdata fontconfig ttf-dejavu && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

WORKDIR /data
COPY entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
COPY quick-core/target/spring-boot-quick.jar spring-boot-quick.jar

# 默认环境变量
ENV PROFILE=dev \
    HeapSize=512m \
    MetaspaceSize=256m \
    VM_OPTIONS=""

EXPOSE 8080

# 使用脚本启动
ENTRYPOINT ["./entrypoint.sh"]
