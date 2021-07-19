<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="300">
<head>
<title>修改ftp任务信息</title>
</head>

<script language="javascript">



// 返 回
function func_record_goBack()
{
	goBack();	// /txn30201001.do
	//var page = new pageDefine( '/txn30101013.do?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id"), '查询采集任务');
	//page.updateRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看ftp文件信息"/>
<freeze:errors/>

<freeze:form action="/txn30201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="ftp_task_id" caption="FTP任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="查看ftp文件信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ftp_task_id" caption="FTP任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="任务ID" datatype="string" style="width:95%"/>
      <freeze:cell property="file_name_en" caption="文件名称"  datatype="string"  style="width:95%"/>
      <freeze:cell property="file_name_cn" caption="文件中文名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_table" caption="对应采集表" show="name"  valueset="资源管理_采集表"  style="width:95%"/>
      <freeze:cell property="collect_mode" caption="采集方式" show="name"  valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:hidden property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:cell property="file_description" caption="文件描述"   style="width:98%"/>
      <freeze:cell property="file_sepeator" caption="列分隔符"    style="width:95%"/>
      <freeze:cell property="file_title_type" caption="标题行类型"  show="name"  valueset="标题行类型" style="width:95%"/>
      <freeze:cell property="month" caption="月份"   valueset="资源管理_月份选择" show="name"  style="width:95%" />
      <freeze:cell property="day_month" caption="日期"   show="name" valueset="资源管理_日期选择"  show="name" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
