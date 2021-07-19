<%@page import="com.yunpan.bean.Resource"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8" />
<head>
	<title>潭州组件文件上传</title>
	<link href="css/layout.css" rel="stylesheet" type="text/css" />
	<link href="js/upload/upload.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/util.js"></script>
	<script type="text/javascript" src="js/tmdrag.js"></script>
	<script type="text/javascript" src="js/tmDialog.js"></script>
	<script type="text/javascript" src="js/upload/tz_upload.js"></script>
	<style type="text/css">html, body{overflow: hidden;}</style>
	<!-- 
		---初学者,有点经验
		1：先写业务,而不是先写特效。是项目里面需要他的时候自然而然就去学习的.比如:验证码，文件上传。
		2：一定是先写业务，必须一定讲业务的代表CURD 增删查改 先把数据库把保存在表中。
		3：在思考数据合法性，完整性-----验证/js /服务器端的验证也好
	 -->
</head>
<body>
<%
	//调用查询出来的文件信息
	List<Resource> resources = ResourceDao.findResources();
	//放在作用域
	pageContext.setAttribute("resources", resources);
%>
<div id="container">
  <div id="header">
	<div class="fl logo"> <a href="http://www.tanzhouedu.net/index"><img src="images/logo.png"></a> </div>
  	<div class="fr"><h1>潭州学院云盘实现功能</h1></div>
  </div>
  <div id="mainContent">
    <div id="sidebar">
    	<ul>
			<li>全部文件</li>
    		<li>文件</li>
    		<li>图片</li>
    		<li>视频</li>
    		<li>其他</li>
    		<li>回收站</li>
    	</ul>
    </div>
    <div id="content">
    	<div id="box">
			<div class="header fl" style="width: 100%;">
				<div class="upload fl"><span id="tmupload"></span></div>
				<div class="fl" style="margin-left: 40px;">
					<a href="javascript:void(0)">新建文件夹</a>
				</div>
			</div>
			<ul class="fl" style="width: 100%;" id="contentbox">
				<li>
					<div class="col c1" style="width: 50%;">
		                <span class="chk fl"><input type="checkbox" class="chk"></span>
		                <span class="fl icon"><img src="images/folder.png"></span>
		                <div class="name fl"><span class="name-text">JavaVIP体验班-系统教程-Arry老师Java</span></div>
		            </div>
		            <div class="col" style="width: 16%">174KB</div>
		            <div class="col" style="width: 23%">2014-11-21 20:18</div>
		            <div class="col buttons" style="width: 10%">
		            	<a href="javascript:void(0);"><img src='images/edit.png'></a>&nbsp;&nbsp;
		            	<a href="javascript:void(0);"><img src='images/delete.gif'></a>
		            </div>
				</li>
				<li>
					<div class="col c1" style="width: 50%;">
		                <span class="chk fl"><input type="checkbox" class="chk"></span>
		                <span class="fl icon"><img src="images/jpg.png"></span>
		                <div class="name fl"><span class="name-text">JavaVIP体验班-系统教程-Arry老师Java</span></div>
		            </div>
		            <div class="col" style="width: 16%">174KB</div>
		            <div class="col" style="width: 23%">2014-11-21 20:18</div>
		            <div class="col buttons" style="width: 10%">
		            	<a href="javascript:void(0);"><img src='images/edit.png'></a>&nbsp;&nbsp;
		            	<a href="javascript:void(0);"><img src='images/delete.gif'></a>
		            </div>
				</li>
				<li>
					<div class="col c1" style="width: 50%;">
		                <span class="chk fl"><input type="checkbox" class="chk"></span>
		                <span class="fl icon"><img src="images/gif.png"></span>
		                <div class="name fl"><span class="name-text">JavaVIP体验班-系统教程-Arry老师Java</span></div>
		            </div>
		            <div class="col" style="width: 16%">174KB</div>
		            <div class="col" style="width: 23%">2014-11-21 20:18</div>
		            <div class="col buttons" style="width: 10%">
		            	<a href="javascript:void(0);"><img src='images/edit.png'></a>&nbsp;&nbsp;
		            	<a href="javascript:void(0);"><img src='images/delete.gif'></a>
		            </div>
				</li>
				<li>
					<div class="col c1" style="width: 50%;">
		                <span class="chk fl"><input type="checkbox" class="chk"></span>
		                <span class="fl icon"><img src="images/text.png"></span>
		                <div class="name fl"><span class="name-text">JavaVIP体验班-系统教程-Arry老师Java</span></div>
		            </div>
		            <div class="col" style="width: 16%">174KB</div>
		            <div class="col" style="width: 23%">2014-11-21 20:18</div>
		            <div class="col buttons" style="width: 10%">
		            	<a href="javascript:void(0);"><img src='images/edit.png'></a>&nbsp;&nbsp;
		            	<a href="javascript:void(0);"><img src='images/delete.gif'></a>
		            </div>
				</li>
			</ul>
		</div>
    </div>
  </div>
