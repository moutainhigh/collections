<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询访问规则列表</title>
</head>

<script language="javascript">

// 增加访问规则
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_service_rule.jsp", "增加访问规则", "modal" );
	page.addRecord();
}

// 修改访问规则
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn403004.do", "修改访问规则", "modal" );
	page.addParameter( "record:rule_id", "primary-key:rule_id" );
	page.updateRecord();
}

// 删除访问规则
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn403005.do", "删除访问规则" );
	page.addParameter( "record:rule_id", "primary-key:rule_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询访问规则列表"/>
<freeze:errors/>

<freeze:form action="/txn403001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询访问规则列表" keylist="rule_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加访问规则" txncode="403003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改访问规则" txncode="403004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除访问规则" txncode="403005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="rule_id" caption="服务访问规则ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="服务ID" style="width:16%" />
      <freeze:cell property="week" caption="是星期几" style="width:14%" />
      <freeze:cell property="start_time" caption="服务当天开始时间" style="width:14%" />
      <freeze:cell property="end_time" caption="服务当天结束时间" style="width:14%" />
      <freeze:cell property="times_day" caption="服务当天可访问次数" style="width:14%" />
      <freeze:cell property="count_dat" caption="服务当天一次可访问记录数目" style="width:14%" />
      <freeze:cell property="total_count_day" caption="服务当天可访问总记录数目" style="width:14%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
