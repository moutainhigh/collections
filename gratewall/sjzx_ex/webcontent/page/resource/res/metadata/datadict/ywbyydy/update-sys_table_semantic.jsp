<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�ҵ�����Ϣ</title>
</head>

<script language="javascript">
function getParameter()
{
	var parameter = 'txncode='+_top.menu.selectedNode.id+'_30402003';
	return parameter;
}
// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����ҵ�����' );	// /txn30402001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30402001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	document.getElementById('label:record:downloadflag').innerHTML='�Ƿ�����أ�';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�ҵ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30402002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="table_no" caption="ҵ������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ҵ�����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="table_no" caption="ҵ������" datatype="string" style="width:95%"/>
      <freeze:select property="sys_id" caption="ҵ������" valueset="ҵ��ϵͳ" notnull="true" style="width:95%" show="name" data="code"/>
      <freeze:hidden property="table_order" caption="����˳��" datatype="number" maxlength="5" minlength="1" style="width:95%"/>
      <freeze:text property="table_name" caption="ҵ�����" datatype="string" maxlength="60" minlength="1" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="ҵ���������" datatype="string" maxlength="100" minlength="1" style="width:95%"/>
      <freeze:radio property="downloadflag" caption="�Ƿ������" valueset="��������" notnull="true" style="width:95%" show="name" data="code"/>
      <freeze:textarea property="demo" caption="��ע" colspan="2" rows="4" maxlength="1000" style="width:98%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
