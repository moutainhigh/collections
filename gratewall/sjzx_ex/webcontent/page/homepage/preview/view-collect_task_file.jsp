<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>查看文件上传采集表信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存文件上传采集表' );	// /txn30301001.do
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
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101023" enctype="multipart/form-data">
  <freeze:block property="record" caption="修改文件上传采集表信息" width="95%">
      
      <freeze:hidden property="file_upload_task_id" caption="文件上传任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string"  style="width:95%"/>
      
      <freeze:hidden property="service_targets_id" caption="所属服务对象" style="width:95%"/>
      <freeze:hidden property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_table" caption="采集表"   valueset="资源管理_采集表" show="name"  style="width:95%"/>
      <freeze:cell property="collect_mode" caption="采集方式" show="name"  valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:cell property="task_description" caption="任务说明" colspan="2"  style="width:98%"/>
      <freeze:cell property="record" caption="备案说明" colspan="2"  style="width:98%"/>
     <%--  <tr >
      <td align="right" height="32">采集文件&nbsp;</td>
      <td colspan="3">
      <%
        DataBus context1 = (DataBus) request.getAttribute("freeze-databus");
        Recordset fileList=null;
        try{
          fileList = context1.getRecordset("fjdb");
      %>
      <br>
      <%
          if(fileList!=null && fileList.size()>0){
            for(int i=0;i<fileList.size();i++){
              DataBus file = fileList.get(i);
              String file_id = file.getValue(FileConstant.file_id);
              String file_name = file.getValue(FileConstant.file_name);
      %>
      <a href="#" onclick="downFile('<%=file_id%>')" title="附件"><%=file_name %></a><br><br>
      <%}}
        }catch(Exception e){
	      System.out.println(e);
        }
      %>
      </td>
    </tr> --%>
      <freeze:hidden property="collect_file_name" caption="采集文件" accept="*.xls,*.txt,*.mdb" style="width:80%" maxlength="200" colspan="2"/>
      
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
      
      <freeze:hidden property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="文件描述"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_file_id" caption="采集文件ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="校验结果文件名称"  maxlength="300" style="width:98%"/>
      <freeze:hidden property="check_result_file_id" caption="校验结果文件ID" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
