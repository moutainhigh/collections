<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>����֪ͨ������Ϣ</title>
</head>
<style type="text/css">
.odd2_b a:hover,.odd1_b a:hover{background:green;color:white;}
</style>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����֪ͨ����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����֪ͨ����' );
}

// ���沢�ر�
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
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����֪ͨ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn315001003" enctype="multipart/form-data">
  <freeze:block property="record" caption="����֪ͨ������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="func_record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="func_record_saveRecord" caption="����" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jbxx_pk" caption="֪ͨ���-����" datatype="string" maxlength="32" style="width:95%"/>
	  <freeze:hidden property="fbrid" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="fbrmc" caption="����������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="fbksid" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="fbksmc" caption="��������" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:hidden property="fbsj" caption="����ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="tznr" caption="֪ͨ����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="tzzt" caption="֪ͨ״̬" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="jsrids" caption="������ids"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="jsrmcs" caption="����������"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="fj_fk" caption="����id"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="delID1" caption="sigle file id " style="width:90%"/>
    <freeze:hidden property="delNAME1" caption="single file name" style="width:90%"/>
    <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"/>
    <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%"/>
      <freeze:text property="tzmc" caption="֪ͨ����" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
     
	<freeze:file property="fjmc1" caption="��������" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="����"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>
	
	
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
