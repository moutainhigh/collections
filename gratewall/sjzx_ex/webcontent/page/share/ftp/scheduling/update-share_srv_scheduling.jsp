<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改共享调度信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享任务调度' );	// /txn40400001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40400001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改共享调度信息"/>
<freeze:errors/>

<freeze:form action="/txn40400002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="srv_scheduling_id" caption="任务调度ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改共享调度信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="srv_scheduling_id" caption="任务调度ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_no" caption="服务编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="service_name" caption="任务名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="job_class_name" caption="触发调用的类名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="scheduling_type" caption="计划任务类型" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="scheduling_day" caption="计划任务日期" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="start_time" caption="计划任务开始时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="end_time" caption="计划任务结束时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="scheduling_count" caption="计划任务执行次数" datatype="string" maxlength="4" style="width:95%"/>
      <freeze:text property="interval_time" caption="每次间隔时间" datatype="string" maxlength="3" style="width:95%"/>
      <freeze:text property="scheduling_week" caption="计划任务周天" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:textarea property="scheduling_day1" caption="计划任务周期中文" colspan="2" rows="4" maxlength="500" style="width:98%"/>
      <freeze:text property="task_expression" caption="表达式" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="is_markup" caption="代码表 有效 无效" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:text property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
