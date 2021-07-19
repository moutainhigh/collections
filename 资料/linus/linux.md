Linux常用命令：
1、开关机
shutdown -r now 或reboot：立刻重启
shutdown -h now：立刻关机

2、系统信息
who am i：查看当前使用的终端
who或w：查看所有终端
uname -m：显示机器的处理器架构（x86_64）
uname -r：显示正在使用的内核版本
cat /proc/version：查看Linux版本信息
cat /proc/cpuinfo：显示CPU的信息
date：显示系统日期
cal 2019 ：显示2019年的日历表
clear：清空命令行
ifconfig：显示或设置网卡（查ip等）
ping -c 3 www.baidu.com：测试百度与本机的连接清况（-c 3 表示测试3次）

3、系统性能
top ：动态实时显示cpu、内存、进程等使用情况（类似windows下的任务管理器）
top -d 2 -p 7427 ：-d为画面更新的秒数，默认5秒，-p为指定进程pid的信息
vmstat 2 10 ：每隔2秒采集一次服务器状态，采集10次（查看内存、io读写状态、cpu）
free -h :查看系统内存及虚拟内存使用情况
df -h :显示磁盘的空间使用情况
iostat ：可查io读写、cpu使用情况
sar -u 3 5 :查看cpu使用情况（3秒一次，共5次）
sar -d 2 3 ：评估磁盘性能
ps -ef | grep 文件名：查看程序执行的情况
ps aux|grep firefox ：获取火狐的进程号（PID）（可查看进程占用cpu、内存百分比及进程触发指令的路径）
kill -9 进程号 ：强制杀死进程
systemctl ：查看正在运行的服务

4、文件和目录
cd ：进入该用户的主目录 ~（root用户为/root,其他用户为/home/用户名）
cd .. ：返回上一级目录（注意要空格）
cd - ：返回上次所在目录
cd / ：返回根目录 （绝对路径）
cd ./目录1/目录2 ：进入当前目录下的子目录（相对路径）
pwd ：显示工作路径（Print Working Directory 的缩写）

ls -a :列出文件下所有的文件，包括以“.“开头的隐藏文件
file 文件或目录 ：显示文件的类型（目录、text、zip、shell脚本等）

mkdir dir1 :创建目录(dir1)（mkdir为make directory的缩写）
mkdir -p ./dir1/dir2 :递归创建目录（-p：父目录不存在时，同时建立）
rmdir 文件夹名：删除文件夹
touch a.txt :创建文件a.txt

rm 文件 ：删除文件
rm -r 目录或文件 ：删除目录（及目录下所有文件）（非空也可以）
rm -rf 目录或文件 ：强制删除，如：rm -rf * 为删除当前目录下所有文件

mv a b :移动或者重命名一个文件或者目录（存在即移动目录或覆盖文件，不存在即改名）
mv /opt/git/g /opt/a ：移动g到opt目录下并改名为a（a目录不存在，若存在则为移动g到a目录下）
mv -t ./test a.txt b.txt ：移动多个文件到某目录下

cp -ai /opt/abc /opt/git/ ：复制abc目录（或文件）到git目录下（选项a表示文件的属性也复制、目录下所有文件都复制；i表示覆盖前询问）

ln：link的缩写，用于建立硬（软）链接，常用于软件安装时建软链接(类似快捷方式)到PATH;
语法：ln [-s] 源文件 目标文件
ln -s /opt/a.txt /opt/git/ :对文件创建软链接（快捷方式不改名还是a.txt）
ln -s /opt/a.txt /opt/git/b :（快捷方式改名为b）（下面的一样可以改名）
ln -s /opt/mulu /opt/git/ :对目录创建软链接
ln /opt/a.txt /opt/git/ :对文件创建硬链接

5、文件权限
chmod [-R] 777 文件或目录：设置权限（chmod a+rwx a=chmod ugo +rwx a=chmod 777 a）
chown [-R] admin:root /opt/ ：变更文件及目录的拥有者和所属组（-R递归处理所有文件和文件夹，admin为拥有者，root为所属者）

