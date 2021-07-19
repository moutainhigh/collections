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

// ����δ�����ҳ��
function go_unclaim_table()
{
	var page = new pageDefine( "/txn80002101.do", "�鿴δ����", "modal" );
	page.goPage();
}

// �����������ҳ��
function go_claim_table()
{
	var page = new pageDefine( "/txn80002201.do", "�����", "modal" );
	page.goPage();
}

// ����δ������ͼҳ��
function go_unclaim_view()
{
	var page = new pageDefine( "/txn80003101.do", "�鿴δ����ͼ", "modal" );
	page.goPage();
}

// ������������ͼҳ��
function go_claim_view()
{
	var page = new pageDefine( "/txn80003201.do", "��ͼ����", "modal" );
	page.goPage();
}


// ����δ���캯��ҳ��
function go_unclaim_function()
{
	var page = new pageDefine( "/txn80004101.do", "�鿴δ�Ϻ���", "modal" );
	page.goPage();
}

// ���������캯��ҳ��
function go_claim_function()
{
	var page = new pageDefine( "/txn80004201.do", "��������", "modal" );
	page.goPage();
}

// ����δ����洢����ҳ��
function go_unclaim_procedure()
{
	var page = new pageDefine( "/txn80005101.do", "�鿴δ����洢����", "modal" );
	page.goPage();
}

// ����������洢����ҳ��
function go_claim_procedure()
{
	var page = new pageDefine( "/txn80005201.do", "�洢���̹���", "modal" );
	page.goPage();
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var unclaimNums = document.getElementsByName("span_record:unclaim_num");
	var claimNums = document.getElementsByName("span_record:claim_num");
	var unclaimValues = getFormAllFieldValues("record:unclaim_num");
	var claimValues = getFormAllFieldValues("record:claim_num");
	var dataObjectNames = getFormAllFieldValues("record:data_object_type");
	
	for (var i=0; i < unclaimValues.length; i++){
		
		if(dataObjectNames[i]=="���ݱ�"){
			unclaimNums[i].innerHTML = "<a onclick='go_unclaim_table()' href='#'>"+unclaimValues[i]+"</a>";
			claimNums[i].innerHTML = "<a onclick='go_claim_table()' href='#'>"+claimValues[i]+"</a>";
		}
		
		if(dataObjectNames[i]=="��ͼ"){
			unclaimNums[i].innerHTML = "<a onclick='go_unclaim_view()' href='#'>"+unclaimValues[i]+"</a>";
			claimNums[i].innerHTML = "<a onclick='go_claim_view()' href='#'>"+claimValues[i]+"</a>";
		}
		
		if(dataObjectNames[i]=="����"){
			unclaimNums[i].innerHTML = "<a onclick='go_unclaim_function()' href='#'>"+unclaimValues[i]+"</a>";
			claimNums[i].innerHTML = "<a onclick='go_claim_function()' href='#'>"+claimValues[i]+"</a>";
		}
		
		if(dataObjectNames[i]=="�洢����"){
			unclaimNums[i].innerHTML = "<a onclick='go_unclaim_procedure()' href='#'>"+unclaimValues[i]+"</a>";
			claimNums[i].innerHTML = "<a onclick='go_claim_procedure()' href='#'>"+claimValues[i]+"</a>";
		}
		
	}	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ݿ������������б�"/>
<freeze:errors/>

<freeze:form action="/txn80002001">
   <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_rd_data_source_id" caption="����Դ����" valueset="ȡ����Դ"  style="width:39%" colspan="2"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�б�" keylist="sys_rd_data_source_id" width="95%" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_data_source_id" caption="����Դid" style="width:20%" />
      <freeze:cell property="@rowid" caption="���" align="center" style="width:5%" />
      <freeze:cell property="db_name" caption="����Դ����" style="width:20%" />
      <freeze:cell property="data_object_type" caption="���ݿ��������" valueset="���ݿ��������" style="width:20%" />
      <freeze:cell property="total_num" caption="���ݿ��������" style="width:20%" />
      <freeze:cell property="unclaim_num" caption="δ��������" style="width:20%" />
      <freeze:cell property="claim_num" caption="����������" style="width:20%" />
      <freeze:cell property="table_name" caption="��������" style="width:20%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
