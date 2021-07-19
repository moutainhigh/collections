<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询报告操作情况列表</title>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
%>
</head>

<script language="javascript">

// 增加报告操作情况
function func_record_addRecord()
{
	var page = new pageDefine( "insert-log_report_use.jsp", "增加报告操作情况", "modal" );
	page.addRecord();
}

// 修改报告操作情况
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620200204.do", "修改报告操作情况", "modal" );
	page.addParameter( "record:log_report_use_id", "primary-key:log_report_use_id" );
	page.updateRecord();
}

// 删除报告操作情况
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620200205.do", "删除报告操作情况" );
	page.addParameter( "record:log_report_use_id", "primary-key:log_report_use_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 修改系统使用情况报告
function addRecord(id)
{
	var page = new pageDefine( "/txn620200108.ajax", "修改系统使用情况报告");
	page.addValue( id, "select-key:log_report_create_id" );
	page.callAjaxService('addRecordBack');
}

function addRecordBack(errCode, errDesc, xmlResults){
      if(errCode == '000000'){
        
	  }
}

function toTurn(url,id){

  var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+url;
  document.getElementById('testa').href=url;
  document.getElementById('testa').click();
  addRecord(id);
  
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//var rootPath = '<%=request.getContextPath()%>';
	//var operate = getFormAllFieldValues("record:operate1");
	//var path = getFormAllFieldValues("record:path");
	//var filename = getFormAllFieldValues("record:filename");
	//var ids = getFormAllFieldValues("record:log_report_create_id");
	
	//for(var i=0;i<operate.length;i++){
	//	var url = filename[i];
	//	var id = ids[i];
	//	document.getElementsByName("span_record:operate")[i].innerHTML ="<a href='javascript:void(0);' onclick='toTurn(\""+url+"\",\""+id+"\");'>"+operate[i]+"</a>";
	//}
	
	//var rootPath = '<%=request.getContextPath()%>';
	//var operate = getFormAllFieldValues("record:operate");
	//var path = getFormAllFieldValues("record:path");
	//var filename = getFormAllFieldValues("record:filename");
	//var ids = getFormAllFieldValues("record:log_report_create_id");
	
	//for(var i=0;i<operate.length;i++){
	//	var URL = rootPath+"/downloadFile?file="+path[i]+"&&fileName="+filename[i];
	//	var id = ids[i];
	//	alert("new");
	//	document.getElementsByName("span_record:operate")[i].innerHTML ="<a href='javascript:void(0);' onclick='toTurn(\""+url+"\",\""+id+"\");'>"+operate[i]+"</a>";
		//document.getElementsByName("span_record:operate")[i].innerHTML ="<a href=\""+URL+"\">"+operate[i]+"</a>";
	//}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询报告操作情况列表"/>
<freeze:errors/>

<freeze:form action="/txn620200201">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="log_report_use_id" caption="日志报告操作id" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="report_name" caption="报告名称"  style="width:90%"/>
      <freeze:datebox property="create_date_start" caption="上传日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="create_date_end" caption="上传日期" style="width:100%" colspan="0"/>
    </td></tr></table>
    <freeze:text property="creator" caption="上传人"  style="width:90%"/>
      <freeze:datebox property="publish_date_start" caption="发布日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="publish_date_end" caption="发布日期" style="width:100%" colspan="0"/>
    </td></tr></table>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="查询报告操作情况列表" rowselect="false" keylist="log_report_use_id" width="95%" navbar="bottom" fixrow="false" multiselect="true" checkbox="false">
      <freeze:button name="record_addRecord" caption="增加报告操作情况" txncode="620200203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改报告操作情况" txncode="620200204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除报告操作情况" txncode="620200205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:hidden property="log_report_use_id" caption="日志报告操作id" />
      <freeze:hidden property="log_report_create_id" caption="日志报告生成id" />
      <freeze:cell property="report_name" caption="报告名称" style="width:28%"  />
      <freeze:cell property="creator" caption="上传人" style="width:12%" />
      <freeze:cell property="create_date" caption="上传日期" style="width:12%" />
      <freeze:cell property="publish_date" caption="发布日期" style="width:12%" />
      <freeze:cell property="browser_person" caption="浏览人" style="width:10%" />
      <freeze:cell property="timestamp" caption="浏览日期" style="width:20%" />
      <freeze:hidden property="browser_date" caption="浏览日期" />
      <freeze:hidden property="operate" caption="操作" />
      <freeze:hidden property="filename" caption="文件名" />
      <freeze:hidden property="path" caption="路径" />
      
  </freeze:grid>
<a id="testa" style="display:none;"></a>
</freeze:form>
</freeze:body>
</freeze:html>
