<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>违法广告</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
td{
	text-align: center;
}
.jazz-pagearea{
	height: 0px;
}
</style>

<script>

		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				fontCss: getFont,
				nameIsHTML: true
			},
			callback: {
				onClick: onClick
			}
		};

		function getFont(treeId, node) {
			return node.font ? node.font : {};
		}
		function onClick(){
			//alert('点击事件');
			//$('#panel_1').panel('close');
			}
		$(document).ready(function() {
		$.ajaxSetup({ cache: false }); //清除缓存
		$.getJSON("wfgg_tree_left.json",function(data){
			$("#treeDemo").tree({setting: setting, data: data});
		});
	});
			
		
</script>
<style>
body{
	overflow-x: hidden;
}
</style>
</head>


<body> 
		<div id="treeDemo" class="ztree"></div>
</body>

</html>