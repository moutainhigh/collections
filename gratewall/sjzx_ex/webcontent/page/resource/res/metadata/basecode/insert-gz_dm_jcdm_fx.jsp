<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���Ӵ��������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{

   var jcsjfx_dm=getFormFieldValue("record:jcsjfx_dm");
   var jcsjfx_mc=getFormFieldValue("record:jcsjfx_mc");  
   if(jcsjfx_dm==""||jcsjfx_mc==""){
       window.alert("����ȷ��д������...");
       return;
   }
   if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jcsjfx_mc))  {
    ��alert("�������ݷ��������в�Ӧ���������ַ�"); 
    ��return;
  ��}   
   if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jcsjfx_dm))  {
    ��alert("�������ݷ�������в�Ӧ���������ַ�"); 
    ��return;
  ��} 
   saveAndExit( '', '��������Ŀ¼����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���������������(����)' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���������������(����)' );	// /txn3010101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn3010101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӵ��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn3010103">
  <freeze:block property="record" caption="���Ӵ��������Ϣ" captionWidth="0.5" width="95%" nowrap="true">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jcsjfx_id" caption="���ݷ���ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="jc_dm_id" caption="����ID" datatype="string" maxlength="36" minlength="1" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="���ݷ������" datatype="string" maxlength="36" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="���ݷ�������" datatype="string" maxlength="150" minlength="1" colspan="2" style="width:95%"/>
      <freeze:hidden property="jcsjfx_fjd" caption="���ݷ���ڵ����" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="szcc" caption="���ڲ��" datatype="string" maxlength="2" value="1" style="width:95%"/>
      <freeze:hidden property="xssx" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="sfmx" caption="�Ƿ���ϸ" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="fx_ms" caption="��������" colspan="2" rows="2" maxlength="32700" style="width:98%"/>
      <freeze:hidden property="sy_zt" caption="ʹ��״̬" datatype="string" maxlength="1" value="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
