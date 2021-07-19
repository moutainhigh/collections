<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="450">
<head>
	<title>共享日志</title>
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
	<freeze:title caption="查询共享服务信息" />
	<freeze:errors />

	<freeze:form action="/txn6010006">
	<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="log_id" caption="主键" style="width:95%"/>
  </freeze:frame>
		<freeze:block theme="view" property="record" caption="查看共享日志"
			width="95%">
			      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
			<freeze:hidden property="log_id" caption="主键"
				datatype="string" maxlength="100" style="width:95%" />
			<freeze:cell property="service_targets_name" caption="服务对象名称" style="width:95%" />
			<freeze:cell property="service_type" caption="服务类型" valueset="资源管理_数据源类型" style="width:95%" />
			<freeze:cell property="service_name" caption="服务名称" style="width:95%" />
			<freeze:cell property="return_codes" caption="返回状态" style="width:95%" />
			<freeze:cell property="service_start_time" caption="服务开始时间" style="width:95%" />
			<freeze:cell property="service_end_time" caption="服务结束时间" style="width:95%" />
			<freeze:cell property="consume_time" caption="服务消耗时间" style="width:95%" />
			<freeze:cell property="record_start" caption="开始记录数" style="width:95%" />
			<freeze:cell property="record_end" caption="结束记录数" style="width:95%" />
			<freeze:cell property="record_amount" caption="本次访问数据量" style="width:95%" />
			<freeze:cell property="access_ip" caption="访问IP" style="width:95%" />
			<freeze:cell property="patameter" caption="输入参数" style="width:95%" colspan="2"/>
			
			
			
		</freeze:block>



	</freeze:form>
</freeze:body>
</freeze:html>
