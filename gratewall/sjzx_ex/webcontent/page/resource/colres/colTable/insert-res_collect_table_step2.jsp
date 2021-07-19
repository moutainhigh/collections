<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
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
#dataitem_tb table{ 
	width: 98%;
}
</style>
</head>
<script type="text/javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "资源管理_数据项类型");
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "资源管理_对应代码表");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var name_ens = new Array;
var all_save = false;
//全页面保存
 function func_record_saveRecord_all(){
	name_ens.length = 0; //清空数据项名称数组
	//重新赋值数据项名称数组，主要用于重名验证
	 $('#dataitem_tb tr:gt(0)').each(function(index){
		 name_ens.push($(this).find("td:eq(1)").text());
	 });
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
		//var dataStr = $('#dataitem_tb').tablet('getAllData');
		//dataStr = eval('('+dataStr+')');
		//dataStr = dataStr.data;
		var dataStr = "{'data': [";
		$('#dataitem_tb tr:gt(0)').each(function(index){
			var dataitem_name_en = $(this).find("td:eq(1)").text();
			var dataitem_name_cn = $(this).find("td:eq(2)").text();
			var dataitem_type = $(this).find("td:eq(3)").find(
					'select option:selected').val();
			var dataitem_long = $(this).find("td:eq(4)").text();
			var is_key = $(this).find("td:eq(5)").find('select option:selected').val();
			var code_table = $(this).find("td:eq(6)").find(
					'select option:selected').val();
			dataStr += "{'dataitem_name_en': '"+dataitem_name_en+"', 'dataitem_name_cn': '"+dataitem_name_cn+
				"', 'dataitem_type': '"+dataitem_type+"', 'dataitem_long': '"+dataitem_long+
				"', 'is_key': '"+is_key+"', 'code_table': '"+code_table+"' },";
		});
		dataStr = dataStr.replace(/,$/, "");
		dataStr += "]}";
		//alert(dataStr);
		var page = new pageDefine("/txn20201014.do", "保存采集资源表新字段");	
		//page.addValue(dataStr, "jsondata");
		//page.addParameter("record:collect_table_id","primary-key:collect_table_id");
		//page.updateRecord();
		setFormFieldValue('record:jsondata', dataStr);
	    saveRecord( '', '保存采集数据表数据项' );
	}
 }
 
// 增加采集数据项表信息
function func_record_addRecord()
{
	clickFlag=0; //忽略烽火台的按钮限制
	//自定义按钮禁用状态
	now_adding = 1;
	/* $('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
	$('#btn_add')[0].onclick = function(){
		alert("请完成本次添加后再次操作.");
	}; */
	var data = getFormFieldValue('record:collect_table_id');
	$("#dataitem_tb").tablet("insertRow", data);
}

	//保存数据项信息
	function checkItem(rowIndex) {
		//var e = event.srcElement;
		//rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (en == "UPDATE_DATE") {
			alert("第"+rowIndex+"行中, UPDATE_DATE为系统保留字段禁止使用！");
			return false;
		}
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (!checkEnName(en, '数据项名称', rowIndex)) {
			return false;
		}
		var tmpAry = new Array;
		tmpAry = tmpAry.concat(name_ens);
		tmpAry[rowIndex-1]="@";
		/* if(tmpAry.join(',').indexOf(en) > -1){
			alert("第"+rowIndex+"行数据项名称'"+en+"'重复!");
			return false;
		} */
		for(var ii=0; ii<tmpAry.length; ii++){
			if(en == tmpAry[ii]){
				alert("第"+rowIndex+"行和第"+(ii+1)+"行数据项名称'"+en+"'重复!");
				return false;
			}
		}
		var dataitem_type = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:eq(3)").find('select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:eq(4)").text();
		if (!checkItemTypeLength(dataitem_type, dataitem_long, rowIndex)) {//校验数据项长度
			return false;
		}
		if (!check_int(dataitem_long, rowIndex)) {//校验数据项长度
			return false;
		}
		return true;
	}

	function saveAndCon(errCode, errDesc, xmlResults) {
		is_name_used[0] = 1;
		if (errCode != '000000') {
			alert('处理错误[' + errCode + ']==>' + errDesc);
			return;
		} else {
			if (!all_save) {
				alert("操作成功!");
			}
			var dataItem_id = _getXmlNodeValues(xmlResults,
					'record:collect_dataitem_id');
			dataItem_id = dataItem_id[0];
			$("#dataitem_tb")
					.find("tr:eq(" + now_line_no + ")")
					.find("td:last")
					.append(
							"<input name='collect_dataitem_id' type='hidden' value='"+dataItem_id+"' />");
			row_index = -1;
			now_adding = -1;
			$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
			$('#btn_add')[0].onclick = function() {
				func_record_addRecord();
			}
			now_line_no = -1;
			//window.location.reload();
		}
		namechecked = true;
	}
	function deleteDataItem() {
		var e = event.srcElement;
		rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
	}

	// 请在这里添加，页面加载完成后的用户初始化操作
	function __userInitPage() {
		var data = new Array;

		data.push({
			"rowIndex" : "序号",
			"dataitem_name_en" : "数据项名称",
			"dataitem_name_cn" : "中文名称",
			"dataitem_type" : "数据项类型",
			"dataitem_long" : "数据项长度",
			"is_key" : "是否主键",
			"code_table" : "对应代码表",
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
			dataitem_type : dataitem_types,
			code_table : code_table_list
		}
		$("#dataitem_tb").tablet(options);
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="增加采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201014.do">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>

  <div style="width:95%;margin-left:20px" >
  <table style="width:75%"  >
   <tr>
   
    <td width="30%">
   
    	<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					第一步,录入采集表基本信息</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					   第二步,录入采集表字段信息</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					第三步,预览并生成表</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
  <freeze:block property="record" caption="采集数据表信息" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:cell property="table_name_en" caption="表名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="表中文名称" datatype="string"   style="width:95%"/>
      <freeze:cell property="table_type" caption="表类型" show="name" valueset="资源管理_表类型"  style="width:95%"/>
      <freeze:cell property="table_desc" caption="表描述" colspan="2"  style="width:98%"/>
      <freeze:hidden property="jsondata" caption="字段json" style="width:95%"/>
  </freeze:block>
  <br>
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td><div title="增加数据项" onclick="func_record_addRecord();" id="btn_add" class="btn_add"></div></td></tr>
  </table>
  <div id="dataitem_tb"></div>
    <table align='center' cellpadding=0 cellspacing=0 width="95%" ><tr>
		<td align="center" height="50" valign="bottom"><div class="btn_next"  onclick="func_record_saveRecord_all()"></div></td>
	</tr>
  </table>
</freeze:form>
</freeze:body>
</freeze:html>
