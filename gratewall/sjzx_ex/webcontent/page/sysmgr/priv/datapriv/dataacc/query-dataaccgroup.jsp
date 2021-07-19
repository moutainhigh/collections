<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据权限分组列表</title>
</head>

<script language="javascript">

// 增加数据权限分组
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataaccgroup.jsp", "增加数据权限分组", "modal" );
	page.addRecord();
}

// 修改数据权限分组
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103054.do", "修改数据权限分组", "modal" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.updateRecord();
}

// 删除数据权限分组
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103055.do", "删除数据权限分组" );
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
<freeze:title caption="查询数据权限分组列表"/>
<freeze:errors/>

<freeze:form action="/txn103051">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="dataaccgrpname" caption="数据权限分组名称" datatype="string" maxlength="40" style="width:95%"/>
      <freeze:text property="dataaccgrpdesc" caption="数据权限分组描述" datatype="string" maxlength="200" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询数据权限分组列表" keylist="dataaccgrpid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加数据权限分组" txncode="103053" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改数据权限分组" txncode="103054" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除数据权限分组" txncode="103055" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="dataaccgrpid" caption="数据权限分组ID" style="width:10%" visible="false"/>
      <freeze:cell property="dataaccgrpname" caption="数据权限分组名称" style="width:32%" />
      <freeze:cell property="dataaccrule" caption="角色权限认证规则" style="width:17%" />
      <freeze:cell property="dataacctype" caption="数据权限分组类型" style="width:17%" />
      <freeze:cell property="dataaccgrpdesc" caption="数据权限分组描述" style="width:34%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
