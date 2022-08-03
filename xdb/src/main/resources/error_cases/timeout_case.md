
# 在mysql执行时，得到 Unexpected exception: null
- 问题描述：

```mysql
mysql> compact fba_daily_inventory_history_report_mws;
ERROR 1064 (HY000): Unexpected exception: null
```
- FE.log如下
```shell
2022-08-03 11:25:06,593 WARN (starrocks-mysql-nio-pool-33|313) [ConnectProcessor.handleQuery():326] Process one query failed because unknown reason:
java.lang.reflect.UndeclaredThrowableException: null
        at com.sun.proxy.$Proxy28.mergeRecord(Unknown Source) ~[?:?]
        at com.starrocks.xdb.XdbRpcClient.merge(XdbRpcClient.java:274) ~[starrocks-fe.jar:?]
        at com.starrocks.sql.parser.XdbSqlParser.parse(XdbSqlParser.java:200) ~[starrocks-fe.jar:?]
        at com.starrocks.qe.ConnectProcessor.handleQuery(ConnectProcessor.java:273) ~[starrocks-fe.jar:?]
        at com.starrocks.qe.ConnectProcessor.dispatch(ConnectProcessor.java:578) ~[starrocks-fe.jar:?]
        at com.starrocks.qe.ConnectProcessor.processOnce(ConnectProcessor.java:813) ~[starrocks-fe.jar:?]
        at com.starrocks.mysql.nio.ReadListener.lambda$handleEvent$0(ReadListener.java:55) ~[starrocks-fe.jar:?]
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [?:1.8.0_282]
        at java.lang.Thread.run(Thread.java:748) [?:1.8.0_282]
Caused by: com.baidu.jprotobuf.pbrpc.ErrorDataException: A error occurred: errorCode=62 errorMessage:method request time out, please check 'onceTalkTimeout' property. current value is:50000(MILLISECONDS) correlationId:95 timeout with bound channel =>[id: 0x2aa790cb, L:/10.49.26.50:35704 - R:/10.49.25.138:9876]
        at com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy.doWaitCallback(ProtobufRpcProxy.java:651) ~[jprotobuf-rpc-core-4.2.1.jar:?]
        at com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy.invoke(ProtobufRpcProxy.java:587) ~[jprotobuf-rpc-core-4.2.1.jar:?]
        ... 10 more
```



  
- drop table 相同错误对应日志
```shell
2022-08-03 11:42:52,704 WARN (heartbeat mgr|42) [HeartbeatMgr.runAfterCatalogReady():152] got exception when doing heartbeat
java.util.concurrent.ExecutionException: java.lang.reflect.UndeclaredThrowableException
        at java.util.concurrent.FutureTask.report(FutureTask.java:122) ~[?:1.8.0_282]
        at java.util.concurrent.FutureTask.get(FutureTask.java:192) ~[?:1.8.0_282]
        at com.starrocks.system.HeartbeatMgr.runAfterCatalogReady(HeartbeatMgr.java:142) [starrocks-fe.jar:?]
        at com.starrocks.common.util.MasterDaemon.runOneCycle(MasterDaemon.java:61) [starrocks-fe.jar:?]
        at com.starrocks.common.util.Daemon.run(Daemon.java:115) [starrocks-fe.jar:?]
Caused by: java.lang.reflect.UndeclaredThrowableException
        at com.sun.proxy.$Proxy28.updateWeList(Unknown Source) ~[?:?]
        at com.starrocks.xdb.XdbRpcClient.weList(XdbRpcClient.java:103) ~[starrocks-fe.jar:?]
        at com.starrocks.xdbmeta.XdbHeart.put(XdbHeart.java:35) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:279) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:215) ~[starrocks-fe.jar:?]
        at java.util.concurrent.FutureTask.run(FutureTask.java:266) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_282]
        at java.lang.Thread.run(Thread.java:748) ~[?:1.8.0_282]
Caused by: com.baidu.jprotobuf.pbrpc.ErrorDataException: A error occurred: errorCode=62 errorMessage:method request time out, please check 'onceTalkTimeout' property. current value is:50000(MILLISECONDS) correlationId:5 timeout with bound channel =>[id: 0x5e46c4ef, L:/10.49.26.50:48150 - R:/10.49.24.162:9876]
        at com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy.doWaitCallback(ProtobufRpcProxy.java:651) ~[jprotobuf-rpc-core-4.2.1.jar:?]
        at com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy.invoke(ProtobufRpcProxy.java:587) ~[jprotobuf-rpc-core-4.2.1.jar:?]
        at com.sun.proxy.$Proxy28.updateWeList(Unknown Source) ~[?:?]
        at com.starrocks.xdb.XdbRpcClient.weList(XdbRpcClient.java:103) ~[starrocks-fe.jar:?]
        at com.starrocks.xdbmeta.XdbHeart.put(XdbHeart.java:35) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:279) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:215) ~[starrocks-fe.jar:?]
        at java.util.concurrent.FutureTask.run(FutureTask.java:266) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_282]
        at java.lang.Thread.run(Thread.java:748) ~[?:1.8.0_282]
2022-08-03 11:42:52,709 WARN (heartbeat mgr|42) [HeartbeatMgr.runAfterCatalogReady():152] got exception when doing heartbeat
java.util.concurrent.ExecutionException: java.lang.reflect.UndeclaredThrowableException
        at java.util.concurrent.FutureTask.report(FutureTask.java:122) ~[?:1.8.0_282]
        at java.util.concurrent.FutureTask.get(FutureTask.java:192) ~[?:1.8.0_282]
        at com.starrocks.system.HeartbeatMgr.runAfterCatalogReady(HeartbeatMgr.java:142) [starrocks-fe.jar:?]
        at com.starrocks.common.util.MasterDaemon.runOneCycle(MasterDaemon.java:61) [starrocks-fe.jar:?]
        at com.starrocks.common.util.Daemon.run(Daemon.java:115) [starrocks-fe.jar:?]
Caused by: java.lang.reflect.UndeclaredThrowableException
        at com.sun.proxy.$Proxy28.updateWeList(Unknown Source) ~[?:?]
        at com.starrocks.xdb.XdbRpcClient.weList(XdbRpcClient.java:103) ~[starrocks-fe.jar:?]
        at com.starrocks.xdbmeta.XdbHeart.put(XdbHeart.java:35) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:279) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:215) ~[starrocks-fe.jar:?]
        at java.util.concurrent.FutureTask.run(FutureTask.java:266) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_282]
        at java.lang.Thread.run(Thread.java:748) ~[?:1.8.0_282]
Caused by: com.baidu.jprotobuf.pbrpc.ErrorDataException: A error occurred: errorCode=62 errorMessage:method request time out, please check 'onceTalkTimeout' property. current value is:50000(MILLISECONDS) correlationId:7 timeout with bound channel =>[id: 0xfa3cdc25, L:/10.49.26.50:39372 - R:/10.49.25.138:9876]
        at com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy.doWaitCallback(ProtobufRpcProxy.java:651) ~[jprotobuf-rpc-core-4.2.1.jar:?]
        at com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy.invoke(ProtobufRpcProxy.java:587) ~[jprotobuf-rpc-core-4.2.1.jar:?]
        at com.sun.proxy.$Proxy28.updateWeList(Unknown Source) ~[?:?]
        at com.starrocks.xdb.XdbRpcClient.weList(XdbRpcClient.java:103) ~[starrocks-fe.jar:?]
        at com.starrocks.xdbmeta.XdbHeart.put(XdbHeart.java:35) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:279) ~[starrocks-fe.jar:?]
        at com.starrocks.system.HeartbeatMgr$BackendHeartbeatHandler.call(HeartbeatMgr.java:215) ~[starrocks-fe.jar:?]
        at java.util.concurrent.FutureTask.run(FutureTask.java:266) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_282]
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_282]
        at java.lang.Thread.run(Thread.java:748) ~[?:1.8.0_282]       
```        
 