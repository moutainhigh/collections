<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>查看采集任务信息</title>
</head>
<script language="javascript">
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102006.do", "查看方法信息", "modal" );
	page.addValue(idx,"primary-key:webservice_task_id");
	
	page.updateRecord();
}

// 保 存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存采集任务表' );	// /txn30101001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30101001.do
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=' + getFormFieldValue('record:service_targets_id');
	return parameter;
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//var targetName = parent.targetName;
	//alert(targetName);
	/* var ids = getFormAllFieldValues("dataItem:webservice_task_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 } */
	//document.getElementById('taskName').innerText = ("服务对象："+document.getElementById('record:task_name').value);
	//document.getElementById('targetName').innerText = ("采集任务名称："+document.getElementById('record:service_targets_name').value);
	//alert(document.getElementById('body-div').clientHeight);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%"/>
  </freeze:frame>
<!-- <br>
	<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center"
		style="border-collapse: collapse;">
		<tr>
			<td id='targetName' width="45%" style="font-weight:bold;text-align:right;padding-right:10px;"></td>
			<td width="15%"></td>
			<td id='taskName' style="text-align:left;font-weight:bold;padding-left:10px;"></td>
		</tr>
	</table> -->
  <freeze:block property="record" caption="查看采集任务信息" width="95%">
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="所属服务对象" style="width:95%"/>
      <freeze:hidden property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="data_source_id" caption="数据源" show="name" valueset="资源管理_数据源"  style="width:95%"/>
      <freeze:cell property="scheduling_day1" caption="采集周期" datatype="string"  style="width:95%"/>
      <freeze:hidden property="collect_type" caption="采集类型" datatype="string" maxlength="20" />
      <freeze:cell property="task_description" caption="任务说明" colspan="2"  style="width:98%"/>
      <freeze:cell property="record" caption="备案说明" colspan="2"  style="width:98%"/>
      <freeze:hidden property="task_status" caption="任务状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="service_targets_name" caption="服务对象名称" datatype="string" maxlength="32" style="width:95%"/>
   </freeze:block>
   <freeze:grid property="dataItem" caption="方法列表" keylist="webservice_task_id" multiselect="false" checkbox="false" width="95%" fixrow="false" >
      <freeze:hidden property="webservice_task_id" caption="方法ID"  />
      <freeze:hidden property="collect_task_id" caption="采集任务ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="method_name_cn" caption="方法中文名称" style="width:20%" />
      <freeze:cell property="method_name_en" caption="方法名称" style="width:15%" />
      <freeze:hidden property="service_no" caption="任务ID"   style="width:15%" />
      <freeze:cell property="method_description" caption="方法描述" style="width:30%" />
      <freeze:hidden property="oper" caption="操作" style="width:15%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
