<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯftp�����б�</title>
</head>

<script language="javascript">

// ����ftp����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_ftp_task.jsp", "����ftp����", "modal" );
	page.addRecord();
}

// �޸�ftp����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30201004.do", "�޸�ftp����", "modal" );
	page.addParameter( "record:ftp_task_id", "primary-key:ftp_task_id" );
	page.updateRecord();
}

// ɾ��ftp����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30201005.do", "ɾ��ftp����" );
	page.addParameter( "record:ftp_task_id", "primary-key:ftp_task_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯftp�����б�"/>
<freeze:errors/>

<freeze:form action="/txn30201001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯftp�����б�" keylist="ftp_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="����ftp����" txncode="30201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�ftp����" txncode="30201004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��ftp����" txncode="30201005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="ftp_task_id" caption="FTP����ID" style="width:10%" />
      <freeze:cell property="collect_task_id" caption="�ɼ�����ID" style="width:10%" />
      <freeze:cell property="service_no" caption="������" style="width:11%" />
      <freeze:cell property="file_name_en" caption="�ļ�����" style="width:18%" />
      <freeze:cell property="file_name_cn" caption="�ļ���������" style="width:18%" />
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" style="width:11%" />
      <freeze:cell property="collect_table" caption="��Ӧ�ɼ���" style="width:11%" />
      <freeze:cell property="file_status" caption="�ļ�״̬" style="width:11%" />
      <freeze:cell property="file_description" caption="�ļ�����" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
