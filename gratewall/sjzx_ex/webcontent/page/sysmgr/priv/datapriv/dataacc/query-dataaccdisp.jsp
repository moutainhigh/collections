<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据权限分配列表</title>
</head>

<script language="javascript">

// 增加数据权限分配
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataaccdisp.jsp", "增加数据权限分配", "modal" );
	page.addRecord();
}

// 修改数据权限分配
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103044.do", "修改数据权限分配", "modal" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.updateRecord();
}

// 删除数据权限分配
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103045.do", "删除数据权限分配" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询数据权限分配列表"/>
<freeze:errors/>

<freeze:form action="/txn103041">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询数据权限分配列表" keylist="objectid,dataaccgrpid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加数据权限分配" txncode="103043" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改数据权限分配" txncode="103044" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除数据权限分配" txncode="103045" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="objectid" caption="对象内码" style="width:34%" />
      <freeze:cell property="dataaccgrpid" caption="数据权限分组内码" style="width:33%" />
      <freeze:cell property="dataaccdispobj" caption="分配对象" style="width:33%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
