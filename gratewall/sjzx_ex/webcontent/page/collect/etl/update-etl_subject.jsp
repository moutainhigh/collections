<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改etl任务信息</title>
</head>

<script language="javascript">
//定制采集周期
function chooseCjzq(){

 	window.showModalDialog("/page/collect/config/schedule/insert-etl_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");

}
// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存etl任务表失败!' );	// /txn30300001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30300001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	document.getElementById("label:record:inteval").innerText = '*'+document.getElementById("label:record:inteval").innerText;
	document.getElementById("label:record:inteval").style.color = 'red';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改etl任务信息"/>
<freeze:errors/>

<freeze:form action="/txn30300002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="etl_id" caption="主键ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改etl任务信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="etl_id" caption="主键ID" datatype="string"/>
      <freeze:browsebox property="res_target_id" caption="服务对象" valueset="资源管理_服务对象名称"  show="name"  notnull="true"   style="width:98%"/>
      <freeze:text property="subj_name" caption="任务名称" datatype="string" notnull="true" maxlength="240"  style="width:98%"/>
      <freeze:text property="inteval" caption="采集周期" datatype="string" notnull="true" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="设置" onclick="chooseCjzq()" class="FormButton">
      
      <freeze:select property="add_type" valueset="资源管理_采集方式" caption="数据处理类型" notnull="true" style="width:98%"/>
      <freeze:textarea cols="2" property="subj_desc" caption="任务描述"  colspan="2"  maxlength="1000"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="是否删除" datatype="string" value="Y" style="width:98%"/>
      <freeze:hidden property="show_order" caption="排序号" style="width:98%"/>
      <freeze:hidden property="task_scheduling_id" caption="计划任务ID"  />
      <freeze:hidden property="scheduling_type" caption="计划任务类型"  />
      <freeze:hidden property="start_time" caption="计划任务开始时间"  />
      <freeze:hidden property="end_time" caption="计划任务结束时间"  />
      <freeze:hidden property="scheduling_week" caption="计划任务周天"  />
      <freeze:hidden property="scheduling_day" caption="计划任务日期"  />
      <freeze:hidden property="scheduling_count" caption="计划任务执行次数"  />
      <freeze:hidden property="interval_time" caption="每次间隔时间"  />
      <freeze:hidden property="schedule_json" caption="运行周期实际数据"  />
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
