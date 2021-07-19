<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加文件上传采集表信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/UUID.js'></script>
<script language="javascript">
var collect_joumal_id = UUID.prototype.createUUID ();
var save_flag = false;
// 保 存
//alert("parent="+window.parent);
function func_record_saveRecord()
{
	save_flag = true;
	setFormFieldValue('record:collect_joumal_id',collect_joumal_id);
	saveRecord( '', '文件上传任务新增失败!' );
	//saveAndExit( '采集数据成功!', '采集数据失败!','/txn30101001.do' );	
	//confirm()
	//alert("输入校验结果");
	//getResult(collect_joumal_id);
	
	//window.parent.func_record_goBack();
	//window.parent.location.href="/txn30101001.do";
	//setTimeout("window.parent.open('/txn30101001.do',window.parent.name)","5000");
	
	//window.parent.func_record_goBack();
}

//查询导入结果
function func_record_getResult()
{
	//alert("save_flag="+save_flag);
	if(save_flag==true){
		var page = new pageDefine( "/txn30101026.ajax", "采集结果" );
		page.addParameter("record:collect_joumal_id","record:collect_joumal_id");
		page.callAjaxService("collectResCallBack");
	}else{
		alert("请先执行保存操作！");
	}
	
	
	
}
function collectResCallBack(errorCode, errDesc, xmlResults){
	
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var collect_table_name = _getXmlNodeValue(xmlResults,"result:collect_table_name");
		var collect_data_amount = _getXmlNodeValue(xmlResults,"result:collect_data_amount");
		var task_consume_time = _getXmlNodeValue(xmlResults,"result:task_consume_time");
		
		var result = "用户您好，本次采集任务，采集表["+collect_table_name+"]采集数据["+collect_data_amount+"]条，共耗时"+task_consume_time+"秒";
		alert(result);
		//parent.func_record_goBack();
	}
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存文件上传采集表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存文件上传采集表','txn30101001.do' );	// /txn30301001.do
}

// 返 回
function func_record_goBack()
{
	//goBack();	// /txn30301001.do  txn30101001
	parent.window.location.href="txn30101001.do";
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加文件上传采集表信息"/>
<freeze:errors/>

<freeze:form action="/txn30101021" enctype="multipart/form-data">
  <freeze:block property="record" caption="增加文件上传采集表信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="file_upload_task_id" caption="文件上传任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_joumal_id" caption="采集日志ID" datatype="string" maxlength="32"  style="width:95%"/>
      
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"    style="width:95%"/>
      <freeze:text property="task_name" caption="任务名称" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="采集表"  notnull="true" maxlength="32" valueset="资源管理_服务对象对应采集表" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:textarea property="task_description" caption="任务说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="备案说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      
      <freeze:file property="fjmc1" caption="采集文件" style="width:80%" accept="*.xls,*.txt,*.mdb" maxlength="100" colspan="2"/>
      
      <freeze:hidden property="collect_file_name" caption="采集文件"  style="width:80%" maxlength="200" />
      <freeze:hidden property="collect_type" caption="采集类型"  value="01"  style="width:95%" />
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
      <freeze:hidden property="log_file_path" caption="日志文件存放路径" datatype="string" maxlength="1000" style="width:95%"/>
      <freeze:hidden property="collect_status" caption="采集状态" datatype="string" maxlength="2" style="width:95%"/>
      
      <freeze:hidden property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="文件描述"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_file_id" caption="采集文件ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="校验结果文件名称"  maxlength="300" style="width:98%"/>
      <freeze:hidden property="check_result_file_id" caption="校验结果文件ID" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
