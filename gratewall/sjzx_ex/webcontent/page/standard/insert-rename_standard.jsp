<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="400">
<head>
<title>���������淶��Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������׼' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������׼' );
}

// ���沢�ر�
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
<freeze:title caption="���ӹ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn603013"  enctype="multipart/form-data">
  <freeze:block property="record" caption="���ӹ�����Ϣ" width="95%"  >
    
      <freeze:button name="record_saveAndExit" caption="����" hotkey="SAVE_CLOSE"  align="center" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="standard_id" caption="��׼ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="�����淶����"  notnull="true" datatype="string" maxlength="100" colspan="2" style="width:98%"/>
      <freeze:select property="standard_type" caption="�淶����"  valueset="�淶����"  notnull="true" style="width:95%"/>
      <freeze:hidden property="specificate_type" caption="��������" value="3" datatype="string" maxlength="20" style="width:95%"/>
     <freeze:hidden property="specificate_status" caption="�淶״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="specificate_no" caption="���ͷ����" datatype="string" maxlength="50" style="width:95%" />
      
      <freeze:select property="issuing_unit" caption="������λ" valueset="�淶������λ"  notnull="true" style="width:95%"/>
      <freeze:datebox property="enable_time" caption="��������" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'>
      <tr><td width='75%'>" style="width:100%"/>
      </td><td width='5%'></td></tr></table>  
     
      <freeze:hidden property="is_markup" caption="���뼯 ��Ч ��Ч" value="Y" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
       <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
       <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
        <freeze:textarea property="specificate_desc" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
    
    <freeze:hidden property="fj_fk" caption="����id"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="delID1" caption="sigle file id " style="width:90%"/>
    <freeze:hidden property="delNAME1" caption="single file name" style="width:90%"/>
    <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"/>
    <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%"/>
     <freeze:hidden property="disable_time" caption="ͣ��ʱ��" datatype="string" maxlength="19" style="width:95%"/>
<freeze:file property="fjmc1" caption="�ϴ��ļ�" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="����"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table> 
 

  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
