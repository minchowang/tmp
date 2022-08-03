#!/bin/bash

export xdb_home=cos://akd-lake-1254213275/xdb/starrocks-2.3.0
export emr_sr_home=/usr/local/service/starrocks

# fe/lib
## backup the old starrocks-fe.jar binary file to starrocks-fe.jar.bak
mv ${emr_sr_home}/lib/fe/starrocks-fe.jar ${emr_sr_home}/lib/fe/starrocks-fe.jar.bak
coscli sync ${xdb_home}/fe/lib/starrocks-fe.jar  ${emr_sr_home}/lib/fe/starrocks-fe.jar


chown hadoop:hadoop  ${emr_sr_home}/lib/fe/starrocks-fe.jar