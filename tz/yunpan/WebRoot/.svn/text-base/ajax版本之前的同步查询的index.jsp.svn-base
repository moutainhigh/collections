<%@page import="com.yunpan.bean.User"%>
<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.bean.Resource"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- c标签 -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 自定义函数标签 -->
<%@taglib uri="/WEB-INF/tld/tz.tld" prefix="tz" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8" />
<head>
	<title>潭州组件文件上传--云盘技术</title>
	<!-- 布局样式 -->
	<link href="css/layout.css" rel="stylesheet" type="text/css" />
	<!-- 上传样式 -->
	<link href="js/upload/upload.css" rel="stylesheet" type="text/css" />
	<!-- jquery核心js -->
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<!-- 工具JS -->
	<script type="text/javascript" src="js/util.js"></script>
	<!-- 拖动JS -->
	<script type="text/javascript" src="js/tmdrag.js"></script>
	<!-- 弹出层js -->
	<script type="text/javascript" src="js/tmDialog.js"></script>
	<!-- 上传组件js -->
	<script type="text/javascript" src="js/upload/tz_upload.js"></script>
	<!-- 去除页面的滚动条 -->
	<style type="text/css">
		html, body{overflow: hidden;}
		#box .name-text input{height:31px;line-height: 31px;width: 240px;}
		.select{background: #c5c5c5}
		#contentbox h1{font-size: 32px;text-align: center;margin-top: 50px;}
		.btns{background: #999;border-radius:2px;padding:8px 10px;color: #fff;}
	</style>
	<!-- 
		---初学者,有点经验
		1：先写业务,而不是先写特效。是项目里面需要他的时候自然而然就去学习的.比如:验证码，文件上传。
		2：一定是先写业务，必须一定讲业务的代表CURD 增删查改 先把数据库把保存在表中。
		3：在思考数据合法性，完整性-----验证/js /服务器端的验证也好
	 -->
</head>
<body>
	<%
		String t = request.getParameter("t");
		Integer type = null;
		if(StringUtils.isNotEmpty(t))type = new Integer(t);
		//调用查询出来的文件信息
		List<Resource> resources = ResourceDao.findResources(type,0,10);
		//放在作用域
		pageContext.setAttribute("resources", resources);
		pageContext.setAttribute("type", type);
	%>
	<div id="container">
	  <div id="header">
		<div class="fl logo"> 
			<a href="http://www.tanzhouedu.net/index"><img src="images/logo.png"></a>
		</div>
	  	<div class="fr"><h1>潭州学院云盘实现功能</h1></div>
	  </div>
	  <div id="mainContent">
	    <div id="sidebar">
	    	<ul>
				<li class="slidebar ${empty type ? 'select':''}"><a href="/yunpan/index.jsp">全部文件</a></li>
	    		<li class="slidebar ${type==1 ? 'select':''}"><a href="/yunpan/index.jsp?t=1">文件</a></li>
	    		<li class="slidebar ${type==2 ? 'select':''}"><a href="/yunpan/index.jsp?t=2">图片</a></li>
	    		<li class="slidebar ${type==3 ? 'select':''}"><a href="/yunpan/index.jsp?t=3">视频</a></li>
	    		<li class="slidebar ${type==4 ? 'select':''}"><a href="/yunpan/index.jsp?t=4">其他</a></li>
	    		<li>回收站</li>
	    	</ul>
	    </div>
	    <!-- 内容区域 -->
	    <div id="content">
	    	<div id="box">
				<div class="header fl" style="width: 100%;">
					<div class="upload fl"><span id="tmupload"></span></div>
					<div class="fl" style="margin-left: 40px;">
						<a href="javascript:void(0)"  op="on" id="checkall" class="btns">全选</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" class="btns">新建文件夹</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" class="btns" onclick="tm_deleteResources()">批量删除</a>
					</div>
					<div class="fr" style="margin-right: 20px;font-size: 14px;">
						当前登陆的用户是:${user.username}&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="service/logout.jsp" style="color: green;">退出</a>
					</div>
				</div>
				<script type="text/javascript">
					$(function(){
						$("#checkall").click(function(){
							var text  = $(this).attr("op");
							if(text=="on"){
								$("#contentbox").find(".chk").prop("checked",true);
								$(this).attr("op","off");
								$(this).text("反选");
							}else{
								$("#contentbox").find(".chk").prop("checked",false);
								$(this).attr("op","on");
								$(this).text("全选");
							}
						});
					});
				</script>
				<!-- 内容包含区 -->
				<ul class="fl" style="width: 100%;" id="contentbox">
					<c:choose>
						<c:when test="${tz:getLength(resources)==0}">
							<h1>暂无上传任何数据</h1>	
						</c:when>
						<c:otherwise>
							<c:forEach var="resource" items="${resources}">
								<li id="tm-items-${resource.id}" class="items" data-opid="${resource.id}">
									<div class="col c1 fl" style="width: 50%;">
						                <span class="fl icon"><input type="checkbox"  name="ridchk" value="${resource.id}" class="chk fl"><img src="images/text.png"></span>
						                <div class="name fl"><span title="${resource.name}" class="name-text">${resource.id}：${resource.name}</span></div>
						            </div>
						            <div class="col" style="width: 16%" title="字节大小:${resource.size}">${resource.sizeString}</div>
						            <div class="col" style="width: 23%;color:green">${tz:dateFormat(resource.createTime,'yyyy-MM-dd HH:mm:ss')}（${tz:dateString2(resource.createTime)}）</div>
						            <div class="col buttons" style="width: 10%">
						            	<a href="javascript:void(0);" class="edit"><img src='images/edit.png'></a>&nbsp;&nbsp;
			<!-- 			            <a href="javascript:void(0);" data-opid="${resource.id}" onclick="tm_deleteresource(this);"><img src='images/delete.gif'></a> -->
						            	<a href="javascript:void(0);" data-opid="${resource.id}" class="delete"><img src='images/delete.gif'></a>
						            </div>
								</li>
							</c:forEach>
						</c:otherwise>	
					</c:choose>
				</ul>
				<!-- 内容包含区 -->
			</div>
	    </div>
	    <!-- 内容区结束 -->
	  </div>
	</div>
	<a href="javascript:void(0);" class="prev">上一页</a>
	<a href="javascript:void(0);" class="next">下一页</a>
	<script type="text/javascript" src="js/resource.js"></script>
	<script type="text/javascript">
		$(function(){
			//下一页
			$(".next").click(function(){
				
			});
			
			//上一页
			$(".prev").click(function(){
				
			});
		});
	</script>
	
</body>
</html>