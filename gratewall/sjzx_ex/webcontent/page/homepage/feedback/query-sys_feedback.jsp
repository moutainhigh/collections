<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������б�</title>
</head>

<script language="javascript">

// �����������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_feedback.jsp", "�����������", "modal" );
	page.addRecord();
}

// �޸��������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn711004.do", "�޸��������", "modal" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.updateRecord();
}

// ɾ���������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn711005.do", "ɾ���������" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ��ӡ�б�
function func_record_printPage()
{
	var page = new pageDefine( "/txn711001.do", "��ӡ�б�", "printWindow" );
	page.printPage( 2 );
}

// �鿴����
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn711006.do", "�鿴����", "modal" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.viewRecord();
}

// �޸���־
function func_record_viewHistoryLog()
{
	var page = new pageDefine( "/txn711007.do", "��ѯ����������޸���־", "modal" );
	page.addParameter( "record:sys_feedback_id", "primary-key:sys_feedback_id" );
	page.goPage( );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn711001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ��������б�" keylist="sys_feedback_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="�����������" txncode="711003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸��������" txncode="711004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���������" txncode="711005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_printPage" caption="��ӡ�б�" txncode="711001" enablerule="0" hotkey="PRINT" align="right" onclick="func_record_printPage();"/>
      <freeze:button name="record_viewRecord" caption="�鿴����" txncode="711006" enablerule="1" hotkey="VIEW" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_viewHistoryLog" caption="�޸���־" txncode="711007" enablerule="1" align="right" onclick="func_record_viewHistoryLog();"/>
      <freeze:cell property="sys_feedback_id" caption="�������ID" style="width:19%" />
      <freeze:cell property="content" caption="�����������" style="width:20%" visible="false" />
      <freeze:cell property="publish_date" caption="����ʱ��" style="width:17%" />
      <freeze:cell property="author" caption="������" style="width:32%" />
      <freeze:cell property="status" caption="��Ч��־" style="width:10%" visible="false"/>
      <freeze:cell property="description" caption="������" style="width:32%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
