#!/bin/bash

# 获取脚本所在目录的绝对路径
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# 文件赋权, 将当前目录下的所有文件的归属权给当前脚本执行用户，防止挂载目录时权限问题
USER_ID=$(id -u)
USER_GID=$(id -g)
sudo chown -R ${USER_ID}:${USER_GID} .
# mysql内部用户uid是999，必须给对应权限，否则无法操作数据库
sudo chown -R 999:999 mysql

# 创建集群网络
sudo docker network create \
    --driver bridge \
    --subnet 172.20.0.0/16 \
    --gateway 172.20.0.1 \
    middleware-network

sudo bash "${SCRIPT_DIR}/nacos/mysql-init.sh"
sudo bash "${SCRIPT_DIR}/start.sh"
