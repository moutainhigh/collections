<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>增加采集任务信息ftp</title>

</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{

 	document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101103.do");
	saveRecord( '', '保存采集任务' );
	func_record_goBack();
	/**var page = new pageDefine("/txn30101000.ajax", "检查数据源名称是否使用");	 ///　检查数据源名称是否使用
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.callAjaxService('nameCheckCallback');*/
}
//返数据保存后跳转回采集任务查询列表
function func_record_goBack()
{
	var flag = getFormFieldValue("record:flag");
	if(flag=="add"){//新增页面
		parent.window.location.href="txn30101001.do";	
	}else if (flag="edit"){//编辑页面
		window.location.href="txn30101001.do";
	}
	
}

// 返 回第二步
function func_record_prev()
{
	var page = new pageDefine( "/txn30101105.do", "配置采集信息");
	page.addParameter("select-key:collect_task_id","select-key:collect_task_id");
	page.addParameter("select-key:task_name","select-key:task_name");
	page.addParameter("select-key:flag","select-key:flag");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.goPage();
}


//定制采集周期
function chooseCjzq(){
var collect_task_id=getFormFieldValue('select-key:collect_task_id');
	if(collect_task_id==null||collect_task_id==""){
	    alert("请先保存采集任务信息!");
	    clickFlag=0;
	    return false;
 }
 var cjzq=getFormFieldValue("record:scheduling_day1");
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
	/* var collect_task_id = getFormFieldValue("record:collect_task_id");
	 //var service_targets_id1 = getFormFieldValue("select-key:service_targets_id");
	 var task_scheduling_id = getFormFieldValue("record:task_scheduling_id");
	alert("collect_task_id="+collect_task_id+"--task_scheduling_id="+task_scheduling_id);  */
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加采集任务信息"/>
<freeze:errors/>
<div style="width: 95%;margin-left:20px;">
		<table style="width: 80%;align:left;">
			<tr>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第一步，配置基本信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第二步，配置文件信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="color: white; background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第三步，配置规则信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>
<freeze:form action="/txn30101102" enctype="multipart/form-data">
 <freeze:block property="select-key" caption="增加采集任务信息" >
	  <freeze:hidden property="collect_task_id" caption="采集任务ID" />
      <freeze:hidden property="service_targets_id" caption="所属服务对象"  />
      <freeze:hidden property="task_name" caption="任务名称" />
      <freeze:hidden property="flag" caption="标记是新增还是编辑"  />
       
	 </freeze:block>
  <freeze:block property="record" caption="查看采集任务信息" width="95%">
      <freeze:text property="scheduling_day1" caption="采集周期" datatype="string" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="设置" onclick="chooseCjzq()" class="FormButton">
       	
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_服务对象所有名称"    style="width:95%"/>
      <freeze:cell property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="task_status" caption="任务状态" datatype="string" valueset="资源管理_归档服务状态" style="width:95%"/>
      <freeze:cell property="data_source_id" caption="数据源" show="name" valueset="资源管理_数据源"  style="width:95%"/>
      <freeze:hidden property="collect_type" caption="采集类型" datatype="string" maxlength="20" />
      <freeze:cell property="task_description" colspan="2"  caption="任务说明"  style="width:98%"/>
      <freeze:cell property="record" caption="备案说明" colspan="2" style="width:98%"/>
      <freeze:hidden property="fjmc" caption="文件上传" />
      <freeze:hidden property="fj_fk" caption="文件上传id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="task_scheduling_id" caption="计划任务ID"  />
      <freeze:hidden property="scheduling_type" caption="计划任务类型"  />
      <freeze:hidden property="start_time" caption="计划任务开始时间"  />
      <freeze:hidden property="end_time" caption="计划任务结束时间"  />
      <freeze:hidden property="scheduling_week" caption="计划任务周天"  />
      <freeze:hidden property="scheduling_day" caption="计划任务日期"  />
      <freeze:hidden property="scheduling_count" caption="计划任务执行次数"  />
      <freeze:hidden property="interval_time" caption="每次间隔时间"  />
      <freeze:hidden property="log_file_path" caption="日志文件路径"  />
      <freeze:hidden property="collect_status" caption="采集状态"  />
    
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
  
  <table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td align="center" height="50">
					<div class="btn_prev" onclick="func_record_prev();"></div>					
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn_save" onclick="func_record_saveRecord();"></div>
				</td>
			</tr>
	</table>

   <freeze:grid property="dataItem" caption="文件列表" keylist="ftp_task_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="ftp_task_id" caption="ftp任务ID"  />
      <freeze:hidden property="collect_task_id" caption="采集任务ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="file_name_cn" caption="文件中文名称" style="width:20%" />
      <freeze:cell property="file_name_en" caption="文件名称" style="width:15%" />
      <freeze:cell property="collect_mode" caption="采集方式"   valueset="资源管理_采集方式" style="width:15%" /> 
      <freeze:cell property="collect_table" caption="采集表" align="center"  valueset="资源管理_采集表" show="name" style="width:20%"/>
      						       
      <freeze:cell property="file_description" caption="文件描述" style="width:30%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
