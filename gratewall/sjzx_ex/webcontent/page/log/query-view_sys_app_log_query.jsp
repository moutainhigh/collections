<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%
long totalCount=0l;
DataBus db = (DataBus)request.getAttribute("freeze-databus");
if(db!=null){
	DataBus db2 = db.getRecord("select-key");
	if(db2!=null){
		totalCount = db2.getLong("totalCount");
	}
	System.out.println(totalCount+"---------------\n");
}

%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询日志查询列表</title>
</head>

<script language="javascript">

//返回最近6个月
function retrunRecent6mongthRecord(){
	var page = new pageDefine( "/txn620100106.do", "增加日志查询" );
	page.addRecord();
}
// 增加日志查询
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_sys_app_log_query.jsp", "增加日志查询", "modal" );
	page.addRecord();
}

// 修改日志查询
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100104.do", "修改日志查询", "modal" );
	page.addParameter( "record:username", "primary-key:username" );
	page.updateRecord();
}

// 删除日志查询
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100105.do", "删除日志查询" );
	page.addParameter( "record:username", "primary-key:username" );
	page.deleteRecord( "是否删除选中的记录" );
}
function funTBChanged(){
	var type = getFormFieldValue('select-key:type');
	alert("type:"+type);
	
	if(type=='4'){
	setAllFieldVisible("record:ipaddress",false);
	}else{
	setAllFieldVisible("record:ipaddress",true);
	}
}
//查看日志交易
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "查看业务日志信息", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	var types = getFormAllFieldValues("record:type");
	for (var i=0; i < operationSpan.length; i++){
	
		if("系统管理"==types[i]){
			operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'>查看日志</a>";
		}
		
	}	
}

function exportExcel(){
  var totalCount = "<%=totalCount%>";
  var numPerPage = 5000;
 if(totalCount>numPerPage){
  var page = new pageDefine( "/txn620100118.do", "设置导出页码数", "modal", 300, 100);
  page.addValue(totalCount,"select-key:totalCount");
  page.addValue(numPerPage,"select-key:numPerPage");
  page.goPage( );
	 
 }else{
	
 /*
 	$("input[type='button']").each(function(){
 		var s = $(this).val();
 		if(s&&s=="导出"){
 			
 			//$(this).attr("disabled","");
 		//	alert( $(this).attr("disabled") );
 		}
 		//alert( $(this).attr("disabled"))
 	})
 */
 	clickFlag=0;
  	doExport(1,totalCount);	
  	
 }
 
}

function doExport(fromCount, toCount){
	document.forms[0].action ="/txn620100117.do?select-key:fromCount="+fromCount+"&select-key:toCount="+toCount;
	document.forms[0].submit();
	document.forms[0].action ="/txn620100101.do";
	
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询全部日志查询列表"/>
<freeze:errors/>

<freeze:form action="/txn620100101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="username" caption="用户账号" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="query_time" caption="操作时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:datebox property="query_date_start" caption="操作时间" prefix="<table width='95%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="query_date_end" caption="操作时间" style="width:100%" colspan="0"/>
    </td></tr></table> 
      <freeze:browsebox property="orgid" caption="所属机构" valueset="机构" show="name" style="width:95%"/>
      <freeze:text property="ipaddress" caption="IP" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="type" caption="日志类型" valueset="操作类型"  style="width:95%" />
  </freeze:block>
<br />
  <freeze:grid property="record" caption="全部日志查询列表" keylist="username" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="export"  caption="导出" enablerule="0"  align="right" onclick='exportExcel();'/>
      <freeze:button name="export"  caption="返回" enablerule="0"  align="right" onclick='retrunRecent6mongthRecord();'/>
      <freeze:button name="record_addRecord" caption="增加日志查询" txncode="620100103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改日志查询" txncode="620100104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除日志查询" txncode="620100105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="username" caption="用户账号" style="width:10%" align="center"/>
      <freeze:cell property="orgid" caption="所属机构" valueset="机构" style="width:28%" align="center"/>
      <freeze:cell property="type" caption="日志类型" valueset="操作类型" style="width:100px" align="center"/>
      <freeze:cell property="query_time" caption="操作时间" style="width:20%" align="center"/>
      <freeze:cell property="ipaddress" caption="IP" style="width:100px" align="center"/>
  	  <freeze:cell property="operation" caption="操作" align="center" style="width:8%" align="center"/>
  	  <freeze:hidden property="flowno" caption="流水号"  style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
