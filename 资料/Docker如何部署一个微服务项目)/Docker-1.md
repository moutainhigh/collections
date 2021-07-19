1、Docker的安装

https://docs.docker.com/install/linux/docker-ce/centos/#uninstall-old-versions 【安装网址】

2、docker的image （分配app运行的资源）

```
docker pull
```



3、docker的container(运行image的容器)

```
docker run -d --name=demo 镜像名称 # 指定名称
docker run -it centos 交互式运行
--进入运行的容器
docker exec -it 容id 执行的命令
docker exec -it 11a5342fsd3 /bin/bash

--启动和停止容器
docker start 容器名称
docker stop 容器名称

--查询看容器的详细信息
docker inspect 容器id

-- 查看日志
docker logs 容器id
```



##### 4.创建docker网络

```
docker network create -d bridge hly-net
# 查看网络
docker network ls
----------
# docker network create -d bridge [netName]
-d：指定Docker网络类型，bridge,overlay(用于Swarm)

# 运行第一个容器
docker run -d -P --name web1 --network hly-net -v /src/webapp:/webapp training/webapp

# 运行第二个容器
docker run -d -P --name web2 --network hly-net -v /src/webapp:/webapp training/webapp

测试网络
# docker exec -it web1 bash
# ping web2
```



![image-20210311211740121](C:\Users\ye\AppData\Roaming\Typora\typora-user-images\image-20210311211740121.png)



4、镜像的构建

4.1 commit （不推荐）

```
docker commit 基础容器 镜像的名称
docker history 查询操作的历史
```

4.2 Dockerfile

```
FROM centos
RUN yum install -y vim -- 安装vim
```

docker build -t 镜像的名称

**dockerfile的语法**

FROM 就是image的基本层

LABEL 就是注释

RUN 就是运行的命令（主要每次运行一个run都会生成新的一层）

eg: yum update && yum install -y vim \

​	python-dev # 反斜杠线换行



WORKDIR 设定义工作目录

WORKDIR /test # 如果不存在将自动创建

WORKDIR test1 # 这样会在test目录下面创建一个test1文件夹

pwd /test/test1

RUN cd 不推荐

尽量使用WORKDIR



ADD and COPY

ADD Hello /

ADD test.tar.gz / # 添加到根目录并解压

WORKDIR /root

ADD hello test/ # root/test/hello

WORKDIR /root

COPY hello test/

ENV

ENV MYSQL_VERSION 5.7

RUN yum install -y mysql-server="${MYSQL_VERSION }"



**VOLUME and EXPOSE**

**VOLUME**

格式为 VOLUME ["/data"]。

创建一个可以从本地主机或其他容器挂载的挂载点，一般用来存放数据库和需要保持的数据等。

**EXPOSE**

格式为 EXPOSE <port> [<port>...]。

告诉 Docker 服务端容器暴露的端口号，供互联系统使用。在启动容器时需要通过 -p 宿主机端口:容器的端口，Docker 主机会自动分配一个端口转发到指定的端口。



**CMD and ENTRYPOINT**

**CMD**

支持三种格式

​    CMD ["executable","param1","param2"] 使用 exec 执行，推荐方式；

​    CMD command param1 param2 在 /bin/sh 中执行，提供给需要交互的应用；

​    CMD ["param1","param2"] 提供给 ENTRYPOINT 的默认参数；

指定启动容器时执行的命令，每个 Dockerfile 只能有一条 CMD 命令。如果指定了多条命令，只有最后一条会被执行。

如果用户启动容器时候指定了运行的命令，则会覆盖掉 CMD 指定的命令。

**ENTRYPOINT**

两种格式：

​    ENTRYPOINT ["executable", "param1", "param2"]

​    ENTRYPOINT command param1 param2（shell中执行）。

配置容器启动后执行的命令，并且不可被 docker run 提供的参数覆盖。

每个 Dockerfile 中只能有一个 ENTRYPOINT，当指定多个时，只有最后一个起效。



```dockerfile
# 基于哪个镜像
FROM java:8
# 将本地文件夹挂载到当前容器
VOLUME /tmp
# 拷贝文件到容器，也可以直接写成ADD comment-server.jar /app.jar
ADD comment-server.jar app.jar
RUN bash -c 'touch /app.jar'
# 开放端口    
EXPOSE 8002
# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```





5、端口映射

**mysql** 

