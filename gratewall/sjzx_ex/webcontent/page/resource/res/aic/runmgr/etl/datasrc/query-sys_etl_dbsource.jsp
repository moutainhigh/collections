<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据来源管理列表</title>
</head>

<script language="javascript">

// 增加数据来源管理
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_etl_dbsource.jsp", "增加数据来源管理", "modal" );
	page.addRecord();
}

// 修改数据来源管理
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn501020004.do", "修改数据来源管理", "modal" );
	page.addParameter( "record:sys_etl_dbsource_id", "primary-key:sys_etl_dbsource_id" );
	page.updateRecord();
}

// 删除数据来源管理
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn501020005.do", "删除数据来源管理" );
	page.addParameter( "record:sys_etl_dbsource_id", "primary-key:sys_etl_dbsource_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询数据来源管理列表"/>
<freeze:errors/>

<freeze:form action="/txn501020001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="dbsource_lb" caption="数据来源类别" valueset="etl_sjlylb" show="name" style="width:55%"/>
      <freeze:text property="db_name" caption="数据库名称" datatype="string" maxlength="20" style="width:55%"/>
  </freeze:block>

  <freeze:grid property="record" caption="数据来源列表" keylist="sys_etl_dbsource_id" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <%-- 
      <freeze:button name="record_addRecord" caption="增加数据来源管理" txncode="501020003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改数据来源管理" txncode="501020004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除数据来源管理" txncode="501020005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      --%>
      <freeze:cell property="sys_etl_dbsource_id" caption="ID" style="width:10%" visible="false"/>
      <freeze:cell property="dbsource_name" caption="数据来源名称" align="center" style="width:14%" />
      <freeze:cell property="dbsource_lb" caption="数据来源类别"  valueset="etl_sjlylb" align="center" style="width:12%" />
      <freeze:cell property="db_name" caption="数据库名称" align="center" style="width:13%" />
      <freeze:cell property="db_lb" caption="数据库类别" align="center" style="width:13%" />
      <freeze:cell property="db_constr" caption="数据库IP" align="center" style="width:13%" />
      <freeze:cell property="db_ms" caption="数据库描述" align="center" style="width:22%" />
      <freeze:cell property="db_user" caption="数据库用户" align="center" style="width:13%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
