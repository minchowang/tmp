-- 创建数据库
CREATE DATABASE xdb
-- 查看数据库
SHOW DATABASES
-- 数据库跳转
USE xdb;
-- 展示表
SHOW TABLES
-- 查看表结构
SHOW CREATE TABLE xdb_t4
-- 如果没有表 先创建表
CREATE TABLE xdb_t4
 (
  `id` VARCHAR(32),
  `comid` VARCHAR(32),
  `billtime` DATETIME,
  `billdate` DATE,
  `bigstate` BIGINT,
  `state` INT ,
  `price` FLOAT,
  `price2` DOUBLE,
  `amount` DECIMAL(18,2),
  `isv` BOOLEAN
)
ENGINE = xdb
PRIMARY KEY (id)
PARTITION BY KEY(comid,YEAR(billtime))
--如果建错表可以删除表
DROP TABLE xdb_t4
-- 测试先增加分区(如果已有分区请忽视)
ALTER TABLE xdb_t4 ADD PARTITION C012022 VALUE ('C01','2022');
ALTER TABLE xdb_t4 ADD PARTITION C012021 VALUE ('C01','2021');
ALTER TABLE xdb_t4 ADD PARTITION C012020 VALUE ('C01','2020');
--如果分区建错可以通过下面语句进行删除
ALTER TABLE xdb_t4 DROP PARTITION C012020;
--通过下面语句可以查看分区
SHOW PARTITIONS FROM xdb_t4;
--第一个值可以修改数据桶数，不修改默认为1；第二个值为并行度，不修改默认为1
ALTER TABLE xdb_t4 PARTITION C01 BUCKETS 5,4
-- 通过下面语句可以查看桶数和并行度
SHOW BUCKETS FROM xdb_t4 PARTITION C01
--如果需要清理数据通过下面语句
truncate table xdb_t4;