</div>

<script type="text/javascript">
	$(function(){
		//禁止文本窗口选中
		tm_forbiddenSelect();
		//禁止浏览器的右键
		document.body.oncontextmenu=document.body.ondragstart= document.body.onselectstart=document.body.onbeforecopy=function(){return false;};
		//工具类的高度是自动换算的
		$("#sidebar").height(getClientHeight()-103);
		//内容栏的高度是自动换算的
		$("#content").height(getClientHeight()-105);
		//这行是浏览器改变的时候自动的改变内容和工具栏的高度
		$(window).resize(function(){
			$("#sidebar").height(getClientHeight()-103);
			$("#content").height(getClientHeight()-105);
		});
		
		//执行保存文件
		tm_saveresource();
	});
	
	
	function tm_saveresource(jdata){
		var name = jdata.name;
		var size = jdata.size;
		var sizeString  = jdata.sizeString;
		var newName = jdata.newName;
		var ext = jdata.ext;
		var url = jdata.url;
		var width = "100";//图片的宽度
		var height = "100";//图片的高度
		var description = "";//描述
		var folderId = 1;//对应的文件夹
		//参数设置
		var params = {
			"name":name,
			"newName":newName,
			"size":size,
			"url":url,
			"sizeString":sizeString,
			"ext":ext,
			"width":width,
			"height":height,
			"description":description,
			"folderId":folderId
		};
		
		//发送ajax
		$.ajax({
			type:"post",
			url:"/yunpan/service/resource.jsp",
			data:params,
			success:function(data){
				var result = data.trim();
				if(result=="success"){
					$("#contentbox").append("<li>"+
						"		<div class='col c1' style='width: 50%;'>"+
				        "        <span class='chk fl'><input type='checkbox' class='chk'></span>"+
				        "        <span class='fl icon'><img src='images/gif.png'></span>"+
				        "        <div class='name fl'><span class='name-text'>"+jdata.name+"</span></div>"+
				        "    </div>"+
				        "    <div class='col' style='width: 16%'>"+jdata.sizeString+"</div>"+
				        "    <div class='col' style='width: 23%'>"+new Date().format("yyyy-MM-dd HH:mm:ss")+"</div>"+
				        "    <div class='col buttons' style='width: 10%'>"+
				        "    	<a href='javascript:void(0);'><img src='images/edit.png'></a>&nbsp;&nbsp;"+
				        "    	<a href='javascript:void(0);'><img src='images/delete.gif'></a>"+
				        "    </div>"+
						"</li>");
						$("#content").height(getClientHeight()-105);
				}else{
					tm_dialoag({title:"添加提示",width:340,height:200,content:"非常抱歉，文件添加失败!"});
				}
			}
		});
	}
	
	
	$.tmUpload({"fileTypes":"*.*",callback:function(data,file){
		var jdata = eval("("+data+")");
		tm_saveresource(jdata);
	}});
</script>
</body>
</html>