一、安装JDK：

1、查看是否安装了openjdk：rpm -qa | grep java

有就卸载，没有就不用：rpm -e nodeps 文件名称

2、安装jdk：

切换目录：cd /usr

新建文件夹：mkdir java

进入java文件夹：cd java

下载或者上传Java安装包

3、解压：tar -zxvf jdk-8u212-linux-x64.tar.gz

4、配置环境变量：vi/vim /etc/profile

添加内容：#java environment

export JAVA_HOME=/usr/java/jdk1.8.0_212

export PATH=$JAVA_HOME/bin:$PATH

export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

5、重新编译环境变量：source /etc/profile

6、测试：java -version

                java
    
                javac

二、安装nginx：

1、安装编译工具及库文件

①gcc安装：yum install -y gcc gcc-c++

②pcre库安装：yum install -y pcre pcre-devel

③zlib库安装：yum install -y zlib zlib-devel

④openssl库安装：yum install -y openssl openssl-devel

2、安装nginx：cd /usr/local/    mkdir nginx

下载：wget -c https://nginx.org/download/nginx-1.17.3.tar.gz

解压：tar -zxvf nginx-1.17.3.tar.gz

切换：cd nginx-1.17.3

配置：./configure

编译安装：make && make install

查找安装路径：whereis nginx

启动：cd  /usr/local/nginx/sbin

          ./nginx    启动时端口被占用：安装net-tool包，yum install net-tools

测试是否安装成功：访问http://192.168.56.101/ (你的虚拟机IP)   

                                 能正常显示nginx首页表示安装成功。

停止：./nginx -s stop/quit

重启：./nginx -s reload

查看nginx版本：./nginx -v

查看nginx进程：ps -ef/aux | grep nginx

开机自启动：vi /etc/rc.local  增加一行 /usr/local/nginx/sbin/nginx   

设置执行权限：chmod 755 rc.local

强制关闭：pkill nginx

编辑配置文件：cd  /usr/local/nginx/conf/nginx.conf         vim nginx.conf

三、安装tomcat：

1、下载：wget -c http://mirror.bit.edu.cn/apache/tomcat/tomcat-7/v7.0.96/bin/apache-tomcat-7.0.96.tar.gz

或者wget http://mirrors.shuosc.org/apache/tomcat/tomcat-9/v9.0.2/bin/apache-tomcat-9.0.2.tar.gz

2、解压：tar -zxvf apache-tomcat-7.0.96

删除安装包：rm -rf apache-tomcat-7.0.96.tar.gz

3、创建tomcat文件夹：mkdir /usr/local/tomcat

复制到该文件夹：mv/cp -r apache-tomcat-7.0.96 /usr/local/tomcat/

修改配置系统设置：vim /etc/profile

路径：export TOMCAT_HOME=/usr/local/tomcat/apache-tomcat-7.0.96/

4、进入tomcat下的bin文件夹并启动tomcat：

切换目录：cd /usr/local/tomcat   cd apache-tomcat-7.0.96/  cd bin

进入后执行命令：./startup.sh    或者 ./catalina.sh start

停止tomcat：./shutdown.sh

查看日志：tail -f logs/catalina.out

5、测试：http://你的服务器IP：端口/

6、修改默认监听端口：vim /usr/local/tomcat/apache-tomcat-7.0.96/conf/server.xml

:1,$s/8080/80/      将8080替换为80

7、修改字符编码：URIEncoding=”utf-8”

8、设置tomcat开机自启动：

在/usr/local/tomcat/apache-tomcat-7.0.96/bin/startup.sh的尾部新增如下四行，保存退出！

export JAVA_HOME=/usr/java/jdk1.8.0_151

export CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:.

export PATH=$JAVA_HOME/bin

ExportCATALINA_HOME=/usr/local/tomcat/apache-tomcat-7.0.96/bin/catalina.shstart

编辑/etc/rc.d/rc.local配置文件，加入如下一行，保存退出！

路径：/usr/local/tomcat/apache-tomcat-7.0.96/bin/startup.sh

