<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询参数维护列表</title>
</head>

<script language="javascript">

// 增加参数维护
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_webservice_patameter.jsp", "增加参数维护", "modal" );
	page.addRecord();
}

// 修改参数维护
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30103004.do", "修改参数维护", "modal" );
	page.addParameter( "record:webservice_patameter_id", "primary-key:webservice_patameter_id" );
	page.updateRecord();
}

// 删除参数维护
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30103005.do", "删除参数维护" );
	page.addParameter( "record:webservice_patameter_id", "primary-key:webservice_patameter_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询参数维护列表"/>
<freeze:errors/>

<freeze:form action="/txn30103001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询参数维护列表" keylist="webservice_patameter_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加参数维护" txncode="30103003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改参数维护" txncode="30103004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除参数维护" txncode="30103005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="webservice_patameter_id" caption="参数ID" style="width:20%" />
      <freeze:cell property="webservice_task_id" caption="webservice任务ID" style="width:20%" />
      <freeze:cell property="patameter_type" caption="参数类型" style="width:20%" />
      <freeze:cell property="patameter_name" caption="参数名" style="width:20%" />
      <freeze:cell property="patameter_value" caption="参数值" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
