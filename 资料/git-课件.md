# git-课件

## 1、git是什么

​	分布式项目版本管理工具

## 2、git下载安装

### 	2.1 git 官网下载

​		2.1.1 命令行工具(GUI Bash)

​		2.1.2 客户端工具（TotoriseGit）

### 	2.2 git 360软件管家下载

## 3、git使用

### 	3.1 git命令行使用

​			3.1.1 基本命令

​			3.1.1 git init  创建版本库 （创建本地版本库）

​			3.1.2 git add  filename 提交到版本库暂存区

​			3.1.3 git commit -m '注释' 将暂存区提交到版本库

​			3.1.4 git log 查看版本库提交历史（可以查询commit提交版本号）

​			3.1.5 git reset --hard HEAD^ 版本回退 首先，Git必须知道当前版本是哪个版本，在Git中，用`HEAD`表示当前版本，也就是最新的提交`1094adb...`（注意我的提交ID和你的肯定不一样），上一个版本就是`HEAD^`，上上一个版本就是`HEAD^^`，当然往上100个版本写100个`^`比较容易数不过来，所以写成`HEAD~100`。

​			3.1.6 git reset --hard  <commit it> 版本回退， <commit it> 指定版本号

​			3.1.7 git reflog 查询命令历史

​			3.1.2 工作区和暂存区

​				 每次修改，如果不用`git add`到暂存区，那就不会加入到`commit`中。

​				 git status 查看工作区和暂存区的状态信息

​		                 git checkout -- file 工作区的撤销修改  (没有-- 就表示切换到分支了)

​				git reset HEAD <file>暂存区的修改撤销掉,重新放回工作区

​				git rm <file> 删除文件 然后commit提交

​			3.1.3 远程仓库

​				git remote add origin git@github.com:zjhlovewtr/test.git 版本仓库与本地仓库进行关联 

​				git push -u origin master 把本地仓库内容推送到远仓仓库   由于远程库是空的，我们第一次推送`master`分支时，加上了`-u`参数，Git不但会把本地的`master`分支内容推送的远程新的`master`分支，还会把本地的`master`分支和远程的`master`分支关联起来，在以后的推送或者拉取时就可以简化命令。

​				git push origin master 修改后推送

​				git clone git@github.com:zjhlovewtr/test.git  从远程仓库克隆一个仓库到本地（不需要git init操作）

​			3.1.4 git 分支

​			         git branch  <branchName> 创建分支

​				git checkout  <branchName>切换分支

​				git merge  <branchName> 合并分支（branch name为目标分支）

​				git branch -d <branchNanme> 删除分支

​				git checkout -b <branchNanme>创建+切换分支 	

​      			冲突解决

​				先文件修改

​				然后git add <fileName>

​				然后git commit -m '分支修改'	

​				最后在合并

​				git merge --no-ff -m "merge with no-ff" dev 普通分支合并  请注意`--no-ff`参数，表示禁用`Fast forward`：

​		               bug 分支

​				git stash 保留修改现场情况

​				git stash list 查询工作现场情况

​				`git stash apply`恢复，但是恢复后，stash内容并不删除，你需要用`git stash drop`来删除；

​				git stash pop 恢复的同时把stash内容也删了

​			3.1.5 标签管理

​				git tag v1.0 创建标签

​			        git tag 查询所有标签 

​				`git push origin <tagname>`可以推送一个本地标签；

​				`git push origin --tags`可以推送全部未推送过的本地标签；

​				`git tag -d <tagname>`可以删除一个本地标签；

​				`git push origin :refs/tags/<tagname>`可以删除一个远程标签。

### 3.2 git客户端端使用

## 4、git集成

### 	4.1 git eclispe集成

### 	4.2 git idea集成