给/etc/rc.d/rc.local加上执行权限：chmod u+x /etc/rc.d/rc.local

9、优化。在/usr/local/tomcat/apache-tomcat-7.0.96/bin/catalina.sh新增如下一行：

路径：JAVA_OPTS=”-XX:MaxPermSize=192M -Xms1000M -Xmx2000M”

四、安装redis：

1、单机redis yum安装：

①检查是否有redis yum 源：yum install redis

下载fedora的epel仓库：yum install epel-release

安装redis数据库：yum install redis

前端启动和停止：./redis-server和 ./redis-cli shutdown

后端启动和停止：先编辑redis.conf,将daemonize no -> daemonize yes。  

再./redis-server redis.conf启动

刷新配置：systemctl daemon-reload

启动redis：systemctl start redis    restart重启

停止redis：systemctl stop redis

查看redis运行状态：systemctl status redis

查看redis进程：ps -ef | grep redis

设置redis为开机自动启动：chkconfig redis on/systemctl enable redis

进入redis服务/打开redis连接：redis-cli

列出所有key：keys *

②修改redis默认端口和密码：

打开配置文件：vi /etc/redis.conf        port 端口号；requirepass 密码

使用配置文件启动redis：redis-server /etc/redis.conf

使用端口登录：redis-cli -h 127.0.0.1 -p 6379(端口号)

输入密码：auth 密码

停止redis：shutdown/kill -9 XXX

2、单机redis wget安装：

1）安装gcc：yum install gcc-c++

2）下载：wget http://download.redis.io/releases/redis-3.0.0.tar.gz

3）解压：tar -zxvf redis-3.0.0.tar.gz     新建redis文件夹：mkdir /usr/local/redis

4）移动或复制：mv/cp  redis-3.0.0/  /usr/local/redis

5）切换：cd /usr/local/redis/redis-3.0.0

6）编译安装：make或者make MALLOC=libc        make install PREFIX= /usr/local/redis

进入到redis的bin目录下启动：./redis-server

把redis解压文件下的redis.conf复制到当前目录下：cp /usr/local/redis/redis-3.0.0/redis.conf /usr/local/redis/bin/

7）编辑redis.conf：vim redis-conf      将daemonize no -> daemonize yes

8）再次启动：./redis-server redis.conf

9）查看运行情况：ps -ef | grep redis

10）打开redis连接：./redis-cli

设置密码：①vim redis.conf  在requirepass后加个密码   

                  ②连接redis后：config set requirepass “密码”    config get requirepass

11）再次启动：./redis-service ./redis.conf

再次连接redis：./redis-cli -a 密码

或者输入验证密码：auth 密码  

12）关闭redis：./redis-cli -a 密码 shutdown

3、集群redis：

新建redis-cluster目录存放集群节点：mkdir /usr/local/redis-cluster

复制redis目录下的bin目录下的所有文件：cp -r redis/bin/ redis-cluster/redis01

删除redis7001目录下的快照文件dump.rdb：rm -rf dump.rdb

编辑redis7001目录下的redis.conf：修改端口号，打开cluster-enabled yes 的注释

复制redis7001文件5份到redis-cluster：cp -r redis7001/  

复redis7002 redis7003 redis7004 redis7005 redis7006

脚本启动所有redis节点：./start-all.sh

修改脚本权限：chmod +x start-all.sh

安装ruby：yum install ruby

下载redis-3.0.0.gem的ruby包

安装gem install redis-3.0.0.gem

将ruby工具复制到redis-cluster目录下：cp redis-trib.rb /usr/local/redis-cluster

脚本文件搭建集群：./redis-trib.rb create --replicas 1 192.168.56.101:7001 ...

最后使用redis-cli连接集群节点：redis7001/redis-cli -p 7001 -c

再次启动集群时，则不用脚本运行，直接连接即可。

要想使用空的集群，需清除数据库：./redis-cli -h 192.168.56.101 -p 7001    flushdb   quit

查看当前集群信息：cluster info

