#!/bin/bash

export xdb_home=cos://akd-lake-1254213275/xdb/starrocks-2.3.0
export emr_sr_home=/usr/local/service/starrocks

# be/lib
## backup the old starrocks_be binary file to starrocks_be.bak
mv ${emr_sr_home}/lib/be/starrocks_be ${emr_sr_home}/lib/be/starrocks_be.bak
coscli sync ${xdb_home}/be/lib/starrocks_be  ${emr_sr_home}/lib/be/starrocks_be

chown hadoop:hadoop ${emr_sr_home}/lib/be/starrocks_be
chmod +x ${emr_sr_home}/lib/be/starrocks_be