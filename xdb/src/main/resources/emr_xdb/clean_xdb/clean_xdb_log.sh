#!/bin/bash

export xdb_home=cos://akd-lake-1254213275/xdb/starrocks-2.3.0
export emr_sr_home=/usr/local/service/starrocks


rm -rf /data/emr/starrocks/fe/log/*
rm -rf /data/emr/starrocks/be/log/*