查看集群里有多少个节点：cluster nodes

五、安装mysql：

1、下载：wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm

2、安装：yum -y install mysql57-community-release-el7-10.noarch.rpm

3、检查mysql源是否安装成功：yum repolist enabled | grep “mysql.*-community.*”

4、yum安装mysql：yum -y install mysql-community-server

5、启动mysql服务：systemctl start mysqld.service

6、查看mysql运行状态：systemctl status mysqld.service

7、重新启动服务：systemctl restart mysqld

8、停止服务：systemctl stop mysqld.service

9、查看初始密码：grep ‘temporary password’/var/log/mysqld.log

10、进入mysql数据库：mysql -u root -p

修改初始密码：先修改两个参数：①mysql> set global validate_password_policy=0;

                                                      ②mysql> set global validate_password_length=4;
    
                                                      ③修改密码：mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY 'new password';

11、授权远程登录：grant all on *.* to root@’%’identified by ‘new password’

注意：主要配置目录

①/etc/my.cnf 是mysqlde主配置文件

②/var/lib/mysql mysql数据库的数据库文件存放位置

③/var/log mysql数据库的日志输出存放位置

④/usr/share/mysql mysql.server命令及配置文件

⑤/usr/bin mysqladmin mysqldump等命令

⑥/etc/rc.d/init.d/ 启动脚本文件mysql的目录

二进制方式安装：

①切换目录：cd /usr/local  

下载：wget http://mirrors.sohu.com/mysql/MySQL-5.5/mysql-5.5.54-linux2.6-x86_64.tar.gz

②解压：tar -zxvf mysql-5.5.54-linux2.6-x86_64.tar.gz

③解压后改名：mv mysql-5.5.54-linux2.6-x86_64 mysql

④添加/etc/my.cnf文件：cd /usr/local/mysql/support-files/

                                       cp -f my-small.cnf /etc/my.cnf

⑤创建mysql目录下的data目录：mkdir -p /usr/local/mysql/data/

⑥将mysql的所属用户改为mysql：chown -R mysql.mysql /usr/local/mysql/

⑦增加/etc/init.d/mysqld，并赋予执行权限，然后初始化MySQL：

增加：cp -f mysql.server /etc/init.d/mysqld

赋予：chmod +x /etc/init.d/mysqld

初始化：/usr/local/mysql/scripts/mysql_install_db --basedir=/usr/local/mysql

--datadir=/usr/local/mysql/data --user=mysql

⑧将/application/mysql/bin/mysqld_safe和/etc/init.d/mysqld中的/usr/local/mysql改为你的mysql存放路径：我的不用改。

Sed -i 's#/usr/local/mysql#/usr/local/mysql#g' /usr/local/mysql/bin/mysqld_safe /etc/init.d/mysqld

⑨启动mysql：/etc/init.d/mysql start

⑩检查mysql是否启动：netstat -lntup | grep mysql

十一、设置环境变量：echo ‘export PATH=/usr/local/mysql/bin:$PATH’>>/etc/profile

           编译：source /etc/profile

十二、进入mysql：mysql -u root -p

           修改密码：mysql > SET PASSWORD FOR ‘root’@’localhost’=PASSWORD(‘newpass’);

十三、重新登录mysql：mysql -u root -p 输入密码

十四、创建数据库和表：mysql>create database userInfo default character set utf8 collate utf8_general_ci;

                                      mysql>use userInfo;
    
                                      mysql>create table test(name varchar(10) not null,address varchar(100) default null) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

十五、导入sql文件：mysql>source /usr/local/cloud_note.sql      

                                 mysql>show databases;

六、安装zookeeper：

1、切换目录 cd /usr/local 新建zookeeper文件夹  进入

下载：wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.9/zookeeper-3.4.9.tar.gz

2、解压：tar -zxvf zookeeper-3.4.9.tar.gz

3、切换zookeeper-3.4.9目录，进入conf

4、复制：cp zoo_sample.cfg zoo.cfg

5、编辑：vi/vim zoo.cfg

