#!/bin/bash

# 文件赋权, 将当前目录下的所有文件的归属权给当前脚本执行用户，防止挂载目录时权限问题    
sudo chown -R $(id -u):$(id -g) .

# 创建集群网络
docker network create \
    --driver bridge \
    --subnet 172.20.0.0/16 \
    --gateway 172.20.0.1 \
    middleware-network

sudo bash nacos/mysql-init.sh
sudo bash start.sh