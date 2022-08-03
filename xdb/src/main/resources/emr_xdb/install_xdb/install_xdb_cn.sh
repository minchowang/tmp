#!/bin/bash

export xdb_home=cos://akd-lake-1254213275/xdb/starrocks-2.3.0
export emr_sr_home=/usr/local/service/starrocks


# be/bin
rm -rf ${emr_sr_home}/bin/*
coscli sync ${xdb_home}/be/bin/ ${emr_sr_home}/bin/ -r --thread-num 16

rm -rf ${emr_sr_home}/bin/start_be.sh
rm -rf ${emr_sr_home}/bin/stop_be.sh

chmod +x ${emr_sr_home}/bin/*
ln -s ${emr_sr_home}/bin/start_cn.sh ${emr_sr_home}/bin/start_be.sh
ln -s ${emr_sr_home}/bin/stop_cn.sh ${emr_sr_home}/bin/stop_be.sh

# be/conf
# rm -rf ${emr_sr_home}/conf/cn.conf
ln -s  ${emr_sr_home}/conf/be.conf  ${emr_sr_home}/conf/cn.conf

# be/lib
rm -rf ${emr_sr_home}/lib/be/*
coscli sync  ${xdb_home}/be/lib/  ${emr_sr_home}/lib/be/ -r --thread-num 16

# be/log

# be/www
rm -rf ${emr_sr_home}/www/*
coscli sync  ${xdb_home}/be/www/  ${emr_sr_home}/www/ -r --thread-num 16

chown hadoop:hadoop -R ${emr_sr_home}

# we storage, cn also need it, otherwise cn cannot startup.
mkdir -p /data/emr/starrocks/we/data
chown hadoop:hadoop -R /data/emr/starrocks/we/data