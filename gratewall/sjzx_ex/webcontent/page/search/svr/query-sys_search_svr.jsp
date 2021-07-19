<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询检索服务列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加检索服务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_search_svr.jsp", "增加检索服务" );
	page.addRecord();
}

// 修改检索服务
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn50010204.do", "修改检索服务", "modal" );
	page.addParameter( "record:sys_search_svr_id", "primary-key:sys_search_svr_id" );
	page.updateRecord();
}

// 删除检索服务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn50010205.do", "删除检索服务" );
	page.addParameter( "record:sys_search_svr_id", "primary-key:sys_search_svr_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询检索服务列表"/>
<freeze:errors/>

<freeze:form action="/txn50010201">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询检索服务列表" keylist="sys_search_svr_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加检索服务" txncode="50010203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改检索服务" txncode="50010204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除检索服务" txncode="50010205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="序号" align="middle" style="width:5%"/>
      <freeze:hidden property="sys_search_svr_id" caption="检索服务ID" style="width:35%" />
      <freeze:cell property="svr_name" caption="服务名称" style="width:25%" />
      <freeze:cell property="columns" caption="检索字段" style="width:70%" visible="true" />
      <freeze:cell property="svr_query" caption="检索查询串" style="width:20%" visible="false" />
      <freeze:cell property="svr_template" caption="检索服务模板" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
