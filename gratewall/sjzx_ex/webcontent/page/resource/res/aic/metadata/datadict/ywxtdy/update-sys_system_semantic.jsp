<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�ϵͳ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����ҵ�����ⶨ��' );	// /txn30401001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30401001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�ϵͳ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30401002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_id" caption="ϵͳID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ϵͳ��Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_id" caption="ϵͳID" style="width:95%"/>
      <freeze:text property="sys_name" caption="��������" datatype="string" maxlength="100" minlength="1" style="width:95%"/>
      <freeze:hidden property="sys_order" caption="����˳��" datatype="number" maxlength="5" minlength="1" style="width:95%"/>
      <freeze:textarea property="sys_simple" caption="��ע" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
