#分布式事务解决方案seata样例

本样例 一共4个服务
eureka server
seata-rm-one
seata-rm-two
seata-rm-three


eureka 负责  seata-server 和 3个业务模块的服务注册 和 发现

seata-rm-one  业务模块1
seata-rm-two  业务模块2
seata-rm-three 业务模块3

业务流程  由 模块1 发起全局事务 ,模块2 模块 3 创建分支事务, 然后 模块1  异常 执行 全局事务回滚
``` 
操作步骤

1. 下载seata server。
   github：https://github.com/seata/seata/releases 下载可能较慢
   seata官网 http://seata.io/zh-cn/blog/download.html 下载
   
2. 修改file.conf

   sh
   service {
     #transaction service group mapping
     #修改，可不改,my_test_tx_group随便起名字。
     vgroup_mapping.my_test_tx_group = "default"
     #only support when registry.type=file, please don't set multiple addresses
     # 此服务的地址
     default.grouplist = "127.0.0.1:8091"
     #disable seata
     disableGlobalTransaction = false
   }
   
   store {
     ## store mode: file、db
     # 修改
     mode = "db"
   
     ## file store property
     file {
       ## store location dir
       dir = "sessionStore"
     }
   
     ## database store property
     #db信息修改
     db {
       ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
   	
       datasource = "druid"
       ## mysql/oracle/h2/oceanbase etc.
       db-type = "mysql"
       driver-class-name = "com.mysql.cj.jdbc.Driver"
       url = "jdbc:mysql://192.168.153.2:3306/seata-server?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai"
       user = "root"
       password = "123456"
     }
   }
 ```

3. registry.conf
   
   ```sh
   #指定服务注册中心 这里我们使用eureka  
   registry {
     # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
     #修改
     type = "eureka"
   
     nacos {
       serverAddr = "localhost"
       namespace = ""
       cluster = "default"
     }
     #修改
     eureka {
       serviceUrl = "http://root:root@localhost:18761/eureka"
       application = "default"
       weight = "1"
     }
     redis {
       serverAddr = "localhost:6379"
       db = "0"
     }
     zk {
       cluster = "default"
       serverAddr = "127.0.0.1:2181"
       session.timeout = 6000
       connect.timeout = 2000
     }
     consul {
       cluster = "default"
       serverAddr = "127.0.0.1:8500"
     }
     etcd3 {
       cluster = "default"
       serverAddr = "http://localhost:2379"
     }
     sofa {
       serverAddr = "127.0.0.1:9603"
       application = "default"
       region = "DEFAULT_ZONE"
       datacenter = "DefaultDataCenter"
       cluster = "default"
       group = "SEATA_GROUP"
       addressWaitTime = "3000"
     }
     file {
       name = "file.conf"
     }
   }
   
   config {
     # file、nacos 、apollo、zk、consul、etcd3
     type = "file"
   
     nacos {
       serverAddr = "localhost"
       namespace = ""
     }
     consul {
       serverAddr = "127.0.0.1:8500"
     }
     apollo {
       app.id = "seata-server"
       apollo.meta = "http://192.168.1.204:8801"
     }
     zk {
       serverAddr = "127.0.0.1:2181"
       session.timeout = 6000
       connect.timeout = 2000
     }
     etcd3 {
       serverAddr = "http://localhost:2379"
     }
     file {
       name = "file.conf"
     }
   }
   
   ```

4. 创建数据库，并建表
    
   ```sh
   三个 seata-server 要用到的表
   
   分支事务表
   CREATE TABLE `branch_table` (
     `branch_id` bigint(20) NOT NULL,
     `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `transaction_id` bigint(20) DEFAULT NULL,
     `resource_group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `branch_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `status` tinyint(4) DEFAULT NULL,
     `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `application_data` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `gmt_create` datetime DEFAULT NULL,
     `gmt_modified` datetime DEFAULT NULL,
     PRIMARY KEY (`branch_id`) USING BTREE,
     KEY `idx_xid` (`xid`) USING BTREE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
   
   全局事务表
   CREATE TABLE `global_table` (
     `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `transaction_id` bigint(20) DEFAULT NULL,
     `status` tinyint(4) NOT NULL,
     `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `transaction_service_group` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `transaction_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `timeout` int(11) DEFAULT NULL,
     `begin_time` bigint(20) DEFAULT NULL,
     `application_data` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `gmt_create` datetime DEFAULT NULL,
     `gmt_modified` datetime DEFAULT NULL,
     PRIMARY KEY (`xid`) USING BTREE,
     KEY `idx_gmt_modified_status` (`gmt_modified`,`status`) USING BTREE,
     KEY `idx_transaction_id` (`transaction_id`) USING BTREE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
   
   
   全局锁
   CREATE TABLE `lock_table` (
     `row_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `xid` varchar(96) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `transaction_id` bigint(20) DEFAULT NULL,
     `branch_id` bigint(20) NOT NULL,
     `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `pk` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     `gmt_create` datetime DEFAULT NULL,
     `gmt_modified` datetime DEFAULT NULL,
     PRIMARY KEY (`row_key`) USING BTREE,
     KEY `idx_branch_id` (`branch_id`) USING BTREE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
   
   
   ```

   表的结构不能错。

5. 接着改RM中的数据库。在每个库中增加。用于回滚。

   ```sh
   用于RM回滚的。
   CREATE TABLE `undo_log` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `branch_id` bigint(20) NOT NULL,
     `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `rollback_info` longblob NOT NULL,
     `log_status` int(11) NOT NULL,
     `log_created` datetime NOT NULL,
     `log_modified` datetime NOT NULL,
     `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
   
   
   ```
三个业务表
````
CREATE TABLE `One` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Two` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Three` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

````

6. 启动seata-server，（seata-server.bat），去eureka中看效果。

   