<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�鿴webservice������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����webservice�����' );	// /txn30102001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30102001.do
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
	
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30102010">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�鿴������Ϣ" width="95%">
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="����ID" datatype="string"  style="width:95%"/>
      <freeze:cell property="method_name_en" caption="��������" datatype="string" style="width:95%"/>
      <freeze:cell property="method_name_cn" caption="������������" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_table" caption="��Ӧ�ɼ���" show="name" valueset="��Դ����_�ɼ���"   style="width:95%"/>
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" show="name" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:hidden property="is_encryption" caption="�Ƿ����"   datatype="string" maxlength="20" style="width:95%"/>
      <freeze:cell property="encrypt_mode" caption="���ܷ���"  show="name" valueset="��Դ����_���ܷ���" value="01" style="width:95%"/>
      <freeze:cell property="method_description" caption="��������" colspan="2"  style="width:98%"/>
      <freeze:hidden property="method_status" caption="����״̬" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="�����б�" keylist="webservice_patameter_id" multiselect="false" checkbox="false" width="95%"  fixrow="false" >
      <freeze:hidden property="webservice_patameter_id" caption="����ID"  />
      <freeze:hidden property="webservice_task_id" caption="����ID"  />
      <freeze:cell property="patameter_type" caption="��������" valueset="�ɼ�����_��������" style="width:15%" />
      <freeze:cell property="patameter_name" caption="������" style="width:15%" />
      <freeze:cell property="patameter_value" caption="����ֵ"   style="width:70%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
