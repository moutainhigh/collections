## 一、Nginx中虚拟主机配置

### 1、基于域名的虚拟主机配置

1、修改宿主机的hosts文件(系统盘/windows/system32/driver/etc/HOSTS)

​	linux ： vim /etc/hosts

格式： ip地址 域名

eg: 192.168.3.172 www.gerry.com

2、在nginx.conf文件中配置server段

```
server {
    listen 80;
    server_name www.gerry.com; # 域名区分
    
    location / {
        root html/gerry;
        index index.html;
    }
}

server {
    listen 80;
    server_name www.rm.com;  # 域名区分
    
    location / {
        root html/rm;
        index index.html;
    }
}
```

### 2、基于端口号的虚拟主机配置

1、在nginx.conf文件中配置server段

```
server {
    listen 80; # 端口区分
    server_name www.gerry.com;
    
    location / {
        root html/gerry;
        index index.html;
    }
}

server {
    listen 8080; # 端口区分
    server_name www.gerry.com;
    
    location / {
        root html/gerry;
        index index.html;
    }
}
```



### 3、基于IP的虚拟主机配置

1、添加网卡的IP别名

```
ifconfig ens33:1 192.168.3.202 broadcast 192.168.3.255 netmask 255.255.255.0 up
route add -host 192.168.3.202 dev ens33:1
ifconfig ens33:2 192.168.3.203 broadcast 192.168.3.255 netmask 255.255.255.0 up
route add -host 192.168.3.203 dev ens33:2
```

 从另外一台服务器Ping 192.168.3.202和192.168.3.203两个IP,如果能够Ping通，则证明配置无误。但是，通过ifconfig和route配置的IP别名在服 务器重启后会消失，不过可以将这两条ifconng和route命令添加到/etc/rc.local文件中，让系统开机时自动运行，以下是相关命令：**vi /etc/rc.local**
在文件末尾增加以下内容，然后保存即可

ifconfig ens33:1 192.168.3.202 broadcast 192.168.3.255 netmask 255.255.255.0 up
route add -host 192.168.3.202 dev ens33:1
ifconfig ens33:2 192.168.3.203 broadcast 192.168.3.255 netmask 255.255.255.0 up
route add -host 192.168.3.203 dev ens33:2

2、修改配置文件做如下的Server段配置

```properties
server {
    listen 80;
    server_name 192.168.3.202;
    
    location / {
        root html/host1;
        index index.html;
    }
}

server {
    listen 80;
    server_name 192.168.3.203;
    
    location / {
        root html/host2;
        index index.html;
    }
}
```

## 二、nginx中server_name的匹配顺序

**前提：**安装echo-nginx-module

1、下载https://github.com/openresty/echo-nginx-module并解压到服务器上

2、进入nginx的安装目录，用下列命令进行配置和安装
./configure --prefix=/usr/local/nginx --add-module=/usr/local/nginx_modules/echo-nginx-module-master
make -j4 && make install

**修改hosts配置文件**

在Hosts文件中做如下配置：

vim /etc/hosts

127.0.0.1 ddd.cn

127.0.0.1 aaa.cn

### 1、最高优先级：完全匹配

首先，在nginx.conf中创建2个server，1个完全匹配，1个通配符匹配

通配符server放在最前，以证明完全匹配的优先级与配置顺序无关

```
server {
    listen 80;
    server_name *.cn;
    
    location / {
        default_type text/html;
        echo "通配符在前";
    }
}

server {
    listen 80;
    server_name ddd.cn;
    
    location / {
        default_type text/html;
        echo "完全匹配";
    }
}
```

### 2、第二优先级：通配符在前

```
server {
    listen 80;
    server_name ddd.*;
    
    location / {
        default_type text/html;
        echo "通配符在后";
    }
}

server {
    listen 80;
    server_name *.cn;
    
    location / {
        default_type text/html;
        echo "通配符在前";
    }
}
```

###  3、第三优先级：通配符在后

```
server {
    listen 80;
    server_name ~^\w+\.cn$;
    
    location / {
        default_type text/html;
        echo "正则匹配";
    }
}

server {
    listen 80;
    server_name ddd.*;
    
    location / {
        default_type text/html;
        echo "通配符在后";
    }
}
```

### 4、listen配置项中default的影响

```
server {
    listen 80;
    server_name ~^\w+\.cn$;
    
    location / {
        default_type text/html;
        echo "正则匹配";
    }
}

server {
    listen 80;
    server_name dddd.xxx;
    
    location / {
        default_type text/html;
        echo "不匹配";
    }
}

server {
    listen 80 default;
    server_name haha.xxx;
    
    location / {
        default_type text/html;
        echo "不匹配找Listen Default";
    }
}
```

### 5、验证default的作用域

