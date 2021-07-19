<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�������������б�</title>
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

// ��ѯ������
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn80002201.do", "�鿴������");
	page.addParameter( "record:sys_rd_data_source_id", "primary-key:sys_rd_data_source_id" );
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
<freeze:title caption="��ѯ���ݿ������������б�"/>
<freeze:errors/>

<freeze:form action="/txn80002000">
   <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_rd_data_source_id" caption="����Դ����" valueset="ȡ����Դ"  style="width:39%" colspan="2"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�б�" keylist="sys_rd_data_source_id" width="95%" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_data_source_id" caption="����Դid" style="width:20%" />
      <freeze:cell property="@rowid" caption="���" align="center" style="width:30px;" />
      <freeze:cell property="db_name" caption="����Դ����" style="width:20%" />
      <freeze:cell property="data_object_type" caption="���ݿ��������" valueset="���ݿ��������" style="width:20%" />
      <freeze:cell property="total_num" caption="���ݿ��������" style="width:20%" />
      <freeze:cell property="unclaim_num" caption="δ��������" style="width:20%" />
      <freeze:cell property="claim_num" caption="����������" style="width:15%" />
      <freeze:cell property="table_name" caption="��������" style="width:20%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
