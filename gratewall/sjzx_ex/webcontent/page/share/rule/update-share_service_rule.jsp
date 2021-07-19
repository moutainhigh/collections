<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改访问规则信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享服务访问规则表' );	// /txn403001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn403001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改访问规则信息"/>
<freeze:errors/>

<freeze:form action="/txn403002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="rule_id" caption="服务访问规则ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改访问规则信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="rule_id" caption="服务访问规则ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="week" caption="是星期几" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="start_time" caption="服务当天开始时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="end_time" caption="服务当天结束时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="times_day" caption="服务当天可访问次数" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="count_dat" caption="服务当天一次可访问记录数目" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="total_count_day" caption="服务当天可访问总记录数目" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
