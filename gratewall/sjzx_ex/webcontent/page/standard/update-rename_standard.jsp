<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="800" height="350">
<head>
<title>修改命名规范信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存规则标准' );	// /txn603001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn603001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改规则信息"/>
<freeze:errors/>

<freeze:form action="/txn603012" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="standard_id" caption="标准ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改规则信息" width="95%" >
    
      <freeze:button name="record_saveAndExit" caption="保存" hotkey="SAVE_CLOSE"  align="center" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="standard_id" caption="标准ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="命名规范名称"  notnull="true" datatype="string" maxlength="100" colspan="2" style="width:98%"/>
      <freeze:select property="standard_type" caption="规范类型"  valueset="规范类型"  notnull="true" style="width:95%"/>
      <freeze:hidden property="specificate_type" caption="规则类型" datatype="string" maxlength="20" style="width:95%"/>
     <freeze:hidden property="specificate_status" caption="规范状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="specificate_no" caption="类型分类号" datatype="string" maxlength="50" style="width:95%" />
      
      <freeze:select property="issuing_unit" caption="发布单位" valueset="规范发布单位"  notnull="true" style="width:95%"/>
      <freeze:datebox property="enable_time" caption="发布日期" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'>
      <tr><td width='70%'>" style="width:100%"/>
      </td><td width='5%'></td></tr></table>  
      <freeze:hidden property="is_markup" caption="代码集 无效 有效" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
       	<freeze:hidden property="fjmc1" caption="通知材料" style="width:80%" maxlength="100" colspan="2"/>

    <freeze:hidden property="fjmc" caption="通知材料" visible="false"/>
    <freeze:hidden property="fj_fk" caption="通知材料id" style="width:90%" visible="false"/>
    <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" visible="false" />
    <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" visible="false"/>
       <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
     <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
     
   
     <freeze:hidden property="disable_time" caption="停用时间" datatype="string" maxlength="19" style="width:95%"/>
      <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
     fileList = context.getRecordset("fjdb");

    if(fileList!=null && fileList.size()>0){
    	out.println("<tr><td height=\"32\" align=\"right\">文件名称：</td><td colspan=\"3\"><table >");
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
  <!-- <span class="btn_add" onclick="addNewRowEx('record:fjmc1','&nbsp;上传文件：','80%')" title="增加">&nbsp;&nbsp;</span> -->
<freeze:file property="fjmc1" caption="上传文件" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="增加"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>

	   <freeze:textarea property="specificate_desc" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>

  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
