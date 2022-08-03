#!/bin/bash

export xdb_home=cos://akd-lake-1254213275/xdb/starrocks-2.3.0
export emr_sr_home=/usr/local/service/starrocks

rm -rf /data/emr/starrocks/fe/log/*
rm -rf /data/emr/starrocks/fe/meta/*


# fe/bin
rm -rf ${emr_sr_home}/bin/show_fe_version.sh
rm -rf ${emr_sr_home}/starrocks/bin/start_fe.sh
rm -rf ${emr_sr_home}/bin/stop_fe.sh
coscli sync ${xdb_home}/fe/bin/ ${emr_sr_home}/bin/ -r --thread-num 16
chmod +x ${emr_sr_home}/bin/*
# fe/conf

# fe/lib
rm -rf ${emr_sr_home}/lib/fe/*
coscli sync  ${xdb_home}/fe/lib ${emr_sr_home}/lib/fe/ -r --thread-num 16

# fe/spark-dpp
rm -rf ${emr_sr_home}/spark-dpp/*
coscli sync  ${xdb_home}/fe/spark-dpp/ ${emr_sr_home}/spark-dpp/ -r --thread-num 16

# fe/webroot
rm -rf ${emr_sr_home}/webroot/*
coscli sync  ${xdb_home}/fe/webroot ${emr_sr_home}/webroot/ -r --thread-num 16

# udf
rm -rf ${emr_sr_home}/udf/*
coscli sync  ${xdb_home}/udf/ ${emr_sr_home}/udf/ -r --thread-num 16



chown hadoop:hadoop -R ${emr_sr_home}