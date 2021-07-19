<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加用户服务限制信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存用户服务限制' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存用户服务限制' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存用户服务限制' );	// /txn50011701.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50011701.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加用户服务限制信息"/>
<freeze:errors/>

<freeze:form action="/txn50011703">
  <freeze:block property="record" caption="增加用户服务限制信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_svr_limit_id" caption="用户服务限制主键" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="用户ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_svr_service_id" caption="服务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="is_limit_week" caption="是否限制本日期" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="is_limit_time" caption="是否限制时间" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="is_limit_number" caption="是否限制次数" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="is_limit_total" caption="是否限制总条数" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="limit_week" caption="限制星期" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="limit_time" caption="限制时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_start_time" caption="起止限制时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_end_time" caption="结束限制时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_number" caption="限制次数" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_total" caption="限制总条数" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_desp" caption="限制描述" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
