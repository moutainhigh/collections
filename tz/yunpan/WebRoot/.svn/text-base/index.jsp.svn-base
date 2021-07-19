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
	<!-- 图片预览插件 -->
	<script type="text/javascript" src="js/tz_imgbox.js"></script>
	<!-- 分页插件 -->
	<script type="text/javascript" src="js/jquery.pagination.js"></script>
	<!-- 去除页面的滚动条 -->
	<style type="text/css">
		html, body{overflow: hidden;}
		#box .name-text input{height:31px;line-height: 31px;width: 240px;}
		.select{background: #c5c5c5}
		#contentbox h1{font-size: 32px;text-align: center;margin-top: 50px;}
		.btns{background: #999;border-radius:2px;padding:8px 10px;color: #fff;}
		.page{margin:15px;line-height: 20px;height:20px;float:left;width: 100%;}
		.off{background:#ccc;padding:5px;color:#fff;}
		.on{background:green;padding:5px;color:#fff;}
		#tmpage{position: relative;top:8px;left:24px;float:left;}
	</style>
	<!-- 
		---初学者,有点经验
		1：先写业务,而不是先写特效。是项目里面需要他的时候自然而然就去学习的.比如:验证码，文件上传。
		2：一定是先写业务，必须一定讲业务的代表CURD 增删查改 先把数据库把保存在表中。
		3：在思考数据合法性，完整性-----验证/js /服务器端的验证也好
		
		4：删除改成逻辑删除,利用update修改表中的is_delete=1
		5：回收站就是查询is_delete=1的数据  : 还原和删除
		
	 -->
</head>
<body>
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
				<li class="slidebar select"><a href="javascript:void(0);" onclick="tm_load(this)">全部文件</a></li>
	    		<li class="slidebar"><a href="javascript:void(0);" data-type="1" onclick="tm_load(this)">文件</a></li>
	    		<li class="slidebar"><a href="javascript:void(0);" data-type="2" onclick="tm_load(this)">图片</a></li>
	    		<li class="slidebar"><a href="javascript:void(0);" data-type="3" onclick="tm_load(this)">视频</a></li>
	    		<li class="slidebar"><a href="javascript:void(0);" data-type="4" onclick="tm_load(this)">其他</a></li>
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
						<a href="javascript:void(0)" class="btns" onclick="tm_deleteResources()">批量删除</a>&nbsp;&nbsp;
						<input type="text" id="keyword" style="height: 32px;line-height: 32px;" autofocus="autofocus"  placeholder="请输入文件名称">&nbsp;&nbsp;<a href="javascript:void(0)" class="btns" onclick="tm_search(this)">搜索</a>
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
				<input type="hidden" id="total" value="0">
				<ul class="fl" style="width: 100%;" id="contentbox"></ul>
				<!-- 分页区 -->
				<div id="tmpage"></div>
			</div>
	    </div>
	    <!-- 内容区结束 -->
	  </div>
	</div>
	<script type="text/javascript" src="js/resource.js"></script>
	<script type="text/javascript">
		$(function(){
			//页面数据加载调用的ajax方法
			tm_load_resources(0,10,function(itemcount){
				tm_init_page(itemcount);
			});
			
			$(document).keydown(function(e){
				if(e.keyCode==13){
					tm_load_resources(0,10,function(itemcount){
						tm_init_page(itemcount);
					});
				}
			});
		});
		
		//参数查询
		function tm_load(obj){
			$(obj).parent().addClass("select").siblings().removeClass("select");
			tm_load_resources(0,10,function(itemcount){
				tm_init_page(itemcount);
			});
		}
		
		//搜索
		function tm_search(obj){
			tm_load_resources(0,10,function(itemcount){
				tm_init_page(itemcount);
			});
		}
		
		//分页初始化调用的方法
		function tm_init_page(itemcount){
			$("#tmpage").pagination(itemcount, {
				num_display_entries : 3, //主体页数
				num_edge_entries : 4,//边缘页数
				current_page : 0,//指明选中页码
				items_per_page : 10, //每页显示多条条
				prev_text : "首页",
				next_text : "尾页",
				showGo:true,//显示
				showSelect:true,
				callback : function(pageNo, psize) {//会回传两个参数一个当前页，显示的页数
					tm_load_resources(pageNo,psize);
				}
			});
		};
		
		//关键字高亮
		function keyHighlighter(keyword){
			$("#contentbox").find(".name-text").each(function(){
				$(this).html($(this).text().replace(keyword,"<label style='color:red'>"+keyword+"</label>"));
			});
		};
		
		function tm_load_resources(pageNo,pageSize,callback){
			var type = $(".select").find("a").data("type");
			var keyword = $("#keyword").val();
			$.ajax({
				type:"post",
				beforeSend:function(){
					$("#contentbox").html("<div style='text-align:center;'><img src='images/loading.gif'/></div>");
				},
				data:{"pageNo":pageNo,"pageSize":pageSize,"type":type,"keyword":keyword},
				url:"/yunpan/service/search.jsp",
				success:function(data){
					$("#contentbox").html(data);
					if(callback){
						var itemcount = $("#resourctotal").val(); 
						callback(itemcount);
					}
					keyHighlighter(keyword);//高亮关键字
					//图片插件初始化
					//$("#contentbox").find(".name-text").tzImgbox({width:960,height:560});
				}
			});
			
// 			$("#contentbox").load("/yunpan/service/search.jsp",{"pageNo":pageNo,"pageSize":pageSize},function(){
// 				if(callback){
// 					var itemcount = $("#resourctotal").val(); 
// 					callback(itemcount);
// 				}
// 			});
		}
	</script>
</body>
</html>