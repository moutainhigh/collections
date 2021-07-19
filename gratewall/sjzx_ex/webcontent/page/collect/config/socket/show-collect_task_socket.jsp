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
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">
// 保 存
function func_record_saveRecord(){
	
	var page = new pageDefine("/txn30101000.ajax", "检查数据源名称是否使用");	
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.callAjaxService('nameCheckCallback');
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
  		  var page = new pageDefine( "/txn30101002.ajax", "保存采集任务信息");
	      page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:collect_type","record:collect_type");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.callAjaxService('updateTable');
  		}
}
function updateTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("保存成功!");
		}
}
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102016.do", "查看方法信息", "modal" );
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

	var ids = getFormAllFieldValues("dataItem:webservice_task_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看采集任务信息"/>
<freeze:errors/>

<freeze:form action="/txn30101006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="查看采集任务信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象" show="name"  valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:cell property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
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
       <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
    fileList = context.getRecordset("fjdb");
    if(fileList!=null && fileList.size()>0){
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
	%>
	<tr>
	<td height="32" align="right">备案附件&nbsp;</td>
	<td colspan="3">
		
	<a href="#" onclick="downFile('<%=file_id%>')" title="附件" ><%=file_name %></a>
	</td>
	</tr>
	
	<% }
	     }
	   }catch(Exception e){
		   System.out.println(e);
	   }
	%>   
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="方法列表" keylist="webservice_task_id" multiselect="false" checkbox="false" width="95%"  fixrow="false" >
      <freeze:hidden property="webservice_task_id" caption="方法ID"  />
      <freeze:hidden property="collect_task_id" caption="采集任务ID"  />
      <freeze:cell property="index" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="method_name_cn" caption="方法中文名称" style="width:20%" />
      <freeze:cell property="method_name_en" caption="方法名称" style="width:15%" />
      <freeze:cell property="service_no" caption="任务ID"   style="width:15%" />
      <freeze:cell property="method_description" caption="方法描述" style="width:30%" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:15%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
