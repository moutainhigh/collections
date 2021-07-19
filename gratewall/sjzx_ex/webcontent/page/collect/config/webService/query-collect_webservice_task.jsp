<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯwebservice�����б�</title>
</head>

<script language="javascript">

// ����webservice����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_webservice_task.jsp", "����webservice����", "modal" );
	page.addRecord();
}

// �޸�webservice����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30102004.do", "�޸�webservice����", "modal" );
	page.addParameter( "record:webservice_task_id", "primary-key:webservice_task_id" );
	page.updateRecord();
}

// ɾ��webservice����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30102005.do", "ɾ��webservice����" );
	page.addParameter( "record:webservice_task_id", "primary-key:webservice_task_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯwebservice�����б�"/>
<freeze:errors/>

<freeze:form action="/txn30102001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯwebservice�����б�" keylist="webservice_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="����webservice����" txncode="30102003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�webservice����" txncode="30102004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��webservice����" txncode="30102005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="webservice_task_id" caption="WEBSERVICE����ID" style="width:12%" />
      <freeze:cell property="collect_task_id" caption="�ɼ�����ID" style="width:12%" />
      <freeze:cell property="service_no" caption="������" style="width:12%" />
      <freeze:cell property="method_name_en" caption="��������" style="width:20%" />
      <freeze:cell property="method_name_cn" caption="������������" style="width:20%" />
      <freeze:cell property="collect_table" caption="��Ӧ�ɼ���" style="width:12%" />
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" style="width:12%" />
      <freeze:cell property="is_encryption" caption="�Ƿ����" style="width:12%" />
      <freeze:cell property="encrypt_mode" caption="���ܷ�ʽ" style="width:12%" />
      <freeze:cell property="method_description" caption="��������" style="width:20%" visible="false" />
      <freeze:cell property="method_status" caption="����״̬" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
