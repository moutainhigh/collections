<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����ά���б�</title>
</head>

<script language="javascript">

// ���Ӳ���ά��
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_webservice_patameter.jsp", "���Ӳ���ά��", "modal" );
	page.addRecord();
}

// �޸Ĳ���ά��
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30103004.do", "�޸Ĳ���ά��", "modal" );
	page.addParameter( "record:webservice_patameter_id", "primary-key:webservice_patameter_id" );
	page.updateRecord();
}

// ɾ������ά��
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30103005.do", "ɾ������ά��" );
	page.addParameter( "record:webservice_patameter_id", "primary-key:webservice_patameter_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ����ά���б�"/>
<freeze:errors/>

<freeze:form action="/txn30103001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ����ά���б�" keylist="webservice_patameter_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���Ӳ���ά��" txncode="30103003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĳ���ά��" txncode="30103004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������ά��" txncode="30103005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="webservice_patameter_id" caption="����ID" style="width:20%" />
      <freeze:cell property="webservice_task_id" caption="webservice����ID" style="width:20%" />
      <freeze:cell property="patameter_type" caption="��������" style="width:20%" />
      <freeze:cell property="patameter_name" caption="������" style="width:20%" />
      <freeze:cell property="patameter_value" caption="����ֵ" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
