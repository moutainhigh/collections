<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="utils.system"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet_param.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加方法信息</title>
<style type="text/css">
#patameter_tb table{
	width: 95%;
	table-layout: fixed;
}
#patameter_tb table td{
	text-overflow: ellipsis;
}
#patameter_tb table tr.activerow td{white-space: nowrap;}
</style>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
if(StringUtils.isNotBlank(context.getValue("jsondata"))){
    out.println("<script>var jsondata=eval('('+'"+context.getValue("jsondata")+"'+')');</script>");
}
%>
<script language="javascript">
<%
TxnContext txnContext = new TxnContext();
CodeMap codeMap2 = PublicResource.getCodeFactory();
Recordset rs2 = codeMap2.lookup(txnContext, "采集任务_参数类型");
out.print("var patameter_types = new Array; ");
for(int ii=0; ii<rs2.size(); ii++){
	out.println("patameter_types.push({\"key\": \""+rs2.get(ii).get("codevalue")
		+"\", \"title\": \""+rs2.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "采集任务_参数格式");
out.print("var patameter_styles = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("patameter_styles.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>

var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
// 保 存
function func_record_saveRecord()
{
	  	 var item=getFormFieldValue('record:method_name_en');
  		  if(!checkItem(item,"100","方法名称")){
  		    return false;
  		  }
	  	 item=getFormFieldValue('record:method_name_cn');
  		  if(!checkItemLength(item,"100","方法中文名称")){
  		    return false;
  		  }
	      item=getFormFieldValue('record:collect_table');
  		  if(!checkItem(item,"100","对应采集表")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:collect_mode');
  		  if(!checkItem(item,"100","采集方式")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:encrypt_mode');
  		  if(!checkItem(item,"100","解密方法")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:method_description');
  		  if(!checkItemLength(item,"2000","方法描述")){
  		    return false;
  		  }
	      var page = new pageDefine( "/txn30102013.do", "保存方法信息");
	      page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	      page.addParameter("record:webservice_task_id","record:webservice_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      
	      page.addParameter("record:service_no","record:service_no");
	      page.addParameter("record:method_name_en","record:method_name_en");
	      page.addParameter("record:method_name_cn","record:method_name_cn");
	      page.addParameter("record:collect_table","record:collect_table");
	      page.addParameter("record:collect_mode","record:collect_mode");
	      page.addParameter("record:is_encryption","record:is_encryption");
	      page.addParameter("record:encrypt_mode","record:encrypt_mode");
	      page.addParameter("record:method_description","record:method_description");
	      page.addParameter("record:method_status","record:method_status");
	      page.updateRecord();
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存webservice任务表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存webservice任务表' );	// /txn30102001.do
}

// 返 回
function func_record_goBack()
{
	//goBack();	// /txn30102001.do
	var page = new pageDefine( "/txn30101062.do", "查询采集服务");
	page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}
// 增加参数信息
function func_record_addRecord2()
{
    var collect_task_id=getFormFieldValue('record:collect_task_id');
    var webservice_task_id=getFormFieldValue('record:webservice_task_id');
    if(webservice_task_id==null||webservice_task_id==""){
	    alert("请先填写采集任务方法信息!");
	    clickFlag=0;
    }else{
	 var page = new pageDefine( "/txn30103006.do", "增加参数",'modal');
	 page.addValue(collect_task_id,"primary-key:collect_task_id");
	 page.addValue(webservice_task_id,"primary-key:webservice_task_id");
	 var service_targets_id=getFormFieldValue("record:service_targets_id");
	 page.addValue(service_targets_id,"primary-key:service_targets_id");
	 page.addRecord();
	}
	 
}
// 增加参数信息
function func_record_addRecord()
{
    var collect_task_id=getFormFieldValue('record:collect_task_id');
    var webservice_task_id=getFormFieldValue('record:webservice_task_id');
    if(webservice_task_id==null||webservice_task_id==""){
	    alert("请先填写采集任务方法信息!");
	    clickFlag=0;
    }else{
	    clickFlag=0; //忽略烽火台的按钮限制
	    //自定义按钮禁用状态
	    now_adding = 1;
	    $('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
	    $('#btn_add')[0].onclick = function(){
	    	alert("请完成本次添加后再次操作.");
	    };
	    var data = getFormFieldValue('record:webservice_task_id');
	    $("#patameter_tb").tablet("insertRow", data);
	    
	    /**
		 var page = new pageDefine( "/txn30103006.do", "增加参数",'modal');
		 page.addValue(collect_task_id,"primary-key:collect_task_id");
		 page.addValue(webservice_task_id,"primary-key:webservice_task_id");
		 var service_targets_id=getFormFieldValue("record:service_targets_id");
		 page.addValue(service_targets_id,"primary-key:service_targets_id");
		 page.addRecord();
		 */
	}
	 
}
// 修改方法信息
function func_record_updateRecord(idx)
{
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	
	
	 
	var page = new pageDefine( "/txn30103008.do", "修改参数信息", "modal" ,1000, 800 );
	
	 var webservice_patameter_id = $("#patameter_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:last").find("input[name='webservice_patameter_id']")
				.val();
	page.addValue(webservice_patameter_id,"primary-key:webservice_patameter_id");
	
	
	var patameter_type=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").find(
					'select option:selected').val();
	page.addValue(patameter_type,"record:patameter_type");	
				
	var patameter_name=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(2)").text();			
	page.addValue(patameter_name,"record:patameter_name");	
	
	var patameter_value=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(3)").text();			
	page.addValue(patameter_value,"record:patameter_value");
	
	var patameter_style=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(4)").find(
					'select option:selected').val();	
	page.addValue(patameter_style,"record:patameter_style");
	
	page.updateRecord();
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
	var data = new Array;
	data.push({
		"rowIndex" : "序号",
		"patameter_type" : "参数类型",
		"patameter_name" : "参数名",
		"patameter_value" : "参数值",
		"patameter_style" : "参数格式",
		"oper" : "操作"
	});
	var options = {
		data : data,
		editable : 3,
		shownum : 10,
		lineNum : false,
		isAlias : false,
		addDelete : false,
		patameter_style: patameter_styles,
		patameter_type: patameter_types
	}
	$("#patameter_tb").tablet(options);
	if(typeof jsondata != 'undefined'){
			$("#patameter_tb").tablet("initRow", jsondata);
		}else{
		 //var data = getFormFieldValue('record:webservice_task_id');
	     //$("#patameter_tb").tablet("insertRow", data);
		}
	$('#patameter_tb table tr.tablet_thead td:eq(3)').attr("white-space","nowrap");
	$('#patameter_tb table tr.tablet_thead td:eq(1)').css("width", "10%");
	$('#patameter_tb table tr.tablet_thead td:eq(5)').css("width", "12%");
	$('#patameter_tb table tr.tablet_thead td:eq(4)').css("width", "12%");
	$('#patameter_tb table tr.tablet_thead td:eq(2)').css("width", "12%");
	$('#patameter_tb table tr.tablet_thead td:eq(0)').css("width", "6%");
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加方法信息"/>
<freeze:errors/>

<freeze:form action="/txn30102018">
  <freeze:block property="record" caption="增加方法信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_no" caption="任务ID：" datatype="string"  style="width:95%"/>
      <freeze:text property="method_name_en" caption="方法名称：" datatype="string" notnull="true" style="width:95%"/>
      <freeze:text property="method_name_cn" caption="方法中文名称" datatype="string"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="对应采集表" show="name" notnull="true" valueset="资源管理_服务对象对应采集表" parameter="getParameter();"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:hidden property="is_encryption" caption="是否加密"   datatype="string"  style="width:95%"/>
      <freeze:select property="encrypt_mode" caption="解密方法"  notnull="true" show="name" valueset="资源管理_解密方法" value="01" style="width:95%"/>
      <freeze:textarea property="method_description" caption="方法描述" colspan="2" rows="2"  style="width:98%"/>
      <freeze:hidden property="method_status" caption="方法状态" datatype="string"  style="width:95%"/>
  </freeze:block>
<br>
   <%--<freeze:grid property="dataItem" caption="参数列表" keylist="webservice_patameter_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
     <freeze:button name="record_addRecord" caption="添加参数" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="webservice_patameter_id" caption="参数ID"  />
      <freeze:hidden property="webservice_task_id" caption="方法ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="patameter_type" caption="参数类型" style="width:20%" />
      <freeze:cell property="patameter_name" caption="参数名" style="width:25%" />
      <freeze:cell property="patameter_value" caption="参数值"   style="width:25%" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:25%" />
  </freeze:grid>--%>
    <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td></td></tr>
  </table>
  <div id="patameter_tb"></div>
</freeze:form>
</freeze:body>
</freeze:html>
