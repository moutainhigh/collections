------

## RocketMQ 集群搭建（两主两从）

#### RocketMQ 架构图



![img](https:////upload-images.jianshu.io/upload_images/15356343-53ff81bb5f88da56.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)

> 消息中间件和RPC最大区别：Broker Cluster存储

**NameServer**: 提供轻量级的服务发现和路由。 每个 NameServer 记录完整的路由信息，提供等效的读写服务，并支持快速存储扩展。

**Broker**: 通过提供轻量级的 Topic 和 Queue 机制来处理消息存储,同时支持推（push）和拉（pull）模式以及主从结构的容错机制。

**Producer**：生产者，产生消息的实例，拥有相同 Producer Group 的 Producer 组成一个集群。

**Consumer**：消费者，接收消息进行消费的实例，拥有相同 Consumer Group 的
 Consumer 组成一个集群。

从 Broker 开始，Broker Master1 和 Broker Slave1 是主从结构，它们之间会进行数据同步，即 Date Sync。同时每个 Broker 与
 NameServer 集群中的所有节
 点建立长连接，定时注册 Topic 信息到所有 NameServer 中。

Producer 与 NameServer 集群中的其中一个节点（随机选择）建立长连接，定期从 NameServer 获取 Topic 路由信息，并向提供 Topic 服务的 Broker Master 建立长连接，且定时向 Broker 发送心跳。

Producer 只能将消息发送到 Broker master，但是 Consumer 则不一样，它同时和提供 Topic 服务的 Master 和 Slave
 建立长连接，既可以从 Broker Master 订阅消息，也可以从 Broker Slave 订阅消息。

------

#### RocketMQ 集群部署模式

#### 单 master 模式：

**优点**：除了配置简单没什么优点，适合个人学习使用。

**缺点**：不可靠，该机器重启或宕机，将导致整个服务不可用。

#### 多 master 模式：

多个 master 节点组成集群，单个 master 节点宕机或者重启对应用没有影响。

**优点**：所有模式中性能最高

**缺点**：单个 master 节点宕机期间，未被消费的消息在节点恢复之前不可用，消息的实时性就受到影响。

**注意**：使用同步刷盘可以保证消息不丢失，同时 Topic 相对应的 queue 应该分布在集群中各个节点，而不是只在某各节点上，否则，该节点宕机会对订阅该 topic 的应用造成影响。

#### 多 master 多 slave 异步复制模式：

在多 master 模式的基础上，每个 master 节点都有至少一个对应的 slave。master
 节点可读可写，但是 slave 只能读不能写，类似于 mysql 的主备模式。

**优点**： 在 master 宕机时，消费者可以从 slave读取消息，消息的实时性不会受影响，性能几乎和多 master 一样。

**缺点**：使用异步复制的同步方式有可能会有消息丢失的问题。

#### 多 master 多 slave 同步双写模式：

同多 master 多 slave 异步复制模式类似，区别在于 master 和 slave 之间的数据同步方式。

**优点**：同步双写的同步模式能保证数据不丢失。

**缺点**：发送单个消息 RT 会略长，性能相比异步复制低10%左右。

**刷盘策略**：同步刷盘和异步刷盘（指的是节点自身数据是同步还是异步存储）

**同步方式**：同步双写和异步复制（指的一组 master 和 slave 之间数据的同步）

**注意**：要保证数据可靠，需采用同步刷盘和同步双写的方式，但性能会较其他方式低。

------

#### 开始集群搭建：2M-2S-SYNC(两主两从同步写)

| 软件及版本   | 下载地址                                                     |
| ------------ | ------------------------------------------------------------ |
| rocketmq-4.2 | <https://www.apache.org/dyn/closer.cgi?path=rocketmq/4.2.0/rocketmq-all-4.2.0-source-release.zip> |
| jdk1.8       | <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html> |

##### 环境介绍:

| IP            | 部署服务   | 角色   |
| ------------- | ---------- | ------ |
| 192.168.3.172 | NameServer | --     |
| 192.168.3.201 | NameServer | --     |
| 192.168.3.220 | broker-a   | master |
| 192.168.3.220 | broker-a-s | slave  |
| 192.168.3.222 | broker-b   | master |
| 192.168.3.222 | broker-b-s | slave  |

##### 环境变量配置：

```
#设置jdk环境变量
export JAVA_HOME=/usr/local/java/jdk1.8.0_191  #jdk安装目录

export JRE_HOME=${JAVA_HOME}/jre

export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:$CLASSPATH

export JAVA_PATH=${JAVA_HOME}/bin:${JRE_HOME}/bin

export PATH=$PATH:${JAVA_PATH}

#设置 rocketmq 环境变量
export ROCKETMQ_HOME=/usr/local/rocketmq/rocketmq-4.2

export PATH=$PATH::$ROCKETMQ_HOME/bin
```

##### 防火墙设置：

如果是开发环境 # service iptables stop 直接关闭防火墙
 若是生产环境就需要配置防火墙，增加端口规则，默认nameserver端口是9876，笔者直接就关闭防火墙服务。

##### 注：另一台机器也是如此操作！

##### 解压完成后创建目录：

```
mkdir /usr/local/rocketmq4.3/data/store 存储路径

mkdir /usr/local/rocketmq4.3/data/store/commitlog  commitLog 存储路径

mkdir /usr/local/rocketmq4.3/data/store/consumequeue 消费队列存储路径存储路径

mkdir /usr/local/rocketmq4.3/data/store/index 消息索引存储路径

[root@node-100 store]# pwd
/usr/local/rocketmq/rocketmq-4.2/data/store
[root@node-100 store]# ls
checkpoint  commitlog  config  consumequeue  index  lock  slave
[root@node-100 store]# 
```

##### 修改配置文件：

```
[root@node-100 2m-2s-sync]# pwd
/usr/local/rocketmq4.3/conf/2m-2s-sync
[root@node-100 2m-2s-sync]# ls
broker-a.properties  broker-a-s.properties  broker-b.properties  broker-b-s.properties  nohup.out
[root@node-100 2m-2s-sync]# vim broker-a.properties
```

##### 192.168.3.222机器配置如下：

##### broker-a.properties：

```
#所属集群名称，如果多个master，那么每个master配置的名称应该一致，要不然识别不了
brokerClusterName=rocketmq-cluster
#broker名称
brokerName=broker-a
#0 表示master，>0 表示slave
brokerId=0
# 指定IP地址
brokerIP1=192.168.3.222
#nameServer地址，分号隔开
namesrvAddr=192.168.3.172:9876;192.168.3.201:9876
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许broker自动创建topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许broker自动创建订阅组，建议线下开始，线上关闭
autoCreateSubscriptionGroup=true
#broker对外服务的监听端口，
#同一台机器部署多个broker，端口号要不同，且端口号之间要相距大些
listenPort=10911
#删除文件的时间节点，默认凌晨4点
deleteWhen=04
#文件保留时间，默认48小时
fileReservedTime=120
#commitLog每个文件的大小，默认大小1g
mapedFileSizeCommitLog=1073741824
#consumeQueue每个文件默认存30w条，根据自身业务进行调整
mapedFileSizeConsumeQueue=300000
destroyMapedFileInterval=120000
redeleteHangedFileInterval=120000
#检查物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#store存储路径，master与slave目录要不同
storePathRootDir=/usr/local/rocketmq/rocketmq-4.2/data/store
#commitLog存储路径
storePathCommitLog=/usr/local/rocketmq/rocketmq-4.2/data/store/commitlog
#限制的消息大小
maxMessageSize=65536
flushCommitLogLeastPages=4
flushConsumeQueueLeastPages=2
flushCommitLogThoroughInterval=10000
flushConsumeQueueThoroughInterval=60000
checkTransactionMessageEnable=false
#发消息线程池数
sendMessageThreadPoolNums=128
#拉去消息线程池数
pullMessageThreadPoolNums=128
#broker角色：
#ASYSC_MASTER 异步复制master
#SYSC_MASTER 同步复制master
#SLAVE 从
brokerRole=SYSC_MASTER
#刷盘方式
#ASYNC_FLUSH 异步刷盘
#SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
```

##### broker-a-s.properties：

```
#所属集群名称，如果多个master，那么每个master配置的名称应该一致，要不然识别不了
brokerClusterName=rocketmq-cluster
#broker名称
brokerName=broker-a
#0 表示master，>0 表示slave
brokerId=0
# 指定IP地址
brokerIP1=192.168.3.222
#nameServer地址，分号隔开
namesrvAddr=192.168.3.172:9876;192.168.3.201:9876
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许broker自动创建topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许broker自动创建订阅组，建议线下开始，线上关闭
autoCreateSubscriptionGroup=true
#broker对外服务的监听端口，
#同一台机器部署多个broker，端口号要不同，且端口号之间要相距大些
listenPort=10950
#删除文件的时间节点，默认凌晨4点
deleteWhen=04
#文件保留时间，默认48小时
fileReservedTime=120
#commitLog每个文件的大小，默认大小1g
mapedFileSizeCommitLog=1073741824
#consumeQueue每个文件默认存30w条，根据自身业务进行调整
mapedFileSizeConsumeQueue=300000
destroyMapedFileInterval=120000
redeleteHangedFileInterval=120000
#检查物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#store存储路径，master与slave目录要不同
storePathRootDir=/usr/local/rocketmq4.3/data/store/slave
#commitLog存储路径
storePathCommitLog=/usr/local/rocketmq4.3/data/store/slave/commitlog
#限制的消息大小
maxMessageSize=65536
flushCommitLogLeastPages=4
flushConsumeQueueLeastPages=2
flushCommitLogThoroughInterval=10000
flushConsumeQueueThoroughInterval=60000
checkTransactionMessageEnable=false
#发消息线程池数
sendMessageThreadPoolNums=128
#拉去消息线程池数
pullMessageThreadPoolNums=128
#broker角色：
#ASYSC_MASTER 异步复制master
#SYSC_MASTER 同步复制master
#SLAVE 从
brokerRole=SLAVE
#刷盘方式
#ASYNC_FLUSH 异步刷盘
#SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
```

##### 192.168.3.220机器配置如下：

##### broker-b.properties：

```
#所属集群名称，如果多个master，那么每个master配置的名称应该一致，要不然识别不了
brokerClusterName=rocketmq-cluster
#broker名称
brokerName=broker-b
#0 表示master，>0 表示slave
brokerId=0
# 指定IP地址
brokerIP1=192.168.3.220
#nameServer地址，分号隔开
namesrvAddr=192.168.3.172:9876;192.168.3.201:9876
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许broker自动创建topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许broker自动创建订阅组，建议线下开始，线上关闭
autoCreateSubscriptionGroup=true
#broker对外服务的监听端口，
#同一台机器部署多个broker，端口号要不同，且端口号之间要相距大些
listenPort=10911
#删除文件的时间节点，默认凌晨4点
deleteWhen=04
#文件保留时间，默认48小时
fileReservedTime=120
#commitLog每个文件的大小，默认大小1g
mapedFileSizeCommitLog=1073741824
#consumeQueue每个文件默认存30w条，根据自身业务进行调整
mapedFileSizeConsumeQueue=300000
destroyMapedFileInterval=120000
redeleteHangedFileInterval=120000
#检查物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#store存储路径，master与slave目录要不同
storePathRootDir=/usr/local/rocketmq4.3/rocketmq-4.2/data/store
#commitLog存储路径
storePathCommitLog=/usr/local/rocketmq4.3/data/store/commitlog
#限制的消息大小
maxMessageSize=65536
flushCommitLogLeastPages=4
flushConsumeQueueLeastPages=2
flushCommitLogThoroughInterval=10000
flushConsumeQueueThoroughInterval=60000
checkTransactionMessageEnable=false
#发消息线程池数
sendMessageThreadPoolNums=128
#拉去消息线程池数
pullMessageThreadPoolNums=128
#broker角色：
#ASYSC_MASTER 异步复制master
#SYSC_MASTER 同步复制master
#SLAVE 从
brokerRole=SYSC_MASTER
#刷盘方式
#ASYNC_FLUSH 异步刷盘
#SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
```

##### broker-b-s.properties：

```
#所属集群名称，如果多个master，那么每个master配置的名称应该一致，要不然识别不了
brokerClusterName=rocketmq-cluster
#broker名称
brokerName=broker-b
#0 表示master，>0 表示slave
brokerId=1
# 指定IP地址
brokerIP1=192.168.3.220
#nameServer地址，分号隔开
namesrvAddr=192.168.3.172:9876;192.168.3.201:9876
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许broker自动创建topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许broker自动创建订阅组，建议线下开始，线上关闭
autoCreateSubscriptionGroup=true
#broker对外服务的监听端口，
#同一台机器部署多个broker，端口号要不同，且端口号之间要相距大些
listenPort=10950
#删除文件的时间节点，默认凌晨4点
deleteWhen=04
#文件保留时间，默认48小时
fileReservedTime=120
#commitLog每个文件的大小，默认大小1g
mapedFileSizeCommitLog=1073741824
#consumeQueue每个文件默认存30w条，根据自身业务进行调整
mapedFileSizeConsumeQueue=300000
destroyMapedFileInterval=120000
redeleteHangedFileInterval=120000
#检查物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#store存储路径，master与slave目录要不同
storePathRootDir=/usr/local/rocketmq4.3/data/store/slave
#commitLog存储路径
storePathCommitLog=/usr/local/rocketmq4.3/data/store/slave/commitlog
#限制的消息大小
maxMessageSize=65536
flushCommitLogLeastPages=4
flushConsumeQueueLeastPages=2
flushCommitLogThoroughInterval=10000
flushConsumeQueueThoroughInterval=60000
checkTransactionMessageEnable=false
#发消息线程池数
sendMessageThreadPoolNums=128
#拉去消息线程池数
pullMessageThreadPoolNums=128
#broker角色：
#ASYSC_MASTER 异步复制master
#SYSC_MASTER 同步复制master
#SLAVE 从
brokerRole=SLAVE
#刷盘方式
#ASYNC_FLUSH 异步刷盘
#SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
```

##### 修改日志配置文件：

```
#创建日志目录
mkdir -p /usr/local/rocketmq4.3/logs

#替换*.xml文件中的{user.home}为自己指定的目录
cd/usr/local/rocketmq4.3/conf && sed -i 's#${user.home}#/root/svr/rocketmq#g'
*.xml
```

##### 改参数：

runbroker.sh,runserver.sh启动参数默认对jvm的堆内存设置比较大(不改启动不起来)，如果是虚拟机非线上环境需要改下参数，大小可以根据自己机器来决定。

```
[root@node-100 bin]# pwd
/usr/local/rocketmq4.3/bin
[root@node-100 bin]# ls
cachedog.sh       mqadmin.cmd   mqbroker.numanode0  mqbroker.xml     mqnamesrv      mqshutdown.cmd  play.sh        runbroker.sh   startfsrv.sh
cleancache.sh     mqadmin.xml   mqbroker.numanode1  mqfiltersrv      mqnamesrv.cmd  nohup.out       README.md      runserver.cmd  tools.cmd
cleancache.v1.sh  mqbroker      mqbroker.numanode2  mqfiltersrv.cmd  mqnamesrv.xml  os.sh           runbroker      runserver.sh   tools.sh
mqadmin           mqbroker.cmd  mqbroker.numanode3  mqfiltersrv.xml  mqshutdown     play.cmd        runbroker.cmd  setcache.sh
[root@node-100 bin]# 
```

##### runbroker.sh：

```
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m"
JAVA_OPT="${JAVA_OPT} -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=30 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:SurvivorRatio=8"
JAVA_OPT="${JAVA_OPT} -verbose:gc -Xloggc:/dev/shm/mq_gc_%p.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintAdaptiveSizePolicy"
JAVA_OPT="${JAVA_OPT} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"
JAVA_OPT="${JAVA_OPT} -XX:+AlwaysPreTouch"
JAVA_OPT="${JAVA_OPT} -XX:MaxDirectMemorySize=15g"
JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages -XX:-UseBiasedLocking"
JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${BASE_DIR}/lib"
#JAVA_OPT="${JAVA_OPT} -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=n"
JAVA_OPT="${JAVA_OPT} ${JAVA_OPT_EXT}"
JAVA_OPT="${JAVA_OPT} -cp ${CLASSPATH}"
```

##### runserver.sh：

```
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m"
JAVA_OPT="${JAVA_OPT} -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8  -XX:-UseParNewGC"
JAVA_OPT="${JAVA_OPT} -verbose:gc -Xloggc:/dev/shm/rmq_srv_gc.log -XX:+PrintGCDetails"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"
JAVA_OPT="${JAVA_OPT}  -XX:-UseLargePages"
JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${BASE_DIR}/lib"
#JAVA_OPT="${JAVA_OPT} -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=n"
JAVA_OPT="${JAVA_OPT} ${JAVA_OPT_EXT}"
JAVA_OPT="${JAVA_OPT} -cp ${CLASSPATH}"
```

##### 注意：两次机器都要设置！

如果还是出现内存问题，可尝试修改下面几个文件：

```
[root@node-100 bin]# pwd
/usr/local/rocketmq/rocketmq-4.2/bin
[root@node-100 bin]# ls *.xml
mqadmin.xml  mqbroker.xml  mqfiltersrv.xml  mqnamesrv.xml
[root@node-100 bin]# 
```

将这几个文件中下面参数给删掉：

```
-XX:PermSize    设置持久代(perm gen)初始值  物理内存的1/64    
-XX:MaxPermSize 设置持久代最大值    物理内存的1/4
```

------

##### 开始启动：

##### 先启动namesrv（两台都要启动）：

```
[root@node-100 bin]# cd /usr/local/rocketmq4.3/bin
[root@node-100 bin]# nohup sh mqnamesrv &
[1] 2601
[root@node-100 bin]# nohup: 忽略输入并把输出追加到"nohup.out"

[root@node-100 bin]# jps
2604 NamesrvStartup
2621 Jps
[root@node-100 bin]# 
```

因为rocketmq是java开发的，所以通过jps命令查看namesrv有没有启动，当然通过日志文件也可以，namesrv正常启动之后，后续再分别启动Broker。

##### 启动BrokerServer（两台都要启动）：

##### 192.168.3.222：

##### broker-a.properties：

```
[root@node-100 bin]# nohup sh mqbroker -c /usr/local/rocketmq4.3/conf/2m-2s-sync/broker-a.properties &
[2] 2646
[root@node-100 bin]# nohup: 忽略输入并把输出追加到"nohup.out"

[root@node-100 bin]# jps
2681 Jps
2650 BrokerStartup
2604 NamesrvStartup
[root@node-100 bin]# 
```

##### broker-a-s.properties：

```
[root@node-100 bin]# nohup sh mqbroker -c /usr/local/rocketmq4.3/conf/2m-2s-sync/broker-b-s.properties &
[3] 2729
[root@node-100 bin]# nohup: 忽略输入并把输出追加到"nohup.out"

[root@node-100 bin]# 
[root@node-100 bin]# jps
2789 Jps
2650 BrokerStartup
2733 BrokerStartup
[root@node-100 bin]# 
```

##### 192.168.3.220：

##### broker-b.properties：

```
[root@node-101 bin]# nohup sh mqbroker -c /usr/local/rocketmq4.3/conf/2m-2s-sync/broker-b.properties &
[2] 7621
[root@node-101 bin]# nohup: 忽略输入并把输出追加到"nohup.out"

[root@node-101 bin]# 
[root@node-101 bin]# jps
7570 NamesrvStartup
7667 Jps
7625 BrokerStartup
[root@node-101 bin]# 
```

##### broker-b-s.properties:

```
[root@node-101 bin]# nohup sh mqbroker -c /usr/local/rocketmq4.3/conf/2m-2s-sync/broker-a-s.properties &
[3] 7701
[root@node-101 bin]# nohup: 忽略输入并把输出追加到"nohup.out"

[root@node-101 bin]# jps
7625 BrokerStartup
7705 BrokerStartup
7774 Jps
[root@node-101 bin]# 
```

##### 启动完成，查看集群信息：

```
[root@node-100 bin]# sh mqadmin clusterlist -n 192.168.5.100:9876
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=128m; support was removed in 8.0
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=128m; support was removed in 8.0
#Cluster Name     #Broker Name            #BID  #Addr                  #Version                #InTPS(LOAD)       #OutTPS(LOAD) #PCWait(ms) #Hour #SPACE
rocketmq-cluster  broker-a                0     192.168.3.222:10911    V4_2_0_SNAPSHOT          0.00(0,0ms)         0.00(0,0ms)          0 429538.48 0.2738
rocketmq-cluster  broker-a                1     192.168.3.222:10950    V4_2_0_SNAPSHOT          0.00(0,0ms)         0.00(0,0ms)          0 429538.48 0.2734
rocketmq-cluster  broker-b                0     192.168.3.220:10911    V4_2_0_SNAPSHOT          0.00(0,0ms)         0.00(0,0ms)          0 429538.48 0.2734
rocketmq-cluster  broker-b                1     192.168.3.220:10950    V4_2_0_SNAPSHOT          0.00(0,0ms)         0.00(0,0ms)          0 429538.48 0.2738
[root@node-100 bin]# 
```

##### 启动成功！

------

##### 测试：

配置namesrv
 rocketmq.config.namesrvAddr=192.168.3.172:9876;192.168.3.201:9876

或者：

```
export NAMESRV_ADDR=192.168.3.172:9876;192.168.3.201:9876
#测试发送端
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
#测试消费端
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
```

------

停止命令：

```
[root@node-101 bin]# sh mqshutdown broker
The mqbroker(7625
7705) is running...
Send shutdown request to mqbroker(7625
7705) OK
[root@node-101 bin]# sh mqshutdown namesrv
The mqnamesrv(7570) is running...
Send shutdown request to mqnamesrv(7570) OK
[3]+  退出 143              nohup sh mqbroker -c /usr/local/rocketmq4.3/conf/2m-2s-sync/broker-a-s.properties
[2]+  退出 143              nohup sh mqbroker -c /usr/local/rocketmq4.3/conf/2m-2s-sync/broker-b.properties
[root@node-101 bin]# 
```

