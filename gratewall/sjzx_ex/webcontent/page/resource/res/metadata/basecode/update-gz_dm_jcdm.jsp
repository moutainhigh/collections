<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĵ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	var jc_dm_dm = getFormFieldValue("record:jc_dm_dm");
	var jc_dm_mc = getFormFieldValue("record:jc_dm_mc");
	
	if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jc_dm_dm))  {
		alert("���������в�Ӧ���������ַ�"); 
		return;
	}
	if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jc_dm_mc))  {
		alert("�������������в�Ӧ���������ַ�"); 
		return;
	}
	saveAndExit( '', '��������������' );	// /txn301011.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn301011.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}
function   checkMe()       
{   
 var loc = getFormFieldValue("record:jc_dm_mc");
  if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(loc))  {
    alert("�������������в�Ӧ���������ַ�"); 
    return false;
  }else{
    return true;
  }
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĵ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn301012">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jc_dm_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" captionWidth="0.5" caption="�޸Ĵ�����Ϣ" width="95%" >
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jc_dm_id" caption="����ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="jc_dm_dm" caption="����" datatype="string" maxlength="36" colspan="2" minlength="1" style="width:95%"/>
      <freeze:text property="jc_dm_mc" caption="��������" validator="checkMe()" datatype="string" maxlength="255" colspan="2" minlength="1" style="width:98%"/>
      <freeze:select property="jc_dm_bzly" caption="�����׼��Դ" valueset="���������׼��Դ" notnull="true" style="width:95%"/>
      <freeze:textarea property="jc_dm_ms" caption="��������" colspan="2" rows="4" maxlength="32700" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
