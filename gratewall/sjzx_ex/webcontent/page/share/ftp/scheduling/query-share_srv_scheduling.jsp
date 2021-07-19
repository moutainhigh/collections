<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享调度列表</title>
</head>

<script language="javascript">

// 增加共享调度
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_srv_scheduling.jsp", "增加共享调度", "modal" );
	page.addRecord();
}

// 修改共享调度
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40400004.do", "修改共享调度", "modal" );
	page.addParameter( "record:srv_scheduling_id", "primary-key:srv_scheduling_id" );
	page.updateRecord();
}

// 删除共享调度
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40400005.do", "删除共享调度" );
	page.addParameter( "record:srv_scheduling_id", "primary-key:srv_scheduling_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询共享调度列表"/>
<freeze:errors/>

<freeze:form action="/txn40400001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询共享调度列表" keylist="srv_scheduling_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加共享调度" txncode="40400003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改共享调度" txncode="40400004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除共享调度" txncode="40400005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="srv_scheduling_id" caption="任务调度ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="服务ID" style="width:12%" />
      <freeze:cell property="service_no" caption="服务编号" style="width:12%" />
      <freeze:cell property="service_name" caption="任务名称" style="width:20%" />
      <freeze:cell property="job_class_name" caption="触发调用的类名" style="width:20%" />
      <freeze:cell property="scheduling_type" caption="计划任务类型" style="width:10%" />
      <freeze:cell property="scheduling_day" caption="计划任务日期" style="width:10%" />
      <freeze:cell property="start_time" caption="计划任务开始时间" style="width:10%" />
      <freeze:cell property="end_time" caption="计划任务结束时间" style="width:10%" />
      <freeze:cell property="scheduling_count" caption="计划任务执行次数" style="width:10%" />
      <freeze:cell property="interval_time" caption="每次间隔时间" style="width:10%" />
      <freeze:cell property="scheduling_week" caption="计划任务周天" style="width:20%" />
      <freeze:cell property="scheduling_day1" caption="计划任务周期中文" style="width:20%" visible="false" />
      <freeze:cell property="task_expression" caption="表达式" style="width:20%" />
      <freeze:cell property="is_markup" caption="代码表 有效 无效" style="width:10%" />
      <freeze:cell property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:cell property="created_time" caption="创建时间" style="width:12%" />
      <freeze:cell property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:cell property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
