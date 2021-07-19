<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�������б�</title>
</head>

<script language="javascript">

// ����������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table.jsp", "����������", "modal" );
	page.addRecord();
}

// �޸�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002204.do", "�޸�������", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// ɾ��������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002205.do", "ɾ��������" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �����
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn80002201.do", "�����");
	page.addParameter( "record:sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record:db_name", "select-key:db_name" );
	page.updateRecord();
}

// �ֶι���
function func_record_columnRecord()
{
	var page = new pageDefine( "/txn80002501.do", "�鿴������", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�������б�"/>
<freeze:errors/>

<freeze:form action="/txn80002207">
   <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="db_name" caption="����Դ����" datatype="string" maxlength="100" style="width:39%" colspan="2"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�б�" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_viewRecord" caption="�����" txncode="80002201" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
  
      <freeze:hidden property="sys_rd_data_source_id" caption="����Դid" style="width:20%" />
      <freeze:cell property="db_name" caption="����Դ����" style="width:20%" />
      <freeze:cell property="claim_num" caption="����������" style="width:20%" />
      <freeze:cell property="table_name" caption="��������" style="width:20%" visible="false"/>
      <freeze:cell property="table_type" caption="�������ݽṹ����" style="width:20%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
