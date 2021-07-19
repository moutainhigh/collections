<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据权限分组项列表</title>
</head>

<script language="javascript">

// 增加数据权限分组项
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataaccgroupitem.jsp", "增加数据权限分组项", "modal" );
	page.addRecord();
}

// 修改数据权限分组项
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103064.do", "修改数据权限分组项", "modal" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.addParameter( "record:dataaccid", "primary-key:dataaccid" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.updateRecord();
}

// 删除数据权限分组项
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103065.do", "删除数据权限分组项" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.addParameter( "record:dataaccid", "primary-key:dataaccid" );
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
<freeze:title caption="查询数据权限分组项列表"/>
<freeze:errors/>

<freeze:form action="/txn103061">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="dataaccgrpid" caption="数据权限分组ID" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询数据权限分组项列表" keylist="dataaccgrpid,dataaccid,objectid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加数据权限分组项" txncode="103063" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改数据权限分组项" txncode="103064" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除数据权限分组项" txncode="103065" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="dataaccgrpid" caption="数据权限分组ID" style="width:25%" />
      <freeze:cell property="objectid" caption="数据权限类型代码" style="width:25%" />
      <freeze:cell property="dataaccid" caption="数据权限内码" style="width:50%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
