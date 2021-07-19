<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ����ݿ��б�</title>
</head>

<script language="javascript">

// ���Ӳɼ����ݿ�
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_database_task.jsp", "���Ӳɼ����ݿ�", "modal" );
	page.addRecord();
}

// �޸Ĳɼ����ݿ�
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30501004.do", "�޸Ĳɼ����ݿ�", "modal" );
	page.addParameter( "record:database_task_id", "primary-key:database_task_id" );
	page.updateRecord();
}

// ɾ���ɼ����ݿ�
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30501005.do", "ɾ���ɼ����ݿ�" );
	page.addParameter( "record:database_task_id", "primary-key:database_task_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�ɼ����ݿ��б�"/>
<freeze:errors/>

<freeze:form action="/txn30501001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ�ɼ����ݿ��б�" keylist="database_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���Ӳɼ����ݿ�" txncode="30501003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĳɼ����ݿ�" txncode="30501004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���ɼ����ݿ�" txncode="30501005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="database_task_id" caption="�ɼ����ݿ�����ID" style="width:50%" />
      <freeze:cell property="collect_task_id" caption="�ɼ�����ID" style="width:50%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
