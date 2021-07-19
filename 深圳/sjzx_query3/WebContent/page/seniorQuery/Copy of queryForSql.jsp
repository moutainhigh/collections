<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/page/seniorQuery/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/page/seniorQuery/easyui/themes/icon.css">
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/easyui/jquery.easyui.min.js"></script>
<script src="<%=request.getContextPath()%>/page/seniorQuery/script/share/jquery.cookie.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>
<body>
	<table id="tt" title="高级查询列表展示结果"></table>
	<script type="text/javascript">
		var fromURL = document.referrer;
		var sql = $.cookie("sqlHTML");
		
		$.ajax({
			url : "../../advQuery/getQueryById.do",
			type : "post",
			dataType : "json",
			data : {
				id : sql
			},
			success : function(data) {
				var getQuerySql = data.data[0].data.sql;
				var choseColumn = data.data[0].data.columnNameCn;
				var cnCols = choseColumn.split(","); //截取出来要展示的数据列
				var cnColsDis = [];
				for (var i = 0; i < cnCols.length; i++) {
					if (cnCols[i].split(".")[1] != undefined) {
						cnColsDis.push(cnCols[i].split(".")[1]);
					}

				}
				var arr1 = [];
				var arr2 = [];
				var rows = getQuerySql.substr(6,
						getQuerySql.indexOf("FROM") - 6);
				var enCols = rows.split(",");//得到要查询的sql语句里面用户选择的列
				var enColsDis = [];
				////拼接展示头开始
				for (var i = 0; i < cnColsDis.length; i++) {
					var obj ={};
					obj.title = cnColsDis[i];
					var name = enCols[i].split(".")[1].toLowerCase();
					name = $.trim(name);
					obj.field = name;
					obj.width = name.length*20;
					arr2.push(obj);
				}
				
				arr1.push(arr2);
				$('#tt').datagrid({
					fit:true,
					url:"../../advQuery/getQueryAdvList.do?sql="+getQuerySql,
					fitColumns : true,
					rownumbers : true,
					border : false,
				    striped : true,
					rownumbers:true,//显示行号
					pagination:true,//显示分页工具条
					pageList : [15, 30, 45],
					pageNumber : 1,
					pageSize : 15,
					columns:arr1,
					
				});
				
			},
			error : function(data) {
				jazz.info(data.responseText);
			}
		});
	</script>
</body>
</html>