<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸Ļ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�����������' );	// /txn7000411.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000411.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ļ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000412">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="id" caption="����ֵID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ļ���������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="id" caption="����ֵID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" datatype="string" maxlength="10" readonly="true" style="width:95%"/>
      <freeze:text property="sys_rd_standard_codevalue" caption="����ֵ" datatype="string" maxlength="20" style="width:95%" notnull="true"/>
      <freeze:textarea property="sys_rd_standard_codename" caption="��������" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="description" caption="˵��" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
