<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询配置功能关系列表</title>
</head>

<script language="javascript">

// 增加配置功能关系
function func_record_addRecord()
{
	var page = new pageDefine( "insert-funcdataobject.jsp", "增加配置功能关系", "modal" );
	page.addRecord();
}

// 修改配置功能关系
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103024.do", "修改配置功能关系", "modal" );
	page.addParameter( "record:funcode", "primary-key:funcode" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.updateRecord();
}

// 删除配置功能关系
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103025.do", "删除配置功能关系" );
	page.addParameter( "record:funcode", "primary-key:funcode" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询配置功能关系列表"/>
<freeze:errors/>

<freeze:form action="/txn103021">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="objectid" caption="数据对象代码" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询配置功能关系列表" keylist="funcode,objectid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加配置功能关系" txncode="103023" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改配置功能关系" txncode="103024" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除配置功能关系" txncode="103025" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="funcode" caption="功能代码" style="width:66%" />
      <freeze:cell property="objectid" caption="数据对象代码" style="width:34%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
