<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="800" height="350">
<head>
<title>�޸������淶��Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '��������׼' );	// /txn603001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn603001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĺ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn603012" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="standard_id" caption="��׼ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĺ�����Ϣ" width="95%" >
    
      <freeze:button name="record_saveAndExit" caption="����" hotkey="SAVE_CLOSE"  align="center" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="standard_id" caption="��׼ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="�����淶����"  notnull="true" datatype="string" maxlength="100" colspan="2" style="width:98%"/>
      <freeze:select property="standard_type" caption="�淶����"  valueset="�淶����"  notnull="true" style="width:95%"/>
      <freeze:hidden property="specificate_type" caption="��������" datatype="string" maxlength="20" style="width:95%"/>
     <freeze:hidden property="specificate_status" caption="�淶״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="specificate_no" caption="���ͷ����" datatype="string" maxlength="50" style="width:95%" />
      
      <freeze:select property="issuing_unit" caption="������λ" valueset="�淶������λ"  notnull="true" style="width:95%"/>
      <freeze:datebox property="enable_time" caption="��������" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'>
      <tr><td width='70%'>" style="width:100%"/>
      </td><td width='5%'></td></tr></table>  
      <freeze:hidden property="is_markup" caption="���뼯 ��Ч ��Ч" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
       	<freeze:hidden property="fjmc1" caption="֪ͨ����" style="width:80%" maxlength="100" colspan="2"/>

    <freeze:hidden property="fjmc" caption="֪ͨ����" visible="false"/>
    <freeze:hidden property="fj_fk" caption="֪ͨ����id" style="width:90%" visible="false"/>
    <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" visible="false" />
    <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" visible="false"/>
       <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
     <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
     
   
     <freeze:hidden property="disable_time" caption="ͣ��ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
     fileList = context.getRecordset("fjdb");

    if(fileList!=null && fileList.size()>0){
    	out.println("<tr><td height=\"32\" align=\"right\">�ļ����ƣ�</td><td colspan=\"3\"><table >");
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr id='<%=file_id%>'>
 <td><a href="#" onclick="downFile('<%=file_id%>')" title="����" ><%=file_name %></a></td>
 <td><a href="#" onclick="delChooseFile('<%=file_id%>','record:delIDs','<%=file_name%>','record:delNAMEs')"  title="ɾ��" ><span class="delete">&nbsp;&nbsp;</span></a>
</tr>
<%  }
    out.println("</table></td></tr>"); 
   }
   }catch(Exception e){
	   System.out.println(e);
   }
%>   
  <!-- <span class="btn_add" onclick="addNewRowEx('record:fjmc1','&nbsp;�ϴ��ļ���','80%')" title="����">&nbsp;&nbsp;</span> -->
<freeze:file property="fjmc1" caption="�ϴ��ļ�" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="����"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>

	   <freeze:textarea property="specificate_desc" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>

  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