```
server {
    listen 80 default;
    server_name *.cn;
    
    location / {
        default_type text/html;
        echo "通配符在前";
    }
}

server {
    listen 80;
    server_name ddd.cn;
    
    location / {
        default_type text/html;
        echo "完全匹配";
    }
}

结论: default关键字在所有server段下面的server_name都匹配不上的时候才有效果
```

### 6、验证没有匹配成功也没有default的情况

```
server {
    listen 80;
    server_name haha.cn;
    
    location / {
        default_type text/html;
        echo "不匹配,通配符在前";
    }
}

server {
    listen 80;
    server_name heihei.cn;
    
    location / {
        default_type text/html;
        echo "不匹配,通配符在后";
    }
}

结论： 在请求的server_name 都不匹配也没有default关键字情况下，会根据配置文件的顺序选择配置文件中第一虚拟主机。
```

## 三、日志的配置

```
http {
    include       mime.types;
    default_type  application/octet-stream;
	# 定义日志的格式
    log_format  main  '$remote_addr - [$time_iso8601] - $msec - $status - $request_time - $body_bytes_sent - "$http_host" - "$request" - "$http_referer" - "$http_user_agent" - "$http_x_forwarded_for"';

　　#设置日志默认存储目录
    access_log  logs/access.log  main;
    error_log   logs/error.log;
}

====== 内置的变量 ===============
$remote_addr, $http_x_forwarded_for（反向） 记录客户端IP地址
$remote_user 记录客户端用户名称
$request 记录请求的URL和HTTP协议
$status 记录请求状态
$body_bytes_sent 发送给客户端的字节数，不包括响应头的大小； 该变量与Apache模块mod_log_config里的“%B”参数兼容。
$bytes_sent 发送给客户端的总字节数。
$connection 连接的序列号。
$connection_requests 当前通过一个连接获得的请求数量。
$msec 日志写入时间。单位为秒，精度是毫秒。
$pipe 如果请求是通过HTTP流水线(pipelined)发送，pipe值为“p”，否则为“.”。
$http_referer 记录从哪个页面链接访问过来的
$http_user_agent 记录客户端浏览器相关信息
$request_length 请求的长度（包括请求行，请求头和请求正文）。
$request_time 请求处理时间，单位为秒，精度毫秒； 从读入客户端的第一个字节开始，直到把最后一个字符发送给客户端后进行日志写入为止。
$time_iso8601 ISO8601标准格式下的本地时间。
$time_local 通用日志格式下的本地时间。
```

## 四、nginx配置location（html location重定向）

匹配指定的请求uri（请求uri不包含查询字符串，如http://localhost:8080/test?id=10，请求uri是/test）

**语法形式**

location   [ = | ~ | ~* | ^~ | @]   /uri/     { configuration }

location   =   /uri         =开头表示精确前缀匹配，只有完全匹配才能生效。

location   ^~   /uri        ^~开头表示普通字符串匹配上以后不再进行正则匹配。

location   ~   pattern     ~开头表示区分大小写的正则匹配。

location   ~*   pattern    ~*开头表示不区分大小写的正则匹配。

location   /uri                  不带任何修饰符，表示前缀匹配。（一般匹配，最长命中匹配）

location /uri/aaa/bbb  最长命中匹配

location   /                       通用匹配，任何未匹配到其他location的请求都会匹配到。

注意：正则匹配会根据匹配顺序，找到第一个匹配的正则表达式后将停止搜索。普通字符串匹配则无视顺序，只会选择最精确的匹配。

### 1、location常用匹配

- **=** ：精准匹配
- **~** ：正则匹配
- **~\*** ：正则匹配，不区分大小写
- **^~** : 普通字符匹配， ^~ 的含义是如果命中，则不会再进行任何的正则匹配
- **前面没有任何修饰**： 普通字符匹配

### 2、location匹配顺序

1. 精确匹配
2. 普通匹配
3. 正则匹配 

### 4、常用配置指令alias、root、proxy_pass

1、alias——别名配置，用于访问文件系统，在匹配到location配置的URL路径后，指向alias配置的路径，如：

> location   /test/  {
>
> ​        alias    /usr/local/; ## 文件路径必须为绝对路径
>
> }

请求/test/1.jpg（省略了协议和域名），将会返回文件/usr/local/1.jpg。

如果alias配置在正则匹配的location内，则正则表达式中必须包含捕获语句（也就是括号**()**），而且alias配置中也要引用这些捕获值。如：

> location   ~*   /img/(.+\.(gif|png|jpeg)) {
>
> ​    alias     /usr/local/images/$1;
>
> }

请求中只要能匹配到正则，比如**/img/flower.png**  或者  **/resource/img/flower.png**，都会转换为请求**/usr/local/images/flower.png**。

2、root——根路径配置，用于访问文件系统，在匹配到location配置的URL路径后，指向root配置的路径，并把请求路径附加到其后，如：

> location   /test/  {
>
> ​        root    /usr/local/;
>
> }

请求/test/1.jpg，将会返回文件/usr/local/test/1.jpg。

