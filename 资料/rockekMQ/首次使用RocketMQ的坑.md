## 首次使用RocketMQ的坑

1、在java端进行rocketmq操作的时候，依赖的版本必须和服务端使用rocketmq版本完全一致。

eg: 安装的版本如果是4.4.0 那么引入的依赖的版本必须是4.4.0 ，否则导致连接失败。

2、必须开启9876/10911端口，建议学习阶段把服务器防火墙关闭，或者放行对应端口。

3、如果在服务上面有多张网卡，它随机选择一张网卡IP地址进行broker绑定。

```
需要导出默认参数进行配置:
brokerIP1 = broker的ip地址
nameservAddr = nameserver ip地址和端口
```

4、在官方文档中异步发送信息的代码中，producer对象在信息发送完毕前不能关闭，否则会出现异常。

## RocketMQ的常用指令:

- 启动nameserver
  - nohup mqnamsrv &

- 启动broker

  - nohup sh mqbroker -n 192.168.3.222:9876 & (原始方式启动)
  - nohup sh mqbroker -c  broker.p & (制定配置文件启动broker)

- 关闭namesrv / broker

  - sh mqshutdown broker (关闭broker)

  - sh mqshutdown namesrv (关闭namesrv)

- 手动创建Topic

  - sh mqadmin updateTopic -b 192.168.3.222:10911 -n 192.168.3.222:9876 -t topic1

    - -b broker服务地址和端口

    - -n nameserver的服务地址和端口

    - -t  topic名称

- 查询当前broker上面的topic列表
  - sh bin/mqadmin topicList –n 192.168.3.222:9876

- 查看broker的默认配置参数
  - sh mqbroker -m
- 导出broker的配置信息到broker.properties文件
  - sh mqbroker -m > broker.properties