<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>查看采集任务信息ftp</title>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存采集任务' );
	/**var page = new pageDefine("/txn30101000.ajax", "检查数据源名称是否使用");	 ///　检查数据源名称是否使用
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.callAjaxService('nameCheckCallback');*/
}

function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("数据源名称已经使用");
  		}else{
  		  var page = new pageDefine( "/txn30101003.do", "保存采集任务表");
  		  
	     /** page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.addParameter("record:fj_fk","record:fj_fk");
	      page.addParameter("record:fjmc","record:fjmc");
	      page.callAjaxService('insertTask');*/
  		
  		}
}
function insertTask(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    var collect_task_id=_getXmlNodeValues(xmlResults,'record:collect_task_id');
		    setFormFieldValue("record:collect_task_id",collect_task_id);
		    setFormFieldValue("primary-key:collect_task_id",collect_task_id);
		    alert("保存成功!");
		}
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存采集任务表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存采集任务表' );	// /txn30101001.do
}

// 返 回
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101001.do", "查询采集任务");
	page.updateRecord();
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType=<%=CollectConstants.TYPE_CJLX_FTP%>";
	return parameter;
}
// 修改方文件信息
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30201004.do", "修改文件信息");
	page.addValue(idx,"primary-key:ftp_task_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	page.updateRecord();
}
// 删除方法信息
function func_record_deleteRecord(idx)
{
if(confirm("是否删除选中的记录")){
	var page = new pageDefine( "/txn30201005.do", "删除文件信息" );
	page.addValue(idx,"primary-key:ftp_task_id");
	page.addParameter("record:service_targets_id","primary-key:service_targets_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.addParameter("record:fj_fk","record:fj_fk");
	page.addParameter("record:fjmc","record:fjmc");
	//page.deleteRecord( "是否删除选中的记录" ); 
	page.updateRecord();
	}
	
}
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30201006.do", "查看文件信息", "modal" );
	page.addValue(idx, "primary-key:ftp_task_id" );
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	page.addValue(collect_task_id,"primary-key:collect_task_id");
	page.updateRecord();
}
// 获取采集任务对应方法
function func_record_getFunction()
{
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	if(collect_task_id==null||collect_task_id==""){
	    alert("请先填写采集任务信息!");
	    clickFlag=0;
	    return false;
    }
	var page = new pageDefine( "/txn30101009.do", "获取采集任务对应方法");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.updateRecord();
}
function changeTarget()
{
	if (getFormFieldValue("record:service_targets_id") != null && getFormFieldValue("record:service_targets_id") != "")
	{
		var name=document.getElementById("record:_tmp_service_targets_id").value;
		setFormFieldValue("record:task_name",name+"_FTP");
		setFormFieldValue("record:data_source_id",0,"");
	}
}
//定制采集周期
function chooseCjzq(){
 var cjzq=getFormFieldValue("record:interval_time");
 if(cjzq==null||""==cjzq){
 	//var page = new pageDefine( "/page/collect/config/schedule/insert-collect_task_scheduling.jsp", "增加任务调度", "modal");
	//page.addRecord();
	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	//window.open("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", null, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 }else{
 	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 	//var page = new pageDefine( "/txn30801004.do", "修改任务调度", "modal");
	//page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	//page.updateRecord();
 }
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//document.getElementById('taskName').innerText = ("服务对象："+document.getElementById('record:task_name').value);
	//document.getElementById('targetName').innerText = ("采集任务名称："+document.getElementById('record:service_targets_name').value);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101015" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%"/>
  </freeze:frame>
<!--   <br>
	<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center"
		style="border-collapse: collapse;">
		<tr>
			<td id='targetName' width="45%" style="font-weight:bold;text-align:right;padding-right:10px;"></td>
			<td width="15%"></td>
			<td id='taskName' style="text-align:left;font-weight:bold;padding-left:10px;"></td>
		</tr>
	</table> -->
  <freeze:block property="record" caption="查看采集任务信息" width="95%">
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="data_source_id" caption="数据源" show="name" valueset="资源管理_数据源"  style="width:95%"/>
      <freeze:cell property="scheduling_day1" caption="采集周期" datatype="string"  style="width:80%"/>
      <freeze:hidden property="collect_type" caption="采集类型" datatype="string" maxlength="20" />
      <freeze:cell property="task_description" caption="任务说明"  style="width:98%"/>
      <freeze:cell property="record" caption="备案说明"  style="width:98%"/>
      <freeze:hidden property="fjmc" caption="文件上传" />
      <freeze:hidden property="service_targets_id" caption="所属服务对象" style="width:95%"/>
      <freeze:hidden property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fj_fk" caption="文件上传id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="task_status" caption="任务状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>
   <freeze:grid property="dataItem" caption="文件列表" keylist="ftp_task_id" multiselect="false" checkbox="false" width="95%" fixrow="false" >
      <freeze:hidden property="ftp_task_id" caption="ftp任务ID"  />
      <freeze:hidden property="collect_task_id" caption="采集任务ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="file_name_cn" caption="文件中文名称" style="width:20%" />
      <freeze:cell property="file_name_en" caption="文件名称" style="width:15%" />
      <freeze:cell property="service_no" caption="任务编号"   style="width:15%" />
      <freeze:cell property="file_description" caption="文件描述" style="width:30%" />
      <freeze:hidden property="oper" caption="操作" style="width:15%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
