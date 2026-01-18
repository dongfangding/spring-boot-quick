#!/bin/bash

sudo chown -R 999:999 mysql
sudo chmod -R 755 mysql

sudo chown -R 1000:1000 es

sudo docker comopose up -d
