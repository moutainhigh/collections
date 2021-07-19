<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8" />
<title>结果预览</title>
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/page/n/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/page/n/easyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/n/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/n/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/n/jquery.cookie.js"></script>
</head>
<body>
	<table id="tt" class="easyui-datagrid" title="结果展示"></table>


	<div id="win" class="easyui-window" title="保存查询配置"
		style="width: 300px; height: 180px;">
		<form style="padding: 10px 20px 10px 40px;">
			<p>
				配置名称: <input type="text" id="saveName">
			</p>
			<div style="padding: 5px; text-align: center;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="javascript:saveData()">保存</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="javascript:$('#dd').dialog('close')">关闭</a>
			</div>
		</form>
	</div>



	<script>
		function isEmpty(val) {
			val = $.trim(val);
			if (val == null)
				return true;
			if (val == undefined || val == 'undefined')
				return true;
			if (val == "")
				return true;
			if (val.length == 0)
				return true;
			if (!/[^(^\s*)|(\s*$)]/.test(val))
				return true;
			return false;
		}

		function isNotEmpty(val) {
			return !isEmpty(val);
		}

		function loading() {
			$('#win').window("close");

			var dataStr = $.cookie("tableInfo");
			var jsonObj = eval('(' + dataStr + ')');
			//http://blog.csdn.net/yanjunlu/article/details/8017167
			//easyui动态列 [[{field:'',  title:'时间',width:150,sortable:true},{field:'',title:'时间',width:150,sortable:true}]]

			var easyColomn = [];
			var tempColData = [];
			//var saveObjJson = [];
			var getQuerySql = $.cookie("skySql");
			//var column_en = "";
			//var column_cn = "";

			//getQuerySql = getQuerySql.substring(6,getQuerySql.lastIndexOf("FROM"));

			for (var i = 0; i < jsonObj.data.length; i++) {
				var columnName = {};
				columnName.field = jsonObj.data[i].column.name_en;
				columnName.title = jsonObj.data[i].column.name_cn;
				tempColData.push(columnName);

				//column_en += jsonObj.data[i].column.column_en+""+jsonObj.data[i].table.column_en;
				//column_cn += jsonObj.data[i].column.name_cn+""+jsonObj.data[i].table.name_cn;

				//column_cn += jsonObj.data[i].table.name_cn+"的"+jsonObj.data[i].column.name_cn+" ";
				//column_en += jsonObj.data[i].table.column_en+""+jsonObj.data[i].column.column_en;

				//var tempSaveInfo = {};
				//tempSaveInfo.column_cn = jsonObj.data[i].column.name_cn;
				// tempSaveInfo.column_en = jsonObj.data[i].column.name_en;
				//tempSaveInfo.name_cn = jsonObj.data[i].table.name_cn;
				//tempSaveInfo.name_en = jsonObj.data[i].table.name_en;

			}
			easyColomn.push(tempColData);
			//用于发给后台的保存的json对像信息
			//saveObjJson.push({"sql":getQuerySql});
			//saveObjJson = JSON.stringify(saveObjJson);
			/* var queryParam = $.cookie("record:param");
			var tableId = $.cookie("record:table_id");
			var name_cn = $.cookie("record:table_name_cn");
			var name_en = $.cookie("record:table_code");
			
			
			var saveObjJson = {};
			saveObjJson.tableId = tableId;
			saveObjJson.name_en = name_en;
			saveObjJson.name_cn = name_cn;
			saveObjJson.column_en = column_en;
			saveObjJson.column_cn = column_cn;
			saveObjJson.sql = getQuerySql;
			saveObjJson.queryNameTransfer="高级查询--企业查询配置";
			var json = JSON.stringify(saveObjJson); */
			$('#tt')
					.datagrid(
							{
								url : "../../advQuery/getQueryAdvList.do",
								method : 'post',
								fit : true,
								pagination : true,
								pageSize : 20,
								pageNumber : 1,
								rownumbers : true,
								loadMsg : '加载中...',
								fitColumns : true,
								columns : easyColomn,
								toolbar : [
										{
											iconCls : 'icon-add',
											text : '保存配置',
											handler : function() {
												// saveData(); 
												$('#win').window('open');
											}
										},
										{
											iconCls : 'icon-save',
											text : '数据导出',
											handler : function() {
												// saveData(); 
												$('#win').window('open');
											}
										},
										{
											iconCls : 'icon-no',
											text : '返回',
											handler : function() {
												// parent.window.document.location.href='/query/page/n/query_config.jsp';
												parent.window.document.location.href = '/query/page/seniorQuery/queryAdv.jsp';
											}
										} ],
								queryParams : {
									sql : getQuerySql
								},
								onLoadSuccess : function(data) {
									//$("#toolbar").css("display","block")
									/*  <div id="toolbar" >
										<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-add" onclick="saveOperRecord()" plain="true">保存</a>
									</div> */
								}
							});

		}

		function saveData() {
			var saveName = $("#saveName").val();

			if (isEmpty(saveName)) {
				alert("保存名称不能为空。")
			} else {
				var dataStr = $.cookie("tableInfo");
				var jsonObj = eval('(' + dataStr + ')');
				var getQuerySql = $.cookie("skySql");
				var queryParam = $.cookie("record:param");
				var tableId = $.cookie("record:table_id");
				var name_cn = $.cookie("record:table_name_cn");
				var name_en = $.cookie("record:table_code");

				var column_en = "";
				var column_cn = "";

				//getQuerySql = getQuerySql.substring(6,getQuerySql.lastIndexOf("FROM"));

				for (var i = 0; i < jsonObj.data.length; i++) {
					column_cn += jsonObj.data[i].table.name_cn + "的"
							+ jsonObj.data[i].column.name_cn + "_";
					column_en += jsonObj.data[i].table.name_cn + " "
							+ jsonObj.data[i].column.name_en;

				}
				var saveObjJson = {};
				saveObjJson.tableId = tableId;
				saveObjJson.name_en = name_en;
				saveObjJson.name_cn = name_cn;
				saveObjJson.column_en = column_en;
				saveObjJson.column_cn = column_cn;
				saveObjJson.sql = getQuerySql;

				saveObjJson.queryNameTransfer = saveName;
				var json = JSON.stringify(saveObjJson);

				$.ajax({
					url : "../../advQuery/saveAdvInfo.do",
					type : "post",
					data : {
						"datas" : json
					},
					success : function(data) {
						alert("保存成功");
						$('#win').window('close');
					},
					error : function(data) {
						//alert(data)
					}
				});
			}
		}

		$(window).load(function() {
			loading();
		})
	</script>
</body>
</html>