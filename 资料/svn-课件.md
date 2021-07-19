# svn-课件

## 1、svn是什么

​	项目版本管理工具，用来管理项目的状态变化

## 2、svn下载安装

### 	2.1 svn 服务器下载安装(VisualSVN Server)

​			2.1.1 360 安全管家下载

### 	2.2 svn客户端安装 （TortoiseSVN）

​			2.2.1 svn客户端下载地址：https://osdn.net/projects/tortoisesvn/storage/1.11.0/

​			2.2.2 svn客户端语言包下载地址：https://osdn.net/frs/redir.php?m=tuna&f=%2Fstorage%2Fg%2Ft%2Fto%2Ftortoisesvn%2F1.11.0%2FLanguage+Packs%2FLanguagePack_1.11.0.28416-x64-zh_CN.msi

## 3、svn使用

### 	3.1 svn分享项目

### 	3.2 svn提交

### 	3.3 svn更新

### 	3.4 svn版本回退

### 	3.4 svn冲突

### 	3.5 svn分支

## 4、svn集成

### 	4.1 svn-eclipse集成

​			**Eclipse安装svn插件的几种方式**

 

**1.在线安装：**

(1).点击 Help --> Install New Software...
![img](http://dl2.iteye.com/upload/attachment/0104/7656/9c3206ae-9e57-3902-b85f-9a77c275ee59.png)
 

(2).在弹出的窗口中点击add按钮，输入Name(任意)和Location(插件的URL)，点击OK
![点击查看原始大小图片](http://dl2.iteye.com/upload/attachment/0104/7658/e3fd7da2-1922-340c-bbe1-2f5c75e0ef16.png)
 

(3).勾选出现的插件内容，一步步安装即可。
![点击查看原始大小图片](http://dl2.iteye.com/upload/attachment/0104/7660/f8274dcb-c717-3422-9e5f-cc660d54d33b.png)
**注：**
目前在线安装svn的版本只有**1.6.x**和**1.8.x**
**地址分别是：**
http://subclipse.tigris.org/update_1.6.x
http://subclipse.tigris.org/update_1.8.x (支持的是Subversion 1.7.x)

 

相关链接：

[Eclipse在线安装Subversion1.82（SVN）插件](http://blog.sina.com.cn/s/blog_533587770101dc22.html)

 

**2.离线安装：**

(1).下载需要的插件包：http://www.oschina.net/p/subclipse/，包里会有"plugins"和"features"两个文件夹
![img](http://dl2.iteye.com/upload/attachment/0104/7711/ea84f1a7-bcd0-32f4-80b8-a174b7c3b9bf.png)
 

(2).找到eclipse目录下"plugins"和"features"文件夹，将下载好的的svn对应名称文件夹下的内容复制到eclipse的同名文件夹中
![点击查看原始大小图片](http://dl2.iteye.com/upload/attachment/0104/7713/bd10fc05-c8dd-36d4-8136-f963194e6b22.png)
 

(3).通过clean重启Eclipse（方式：打开cmd,进入eclipse安装目录，执行**eclipse.exe -clean**）
![img](http://dl2.iteye.com/upload/attachment/0104/7715/7c73db50-2094-3be0-985d-82e623e70b03.png)

 

**3.link方式：**

(1).下载subeclipse插件

(2).在任意盘符下新建文件夹Plugins,在里面新建文件夹subclipse,点击进入，将下载好的subclipse压缩包解压在subclipse文件夹中
![img](http://dl2.iteye.com/upload/attachment/0104/7746/137a7667-3c94-36b1-bc42-27756bd6ab59.png)
 

(3).进入eclipse安装目录，创建links文件夹，在文件夹中创建subclipse.link文件
![img](http://dl2.iteye.com/upload/attachment/0104/7748/89f2bff9-7da0-3459-bba0-744a151523c8.png)
 

(4).在subclipse.link文件中输入路径地址：path=D:\\Plugins\\subclipse
![img](http://dl2.iteye.com/upload/attachment/0104/7751/69922446-bd37-38ba-a493-1722d8a73b3b.png)



(5).通过clean重启Eclipse（方式：打开cmd,进入eclipse安装目录，执行**eclipse.exe -clean**）

 

**注意：**

(1).**link文件名，插件所在的文件夹名，path路径名，三者必须一致！**

(2).如果想暂时不启动插件：只需把link文件删除，或者将path路径改为非插件所在路径即可

### 	4.2 svn-idea集成

[IntelliJ Idea 集成svn 和使用](https://www.cnblogs.com/pejsidney/p/8758105.html)

IntelliJ Idea 集成svn 和使用

开始使用IntelliJ Idea尝试了一下，虽然快捷键与eclipse 有些不同，但是强大的搜索功能与“漂亮的界面”（个人认为没有eclipse好看 ），还是值得我们去使用的。

刚开始使用的 idea要去集成svn，下载公司的项目 。

既然要使用svn，那么首先我们需要下载一个 svn的客户端，可以到这里下载对应的安装程序：<http://subversion.apache.org/packages.html#windows>

 

我是用的是TortoiseSVN(小乌龟)，下载后安装 ，然后记住安装路径，我安装的是64位的。

TortoiseSVN的下载地址 ：   https://tortoisesvn.net/downloads.html

![img](https://images2015.cnblogs.com/blog/979024/201612/979024-20161221110559714-725318014.png)

 

在安装svn客户端的时候一定要勾选，否则在idea上集成svn的时候会找不到 svn.exe 而报错。

如果安装时忘记勾选了的话，安装包重新运行，选择modify，然后勾选command line client tools项就行了。

![img](https://images2015.cnblogs.com/blog/979024/201612/979024-20161221112134870-2036513943.png)

报错信息：![img](https://images2015.cnblogs.com/blog/979024/201612/979024-20161221112232651-386045117.png)

 

 

安装好svn客户端后，想启用idea的SVN插件还需要在idea配置一下，file - setting 按钮打开设置界面 或者（Ctrl + Alt + S）快捷键 ，如下图所示：

![img](https://images2015.cnblogs.com/blog/979024/201612/979024-20161221110950636-1549245375.png)

 

重启一下你的IntelliJ Idea，然后从svn库中下载项目：

 ![img](https://images2015.cnblogs.com/blog/979024/201612/979024-20161221112956761-655969517.png)

 

输入公司的svn的地址check 出你想要的项目，就OK了

![img](https://images2015.cnblogs.com/blog/979024/201612/979024-20161221113148526-1550889507.png)

