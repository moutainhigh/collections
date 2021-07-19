<%@page import="java.util.ArrayList"%>
<%@page import="utils.system"%>
<%@page import="com.gwssi.common.util.JsonDataUtil"%>
<%@page import="com.gwssi.common.util.JSONUtils"%>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator4m.js"></script>

<freeze:html width="1000" height="700">
<head>
	<title>修改采集数据表信息</title>
	<style type="text/css">
#dataitem_tb table {
	width: 98%;
}
</style>



<script type="text/javascript">
//预定义的日期格式
<%

TxnContext dateContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(dateContext, "采集任务_日期格式");
String tmp = "";
out.println("var date_type_array = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("date_type_array.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
//预定义的参数格式
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "采集任务_参数类型");
out.println("var param_type_array = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("param_type_array.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}

//参数值列表数据 （编辑页面用）
TxnContext context = (TxnContext) request.getAttribute("freeze-databus");
String dataSt=context.getValue("dataSt");

%>

	// 请在这里添加，页面加载完成后的用户初始化操作
	
	function __userInitPage() {
		
		var datas='<%=dataSt%>';
		var data = new Array;
		data.push({
			"rowIndex" : "序号",
			"param_value_type" : "值类型",
			"patameter_name" : "名称",
			"patameter_value" : "值",
			"style" : "值格式",
			"showorder" : "顺序",
			"oper" : "操作"
		});
		var options = {
			data : data,
			editable : true,
			shownum : 10,
			lineNum : false,
			isAlias : false,
			shownum : 10,
			addDelete : false,
			param_value_type : param_type_array,
			style : date_type_array
		}
		$("#dataitem_tb").tablet(options);
		//初始化参数值（编辑页面
		if(datas){
			var datas=eval(datas);
			if(datas && datas.length){
				
				for(var i=0;i<datas.length;i++){
					alert($("#dataitem_tb"));
					
					$("#dataitem_tb").tablet("initData", datas[i]);
				}
			}
			
		}
		
	}
	
function func_record_addRecord() {
	clickFlag=0; //忽略烽火台的按钮限制
	//自定义按钮禁用状态
	now_adding = 1;
	var data = getFormFieldValue('select-key:webservice_task_id');
	$("#dataitem_tb").tablet("insertParam", data);
}

function deleteDataItem(rowIndex) {
	//var e = event.srcElement;
	//rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	var item=$("#dataitem_tb").find("tr:eq(" + rowIndex + ")");	
	item.remove();
}

function func_record_saveAndExit(){
	var flag = false;
	$('#dataitem_tb tr:gt(0)').each(function(index){
		//规则校验
		flag = checkItem(++index);
		if(flag == false){
			return false;
		}
	});
	//通过校验
	if(flag){
		var paramValue="";
		var dataStr = "{'data': [";
		$('#dataitem_tb tr:gt(0)').each(function(index){
			var param_value_type = $(this).find("td:eq(1)").find(
					'select option:selected').val();
			var patameter_value_name = $(this).find("td:eq(2)").text();
			var patameter_value_value = $(this).find("td:eq(3)").text();
			var style = $(this).find("td:eq(4)").find('select option:selected').val();
			var showorder = $(this).find("td:eq(5)").text();
			var ws_param_value_id=$(this).find("td:eq(7)").text();
			dataStr += "{'param_value_type': '"+param_value_type+"', 'patameter_value_name': '"+patameter_value_name+
				"', 'patameter_value_value': '"+patameter_value_value+"', 'style': '"+style+
				"', 'showorder': '"+showorder+"','ws_param_value_id':'"+ws_param_value_id+"' },";
			paramValue+=(patameter_value_name+"="+patameter_value_value+",");
		});
		dataStr = dataStr.replace(/,$/, "");		
		dataStr += "]}";
		
		paramValue = paramValue.replace(/,$/, "");
		//alert(paramValue);
		//alert(dataStr);
		//var page = new pageDefine("/txn20201014.do", "保存采集资源表新字段");	
		var page = new pageDefine("/txn30103003.do", "保存采集资源表新字段");	
		
		setFormFieldValue('record:jsondata', dataStr);
		setFormFieldValue('record:patameter_value', paramValue);
	    saveAndExit( '', '保存采集数据表数据项' );
	}else{
		alert("请添加至少一个参数");
	}
 }
 
 function func_record_goBack(){
 	goBack();
 }

	_browse.execute('__userInitPage()');
</script>
</head>
<freeze:body>
	<freeze:title caption="增加采集数据表信息" />
	<freeze:errors />

	<freeze:form action="/txn30103003.do">
		<freeze:frame property="select-key" width="95%">
			<freeze:hidden property="webservice_task_id" caption="webservice任务ID"
				style="width:95%" />
		</freeze:frame>

		<freeze:block property="record" caption="参数信息" width="95%">
			<freeze:hidden property="webservice_patameter_id" caption="参数ID"
				datatype="string" maxlength="32" minlength="1" style="width:95%" />
			<freeze:text property="patameter_type" caption="参数类型"
				datatype="string" maxlength="20" minlength="1" style="width:95%" />
			<freeze:text property="patameter_name" caption="参数名称"
				datatype="string" maxlength="100" minlength="1" style="width:95%" />
			<freeze:hidden property="patameter_value" caption="参数值"
				datatype="string" maxlength="1000" minlength="1" style="width:95%" />
			<freeze:select property="patameter_style" caption="参数格式" show="name"
				valueset="采集任务_参数格式" notnull="true" style="width:95%" />
			<freeze:hidden property="jsondata" caption="字段json" style="width:95%"/>
		</freeze:block>
		<br>
		<table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td>
					<div title="增加数据项" onclick="func_record_addRecord();" id="btn_add"
						class="btn_add"></div>
				</td>
			</tr>
		</table>
		<table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td>
					<div id="dataitem_tb" ></div>
				</td>
			</tr>
		</table>
		<table align='center' cellpadding=0 cellspacing=0 width="95%">

			<tr>
				<td>
				<td align="center" height="50" valign="bottom">
					<div class="btn_save" onclick="func_record_saveAndExit();"></div>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn_cancel" onclick="func_record_goBack();"></div>
				</td>
			</tr>

		</table>
	</freeze:form>
</freeze:body>
</freeze:html>
