<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>分页插件使用说明-潭州学院keke</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/util.js"></script>
	<script type="text/javascript" src="js/jquery.pagination.js"></script>
	<style type="text/css">
		/*分页*/
		.pagination{font-size:12px;}
		.pagination a{text-decoration:none;border:solid 1px #ccc;color:#999;}
		.pagination a,.pagination span{display:block;float:left;padding:0.3em 0.5em;margin-right:5px;margin-bottom:5px;min-width:1em;text-align:center;}
		.pagination .current{background:#999;color:#fff;border:solid 1px #ccc;}
		.pagination .current.prev,.pagination .current.next{color:#999;border-color:#999;background:#fff;}
		.tm_psize_go{padding:5px;margin-right:4px; float:left;height:32px;line-height:32px;position:relative;border:1px solid #ccc;}
		#tm_pagego{height:31px;line-height:31px;width:30px;float:left;text-align:center;border:1px solid #ccc;}
	</style>
  </head>
  <body>
  
  	
  	<div class="page"></div>
  	<script type="text/javascript">
  		//itemcount:分页的总数
		$(".page").pagination(77, {
			num_display_entries : 3, //主体页数
			num_edge_entries : 4,//边缘页数
			current_page : 0,//指明选中页码
			items_per_page : 10, //每页显示多条条
			prev_text : "首页",
			next_text : "尾页",
			showGo:false,//控制是否显示go 页 ,默认是true
			showSelect:true,//控制是否现在下拉框 默认是true
			callback : function(pageNo, psize) {//会回传两个参数一个当前页，显示的页数
				alert(pageNo+"===="+psize);
			}
		});
	</script>
  </body>
</html>
