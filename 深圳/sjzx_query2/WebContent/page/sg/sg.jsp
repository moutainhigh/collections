<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/page/integeration/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/share/jquery.cookie.js"></script>
<title>高级查询配置</title>
<style>

.fl {
	float: left;
}

.posR {
	position: relative;
}

.posA {
	position: absolute;
}

.wide0 {
	width: 120px;
	text-align: center;
}

.bline {
	width: 120px;
	height: 1px;
	background: red;
}

.wide1 {
	width: 150px;
}

.clear {
	clear: both
}

.top0 {
	top: 19px;
}

.selectQuery{
	height: 120px;
	width: 120px;
}
</style>
</head>
<body>
	<div>
		请选择主题:
		<select id="topic">
			<option value="请选择">请选择</option>
		</select>


		<div class="posR">
			<p class="fl wide0">配置查询范围</p>
			<p class="fl wide0">配置查询条件</p>
			<p class="fl wide0">预览</p>
			<p class="bline posA top0"></p>
			<p class="clear"></p>
		</div>

		<div class="table1 fl wide1">
			<p>选择数据表</p>
			<div >
				<select id="chooseTable" size="10" multiple="multiple" class="selectQuery"> </select>
			</div>
		</div>
		<div class="table2 fl wide1">
			<p>已选择数据表</p>
			<div class="">
				<select id="hasChooseTable" size="10" multiple="multiple" class="selectQuery"></select>
			</div>
		</div>
		<div class="table3 fl wide1">
			<p>数据项</p>
			<div class="">
				<select id="ChooseTableColumn" size="10" multiple="multiple" class="selectQuery"></select>
			</div>
		</div>
		<div class="table4 fl wide1">
			<p>已选择数据项</p>
			<div class="">
				<select id="hasChooseTableColumn" size="10" multiple="multiple" class="selectQuery"></select>
			</div>
		</div>


		<div class="result fl">
			<p>结果集</p>
			<table>
				<thead>
					<tr>
						<th>表名</th>
						<th>数据项名</th>
						<th>类型</th>
						<th>长度</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>删除</td>
					</tr>
				</tbody>
			</table>
		</div>

		<script>
		$.ajax({
			url : "../../advQuery/getTable.do",
			type : "post",
			dataType : "json",
			success : function(data) {
				var data = data.data[0].data;
				for (var i = 0; i < data.length; i++) {
					$("#topic").append("<option value='"+data[i].key+"'>"+ data[i].title + "</option>");
				}

				$("#topic").change(function() {
					var _this = $(this).children('option:selected');
					$("#chooseTable").empty();
					if(_this.val()!="请选择"){
						$("#chooseTable").append("<option value='"+_this.val()+"'>"+ _this.html() + "</option>");
					}
				});
			}
		});
		</script>
	</div>
</body>
</html>