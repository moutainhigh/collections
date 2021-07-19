<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
	<title>采集日志</title>
</head>

<script language="javascript">





// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	

}
function func_record_goBack()
{
	//window.close();	// /txn201001.do
	goBackNoUpdate();
}




_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:title caption="查询采集任务信息" />
	<freeze:errors />

	<freeze:form action="/txn6011006">
	<freeze:frame property="select-key" width="95%">
      <freeze:hidden property="collect_joumal_id" caption="主键" style="width:95%"/>
  </freeze:frame>
		<freeze:block theme="view" property="record" caption="查看采集日志"
			width="95%">
			      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
	  <freeze:hidden property="collect_joumal_id" caption="采集日志ID" style="width:10%" visible="false"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%" />
      <freeze:cell property="task_name" caption="任务名称" style="width:95%" />
      <freeze:hidden property="service_targets_id" caption="服务对象ID" style="width:95%" />
      <freeze:cell property="service_targets_name" caption="服务对象名称" style="width:95%" />
      <freeze:cell property="collect_type" caption="服务类型"  valueset="资源管理_数据源类型" style="width:95%" />
      <freeze:hidden property="task_id" caption="任务ID" style="width:95%" />
      <freeze:cell property="service_no" caption="任务编号" style="width:95%" />
      <freeze:cell property="method_name_cn" caption="方法名称"  style="width:95%" />
      <freeze:cell property="collect_table_name" caption="采集表名称"  style="width:95%" /> 
      <freeze:cell property="task_start_time" caption="任务开始时间" style="width:95%" />
      <freeze:cell property="task_end_time" caption="任务结束时间" style="width:95% " />
      <freeze:cell property="task_consume_time" caption="任务用时单位(秒)" style="width:95%" />
      <freeze:cell property="return_codes" caption="返回结果" style="width:95%" />
      <freeze:cell property="collect_data_amount" caption="本次采集数据量" style="width:95%" />
      <freeze:hidden property="task_status" caption="执行状态" valueset="资源管理_归档服务状态" style="width:95%" />
     
		</freeze:block>



	</freeze:form>
</freeze:body>
</freeze:html>