6、文件查找
locate a.txt：在系统全局范围内查找文件名包含a.txt字样的文件（比find快）;
find ./ -name index.jsp：查找当前目录下名称为index.jsp的文件
which java：在环境变量$PATH设置的目录里查找符合条件的文件，并显示路径（查询运行文件所在路径）
whereis java :查看安装的软件的所有的文件路径（whereis 只能用于查找二进制文件、源代码文件和man手册页，一般文件的定位需使用locate命令）

7、查看文件的内容
cat [-n] 文件名：显示文件内容，连行号一起显示
less/more 文件名：一页一页的显示文件内容（搜索翻页同man命令）
head [-n] 文件名 ：显示文件头n行内容，n指定显示多少行
tail [-nf] 文件名:显示文件尾几行内容,n指定显示多少行,f用于实时追踪文件的所有更新，常用于查阅正在改变的日志文件（如tail -f -n 3 a.log 表示开始显示最后3行，并在文件更新时实时追加显示，没有-n默认10行）

8、文本处理
ls -l>file ：输出重定向>（改变原来系统命令的默认执行方式）：ls -l命令结果输出到file文件中，若存在，则覆盖
cat file1 >>file ：输出重定向之cat命令结果输出追加到file文件(>表示覆盖原文件内容，>>表示追加内容)
ls fileno 2>file ： 2>表示重定向标准错误输出（文件不存在，报错信息保存至file文件）；
cowsay <a.txt :重定向标准输入’命令<文件’表示将文件做为命令的输入（为从文件读数据作为输入）
vim 文件：编辑查看文件（同vi）

9、用户与权限
useradd 用户名 ：创建用户
userdel -r 用户名 :删除用户：（-r表示把用户的主目录一起删除）
usermod -g 组名 用户名 ：修改用户的组
usermod -aG 组名 用户名 ：将用户添加到组
groups test ：查看test用户所在的组
cat /etc/group |grep test ：查看test用户详情：用户名:口令:用户标识号:组标识号:注释性描述:主目录:登录Shell
passwd [ludf] 用户名 ：用户改自己密码，不需要输入用户名，选项-d:指定空口令,-l:禁用某用户，-u解禁某用户，-f：强迫用户下次登录时修改口令
groupadd 组名 ：创建用户组
groupdel 用户组 ：删除组
groupmod -n 新组名 旧组名 ：修改用户组名字
su - 用户名：完整的切换到一个用户环境（相当于登录）（建议用这个）（退出用户：exit）
su 用户名 :切换到用户的身份（环境变量等没变，导致很多命令要加上绝对路径才能执行）
sudo 命令 ：以root的身份执行命令（输入用户自己的密码，而su为输入要切换用户的密码，普通用户需设置/etc/sudoers才可用sudo）

10、磁盘管理
df -h :显示磁盘的空间使用情况 及挂载点
df -h /var/log :（显示log所在分区（挂载点）、目录所在磁盘及可用的磁盘容量）
du -sm /var/log/* | sort -rn : 根据占用磁盘空间大小排序（MB）某目录下文件和目录大小
fdisk -l :查所有分区及总容量，加/dev/sda为查硬盘a的分区）
fdisk /dev/sdb :对硬盘sdb进行分区
mount /dev/sda1 /mnt ：硬盘sda1挂载到/mnt目录（mount 装置文件名 挂载点）
mount -t cifs -o username=luolanguo,password=win用户账号密码,vers=3.0 //10.2.1.178/G /mnt/usb :远程linux 共享挂载windows的U盘,G为U盘共享名，需设置U盘共享
mount -o loop /opt/soft/CentOS-7-x86_64-DVD-1708.iso /media/CentOS ：挂载iso文件
umount /dev/sda1 ：取消挂载（umount 装置文件名或挂载点）

11、压缩、解压和打包备份
file 文件名：查文件类型
tar -zxvf 文件名：解压
tar -zcvf 文件名：压缩
gzip：用来对文件进行压缩和解压缩
unzip：对ZIP格式的压缩文件进行解压缩

12、软件安装
yum 安装
rpm 安装
wget http://路径   下载