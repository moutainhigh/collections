<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�鿴�ɼ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����ɼ��������' );	// /txn20202001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn20202001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�ɼ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20202002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID" style="width:95%"/>
  </freeze:frame>

 <freeze:block property="tableinfo" caption="�ɼ����ݱ���Ϣ" width="95%">
     <freeze:cell property="service_targets_id" caption="�����������"   show="name" valueset="��Դ����_�����������"  style="width:95%"/>
     <freeze:cell property="table_name_en" caption="������"   show="name" valueset="��Դ����_�����������"  style="width:95%"/>
     <freeze:cell property="table_name_cn" caption="���ݱ�������"   style="width:95%"/>
     <freeze:cell property="table_type" caption="������"  valueset="��Դ����_������" style="width:95%"/>
     <freeze:cell property="table_desc" caption="������" colspan="2"  style="width:98%"/>
  </freeze:block>
   <br/>
  <freeze:block property="record" caption="�ɼ���������Ϣ" width="95%">
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="dataitem_name_en" caption="����������"   style="width:95%"/>
      <freeze:cell property="dataitem_name_cn" caption="��������"  style="width:95%"/>
      <freeze:cell property="dataitem_type" caption="����������"  show="name" valueset="��Դ����_����������" style="width:95%"/>
      <freeze:cell property="dataitem_long" caption="�������"  style="width:95%"/>
      <freeze:cell property="is_key" caption="�Ƿ�����" show="name" valueset="��Դ����_�Ƿ�����" style="width:95%"/>
      <freeze:cell property="code_table" caption="��Ӧ�����" show="name" valueset="��Դ����_��Ӧ�����" style="width:95%"/>
      <freeze:hidden property="dataitem_long_desc" caption="����������" colspan="2"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
