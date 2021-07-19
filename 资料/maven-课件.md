# maven-课件

## 1、maven是什么

​	Maven项目对象模型(POM)，可以通过一小段描述信息来管理项目的构建，报告和文档的软件项目管理工具。

## 2、maven下载安装

​	2.1 在浏览器中打开下载地址：<http://maven.apache.org/download.cgi>

​	2.2 安装环境变量

## 3、maven 创建项目

​	输入命令 mvn archetype:generate，按回车，根据提示输入参数，如果是第一次使用，需要下载插件，稍等几分钟即可。

切换目录，输入指令

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913141852959-173169663.png)

选择骨架（模板）:

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913142020821-1822503065.png)

输入座标：

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913142317191-456871827.png)

确认后下载骨架，成功后的提示如下：

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913142407355-1768544193.png)

将项目转换成IDEA项目：

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913142639243-327620791.png)

成功后可以看到增加了项目信息:

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913142758431-459153775.png)

在IDEA中就可以直接打开项目了：

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913142829283-1140632307.png)

将项目打包

输入指令：mvn package

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913143041716-2013832196.png)

打包成功后：

![img](https://images2018.cnblogs.com/blog/63651/201809/63651-20180913143105187-447615584.png)

## 4、maven 项目标识

### 4.1 项目标识

 <dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.11</version>
  <scope>test</scope>
</dependency>

### 4.2 坐标

groupId , artifactId , version 三个元素是项目的坐标，唯一的标识这个项目。

groupId 项目所在组，一般是组织或公司

artifactId 是当前项目在组中的唯一ID；

version 表示版本，SNAPSHOT表示快照，表示此项目还在开发中，不稳定。

groupId 和实际项目不一定是一一对应的，maven 有模块的概念，例如 spring-core, spring-context...；groupId 也不应该只对应公司或组织名，建议具体到项目名，因为公司或者组织下有多个项目，而artifactId只能代表模块名。

### 4.3 依赖范围

1. compile : 编译，测试，运行都有效，默认的选择
2. test : 测试有效，例如junit
3. provided : 编译，测试有效，例如 servlet ，运行时容器会提供实现
4. runtime : 运行和测试有效，例如 jdbc，编译时只需相应的接口，测试和运行时才需要具体的实现
5. system : 编译，测试有效。使用此范围的依赖必须通过systemPath元素显式的指定依赖文件，因而
   此类依赖是不通过Maven仓库解析的，一般适合于本机测试环境下，依赖本地起的服务。

## 5、maven 指令

1、 显示版本信息

mvn -version
mvn -v 

2、使用互动模式创建项目

mvn archetype:generate

3、使用非互动模式（指定参数创建项目）

普通项目骨架：

mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=myapp -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

Web项目骨架：

mvn archetype:generate -DgroupId=com.zhangguo  -DartifactId=webappdemo  -Dpackage=com.zhangguo.webappdemo  -DarchetypeArtifactId=maven-archetype-webapp   -Dversion=1.0 -DinteractiveMode=No

4、将项目转化为idea项目

mvn idea:idea

5、将项目转化为Eclipse项目

mvn eclipse:eclipse

6、编译,将Java 源程序编译成 class 字节码文件

mvn compile

7、测试，并生成测试报告

mvn test

8、将以前编译得到的旧的 class 字节码文件删除

mvn clean

9、打包,动态 web工程打 war包，Java工程打 jar 包

mvn package

10、将项目生成 jar 包放在仓库中，以便别的模块调用，安装到本地

mvn install

\11. 生成项目相关信息的网站：mvn site

\12. 编译测试的内容：mvn test-compile

\13. 只打jar包：mvn jar:jar

\14. 只打war包：mvn war:war

\15. 清除eclipse的一些系统设置：mvn eclipse:clean

\16. 运行项目于jetty上：mvn jetty:run

\17. 生成Wtp插件的Web项目：mvn -Dwtpversion=1.0 eclipse:eclipse

\18. 清除Eclipse项目的配置信息(Web项目)：mvn -Dwtpversion=1.0 eclipse:clean

19、部署项目：

```
mvn deploy:deploy-file -DgroupId=com -DartifactId=client -Dversion=0.1.0 -Dpackaging=jar -Dfile=d:\client-0.1.0.jar -DrepositoryId=maven-repository-inner -Durl=ftp://xxxxxxx/opt/maven/repository/
```

其它指令：

## 5、maven-聚合

​	5.1 maven聚合是什么

​	聚合在java 中是在A对象功能完整需要 另外B对象来实现，所以A类中有着B类对象的引用，但是A对象的生命周期结束，B对象依然可以存在，简而言之就两个对象的生命周期不同。当然在maven中，聚合又是为了解决另外一个问题而存在的一种机制。通常我们的项目都是都模块的，而每个模块又是一个 maven项目，所以每次开发完了编译都需要一个一个模块的去执行，这就违背了maven的自动化理念。

​	5.2 maven聚合操作

​	<modules>
   	<module>a</module>
  	 <module>b</module>
   	<module>c</module>

​       </modules>

​	a,b,c 代表子模块（同时构建）

## 6、maven-继承

​	6.1 maven继承是什么

maven继承的作用其实和java中继承的作用是类似的，基本上都是主要为了实现重用，java中类的继承，减少了重复的代码，而maven中的继承则减少了pom.xml的配置。
因此显而易见的是，这里所说的maven继承更确切的说，应该指的是pom.xml配置的继承。

​	6.2 maven继承操作

​	<parent>
   	 <artifactId>A</artifactId>
   	 <groupId>com.javxuam.maven</groupId>
   	 <version>1.0-SNAPSHOT</version>
  	  <!--最好声明一下以当前pom文件目录为基准
​    的父工程的pom文件-->
​    	<relativePath>../pom.xml</relativePath>
​	</parent>	

## 7、maven-插件

### 	7.1 maven-插件是什么

​		Maven本质上是一个插件框架，它的核心并不执行任何具体的构建任务，所有这些任务都交给插件来完成。就是改变项目状态的功能点称作为插件

### 7.2 maven-插件使用

​	我们在输入 `mvn` 命令的时候 比如 `mvn clean`，`clean` 对应的就是 Clean 生命周期中的 clean 阶段。但是 clean 的具体操作是由 `maven-clean-plugin` 来实现的。

​       mvn [plugin-name]:[goal-name]

​         7.2.1 tomcat插件使用

	tomcat:deploy   --部署一个web war包
tomcat:reload   --重新加载web war包
tomcat:start    --启动tomcat
tomcat:stop    --停止tomcat
tomcat:undeploy--停止一个war包
tomcat:run  启动嵌入式tomcat ，并运行当前项目

clean tomcat7:run 执行tomcat7

	       <plugin>
	            <groupId>org.apache.tomcat.maven</groupId>
	            <artifactId>tomcat7-maven-plugin</artifactId>
	            <version>2.2</version>
	            <configuration>
	                 <uriEncoding>UTF-8</uriEncoding>
	                 <port>9999</port>
	            </configuration>
	        </plugin>
​	7.2.2 jetty插件使用

​		jetty:run -Djetty.port=9080

         <!-- jetty插件 -->
        <plugin>
        <groupId>org.eclipse.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>9.4.4.v20170414</version>
        <configuration>
            <scanIntervalSeconds>5</scanIntervalSeconds>
            <webApp>
                <contextPath>/</contextPath>
            </webApp>
            <connectors>
                <connector implementation="org.eclipse.jetty.server.nio.SelectChannelConnector">
                    <port>10000</port>
                </connector>
            </connectors>
        </configuration>
    </plugin>

## 8、maven-传递依赖

### 8.1 传递依赖是什么

1. 项目间传递

​    如果我的当前项目是project1，project1要依赖project2，project1依赖project2的配置中加上 <optional>true</optional>，表示依赖可选，

```
<dependency>
  <groupId>com.projecct</groupId>
  <artifactId>project2</artifactId>
  <version>1.0</version>
  <scope>compile</scope>
  <optional>true</optional>
</dependency>
```

 那么以后所有声明依赖project1的项目如果也依赖project2，就必须写手动声明。比如project3依赖project1和project2，如果project3只声明了对project1的依赖，那么project2不会自动加入依赖，需要重新声明对project2的依赖。

这种方式排除不了我项目中对第三方jar包所依赖的其他依赖，因为我不可能去修改第三方jar包的pom文件，所以只适合在项目组内部使用。

### 8.2 依赖过滤

（1）单依赖过滤

​       同依赖过滤直接处理：可以过滤一个或者多个，如果过滤多个要写多个<exclusion>。这个也解决不了我的问题，或者说解决太麻烦，我那里知道hbase要依赖那些包，记不住。

```
<dependency>    
  <groupId>org.apache.hbase</groupId>
  <artifactId>hbase</artifactId>
  <version>0.94.17</version> 
  <exclusions>  
     <exclusion>	 
       <groupId>commons-logging</groupId>		
       <artifactId>commons-logging</artifactId>  
     </exclusion>  
  </exclusions>  
</dependency>
```

（2）多依赖过滤

  把所以依赖都过滤了。手起刀落~啊，世界都安静了。

<dependency>
  <groupId>org.apache.hbase</groupId>
  <artifactId>hbase</artifactId>
  <version>0.94.17</version>
  <exclusions>
    <exclusion>
      <groupId>*</groupId>
      <artifactId>*</artifactId>
    </exclusion>
  </exclusions>
</dependency>