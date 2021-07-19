<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询服务查询条件列表</title>
</head>

<script language="javascript">

// 增加服务查询条件
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_service_condition.jsp", "增加服务查询条件", "modal" );
	page.addRecord();
}

// 修改服务查询条件
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40210004.do", "修改服务查询条件", "modal" );
	page.addParameter( "record:condition_id", "primary-key:condition_id" );
	page.updateRecord();
}

// 删除服务查询条件
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40210005.do", "删除服务查询条件" );
	page.addParameter( "record:condition_id", "primary-key:condition_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询服务查询条件列表"/>
<freeze:errors/>

<freeze:form action="/txn40210001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="need_input" caption="是否需要参数" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询服务查询条件列表" keylist="condition_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加服务查询条件" txncode="40210003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改服务查询条件" txncode="40210004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除服务查询条件" txncode="40210005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="condition_id" caption="服务查询条件ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="服务ID" style="width:12%" />
      <freeze:cell property="frist_connector" caption="连接符1" style="width:12%" />
      <freeze:cell property="left_paren" caption="左括号" style="width:12%" />
      <freeze:cell property="table_name_en" caption="表名称" style="width:20%" />
      <freeze:cell property="table_name_cn" caption="表中文名称" style="width:20%" />
      <freeze:cell property="column_name_en" caption="字段名称" style="width:20%" />
      <freeze:cell property="column_name_cn" caption="字段中文名称" style="width:20%" />
      <freeze:cell property="second_connector" caption="连接符2" style="width:12%" />
      <freeze:cell property="param_value" caption="参数值" style="width:12%" />
      <freeze:cell property="param_type" caption="参数值类型" style="width:12%" />
      <freeze:cell property="right_paren" caption="右括号" style="width:12%" />
      <freeze:cell property="show_order" caption="显示顺序" style="width:10%" />
      <freeze:cell property="need_input" caption="是否需要参数" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
