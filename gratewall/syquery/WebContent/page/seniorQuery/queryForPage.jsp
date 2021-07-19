<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script src="script/share/jquery.cookie.js" type="text/javascript"></script>
<style type="text/css">
h2 {
	font-size: 12px;
	font-weight: normal;
}
h2{font-size: 12px; font-weight: normal;}
th{background: #F8F9FB;padding: 10px; border:1px solid #E9F0F6;border-width:0px 1px 1px 0px;}
td{text-align: center;padding: 5px 6px;border-width:0px 1px 1px 0px;}
tr:hover{background: #D7E1C8;}
</style>
<!-- border="1" cellspacing="0" cellpadding="0" width="100%"	 -->
</head>
<body>
	<div class="">
		<h2 id="info"></h2>
		<!-- 由于数量过于庞大，系统仅显示前10条记录数 -->
		<span id="flag"></span>
	</div>
	<div class="resultList">
		<table id="good" width="100%" border="1" cellspacing="0"	cellpadding="0">
		<thead>
		</thead>
		<tbody>
		
		</tbody>
		</table>
	</div>
	<script type="text/javascript">
		var sql = $.cookie("sqlHTML"); //indexOf为-1表示sql不能为
		if (sql != null&&(sql.indexOf("*")==-1)) {
			$.ajax({
				url : "../../advQuery/getQueryAdvList.do",
				type : "post",
				dataType : "json",
				data : {
					sql : sql
				},
				success : function(data) {
					var colsNum = $.cookie("colsNum");
					var colsCn = $.cookie("cols"); //得到显示的列信息
					var tempColumn = colsCn.split("mo_");
					var _title ="";
					for(var i =0;i<tempColumn.length;i++){
						 _title += "<td>"+tempColumn[i]+"</td>";
					}
					$("#good thead").append("<tr>"+_title+"</tr>");
					var dataStr = data.data[0].data;
					if (dataStr == "") {
						$("#info").html("");
						var html = "<tr><td style='color:red' colspan='2'>没有查询到数据</td></tr>";
						$("#good").append(html);
					} else {
						var html = "";
						for ( var i in dataStr) {
							for(var j in dataStr[i]){
								for(var k =0;k<tempColumn.length;k++){
									html += "<td>"+dataStr[i][j]+"</td>";
								}
								html = "<tr>"+html+"</tr>";
							}
						}
						$("#good tbody").append(html);
						$("#info").html("由于数量过于庞大，系统仅显示前10条记录数");
					}
				},
				error : function(data) {
					//jazz.info(data.responseText);
				}
			});
		}
	</script>
</body>
</html>