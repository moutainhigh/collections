<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"	type="text/javascript"></script>
<script src="script/share/jquery.cookie.js"	type="text/javascript"></script>
<style type="text/css">
	h2{font-size: 12px; font-weight: normal;}
	th{background: #F8F9FB;padding: 10px; border:1px solid #E9F0F6;border-width:0px 1px 1px 0px;}
	td{text-align: center;padding: 5px 6px;border-width:0px 1px 1px 0px;}
	tr:hover{background: #D7E1C8;}
	</style>
</head>
<body>
<div class=""> 
	<!-- <h2 id="info">由于数量过于庞大，系统仅显示前10条记录数</h2> -->
	<h2 id="info"></h2>
	<span id="flag"></span>
</div>
<div class="resultList">

	<table id="good" width="100%" border="1" cellspacing="0"	cellpadding="0" >
			<thead>
				<tr><th>数据属性</th><th>数据值</th></tr>
			</thead>
		</table>
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
					$.ajax({
						url:"../../advQuery/getQueryAdvList.do",
						type:"post",
						dataType:"json",
						data:{sql:getQuerySql},
						success:function(data){
							var dataStr = data.data[0].data;
							if(dataStr==""){
								$("#info").html("");
								var html = "<tr><td style='color:red' colspan='2'>没有查询到数据</td></tr>";
								$("#good").append(html)
							}else{
								var html = "";
								for ( var i in dataStr) {
									for(var j in dataStr[i]){
										html += "<tr><td width='100'>" + j + "</td><td>"
												+ dataStr[i][j] + "</td></tr>";
										}
								}
								$("#good").append(html);
								$("#info").html("由于数量过于庞大，系统仅显示前10条记录数");
							}
						},
						error:function(data){
							$("#flag").html("数据库连接超时，查询失败")
						}
					});
					
				},
				error : function(data) {
					jazz.info(data.responseText);
				}
			});
	</script>
</div>
</body>
</html>