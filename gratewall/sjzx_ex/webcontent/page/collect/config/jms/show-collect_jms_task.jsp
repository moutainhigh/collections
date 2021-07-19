<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>查看webservice任务信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存webservice任务表' );	// /txn30102001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30102001.do
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
	
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看方法信息"/>
<freeze:errors/>

<freeze:form action="/txn30102010">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="查看方法信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="任务ID" datatype="string"  style="width:95%"/>
      <freeze:cell property="method_name_en" caption="方法名称" datatype="string" style="width:95%"/>
      <freeze:cell property="method_name_cn" caption="方法中文名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_table" caption="对应采集表" show="name" valueset="资源管理_采集表"   style="width:95%"/>
      <freeze:cell property="collect_mode" caption="采集方式" show="name" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:hidden property="is_encryption" caption="是否加密"   datatype="string" maxlength="20" style="width:95%"/>
      <freeze:cell property="encrypt_mode" caption="解密方法"  show="name" valueset="资源管理_解密方法" value="01" style="width:95%"/>
      <freeze:cell property="method_description" caption="方法描述" colspan="2"  style="width:98%"/>
      <freeze:hidden property="method_status" caption="方法状态" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="参数列表" keylist="webservice_patameter_id" multiselect="false" checkbox="false" width="95%"  fixrow="false" >
      <freeze:hidden property="webservice_patameter_id" caption="参数ID"  />
      <freeze:hidden property="webservice_task_id" caption="方法ID"  />
      <freeze:cell property="patameter_type" caption="参数类型" valueset="采集任务_参数类型" style="width:15%" />
      <freeze:cell property="patameter_name" caption="参数名" style="width:15%" />
      <freeze:cell property="patameter_value" caption="参数值"   style="width:70%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
