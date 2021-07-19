<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询弹窗任务列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加弹窗任务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_popwindow.jsp", "增加弹窗任务", "modal" );
	page.addRecord();
}

// 修改弹窗任务
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn60800004.do", "修改弹窗任务", "modal" );
	page.addParameter( "record:sys_popwindow_id", "primary-key:sys_popwindow_id" );
	page.updateRecord();
}

// 删除弹窗任务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn60800005.do", "删除弹窗任务" );
	page.addParameter( "record:sys_popwindow_id", "primary-key:sys_popwindow_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		 operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' title='修改' href='#'><div class='edit'></div></a>"
		 +"<a onclick='setCurrentRowChecked(\"record\");func_record_deleteRecord();' title='删除' href='#'><div class='delete'></div></a>";
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询弹窗任务列表"/>
<freeze:errors/>

<freeze:form action="/txn60800001">
<div style="display:none">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="content" caption="内容" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:datebox property="expire_date" caption="到期日期至" datatype="string" maxlength="10" style="width:95%"/>
  </freeze:block>
</div>  
  <freeze:grid property="record" caption="查询弹窗任务列表" keylist="sys_popwindow_id" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="record_addRecord" caption="增加" txncode="60800003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="60800005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:cell property="sys_popwindow_id" caption="系统任务ID" visible="false" />
      <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
      <freeze:cell property="content" caption="内容" style="width:15%" />
      <freeze:cell property="PUBLISH_DEPART" caption="发布部门" style="width:13%" />
      <freeze:cell property="publish_date" caption="发布日期" style="width:13%" datatype="date"/>
      <freeze:cell property="expire_date" caption="到期日期" style="width:13%" datatype="date"/>
      <freeze:cell property="AUTHOR" caption="发布人" style="width:13%" />
      <freeze:hidden property="roles" caption="角色" style="width:13%"  />
      <freeze:cell property="operation" style="width:15%;" caption="操作" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
