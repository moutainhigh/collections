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
	</style>
	<!-- 
		---初学者,有点经验
		1：先写业务,而不是先写特效。是项目里面需要他的时候自然而然就去学习的.比如:验证码，文件上传。
		2：一定是先写业务，必须一定讲业务的代表CURD 增删查改 先把数据库把保存在表中。
		3：在思考数据合法性，完整性-----验证/js /服务器端的验证也好
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
				<input type="hidden" id="total" value="0">
				<ul class="fl" style="width: 100%;" id="contentbox"></ul>
				<!-- 内容包含区 -->
				<div class="page">
					<a href="javascript:void(0);" class="prev off">上一页</a>
					<a href="javascript:void(0);" class="next on">下一页</a>
					<select id="psize" onchange="tm_change_psize(this)">
						<option value="10" selected="selected">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="50">50</option>
						<option value="100">100</option>
					</select>
				</div>
				<div id="tmpage"></div>
			</div>
	    </div>
	    <!-- 内容区结束 -->
	  </div>
	</div>
	
	
	<script type="text/javascript" src="js/resource.js"></script>
	
	<script type="text/javascript">
		
		
		
		
		function tm_init_page(itemcount){
			$("#tmpage").pagination(itemcount, {
				num_display_entries : 3, //主体页数
				num_edge_entries : 4,//边缘页数
				current_page : 0,//指明选中页码
				items_per_page : 10, //每页显示多条条
				prev_text : "首页",
				next_text : "尾页",
				showGo:true,
				showSelect:true,
				callback : function(pageNo, psize) {//会回传两个参数一个当前页，显示的页数
					alert(pageNo+"===="+psize);
				}
			});
		}
		
	</script>
	
	<script type="text/javascript">
		var pno = 0;
		$(function(){
			//下一页
			$(".next").click(function(){
				var total = $("#total").val();
				var psize = $("#psize").val();
				pno = pno + 1;
				var pt = pno * psize;
				var ps = Math.floor(total / psize);//ps是总页面
				//当当前的页面和总页数一样的时候，把按钮下一页的按钮变灰
				if(pno == ps){
					$(".next").removeClass("on").addClass("off");
				}
				//如果当前总数超过了总，那么当前页-1
				if(pt >= total){//已经到尾页 
					pno = pno - 1;
					return;
				}
				tm_search_resource(pno * psize,psize);
				$(".prev").removeClass("off").addClass("on");
				
			});
			
			//上一页
			$(".prev").click(function(){
				var psize = $("#psize").val();
				pno = pno - 1;
				//如果pno等于0的时候代表第一页了
				if(pno==0){
					//禁止按钮再次点击
					$(".prev").removeClass("on").addClass("off");
				}
				
				//已经到0
				if(pno < 0){
					pno=0;//没有上一页
					return;
				}
				//查询数据
				tm_search_resource((pno * psize),psize);
				$(".next").removeClass("off").addClass("on");
			});
			
			//查询数据信息
			
			tm_search_resource(0,$("#psize").val());
		});
		
		function tm_change_psize(obj){
			//因为要回归到首页，防止出现不连续问题
			pno = 0;
			//上一页和下一页回顾到初始状态
			$(".prev").removeClass("on").addClass("off");
			$(".next").removeClass("off").addClass("on");
			tm_search_resource(0,obj.value);
		}
		
		
		function tm_search_resource(pageNo,pageSize){
			$.ajax({
				type:"post",
				beforeSend:function(){
					$("#contentbox").html("<div style='text-align:center;'><img src='images/loading.gif'/></div>");
				},
				data:{"pageNo":pageNo,"pageSize":pageSize},
				url:"/yunpan/service/search.jsp",
				success:function(data){
					//获取服务器传递出来的json字符串数据
					var jdata = data.trim();
					//讲json字符串数据转变成javascript里面的对象
					var dataJson = eval("("+jdata+")");
					$("#total").val(dataJson.total);
					//初始化分页
					tm_init_page(dataJson.total);
					var jsonData = eval("("+dataJson.datas+")");
					var length = jsonData.length;
					var html = "";
					for(var i=0;i<length;i++){
						html +="<li id='tm-items-"+jsonData[i].id+"' class='items' data-opid='"+jsonData[i].id+"'>"+
						"		<div class='col c1 fl' style='width: 50%;'>"+
				        "        <span class='fl icon'><input type='checkbox'  name='ridchk' value='"+jsonData[i].id+"' class='chk fl'><img src='images/text.png'></span>"+
				        "        <div class='name fl'><span title='"+jsonData[i].names+"' class='name-text'>"+(i+1)+"="+jsonData[i].id+"："+jsonData[i].name+"</span></div>"+
				        "    </div>"+
				        "    <div class='col' style='width: 16%' title='字节大小:"+jsonData[i].size+"'>"+jsonData[i].sizeString+"</div>"+
				        "    <div class='col' style='width: 23%;color:green'>"+new Date().format("yyyy-MM-dd HH:mm:ss")+"（刚刚）</div>"+
				        "    <div class='col buttons' style='width: 10%'>"+
				        "    	<a href='javascript:void(0);' class='edit'><img src='images/edit.png'></a>&nbsp;&nbsp;"+
				        "    	<a href='javascript:void(0);' data-opid='"+jsonData[i].id+"' class='delete'><img src='images/delete.gif'></a>"+
				        "    </div>"+
						"</li>";
					}
					$("#contentbox").html(html);
				}
			});
		}

	</script>
	
</body>
</html>