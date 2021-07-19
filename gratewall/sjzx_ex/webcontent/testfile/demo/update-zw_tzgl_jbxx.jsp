<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�֪ͨ������Ϣ</title>
</head>
<style type="text/css">
.odd2_b a:hover,.odd1_b a:hover{background:green;color:white;}
</style>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	
	saveAndExit( '', '����֪ͨ����' );	// /txn315001001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn315001001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//document.getElementById('label:record:fjmc').style.display='none';
	//document.getElementById('label:record:fj_fk').style.display='none';
	//document.getElementById('label:record:delIDs').style.display='none';
	//document.getElementById('label:record:delNAMEs').style.display='none';
	//document.getElementById('record:fjmc').style.display='none';
	//document.getElementById('record:fj_fk').style.display='none';
	//document.getElementById('record:delIDs').style.display='none';
	//document.getElementById('record:delNAMEs').style.display='none';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�֪ͨ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn315001002" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jbxx_pk" caption="֪ͨ���-����" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�֪ͨ������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jbxx_pk" caption="֪ͨ���-����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="tzmc" caption="֪ͨ����" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
       
      <freeze:hidden property="fjmc" caption="֪ͨ����" />
      <freeze:hidden property="fj_fk" caption="֪ͨ����id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="fbrid" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="fbrmc" caption="����������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="fbksid" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="fbksmc" caption="��������" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:hidden property="fbsj" caption="����ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="tznr" caption="֪ͨ����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="tzzt" caption="֪ͨ״̬" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="jsrids" caption="������ids"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="jsrmcs" caption="����������"  maxlength="2000" style="width:98%"/>
      
      
      
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
    
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
