<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改文件上传采集表信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/UUID.js'></script>
<script language="javascript">
var collect_joumal_id = UUID.prototype.createUUID ();


// 保存并关闭
function func_record_saveAndExit()
{
	setFormFieldValue('record:collect_joumal_id',collect_joumal_id);
	saveAndExit( '', '修改采集数据失败!'  );	// /txn30301001.do
}

// 返 回
function func_record_goBack()
{
	
	goBack();	// /txn30301001.do  txn30101001
	//parent.window.location.href="txn30101001.do";
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//var filepath = getFormFieldValue("record:log_file_path");
	//var num = filepath.lastIndexOf("/");
	//var filename = filepath.substring(num+1);
	//var url = "/downloadFile?file="+filepath+"&&fileName="+filename;	
	//document.getElementById("logfile").href= url;
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改文件上传采集表信息"/>
<freeze:errors/>

<freeze:form action="/txn30101023" enctype="multipart/form-data">
  <freeze:block property="record" caption="修改文件上传采集表信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="file_upload_task_id" caption="文件上传任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="collect_joumal_id" caption="采集日志ID" datatype="string" maxlength="32"  style="width:95%"/>
      
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"    style="width:95%"/>
      <freeze:text property="task_name" caption="任务名称" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="采集表"  notnull="true" maxlength="32" valueset="资源管理_服务对象对应采集表" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:textarea property="task_description" caption="任务说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="备案说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:file property="collect_file_name" caption="采集文件" accept="*.xls,*.txt,*.mdb" style="width:80%" maxlength="200" colspan="2"/>
      <freeze:hidden property="collect_status" caption="采集状态" datatype="string" maxlength="2" style="width:95%"/>
      
      <freeze:hidden property="collect_type" caption="采集类型" valueset="采集任务_采集类型" value="01"  style="width:95%" />
      <freeze:hidden property="data_source_id" caption="数据源"  style="width:95%"/>
      <freeze:hidden property="fjmc" caption="文件上传" />
      <freeze:hidden property="fj_fk" caption="文件上传id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="task_status" caption="任务状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="文件描述"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_filse_id" caption="采集文件ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="校验结果文件名称"   style="width:98%"/>
      <freeze:hidden property="log_file_path" caption="日志文件存放路径" datatype="string" maxlength="1000" style="width:95%"/>
      <freeze:hidden property="check_result_file_id" caption="校验结果文件ID" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