6、修改zoo.cfg：dataDir=/tmp/zookeeper/data     dataLogDir=/tmp/zookeeper/log

7、在tmp目录下创建目录：mkdir /tmp/zookeeper

                                 目录：mkdir /tmp/zookeeper/data      mkdir /tmp/zookeeper/log

8、配置环境变量：vi /etc/profile

添加：export ZOOKEEPER=/usr/local/zookeeper/zookeeper-3.4.9    

           export PATH=$PATH:$ZOOKEEPER/bin

9、启动zookeeper：./zkServer.sh start

10、查看运行状态：./zkServer.sh status

11、启动客户端：./zkCli.sh

12、停止zookeeper：./zkServer.sh stop

七、安装fastdfs：

1、检查是否已安装libevent，有就卸载：yum -y remove libevent

安装libevent：yum -y install libevent

或者上传tar.gz包

解压：tar -zxvf libevent-2.0.20-stable.tar.gz

切换目录：cd libevent

编译及安装：./configure -prefix=/usr    make && make install

2、安装libfastcommon

切换目录：cd /usr/local

下载：wget https://github.com/happyfish100/libfastcommon/archive/V1.0.7.tar.gz

或者使用rz上传文件：libfastcommonV1.0.7.tar.gz

解压：tar -zxvf libfastcommonV1.0.7.tar.gz   删除rm -rf libfastcommonV1.0.7.tar.gz

进入解压文件夹：cd libfastcommon-1.0.7/

编译：./make.sh

安装：./make.sh install

拷贝：cp /usr/lib64/libfastcommon.so  /usr/lib/

3、安装tracker服务(追踪服务器)，fastdfs

切换目录：cd /usr/local

下载：wget https://github.com/happyfish100/fastdfs/archive/V5.05.tar.gz

或者使用rz上传文件：FastDFS_v5.05.tar.gz

解压：tar -zxvf FastDFS_v5.05.tar.gz  删除rm -rf FastDFS_v5.05.tar.gz

进入解压文件：cd FastDFS

编译：./make.sh

安装：./make.sh install

拷贝：cd conf/     cp * /etc/fdfs

配置：进入/etc/fdfs目录，cd /etc/fdfs   cp tracker.conf.sample tracker.conf

修改配置文件：vi/vim tracker.conf

存放数据和日志的路径改为：base_path=/fastdfs/tracker

创建tracker基础数据目录，即base_path对应的目录：mkdir -p /fastdfs/tracker

启动tracker：/etc/init.d/fdfs_trackerd start

查看运行状态：netstat -unltp | grep fdfs

重新启动：/usr/bin/fdfs_trackerd  /etc/fdfs/tracker.conf restart

4、安装storage存储服务器，配置FastDFS存储

进入目录：cd /etc/fdfs

复制：cp storage.conf.sample storage.conf

编辑：vi/vim storage.conf     

修改数据和日志路径：base_path=/fastdfs/storage

修改存储地址：store_path0=/fastdfs/storage

指定tracker服务器的ip与端口：tracker_server=192.168.56.101:22122

创建storage基础数据目录，对应base_path目录：mkdir -p /fastdfs/storage

启动：/etc/init.d/fdfs_storaged start

查看storage是否启动成功：netstat -unltp | grep fdfs

启动服务：/usr/bin/fdfs_storaged  /etc/fdfs/storage.conf restart

5、测试

修改tracker服务器中的客户端配置文件：cd /etc/fdfs

复制：cp client.conf.sample client.conf

编辑：vi/vim client.conf

修改数据和日志路径：base_path=/fastdfs/client

Tracker端口：tracker_server=192.168.56.101:22122

上传测试：/usr/bin/fdfs_upload_file /etc/fdfs/client.conf  upload 文件名(name.jpeg)

八、安装mq：

1、安装rabbitmq：

切换cd /usr/local 新建mkdir rabbitmq   cd rabbitmq

①安装erlang环境：