3、proxy_pass——反向代理配置，用于代理请求，适用于前后端负载分离或多台机器、服务器负载分离的场景，在匹配到location配置的URL路径后，转发请求到proxy_pass配置额URL，是否会附加location配置路径与proxy_pass配置的路径后是否有"/"有关，有"/"则不附加，如：

> location   /test/  {
>
> ​        proxy_pass    http://127.0.0.1:8080;
>
> }

## 五、Nginx的Rewrite的使用

Rewrite规则含义就是某个URL重写成特定的URL，从某种意义上说**为了美观或者对搜索引擎友好，提高收录量及排名等。**

Rewrite规则的最后一项参数为flag标记，支持的flag标记主要有以下几种： 

**1)**    **last** **：相当于Apache里的(L)标记，表示完成rewrite；**

**2)**    **break**；本条规则匹配完成后，终止匹配，不再匹配后面的规则

**3)**    **redirect**：返回302临时重定向，浏览器地址会显示跳转后的URL地址

**4)**    **permanent**：返回301永久重定向，浏览器地址栏会显示跳转后的URL地址

5)     **last**和break用来实现URL重写，浏览器地址栏URL地址不变。

 

a)       例如用户访问[www.test.com](http://www.test.com/)，想直接跳转到网站下面的某个页面，[www.test.com/new.index.html](http://www.test.com/new.index.html)如何来实现呢？

我们可以使用Nginx Rewrite 来实现这个需求，具体如下：

在server中加入如下语句即可：

**rewrite  ^/$  http://www.test.com/index01.html  permanent;**

*代表前面0或更多个字符

+代表前面1或更多个字符

？代表前面0或1个字符

^代表字符串的开始位置

$代表字符串结束的位置

。为通配符，代表任何字符

规则里面的$1$2你不知道是怎么来的话，只要记住，第一个()里面的是$1，第二个()里面的是$2.
请求的URL是给人看的，重写后的URL是给电脑看的。



执行搜索

这个规则的目的是为了执行搜索，搜索URL中包含的关键字。

```
请求的URL	//hqidi.com/search/some-search-keywords
重写后URL	//hqidi.com/search.php?p=some-search-keywords
重写规则	        rewrite ^/search/(.*)$ /search.php?p=$1?;
```

用户个人资料页面

大多数运行访问者注册的动态网站都提供一个可以查看个人资料的页面，这个页面的URL包含用户的UID和用户名

```
请求的URL	//hqidi.com/user/47/dige
重写后URL	//hqidi.com/user.php?id=47&name=dige
重写规则	        rewrite ^/user/([0-9]+)/(.+)$ /user.php?id=$1&name=$2?;
```

多个参数

有些网站对字符串参数使用不同的语法，例如 通过斜线“/”来分隔非命名参数

```
请求的URL	//hqidi.com/index.php/param1/param2/param3
重写后URL	//hqidi.com/index.php?p1=param1&p2=param2&p3=param3
重写规则	        rewrite ^/index.php/(.*)/(.*)/(.*)$ /index.php?p1=$1&p2=$2&p3=$3?;
```

类似百科的格式

这种格式特点，一个前缀目录，后跟文章名称

```
请求的URL	//hqidi.com/wiki/some-keywords
重写后URL	//hqidi.com/wiki/index.php?title=some-keywords
重写规则	        rewrite ^/wiki/(.*)$ /wiki/index.php?title=$1?;
```

论坛

论坛一般用到两个参数，一个话题标识(topic)一个出发点(starting post)

```
请求的URL	//hqidi.com/topic-1234-50-some-keywords.html
重写后URL	//hqidi.com/viewtopic.php?topic=1234&start=50
重写规则        	rewrite ^/topic-([0-9]+)-([0-9]+)-(.*)\.html$ viewtopic.php?topic=$1&start=$2?;
```

新网站的文章

这种URL结构的特点，由一个文章标识符，后跟一个斜线，和一个关键字列表组成。

```
请求的URL	//hqidi.com/88/future
重写后URL	//hqidi.com/atricle.php?id=88
重写规则	        rewrite ^/([0-9]+)/.*$ /aticle.php?id=$1?;
```

最后一个问号

若被替换的URI中含有参数(类似/app/test.php?id=5之类的URI)，默认情况下参数会被自动附加到替换串上，可以通过在替换串的末尾加上?标记来解决这一问题。

```
rewrite ^/users/(.*)$ /show?user=$1? last;
```

比较一个加上？标记和不加？标记的URL跳转区别：

```
rewrite ^/test(.*)$ //hqidi.com/home premanent;
```

访问//hqidi.com/test?id=5 经过301跳转后的URL地址为 //hqidi.com/home?id=5

```
rewrite ^/test(.*)$ //hqidi.com/home? premanent;
```

访问//hqidi.com/test?id=5 经过301跳转后的URL地址为 //hqidi.com/home



### NginxRewrite 规则相关指令

