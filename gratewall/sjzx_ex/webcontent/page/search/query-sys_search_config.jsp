<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询全文检索列表</title>
</head>

<script language="javascript">

// 增加全文检索
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_search_config.jsp", "增加全文检索", "modal" );
	page.addRecord();
}

// 修改全文检索
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn50030104.do", "修改全文检索", "modal" );
	page.addParameter( "record:sys_search_config_id", "primary-key:sys_search_config_id" );
	page.updateRecord();
}

// 删除全文检索
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn50030105.do", "删除全文检索" );
	page.addParameter( "record:sys_search_config_id", "primary-key:sys_search_config_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询全文检索列表"/>
<freeze:errors/>

<freeze:form action="/txn50030101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="sys_search_config_id" caption="搜索配置ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="用户ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询全文检索列表" keylist="sys_search_config_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加全文检索" txncode="50030103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改全文检索" txncode="50030104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除全文检索" txncode="50030105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="sys_search_config_id" caption="搜索配置ID" style="width:18%" />
      <freeze:cell property="sys_svr_user_id" caption="用户ID" style="width:18%" />
      <freeze:cell property="permit_subject" caption="授权主题" style="width:20%" visible="false" />
      <freeze:cell property="create_by" caption="创建人" style="width:16%" />
      <freeze:cell property="create_date" caption="创建日期" style="width:18%" />
      <freeze:cell property="config_order" caption="配置顺序" style="width:15%" />
      <freeze:cell property="is_pause" caption="是否暂停" style="width:15%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