docker pull mysql:5.7

docker run --name docker-mysql -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:5.7

```
docker run -p 3306:3306 --name mysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7

docker run -p 3306:3306 --name mysql -v /mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123 -d mysql:5.7

# 设置编码格式的
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

-v mysql_data:/var/lib/mysql
/var/lib/mysql是mysql容器数据存储位置
-e MYSQL_ROOT_PASSWORD=t
设置mysql密码


绑定其他容器的ip地址和host名称
--link 其他容器名称:别名名称
docker run -d --link 其他容器名称:别名名称 .....
```



**redis**

docker pull redis

docker run --name dokcer-redis -d -p 6379:6379 redis



### **6、服务的编排:**

docker-compose的安装

##### 通过docker-compose.yml方式运行

```dockerfile
version: '2'
services:

 mysql:
    image: mysql
    restart: always
    container_name: mysql
    environment:
      MYSQL_ROOT_PASSWORD: roof
    ports:
      - "3306:3306"
    volumes:
      - mysql_vol:/var/lib/mysql
    networks:
      - micro

 server:
    image: server
    container_name: server
    ports:
      - "8761:8761"
    networks:
      - micro

 client:
    image: client
    container_name: client
    ports:
      - "8762:8762"
    depends_on:
      - mysql
      - server
    networks:
      - micro

networks:
  micro:
    driver: bridge

volumes:
  mysql_vol:
```

docker-compose up -d 启动部署











### 在docker部署微服务项目:

1、使用dockerfile构建项目镜像

2、在docker使用生成镜像启动对应服务

| 服务名称       | 端口 |
| -------------- | ---- |
| comment-server | 8002 |
| house-server   | 8011 |
| user-server    | 8000 |
| esp-zuul       | 9000 |
| esp-web        | 7000 |

comment-server ->user-server



部署用户服务

```
docker run -d --name user-server --hostname user -p 8000:8000 house/user
```

房产服务

```
docker run -d --name house-server --hostname house --link user-server:user -p 8011:8011 house/house
```

部署评论服务

```
docker run -d --name comment-server --hostname comment --link user-server:user -p 8002:8002 house/comment
```

部署网关

```
docker run -d --name zuul-server --hostname zuul --link house-server:house --link user-server:user --link comment-server:comment -p 9000:9000 house/zuul
```

部署web

```
docker run -d --name web --link zuul-server:zuul -p 7000:7000 house/web
```

 docker run -d --name cms --hostname cms-web -p 8080:8080 docker-cms

 docker run -d --name 应用名称--hostname 主机名称   -p 8080:8080 docker-cms（docker 容器里面的镜像名称）







docker stop $(docker ps -a -q)

docker rm $(docker ps -qf status=exited)

 docker rm $(docker ps -a -q)



1  要删除docker部署的应用服务，就要先停止所有的container，这样才能够删除其中的images：

  docker stop $(docker ps -a -q)  

2  还要删除所有的container

  docker rm $(docker ps -a -q)

3  然后删除docker部署的应用

  docker rmi 3458979c7744（后为服务的ID号）

4  查看服务的ID号

  docker images



（1）删除所有未运行的容器；运行的删除不了

## docker rm $(docker ps -a -q)

（2）根据容器的状态删除状态为Exited的容器

## docker rm $(docker ps -qf status=exited)

（3）docker 1.13版本以后，可以使用docker container prune删除孤立的容器

docker container prune



https://www.jianshu.com/p/38222a404e9f

# docker exec -it a4e7cac0cb1f /bin/bash

https://jingyan.baidu.com/article/597035520b71d8cec007408b.html

如果需要将zookeeper文件映射到主机上，可以使用命令docker run --privileged=true -d --name zookeeper --publish 2181:2181 -d zookeeper

平常命令 docker run -dit --name zookeeper -p 2181:2181 zookeeper:latest 

docker exec -it 容器名称 /bin/bash

https://blog.51cto.com/suyanzhu/2383899







docker stop 67b9d711940b

docker rm 67b9d711940b

https://blog.csdn.net/ezbuy/article/details/82462952

https://blog.csdn.net/Kohang/article/details/85125054?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control

https://blog.csdn.net/weiguang1017/article/details/76212203?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.baidujs&dist_request_id=1328680.8629.16161223801775299&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.baidujs

https://blog.csdn.net/ezbuy/article/details/82462952