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
<table id = "tt"></table>

	<script type="text/javascript">
	
	var froms = location.href;
	
	if(froms!=null){
		
		var sql = location.href.split("&")[1];
	 	var temp = sql.split("=")[1];
		
			$.ajax({
				url : "../../advQuery/getQueryById.do",
				type : "post",
				dataType : "json",
				data : {
					id : temp
				},
				success : function(data) {
					var getQuerySql = data.data[0].data.sql;
					var cnColsName = data.data[0].data.columnNameCn;
					var cnCols = cnColsName.split(","); //截取出来要展示的数据列
					var cnColsDis = [];
					for (var i = 0; i < cnCols.length; i++) {
						if (cnCols[i].split(".")[1] != undefined) {
							cnColsDis.push(cnCols[i].split(".")[1]);
						}
					}
					
					var rows = getQuerySql.substr(6,getQuerySql.indexOf("FROM") - 6);
					var enCols = rows.split(",");//得到要查询的sql语句里面用户选择的列
					var enColsDis = [];
					//easyui必须构造成为[[{field:'code',title:'Code'},{field:'code',title:'Code'}]]形式才能显为动态列
					var easyColomn = [];
					var array = [];
					for (var i = 0; i < enCols.length; i++) {
						var obj ={};
						var name = enCols[i].split(".")[1].toUpperCase();
						name = $.trim(name);
						enColsDis.push(name);
						obj.title =cnColsDis[i] ;
						obj.field = name;
						obj.width = name[i].length*10;
						array.push(obj);
					}
					easyColomn.push(array);
					console.log(easyColomn);
					//console.log(cnColsDis);
					//console.log(enColsDis);
					 $('#tt').datagrid({
				            url: "../../advQuery/getQueryAdvList.do",
				            method: 'post',
				            fit:true,
				            pagination:true,   
				            pageSize:20,   
				            pageNumber:1,   
				            rownumbers:true,
				            loadMsg:'加载中...',
				            fitColumns:true,
				            columns: easyColomn,
				            queryParams:{sql:getQuerySql}
				    });
				}
			});
		
	}
	
	
	
	</script>
</body>
</html>