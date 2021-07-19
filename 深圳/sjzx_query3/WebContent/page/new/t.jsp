<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>增加服务信息</title>
<link href="<%=request.getContextPath()%>/page/new/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/page/new/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/page/new/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/new/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/new/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/new/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/page/new/share_service.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/new/ye.inter.js"></script>




<style type="text/css">
.cssSelect1 {
	/*width:120px !important;*/
	height: auto;
}

.cssSelect {
	/*width:120px !important;*/
	height: auto;
}
</style>
</head>
<body>

	<form>
		<select id="selectTopic" style="width: 55%">
			<option value='0'>请选择</option>
		</select>
	</form>
	<br />
	<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse: collapse;">
		<tr>
			<td>
				<div style="width: 100%;">
					<dl class="tabs" id="tabs">
						<dt>
							<a id="dta">配置结果集</a>
							&nbsp;&nbsp;
							<a>查询条件</a>
							&nbsp;&nbsp;
							<a>配置输入参数</a>
							&nbsp;&nbsp;
							<a>预览SQL语句</a>
						</dt>
						<!-- 第一个标签页开始 -->
						<dd>
							<div>
								<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
									<tr style="padding-top: 5px;">
										<td style="" valign="top" width="120px;">
											<div style="width: 100%; text-align: left; font-size: 14px; color: 333#;">请选择数据表：</div>
											<div style="width: 100%;" id="tab1_table_all_div"></div>
										</td>
										<td style="" valign="top" width="120px">
											<div style="width: 100%; text-align: left; font-size: 14px; color: 333#;">请选择数据项：</div>
											<div style="background-color: white; width: 100%;" id="tab1_col_all_div"></div>
										</td>
										<td style="border: 1px solid gray;" width="20px">
											<div style="width: 100%; height: 160px; line-height: 40px; text-align: center;">
												<!-- <a href="javascript:void(0)" onclick="table2col()" >&lt;</a>
      				<a href="javascript:void(0)" onclick="" >&lt;&lt;</a> -->
												<a href="javascript:void(0)" onclick="addMultiData()">&gt;</a>
												<a href="javascript:void(0)" onclick="addMultiData()">&gt;&gt;</a>
											</div>
										</td>
										<td valign="top" style="">
											<div style="width: 100%; text-align: left; font-size: 14px; color: 333#; padding-left: 10px;">结果集：</div>
											<div style="width: 100%;" id="tab1_col_selected_div"></div>
										</td>
									</tr>
								</table>
							</div>
						</dd>
						<!-- 第一个标签页结束 -->
						<!-- 第二个标签页开始 -->
						<dd>
							<div>
								<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
									<tr>
										<td style="border: 1px solid gray;">
											<table style="width: 100%; background-color: #dee6e9; border-collapse: collapse;" border=1 cellpadding=2 cellspacing=0 align="center">
												<tr>
													<td nowrap>条件</td>
													<td nowrap>括弧</td>
													<td nowrap>数据表</td>
													<td nowrap>字段</td>
													<td nowrap>条件</td>
													<td nowrap>值</td>
													<td nowrap>括弧</td>
												</tr>
												<tr>
													<td><select id="tab2_logic">
															<option value='no1' selected></option>
															<option value="AND">并且(AND)</option>
															<option value="OR">或者(OR)</option>
														</select></td>
													<td><select id="leftParen">
															<option selected value=""></option>
															<option value="(">(</option>
															<option value="((">((</option>
															<option value="(((">(((</option>
															<option value="((((">((((</option>
														</select></td>
													<td style="width: 80px; background-color: white; overflow: hidden;"><div id="tab2_table_all_div"></div></td>
													<td style="width: 80px; background-color: white; overflow: hidden;"><div id="tab2_col_all_div"></div></td>
													<td><select id="paren"></select></td>
													<td onmouseover="showSelectedValue(this)"><input id="param_value" name="param_value" type="text" /> <select id="param_value_select" style="width: 150px; display: none;"></select></td>
													<td><select id="rightParen">
															<option value=""></option>
															<option value=")">)</option>
															<option value="))">))</option>
															<option value=")))">)))</option>
															<option value="))))">))))</option>
														</select></td>
												</tr>
												<tr>
													<td colspan=7 align="right" nowrap><span style="color: red;" id="tab2_error_msg">&nbsp;&nbsp;</span>
														<button onclick="addCondData()">添加条件</button>&nbsp;&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td style="padding: 10px;" valign="top">
											<div id="tab2_cond_table_div"></div>
										</td>
									</tr>
									<tr id="old_condition">
										<td style="padding: 0 10px 0; text-align: left;" valign="top">配置条件</td>
									</tr>
									<tr id="old_condition1">
										<td style="padding: 10px;" valign="top">
											<div id="tab2_interface_div"></div>
										</td>
									</tr>
								</table>
							</div>
						</dd>
						<!-- 第二个标签页结束 -->
						<!-- 第三个标签页开始 -->
						<dd>
							<div>
								<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
									<tr>
										<td style="border: 1px solid gray;">
											<table style="width: 100%; background-color: #dee6e9; border-collapse: collapse;" border=1 cellpadding=2 cellspacing=0>
												<tr>
													<td>逻辑条件</td>
													<td>括弧</td>
													<td>数据表</td>
													<td>数据项</td>
													<td>类型</td>
													<td>条件</td>
													<td>值</td>
													<td>括弧</td>
												</tr>
												<tr>
													<td><select id="tab3_cond">
															<option selected value='no'></option>
															<option value='AND'>并且(AND)</option>
															<option value='OR'>或者(OR)</option>
														</select></td>
													<td nowrap><select id="tab3_leftParen">
															<option selected value=""></option>
															<option value="(">(</option>
															<option value="((">((</option>
															<option value="(((">(((</option>
															<option value="((((">((((</option>
														</select></td>
													<td style="width: 80px; background-color: white; overflow: hidden;"><div id="tab3_table_all_div"></div></td>
													<td style="width: 80px; background-color: white; overflow: hidden;"><div id="tab3_col_all_div"></div></td>
													<td nowrap><select onchange="setOperator('#tab3_col_type', '#tab3_paren');" id="tab3_col_type">
															<option value='1' selected>变长字符型</option>
															<option value='3'>数值型</option>
															<option value='2'>日期型</option>
														</select></td>
													<td nowrap><select id="tab3_paren"></select></td>
													<td nowrap></td>
													<td nowrap><select id="tab3_rightParen">
															<option value=""></option>
															<option value=")">)</option>
															<option value="))">))</option>
															<option value=")))">)))</option>
															<option value="))))">))))</option>
														</select></td>
												</tr>
												<tr>
													<td colspan="8" align="right">
														<button onclick="addTab3Data();">添加条件</button>&nbsp;&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td valign="top" style="padding: 10px;">
											<div style="width: 100%;" id="tab3_param_div"></div>
										</td>
									</tr>
								</table>
								<br />
							</div>
						</dd>
						<!-- 第三个标签页结束 -->
						<dd>
							<div>
								<table class="dd_table" border="1" style="table-layout: fixed;" cellpadding="3" cellspacing="0" width="100%" align="center">
									<tr style="padding-top: 5px;">
										<td><textarea readonly style="width: 100%; height: 200px;" id="sql_last"></textarea></td>
									</tr>
								</table>
							</div>
						</dd>
					</dl>
				</div>
			</td>
		</tr>
	</table>
	<br />

	<script>
	
	
		var tab1_col_all = new Array; 
		function inits() {
			//初始化第一个标签页的表格
			var tab1_col_selected_opt = {
				addDelete : true,
				data : [{
					"table" : "表名",
					"column" : "数据项名",
					"alias" : "别名",
					"column_type" : "类型",
					"column_length" : "长度"
				}],
				editable: 3,
				onClick : function(event){
				},
				shownum:5,
				onDelete: function(){
					var delData = arguments[0];
					console.log(delData)
					if(delData){
						getColumnsByTable(delData.table.id);
						$("#tab1_table_all_div_id option#"+delData.table.id).attr("selected", true);
					}
					deleteReturn();
				}
			};
			$(tab1_col_selected_div).tablet(tab1_col_selected_opt);
			
			
			
			$.ajax({
				type : "post",
				url : "../../advQuery/getTopic.do",
				async : false,
				dataType : "json",
				success : function(data) {
					var datas = data.data[0].data;
					for (var i = 0; i < datas.length; i++) {
						$("#selectTopic").append(
								"<option value='"+datas[i].key+"'>"
										+ datas[i].title + "</option>");
					}
				}
			});
		}

		$(function() {
			inits();
		})

		$('#selectTopic').change(function() {
			var topicVal = $(this).children('option:selected').val();
			var topicText = $(this).children('option:selected').text();

			if (topicVal != 0) {
				$.ajax({
					type : "post",
					url : "../../advQuery/getTable.do?topicId=" + topicVal,
					async : false,
					dataType : "json",
					success : function(data) {
						var datas = data.data[0].data;
						var tablesListOfTop = datas;
						var opt_table = {
							data : tablesListOfTop,
							onClick : function(event, key) {
								getSelectTable(key, 'tab1_table_all_div');
							}
						}
						//$('#tab1_table_all_div').dataSelector('destroy')
						$('#tab1_table_all_div').dataSelector(opt_table);
					}
				});
			}
		})

		function getSelectTable(key, container) {
			if (typeof (currentSystem) == "undefined") {
				currentSystem = key;
			}
			$.ajax({
				type : "post",
				url : "../../advQuery/getColumn.do?tableId=" + key,
				async : false,
				dataType : "json",
				success : function(data) {
					var datas = data.data[0].data;
					var tableId = key;
					
					tab1_col_all = datas;//配置表中的数据列
					var table_Col_All = {
						data : datas,
						onClick : function(event, key) {
							selectCols(key, 'tab1_col_all_div',datas.length,tableId);
						}
					}
					//$('#tab1_col_all_div').dataSelector('destroy')
					$('#tab1_col_all_div').dataSelector(table_Col_All);
				}
			});
		}
		
		
		
		function selectCols(key,container,col_length,tableId){
			if (typeof (currentSystem) == "undefined") {
				currentSystem = key;
			}
			col2table(key,col_length,tableId);
			var col_selected = $(tab1_col_all_div).dataSelector("getSelectedNodes");
		        for (var x in col_selected) {
		          $(tab1_col_all_div).dataSelector("removeSelectedNodes", x);
		        }
		}
		
		
		
		//从字段列表选择字段到表格

		function col2table(key,col_length,tableId) {
//			alert(key);
		  var selected_col = null;
		  var rowData = [];
		  for (var ii = 0; ii < col_length; ii++) {
		     if (tab1_col_all[ii].key == key) {
		      selected_col = tab1_col_all[ii];
		      rowData = [{
		        //        "sort":sort,
		        "table": {
		          "id": tableId,
		          "name_en": selected_col.dcTableNameEn,
		          "name_cn": selected_col.dcTableNameCn
		        },
		        "column": {
		          "id": selected_col.key,
		          "name_en": selected_col.dataitem_name_en,
		          "name_cn": selected_col.title
		        },
		        "alias": "",
		        "column_type": selected_col.codename,
		        "column_length":"" + selected_col.colLenght
		      }];
		      break;
		    } 
		  };

		  $(tab1_col_selected_div).tablet("addData", rowData);
		}
	</script>
</body>
</html>