A、yum install http://www.rabbitmq.com/releases/erlang/erlang-19.0.4-1.el7.centos.x86_64.rpm

B、rpm -Uvh http://www.rabbitmq.com/releases/erlang/erlang-18.1-1.el7.centos.x86_64.rpm

C、wget https://packages.erlang-solutions.com/erlang-solutions-1.0-1.noarch.rpm

安装rpm -Uvh erlang-solutions-1.0-1.noarch.rpm

安装yum -y install erlang

输入erl -version，查看erlang版本

②安装rabbitmq软件：

A、(wget)yum install http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.15/rabbitmq-server-3.6.15-1.el7.noarch.rpm

安装(wget后) rpm -Uvh rabbitmq-server-3.6.15-1.el7.noarch.rpm

安装yum -y install socat*

B、rpm -Uvh http://www.rabbitmq.com/releases/rabbitmq-server/v3.5.6/rabbitmq-server-3.5.6-1.noarch.rpm

③查看rabbitmq是否安装成功：rpm -qa | grep rabbitmq

④启动：rabbitmq-server -detached   或者 systemctl start rabbitmq-server.service

开机启动:systemctl enable rabbitmq-server.service

⑤关闭：rabbitmqctl stop  或者 systemctl stop rabbitmq-server.service

⑥查看服务状态：rabbitmqctl status  或者 systemctl status rabbitmq-server.service

⑦列出角色(查看当前所有用户)：rabbitmqctl list_users

查看默认guest用户的权限：rabbitmqctl list_user_permissions guest

先删除默认用户：rabbitmqctl delete_user guest

添加新用户：rabbitmqctl add_user username password

设置用户tag：rabbitmqctl set_user_tags username password

赋予用户默认vhost的全部操作权限：rabbitmqctl set_permissions -p / username “.*” “.*” “.*”

查看用户的权限：rabbitmqctl list_user_permissions username

⑧配置网页插件：rabbitmq-plugins enable rabbitmq_management 访问http://localhost:15672

设置配置文件，并开启用户远程访问：cd /etc/rabbitmq

复制：cp /usr/local/rabbitmq/rabbitmq-server-3.5.6/rabbitmq.config.example /etc/rabbitmq/

移动：mv rabbitmq.config.example rabbitmq.config

编辑：vi /etc/rabbitmq/rabbitmq.config     

修改前：%%{loopback_users,[]}, 修改后：{loopback_users,[]}

查看日志位置：systemctl status rabbitmq-server.service

⑨配置防火墙：

A、firewall-cmd --permanent --add-port=15672/tcp

B、firewall-cmd --permanent --add-port=5672/tcp

C、systemctl restart firewalld.service

⑩默认账号登录：guest/guest 登录localhost:15672

九、安装docker：

1、检查内核版本，必须是3.10及以上：uname -r

2、再安装docker：yum install -y docker   输入y确认安装

卸载旧版本：yum -y remove docker

查看是否安装成功：yum list installed | grep docker

3、启动docker：systemctl start docker

查看是否启动成功：systemctl status docker

查看docker版本：docker -v

4、开机启动docker：systemctl enable docker

5、停止docker：systemctl stop docker

6、修改docker配置文件：vi/vim /etc/sysconfig/docker

编辑：把--selinux-enabled=false然后esc退出，:wq保存退出

7、docker常用命令及操作

8、使用docker命令：docker images查看docker已经安装的镜像

十、安装solr：

在安装前需要安装好JDK和Tomcat

1、下载：wget http://archive.apache.org/dist/lucene/solr/5.0.0/solr-5.0.0.tgz

2、解压：tar -zxvf solr-5.0.0.tgz

创建目录：mkdir /usr/local/solr

复制：cp -r solr-5.0.0 /usr/local/solr

3、启动solr服务：cd bin      ./solr start

关闭solr服务：./solr stop -all

查看solr服务状态：./solr status

4、测试：http://虚拟机ip:8983/solr/#/

5、创建一个core实例：./solr create -c name

6、修改配置文件schema.xml

另外还可以部署到tomcat中去！