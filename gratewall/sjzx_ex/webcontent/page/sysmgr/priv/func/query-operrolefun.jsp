<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询角色功能权限列表</title>
</head>

<script language="javascript">

// 增加角色功能权限
function func_record_addRecord()
{
	var page = new pageDefine( "insert-operrolefun.jsp", "增加角色功能权限", "modal" );
	page.addRecord();
}

// 修改角色功能权限
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103034.do", "修改角色功能权限", "modal" );
	page.addParameter( "record:roleaccid", "primary-key:roleaccid" );
	page.updateRecord();
}

// 删除角色功能权限
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103035.do", "删除角色功能权限" );
	page.addParameter( "record:roleaccid", "primary-key:roleaccid" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询角色功能权限列表"/>
<freeze:errors/>

<freeze:form action="/txn103031">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="roleid" caption="角色编号" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询角色功能权限列表" keylist="roleaccid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加角色功能权限" txncode="103033" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改角色功能权限" txncode="103034" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除角色功能权限" txncode="103035" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="roleaccid" caption="角色权限代码" style="width:10%" visible="false"/>
      <freeze:cell property="roleid" caption="角色编号" style="width:33%" />
      <freeze:cell property="txncode" caption="交易代码" style="width:35%" />
      <freeze:cell property="dataaccrule" caption="数据权限认证规则" style="width:32%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
