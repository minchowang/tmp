#!/bin/bash

export xdb_home=cos://akd-lake-1254213275/xdb/starrocks-2.3.0
export emr_sr_home=/usr/local/service/starrocks

rm -rf /data/emr/starrocks/be/storage/*
rm -rf /data/emr/starrocks/be/log/*

rm -rf /data/emr/starrocks/we/data/*


# be/bin
rm -rf ${emr_sr_home}/bin/show_be_version.sh
rm -rf ${emr_sr_home}/starrocks/bin/start_be.sh
rm -rf ${emr_sr_home}/bin/stop_be.sh
rm -rf ${emr_sr_home}/bin/meta_tool.sh
coscli sync ${xdb_home}/be/bin/ ${emr_sr_home}/bin/ -r --thread-num 16
chmod +x ${emr_sr_home}/bin/*
# be/conf


# be/lib
rm -rf ${emr_sr_home}/lib/be/*
coscli sync  ${xdb_home}/be/lib/  ${emr_sr_home}/lib/be/ -r --thread-num 16

# be/log

# be/www
rm -rf ${emr_sr_home}/www/*
coscli sync  ${xdb_home}/be/www/  ${emr_sr_home}/www/ -r --thread-num 16

chown hadoop:hadoop -R ${emr_sr_home}
# we storage
mkdir -p /data/emr/starrocks/we/data
chown hadoop:hadoop -R /data/emr/starrocks/we/data