作者：keke
开发时间：2015-10-17 23:28
项目名称：潭州学院学生作业管理系统
环境：myeclipse2014 + win7 +jdk1.7+ tomcat7.0
技术框架：SpringMvc + Hibernate
数据库：Mysql5.6+
数据库库名称：tzexam

项目的结构
/TzExam
	/TzExam/src
		com.tz.core
		com.tz.dao
		com.tz.model
		com.tz.service
		com.tz.util
		com.tz.web
	/TzExam/config
		/TzExam/config/applicationContext.xml
		/TzExam/config/log4j.properties
	/TzExam/test
	/TzExam/WebRoot
		/TzExam/WebRoot/css
		/TzExam/WebRoot/images
		/TzExam/WebRoot/js
		/TzExam/WebRoot/META-INF
		/TzExam/WebRoot/WEB-INF
		/TzExam/WebRoot/WEB-INF/lib
		/TzExam/WebRoot/WEB-INF/web.xml
		/TzExam/WebRoot/index.jsp
		/TzExam/WebRoot/readme.txt
模块：
	学科表
	
	id	int(11)	NO	PRI		auto_increment
	name	varchar(100)	YES			
	description	text	YES			
	create_time	timestamp	NO		CURRENT_TIMESTAMP	
	is_delete	int(1)	YES			
	status	int(1)	YES			
	update_time	datetime	YES			
	user_id	int(11)	YES			
	up_user_id	int(11)	YES			

	查询学科表信息，条件：status=1 (发布) 并且is_delete = 0(没删除)的学科信息，并且排创建时间降序create_time desc;


