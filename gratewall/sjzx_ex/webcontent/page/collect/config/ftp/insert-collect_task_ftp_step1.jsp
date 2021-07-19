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
	
 	 document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101101.do");
	saveRecord( '', '保存采集任务' );
	//saveAndGoNext('','','/page/collect/config/ftp/insert-collect_task_ftp_step2.jsp');
	//page.callAjaxService('doCallback');
 	
	
}

// 返 回
function func_record_goBack()
{
	var flag = getFormFieldValue("select-key:flag");
	if(flag=="add"){
		parent.window.location.href="txn30101001.do";	
	}else if (flag="edit"){
		window.location.href="txn30101001.do";
	}
	
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType=<%=CollectConstants.TYPE_CJLX_FTP%>";
	return parameter;
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



// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
/* 	 var collect_task_id = getFormFieldValue("record:collect_task_id");
	 var service_targets_id = getFormFieldValue("record:service_targets_id");
	 
	alert("collect_task_id="+collect_task_id+"--service_targets_id="+service_targets_id);  */
	
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
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="color: white; background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第一步，配置基本信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
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
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第三步，配置规则信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>
<freeze:form action="/txn30101101" enctype="multipart/form-data">
	 <freeze:block property="select-key" caption="增加采集任务信息" >
	  <freeze:hidden property="collect_task_id" caption="采集任务ID" />
      <freeze:hidden property="service_targets_id" caption="所属服务对象"  />
      <freeze:hidden property="task_name" caption="任务名称" />
      <freeze:hidden property="flag" caption="标记是新增还是编辑"  />
       
	 </freeze:block>
  <freeze:block property="record" caption="增加采集任务信息" width="95%">
      <freeze:button name="record_nextRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
    
       <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称" onchange="changeTarget()"   style="width:95%"/>
      <freeze:text property="task_name" caption="任务名称" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="data_source_id" caption="数据源" show="name" valueset="资源管理_服务对象对应数据源" notnull="true" parameter="getParameter()"  style="width:95%"/>
      <freeze:select property="task_status" caption="任务状态" show="name" valueset="资源管理_归档服务状态" notnull="true"  style="width:95%"/>
      
      <%-- <freeze:text property="scheduling_day1" caption="采集周期" datatype="string" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="设置" onclick="chooseCjzq()" class="FormButton">
       --%>
       <freeze:hidden property="collect_type" caption="采集类型" datatype="string" maxlength="20" />
      <freeze:textarea property="task_description" caption="任务说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="备案说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
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
    	out.println("<tr><td height=\"32\" align=\"right\">备案文件：</td><td colspan=\"3\"><table >");
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr id='<%=file_id%>'>
 <td><a href="#" onclick="downFile('<%=file_id%>')" title="附件" ><%=file_name %></a></td>
 <td><a href="#" onclick="delChooseFile('<%=file_id%>','record:delIDs','<%=file_name%>','record:delNAMEs')"  title="删除" ><span class="delete">&nbsp;&nbsp;</span></a>
</tr>
<%  }
    out.println("</table></td></tr>"); 
   }
   }catch(Exception e){
	   System.out.println(e);
   }
%> 
<freeze:file property="fjmc1" caption="备案文件" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="增加"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>

  </freeze:block>
 <br>

</freeze:form>
</freeze:body>
</freeze:html>
