<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tlds/tz.tld" prefix="tz" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>潭州学院-音乐列表管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@include  file="/WEB-INF/common/common.jsp"%>
	<script type="text/javascript" src="${basePath}/js/music/tz_index.js"></script>
	<style type="text/css">
		#box{width: 1024px;height:100px;position: relative;top:68px;margin: 0 auto;padding:20px;}
		#box .tab{width: 100%;border: 0px;}
		#box .tab th{background:#141414;color:#fff;border-right:1px solid #fff;font-size:14px;}
		#box .tab td,th{border: 1px solid #000;padding:5px;}
		#box .tab tbody td{font-size:12px;}
		.green{color: green}
		.red{color: red}
	</style>
  </head>
  <body>
    <%@include  file="/WEB-INF/common/header.jsp"%> 
    <c:if test="${user.id!=1}">
    	<script type="text/javascript">
    		window.location.href = "/error.jsp";
    	</script>
    </c:if> 
    <c:if test="${user.id==1}"> 
	    <div id="box">
	    	<div><h1>期刊管理:</h1></div>
	    	<br/>
	    	<hr/>
	    	<br/>
	    	<table class="tab">
	    		<thead>
	    			<tr>
	    				<th>ID</th>
	    				<th>封面</th>
	    				<th>标题</th>
	    				<th>类型</th>
	    				<th>点击数</th>
	    				<th>第几期</th>
	    				<th>创建时间</th>
	    				<th>发布状态</th>
	    				<th>操作</th>
	    			</tr>
	    		</thead>
	    		<tbody id="tbody"></tbody>
	    	</table>
	    </div>
	  	<script type="text/javascript">
		  	$(function(){
		  		$("#tbody").load("/period/template");
		  	});
		  	
	  		var timer = null;
	  		function tz_publish(obj){
	  		   clearTimeout(timer);
	  		   timer=setTimeout(function(){
		  		    var opid = $(obj).data("opid");
		  		    var status = $(obj).data("status");
		  		    var cstatus ;
		  		    if(status==1){
		  		    	cstatus=0;
		  		    }else{
		  		    	cstatus = 1;
		  		    }
		  		    
	  				$.ajax({
	  					type:"post",
	  					beforeSend:function(){loading("操作进行中,请稍后...");},
	  					error:function(){loading("操作失败...",2);},
	  					url:"/period/update",
	  					data:{"status":cstatus,"id":opid},
	  					success:function(data){
	  						if(!data){
	  							loading("操作失败...",2);
	  						}else{
	  							if(status==1){
	  				  		    	cstatus=0;
	  				  		    	$(obj).toggleClass("green red").text("未发布");
	  				  		    }else{
	  				  		    	cstatus = 1;
	  				  		    	$(obj).toggleClass("green red").text("发布");
	  				  		    }
	  							$(obj).data("status",cstatus);
	  							loading("remove");
	  						}
	  					}
	  				 });
	  			},200);
	  		};
			 //工具类的收集和整理
			 //开发流程，拦截，
			 function tm_deleteperiod(obj){
				 var opid = $(obj).data("opid");
				 $.tzAlert({title:"删除提示",content:"您确定删除吗?",callback:function(ok){
					 if(ok){
						 $.ajax({
							type:"post",
							url:"/period/update",
							data:{"isDelete":1,"id":opid},
							success:function(data){
								if(!data){
									loading("操作失败...",2);
								}else{
	// 								$("#tm_peroid_"+opid).fadeOut("slow",function(){
	// 									$(this).remove();
	// 								});
									$(obj).parents(".tz_items").fadeOut("slow",function(){
										$(this).remove();
		 							});
								}
							}
						 });
					 }
				 }});
			 }
			 
			 
			 //作业：列表分页，搜索,
	  	</script>
  	</c:if>
  </body>
</html>
